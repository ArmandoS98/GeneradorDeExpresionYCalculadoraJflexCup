/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author Armando Santos
 */
public class PostfixPojo {

    private String expresion;
    private String[] expresionArray;

    public PostfixPojo() {
    }

    public PostfixPojo(String expresion, String[] expresionArray) {
        this.expresion = expresion;
        this.expresionArray = expresionArray;
    }

    public String getExpresion() {
        return expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }

    public String[] getExpresionArray() {
        return expresionArray;
    }

    public void setExpresionArray(String[] expresionArray) {
        this.expresionArray = expresionArray;
    }

}
