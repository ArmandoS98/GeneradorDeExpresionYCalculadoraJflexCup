/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextArea;

/**
 *
 * @author Armando Santos
 * @author Armando Santos
 * @author Armando Santos
 * @author Armando Santos
 */
public class GenerarExpresion {

    /**
     * FUNCIONAMIENTO CON CHAR: Validamos la presedencia de los signos
     * encontrados
     *
     * @param ch representa el signo que se esta leyendo actualmente
     * @return Retorna un valor entero, con la presendcia del signo
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
     * FUNCIONAMIENTO CON STRING: Validamos la presedencia de los signos
     * encontrados
     *
     * @param ch representa el signo que se esta leyendo actualmente
     * @return Retorna un valor entero, con la presendcia del signo
     */
    public static int Prec(String ch) {
        switch (ch) {
            case "+":
            case "-":
                return 1;

            case "*":
            case "/":
            case "%":
                return 2;

            case "^":
                return 3;
        }
        return -1;
    }

    /**
     * SIN EXPRESION REGULAR: Metodo que ayuda a separa la expresion ingresada
     * por el usuario a un array de datos que servira para recorrer la misma
     *
     * @param ch es la expresion infija ingresada por el usuario
     * @return Retorna un Array de datos que servira para recorrer en la
     * notacion postfija
     */
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

    /**
     * CON EXPRESION REGULAR: Metodo que ayuda a separa la expresion ingresada
     * por el usuario a un array de datos que servira para recorrer la misma
     *
     * @param ch es la expresion infija ingresada por el usuario
     * @return Retorna un Array de datos que servira para recorrer en la
     * notacion postfija
     */
    public static String[] validarSiContieneV2(String ch) {

        String temp = "";

        //305 + 12 / 1 - z - 5.9
        String input = ch.replace(" ", "");
        //Pattern pattern = Pattern.compile("-?[0-9.]+|[A-Za-z]+|[\\+\\*-/()]|==|<=|>=|&&|[|]{2}");

        /**
         * 26 - 04 - 2021 Cambio de la expresion para que devolvienta los
         * valores correcto sin tener que valuar los signos DE
         * [0-9.]+|[A-Za-z]+|[\\+\\*-^/()] A [0-9.]+|[A-Za-z]+|(.) Referencias
         * https://stackoverflow.com/questions/39034150/get-all-not-matching-characters-from-a-regex
         * Testeo de las expresionces regulares Demo Live
         * https://regex101.com/r/bicrhA/1
         *
         */
        Pattern pattern = Pattern.compile("[0-9.]+|[A-Za-z]+|(.)");
        Matcher match = pattern.matcher(input);

        while (match.find()) {
            temp += match.group() + ",";
        }

        System.out.println(temp);
        return temp.split(",");
    }

