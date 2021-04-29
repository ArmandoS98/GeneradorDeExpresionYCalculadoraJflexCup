package CalculadoraJflexCup.Tree;

public class Identifier extends Tree {

    private String id;

    public Identifier(String s) {
        id = s;
    }

    @Override
    public Double eval(Environment e) {
        return e.lookup(id);
    }

    @Override
    public void print() {
        System.out.print("(IDENT " + id + ")");
    }
}
