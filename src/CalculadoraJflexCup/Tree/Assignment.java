package CalculadoraJflexCup.Tree;

public class Assignment extends Tree {

    private String lhs;
    private Tree rhs;

    public Assignment(String s, Tree e) {
        lhs = s;
        rhs = e;
    }

    public Double eval(Environment e) {
        Double val = rhs.eval(e);
        e.set(lhs, val);
        return val;
    }

    public void print() {
        System.out.print("(ASSIGNMENT " + lhs + ", ");
        rhs.print();
        System.out.print(")");
    }
}