    /**
     * CHAR: Metodo para generar la Expresion POSTFIJA
     *
     * @param exp = notacion infija ingresada por el usuario
     * @return = Retor un String donde esta contenida la notacion postfija
     */
    public static String infixToPostfix(String exp) {
        String result = "";

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < exp.length(); ++i) {
            char c = exp.charAt(i); //

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
     * STRING: Metodo para generar la Expresion POSTFIJA
     *
     * @param exp = notacion infija ingresada por el usuario
     * @return = Retor un Objeto donde esta contenida la notacion postfija asi
     * como un array de la misma
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

                // 10 + 2 - z
                //0 + 2 - z
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
                        System.out.println("Holis, si viste esto  que observador ");
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

            //Calculamos el resulado de la expresion postfix
            //Referencia = https://www.oscarblancarteblog.com/2017/06/21/evaluador-expresiones-postfijas-codigo-fuente/
            mPojo.setExpresion(result);
            mPojo.setExpresionArray(res);
            return mPojo;
        } catch (Exception ex) {
            mPojo.setExpresion("Expresion invalida");
            mPojo.setExpresionArray(null);
            return mPojo;
        }

    }

    /**
     * Metodo con el cual se genera la notacion Prefija
     *
     * @param infix = notacion infija ingresada por el usuario
     * @return = Retorna un String donde esta contenida la expresion Prefija.
     */
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

    /**
     * Metodo para reversionar la notacion postfija, la cual se usa para genrar
     * la notacion Prefija
     *
     * @param str = Notacion Postfija genrada
     * @param start = Inicio de la lectura
     * @param end = Fin de la lectura
     * @return = Un array de String en la cual esta contenida la notacion
     * Prejija
     */
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

    /**
     * Metodo para reversionar la expresion infija ingresada por el usuario
     *
     * @param myArray = Array de elementos de la notacion infija
     * @return = Array de los elementos reversionados de la notacion infija.
     */
    static String[] reverseString(String myArray[]) {
        System.out.println("Reversed String Array1 : " + Arrays.asList(myArray));
        Collections.reverse(Arrays.asList(myArray));
        System.out.println("Reversed String Array : " + Arrays.asList(myArray));
        String[] maiz = new String[myArray.length];
        maiz = Arrays.asList(myArray).toArray(maiz);

        return maiz;
    }

    /**
     * Metodo utilizado para validar si el carater leido es un NUMERO o CARACTER
     *
     * @param cadena = Caracter leido actualmente
     * @return = Verdadero(Si es NUMERO o DIGITO), Falso(Si es cualquier otro
     * signo)
     */
    public static boolean isLetterOrDigit(String cadena) {
        StringBuilder ret = new StringBuilder();
        Matcher matches = Pattern.compile("[A-Za-z0-9]+").matcher(cadena);

        while (matches.find()) {
            ret.append(matches.group());
        }

        return !ret.toString().equals("");
    }

    /**
     * Calculamos el valor de la expresion que el usuario ingreso
     *
     * @param postfix = Array de String donde esta contenida la sentancia
     * postfix
     * @param lblError = JTextArea donde se desplegaran los errores
     * @return = Retoranara un valor tipo entero de la expresion ingresada por
     * el usuario
     */
    public static String valuarExpresion(String[] postfix, JTextArea lblError) {
        ArrayList<String> token = new ArrayList<>();
        String _postfixs = "";
        boolean tipoDeCasteo = false;

        for (String val : postfix) {
            if (val != null && !val.isEmpty()) {
                _postfixs += val + ",";
            }
        }

        int longitud = _postfixs.length();
        String _postfix = _postfixs.substring(0, (longitud - 1));

        StringTokenizer st = new StringTokenizer(_postfix, ",");
        while (st.hasMoreTokens()) {
            token.add(st.nextToken());
        }

        if (token.size() == 1) {
            try {
                return String.valueOf(Integer.parseInt(token.get(0)));
            } catch (NumberFormatException e) {
                lblError.setText("No se pudo valuar la expresion");
                return "0";
            }

        }
        int c = 0;
        int bandera = 0;

        lblError.setText(token.toString() + "\n");
        while (token.size() != 1) {

            String operador = token.get(c);

            if (!operador.equals(null) && !operador.isEmpty()) {
                if (operador.equals("+") || operador.equals("-") || operador.equals("*") || operador.equals("/") || operador.equals("^") || operador.equals("%")) {
                    String operando1 = token.get(c - 1);
                    String operando2 = token.get(c - 2);

                    //Creo un if donde valido si contiene algun double
                    if (!tipoDeCasteo) {
                        boolean hayDecimales = operando1.contains(".") || operando2.contains(".");
                        tipoDeCasteo = hayDecimales;
                    }

                    token.remove(c);
                    token.remove(c - 1);
                    token.remove(c - 2);
                    if (operador.equals("+")) {
                        try {
                            String suma = tipoCasteoOperacion(operador, operando2, operando1, tipoDeCasteo);
                            token.add(c - 2, suma);
                            c = 0;
                        } catch (NumberFormatException e) {
                            lblError.setText("Parser error\n" + e);
                            return "0";
                        }
                    } else if (operador.equals("-")) {
                        try {
                            String resta = tipoCasteoOperacion(operador, operando2, operando1, tipoDeCasteo);
                            token.add(c - 2, resta);
                            c = 0;
                        } catch (NumberFormatException e) {
                            lblError.setText("Parser error\n" + e);
                            return "0";
                        }
                    } else if (operador.equals("*")) {
                        try {
                            String multiplicacion = tipoCasteoOperacion(operador, operando2, operando1, tipoDeCasteo);
                            token.add(c - 2, multiplicacion);
                            c = 0;
                        } catch (NumberFormatException e) {
                            lblError.setText("Parser error\n" + e);
                            return "0";
                        }
                    } else if (operador.equals("/")) {
                        try {
                            String divicion = tipoCasteoOperacion(operador, operando2, operando1, tipoDeCasteo);
                            token.add(c - 2, divicion);
                            c = 0;
                        } catch (NumberFormatException e) {
                            lblError.setText("Parser error\n" + e);
                            return "0";
                        }
                    } else if (operador.equals("%")) {
                        try {
                            String mod = tipoCasteoOperacion(operador, operando2, operando1, tipoDeCasteo);
                            token.add(c - 2, mod);
                            c = 0;
                        } catch (NumberFormatException e) {
                            lblError.setText("Parser error\n" + e);
                            return "0";
                        }
                    } else {
                        try {
                            String potencia = tipoCasteoOperacion(operador, operando2, operando1, tipoDeCasteo);;
                            token.add(c - 2, potencia);
                            c = 0;
                        } catch (NumberFormatException e) {
                            lblError.setText("Parser error\n" + e);
                            return "0";
                        }
                    }
                } else {
                    c++;
                }
            }

        }

        try {
            Object resul = 0;
            if (tipoDeCasteo) {
                resul = Double.parseDouble(token.get(0));
            } else {
                resul = Integer.parseInt(token.get(0));
            }

            return String.valueOf(resul);
        } catch (NumberFormatException e) {
            lblError.setText("Error al parsear el resultado\n" + e);
            return "0";
        }

    }

    /**
     * Metodo donde valuamos la cantidad de parentencias indicando que para que
     * todo este correcto tiene que ver un numero par de parentecias de lo
     * contrario le retornamos un error al usuario
     *
     * @param operacion = pasamos la expresion infija como parametro
     * @param lblError = JTextArea donde visualizamos la consola del proyecto
     * @return = Verdadero(Numero pares de parentecis), Falso(Numero impar)
     */
    public static boolean validarParentesis(String operacion, JTextArea lblError) {
        Stack<Character> pila = new Stack<Character>();

        char[] ecuacion = operacion.toCharArray();
        for (int c = 0; c < ecuacion.length; c++) {
            char caracter = ecuacion[c];
            if (caracter == '(') {
                pila.push(caracter);
            } else if (caracter == ')') {
                if (pila.empty()) {
                    String salida = generarError(operacion, c);
                    salida += "Numero de parentesis impares";
                    lblError.setText(salida);
                    return false;
                } else {
                    pila.pop();
                }
            }
        }
        if (!pila.empty()) {
            String salida = generarError(operacion, ecuacion.length - 1);
            salida += "Numero de parentesis impares,Se esperaba que se cerrara los parentesis";
            lblError.setText(salida);
            return false;

        }
        return true;
    }

    /**
     * Metodo que se asegura que este correctamente planteda la exprecion
     *
     * @param infijo es la exprecion matematica a evaluar
     * @param lblError
     * @return devuleve un true si la exprecion esta alternada correctamente,
     * false de lo contrario
     */
    public static boolean evaluarAlternaciones(String infijo, JTextArea lblError) {

        String[] cadena = validarSiContieneV2(infijo);
        String ultimoElemento = cadena[0];
        StringBuilder areaTexto = new StringBuilder();

        //Se da por entendido que todas las expreciones son correctas hasta que se encuentre un error
        boolean validacion = true;

        for (int c = 1; c < cadena.length; c++) {
            String caracter = cadena[c];
            if (isLetterOrDigit(caracter)) {
                if (ultimoElemento.equals(")")) {
                    String salida = generarError(infijo, c);
                    salida += "No se puede poner un numero despues de un ')'\n";
                    areaTexto.append(salida);
                    validacion = false;
                } else {
                    ultimoElemento = caracter;
                }
            } else if (caracter.equals("(")) {
                if (ultimoElemento.equals("(")) {
                    ultimoElemento = caracter;
                } else if (ultimoElemento.equals("+") || ultimoElemento.equals("-") || ultimoElemento.equals("*") || ultimoElemento.equals("/") || ultimoElemento.equals("^")) {
                    ultimoElemento = caracter;
                } else {
                    String salida = generarError(infijo, c);
                    salida += "Despues de un '" + ultimoElemento + "' no puede venir un '(' \n";
                    areaTexto.append(salida);
                    validacion = false;
                }
            } else if (caracter.equals(")")) {
                if (ultimoElemento.equals(")")) {
                    ultimoElemento = caracter;
                } else if (isLetterOrDigit(ultimoElemento)) {
                    ultimoElemento = caracter;
                } else {
                    String salida = generarError(infijo, c);
                    salida += "Despues de un '" + ultimoElemento + "' no puede venir un ')' \n";
                    areaTexto.append(salida);
                    validacion = false;
                }
            } else if (caracter.equals("+") || caracter.equals("-") || caracter.equals("*") || caracter.equals("/") || caracter.equals("^") || caracter.equals("%")) {
                if (ultimoElemento.equals(")")) {
                    ultimoElemento = caracter;
                } else if (isLetterOrDigit(ultimoElemento)) {
                    ultimoElemento = caracter;
                } else {
                    String salida = generarError(infijo, c);
                    salida += "Despues de un '" + ultimoElemento + "' no puede venir un Operador '" + caracter + "' \n";
                    areaTexto.append(salida);
                    validacion = false;
                }
            } else {
                String salida = generarError(infijo, c);
                salida += "Despues de un '" + ultimoElemento + "' sigue  simbolo no reconocido '" + caracter + "'\n";
                areaTexto.append(salida);
                validacion = false;
            }
        }

        lblError.setText(areaTexto.toString());
        return validacion;
    }

    /**
     * Metodo que nos indica si la exprecion termina con un operador
     *
     * @param infijo = expresion infijo introducida por el usurio
     * @param lblError = retorna true si empiesa con un operador, false de lo
     * contrario
     * @return
     */
    public static boolean terminaConOperador(String infijo, JTextArea lblError) {
        char[] cadena = infijo.toCharArray();
        for (int c = cadena.length - 1; c > 0; c--) {
            char caracter = cadena[c];
            if (Character.isDigit(caracter)) {
                return false;
            }
            if (Character.isLetter(caracter)) {
                return false;
            } else if (caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/' || caracter == '^') {
                String salida = generarError(infijo, c);
                salida += "La exprecion no puede terminar con operador";
                lblError.setText(salida);
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo que nos sirve para saber si la exprecion empiesa con un operador
     *
     * @param infijo = expresion infijo introducida por el usurio
     * @param lblError = retorna true si empiesa con un operador, false de lo
     * contrario
     * @return
     */
    public static boolean empiesaConOperador(String infijo, JTextArea lblError) {
        char[] cadena = infijo.toCharArray();
        for (int c = 0; c < cadena.length; c++) {
            char caracter = cadena[c];
            if (Character.isDigit(caracter)) {
                return false;
            }
            if (Character.isLetter(caracter)) {
                return false;
            } else if (caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/' || caracter == '^') {
                String salida = generarError(infijo, c);
                salida += "La exprecion nu puede empesar con operador";
                lblError.setText(salida);
                return true;
            }
        }
        return false;
    }

    /**
     * Visualizacion enl a consola dle error que se esta produciendo
     *
     * @param infijo
     * @param indice
     * @return
     */
    private static String generarError(String infijo, int indice) {
        String error[] = new String[infijo.length()];
        for (int c = 0; c < error.length; c++) {
            error[c] = "  ";
        }
        error[indice] = "^";

        String error2 = "";
        for (int c = 0; c < error.length; c++) {
            error2 += error[c];
        }
        return infijo + "\n" + error2 + "\n";
    }

    private static String tipoCasteoOperacion(String operador, String operando2, String operando1, boolean tipoDeCasteo) {
        int valor = 0;
        double valor_d = 0.0;

        System.out.println("operador: " + tipoDeCasteo);
        switch (operador) {
            case "+":
                if (tipoDeCasteo) {
                    valor_d = (Double.parseDouble(operando2) + Double.parseDouble(operando1));
                } else {
                    valor = (Integer.parseInt(operando2) + Integer.parseInt(operando1));
                }
                break;
            case "-":
                if (tipoDeCasteo) {
                    valor_d = Double.parseDouble(operando2) - Double.parseDouble(operando1);
                } else {
                    valor = (Integer.parseInt(operando2) - Integer.parseInt(operando1));
                }
                break;
            case "*":
                if (tipoDeCasteo) {
                    valor_d = Double.parseDouble(operando2) * Double.parseDouble(operando1);
                } else {
                    valor = (Integer.parseInt(operando2) * Integer.parseInt(operando1));
                }
                break;
            case "/":
                if (tipoDeCasteo) {
                    valor_d = Double.parseDouble(operando2) / Double.parseDouble(operando1);
                } else {
                    valor = (Integer.parseInt(operando2) / Integer.parseInt(operando1));
                }
                break;
            case "^":
                if (tipoDeCasteo) {
                    valor_d = Math.pow(Double.parseDouble(operando2), Double.parseDouble(operando1));
                } else {
                    valor = (int) Math.pow(Integer.parseInt(operando2), Integer.parseInt(operando1));
                }
                break;
            case "%":
                if (tipoDeCasteo) {
                    valor_d = (Double.parseDouble(operando2) % Double.parseDouble(operando1));
                } else {
                    valor = (Integer.parseInt(operando2) % Integer.parseInt(operando1));
                }
                break;
        }

        String res;
        if (tipoDeCasteo) {
            res = String.valueOf(valor_d);
        } else {
            res = String.valueOf(valor);
        }

        return res;
    }
}
