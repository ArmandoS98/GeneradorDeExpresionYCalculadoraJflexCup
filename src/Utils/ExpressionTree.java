/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.Stack;
import javax.swing.JTextArea;

/**
 * DESARROLLADO POR Kevyn Salvador Posadas Tote - 201901858 Brodner Morales -
 * 201902589 David Alfredo Díaz Veras - 201901175 Armando Emmanuel Santos Coy -
 * 201900674 Como proyecto para la clase de compiladores de la Universidad Da
 * Vinci de Guatemala Año 2021
 */
public class ExpressionTree {
    //Verificar si el caracter es un operador

    public static boolean isOperator(String c) {
        return c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/") || c.equals("^");
    }

    //Verificar si el caracter es un operador
    static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    public static void inorder(Node t, int pos) {
        if (t != null) {
            inorder(t.left, 0);
            String a = t.value;
            System.out.print(a + " " /*" (" + ((pos == 0) ? "Izquierda" : "Derecha") + ")"*/);
            inorder(t.right, 1);
        }

    }

    public static void preorder(Node t, int pos) {
        if (t != null) {
            String a = t.value;
            System.out.print(a + " " /*" (" + ((pos == 0) ? "Izquierda" : "Derecha") + ")"*/);
            inorder(t.left, 0);
            inorder(t.right, 1);
        }

    }

    public static void postorder(Node t, int pos) {
        if (t != null) {
            inorder(t.left, 0);
            inorder(t.right, 1);
            String a = t.value;
            System.out.print(a + " " /*" (" + ((pos == 0) ? "Izquierda" : "Derecha") + ")"*/);
        }

    }

    public Stack<Node> invierte(Stack<Node> p) {
        Stack<Node> nueva = new Stack<>();
        while (!p.isEmpty()) {
            nueva.push(p.pop());
        }
        return nueva;
    }

    // Retorna el nodo raiz de la expresion dada
    public static Node constructTree(String[] postfix, JTextArea visualizar, String postfija, String infija, String prefija) {
        String msg = "";
        Stack<Node> st = new Stack<>();
        Node t; //Final
        Node t1, t2; //Temporales

        for (String postfix1 : postfix) {
            if (postfix1 != null) {
                // Digitos o Literales
                if (!isOperator(postfix1)) {
                    t = new Node(postfix1);
                    st.push(t);
                } else // Operadores
                {
                    t = new Node(postfix1);
                    String raiz = t.value;
                    msg += "Raiz " + raiz + "\n";
                    // Le hacemos Pop a los 2 primes nodos que esten en el top de la stack
                    t1 = st.pop(); //Se retira el primero
                    t2 = st.pop(); //Se retira el segundo
                    msg += "Nodo " + t1.value + " a la derecha de " + raiz + "\n";
                    msg += "Nodo " + t2.value + " a la izquierda de " + raiz + "\n";
                    //creamos los nodos hijos
                    t.right = t1;
                    t.left = t2;
                    st.push(t);
                }
            }
        }

        t = st.peek();
        msg += "ROOT : " + t.value + "\n";
        visualizar.setText(msg);
        st.pop();

        // guardar en archivo 31/03/2021
        GuardarFichero mFichero = new GuardarFichero(msg, postfija, infija, prefija);
        mFichero.save();
        return t;
    }

    public static Node_v2 constructTree(char postfix[], JTextArea visualizar, String postfija, String infija) {
        Stack<Node_v2> st = new Stack<>();
        Node_v2 t; //Final
        Node_v2 t1, t2; //Temporales

        for (int i = 0; i < postfix.length; i++) {

            // If operand, simply push into stack
            if (!isOperator(postfix[i])) {
                t = new Node_v2(postfix[i]);
                st.push(t);
            } else // operator
            {
                t = new Node_v2(postfix[i]);
                char raiz = t.value;
                System.out.println("Raiz -> " + raiz);

                // Le hacemos Pop a los 2 primes nodos que esten en el top de la stack
                t1 = st.pop(); //Se retira el primero
                t2 = st.pop(); //Se retira el segundo

                System.out.println("Nodo " + t1.value + " a la derecha de " + raiz);
                System.out.println("Nodo " + t2.value + " a la izquierda de " + raiz);
                //creamos los nodos hijos
                t.right = t1;
                t.left = t2;

                //System.out.println(t1.value + " esp " + t2.value);
                // Se agrega este nuevo nodo a al stack (Sub exprecion)
                st.push(t);
            }
        }

        //  only element will be root of expression
        // tree
        t = st.peek();
        System.out.println("ROOT : " + t.value);
        st.pop();

        return t;
    }
}
