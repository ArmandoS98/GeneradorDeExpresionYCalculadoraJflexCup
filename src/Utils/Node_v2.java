/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 * @author Tote
 * @author Brodner
 * @author David
 * @author Armando Santos
 */
public class Node_v2 {

    char value;
    Node_v2 left, right;

    Node_v2(char item) {
        value = item;
        left = right = null;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public Node_v2 getLeft() {
        return left;
    }

    public void setLeft(Node_v2 left) {
        this.left = left;
    }

    public Node_v2 getRight() {
        return right;
    }

    public void setRight(Node_v2 right) {
        this.right = right;
    }
}
