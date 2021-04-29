package CalculadoraJflexCup.Tree;
import java.util.HashMap;
import java.util.Map;

public class Environment {

    private HashMap<String,Double> map;

    public Environment() {
        map = new HashMap<>();
    }

    public Double lookup(String var) {
        return map.get(var);
    }

    public void set(String var, Double val) {
        map.put(var, val);
    }

    public void print () {
        for (Map.Entry<String,Double> entry: map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public static void main (String [] args)
    {
        Environment e = new Environment();

        e.set("hello", 1.0);
        e.set("world", 2.0);

        System.out.println("Hello = " + e.lookup("hello"));
        System.out.println("World = " + e.lookup("world"));
    }

}
