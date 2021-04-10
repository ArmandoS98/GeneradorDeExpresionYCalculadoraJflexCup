/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Armando Santos
 */
public class GenerarExpresion {

    /**
     * función para devolver la precedencia de un operador dado
     *
     * @param ch
     * @return
     */
    public static int Prec(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    /**
     * Version COn String
     *
     * @param ch
     * @return
     */
    public static int Prec(String ch) {
        switch (ch) {
            case "+":
            case "-":
                return 1;

            case "*":
            case "/":
                return 2;

            case "^":
                return 3;
        }
        return -1;
    }

    public static String[] validarSiContiene(String ch) {
        String[] signos = new String[]{"+", "-", "*", "/", "^"};
        String[] temp;
        String nuevo = ch;
        String tm = "";
        for (String signo : signos) {
            if (nuevo.contains(signo)) {
                String detectar = ((signo.equals("+") || signo.equals("*")) ? "\\" : "") + signo;
                String[] maiz = nuevo.split(detectar);

                tm += maiz[0] + ",";
                tm += signo + ",";

                nuevo = maiz[1];
            }
        }
        tm += nuevo;
        temp = tm.split(",");
        return temp;
    }

    public static String[] validarSiContieneV2(String ch) {

        String temp = "";

        //305 + 12 / 1 - z - 5.9
        String input = ch;
        //Pattern pattern = Pattern.compile("-?[0-9.]+|[A-Za-z]+|[\\+\\*-/()]|==|<=|>=|&&|[|]{2}");
        Pattern pattern = Pattern.compile("[0-9.]+|[A-Za-z]+|[\\+\\*-^/()]|==|<=|>=|&&|[|]{2}");
        Matcher match = pattern.matcher(input);

        while (match.find()) {
            temp += match.group() + ",";
        }
        //System.out.println(actual);
        return temp.split(",");
    }

    /**
     * Método principal que convierte infix expression a postfix expression.
     * CHAR
     *
     * @param exp
     * @return
     */
    public static String infixToPostfix(String exp) {
        String result = "";

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < exp.length(); ++i) {
            char c = exp.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                result += c;
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result += stack.pop();
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek())) {
                    result += stack.pop();
                }
                stack.push(c);
            }

        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                return "Invalid Expression";
            }
            result += stack.pop();
        }
        return result;
    }

    /**
     * Metodo para detectar cualquier dato STRING
     *
     * @param exp
     * @return
     */
    public static PostfixPojo infixToPostfixV2(String exp) {
        String result = "";
        PostfixPojo mPojo = new PostfixPojo();
        // Inicializamos la stack
        Stack<String> stack = new Stack<>();

        String[] limit = validarSiContieneV2(exp);
        String[] res = new String[limit.length];
        try {

            int posi = 0;

            for (int i = 0; i < limit.length; ++i) {
                String c = limit[i];

                //Escanear es dugito o letra lo agregar al resultado
                if (isLetterOrDigit(c)) {
                    result += c + " ";
                    res[posi] = c;
                    posi++;
                } //Si encontramos un "(" lo agregamos en la stack
                else if (c.equals("(")) {
                    stack.push(c);
                } //Si encontramos un ")", le hacemos un pop a las stack, siempre y cuando el ultimo dato de la stack no sea "("
                else if (c.equals(")")) {
                    while (!stack.isEmpty() && !stack.peek().equals("(")) {
                        res[posi] = stack.peek();
                        posi++;
                        result += stack.pop() + " ";
                    }
                    stack.pop();
                } else //Si encontramos un operador
                {
                    while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek())) {
                        res[posi] = stack.peek();
                        posi++;
                        result += stack.pop() + " ";
                    }
                    stack.push(c);
                }

            }
            //Realizamos el pop para todos los operadores en la Stack
            while (!stack.isEmpty()) {
                if (stack.peek().equals("(")) {
                    mPojo.setExpresion("Invalid Expression");
                    mPojo.setExpresionArray(res);
                    return mPojo;
                }

                res[posi] = stack.peek();
                posi++;
                result += stack.pop() + " ";

            }
            mPojo.setExpresion(result);
            mPojo.setExpresionArray(res);
            return mPojo;
        } catch (Exception ex) {
            mPojo.setExpresion("Expresion invalida");
            mPojo.setExpresionArray(null);
            return mPojo;
        }

    }

    // Reverse the letters of the word
    static String[] reverse(String str[], int start, int end) {

        // Temporary variable to store character
        String temp;
        while (start < end) {
            // Swapping the first and last character
            temp = str[start];
            str[start] = str[end];
            str[end] = temp;
            start++;
            end--;
        }
        return str;
    }

    static String[] reverseString(String myArray[]) {
        System.out.println("Reversed String Array1 : " + Arrays.asList(myArray));
        Collections.reverse(Arrays.asList(myArray));
        System.out.println("Reversed String Array : " + Arrays.asList(myArray));
        String[] maiz = new String[myArray.length];
        maiz = Arrays.asList(myArray).toArray(maiz);

        return maiz;
    }

    public static String infixToPrefix(String infix) {

        String preis = "";
        String[] limit = validarSiContieneV2(infix);

        int l = limit.length;
        limit = reverseString(limit);

        try {
            for (int i = 0; i < l; i++) {
                if (limit[i].equals("(")) {
                    limit[i] = ")";
                    //i++;
                } else if (limit[i].equals(")")) {
                    limit[i] = "(";
                    //i++;
                }
            }

            System.out.println("Reversed String Array : " + Arrays.asList(limit));

            for (String string : limit) {
                if (string != null) {
                    preis += string;
                }
            }

            PostfixPojo prefix = infixToPostfixV2(preis);
            l = prefix.getExpresionArray().length;
            String[] prefix1 = reverse(prefix.getExpresionArray(), 0, l - 1);

            preis = "";
            for (String string : prefix1) {
                if (string != null) {
                    preis += string + " ";
                }
            }
            return preis;
        } catch (Exception ex) {
            return "Expresion invalida";
        }

    }

    private static boolean isLetterOrDigit(String cadena) {
        StringBuilder ret = new StringBuilder();
        Matcher matches = Pattern.compile("[A-Za-z0-9]+").matcher(cadena);

        while (matches.find()) {
            ret.append(matches.group());
        }

        return !ret.toString().equals("");
    }
}
