package CalculadoraJflexCup.Tree;


public class Literal extends Tree {

    Double val;
    public Literal(Double i) {
        val = i;
    }

    @Override
    public Double eval(Environment e) {
        return val;
    }
    @Override
    public void print() {
        System.out.print("(LITERAL " + val + ")");
    }
}

