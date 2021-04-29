package CalculadoraJflexCup.Operaciones;

import CalculadoraJflexCup.Tree.Environment;
import CalculadoraJflexCup.Tree.Tree;

public class Multiply extends Tree {

    Tree left;
    Tree right;

    public Multiply(Tree l, Tree r) {
        left = l;
        right = r;
    }

    @Override
    public Double eval(Environment e) {
        return left.eval(e) * right.eval(e);
    }

    @Override
    public void print() {
        System.out.print("(MULTIPLY ");
        left.print();
        System.out.print(", ");
        right.print();
        System.out.print(")");
    }

}
