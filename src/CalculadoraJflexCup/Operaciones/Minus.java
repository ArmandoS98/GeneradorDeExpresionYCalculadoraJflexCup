package CalculadoraJflexCup.Operaciones;

import CalculadoraJflexCup.Tree.Environment;
import CalculadoraJflexCup.Tree.Tree;

public class Minus extends Tree {

    Tree left;
    Tree right;

    public Minus(Tree l, Tree r) {
        left = l;
        right = r;
    }

    @Override
    public Double eval(Environment e) {
        return left.eval(e) - right.eval(e);
    }

    @Override
    public void print() {
        System.out.print("(MINUS ");
        left.print();
        System.out.print(", ");
        right.print();
        System.out.print(")");
    }

}
