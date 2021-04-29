/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalculadoraJflexCup.Compilador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Armando Santos
 */
public class LexicalGrammarGenerator {

    private static final String mainPath = "C:/Users/Armando Santos/Documents/NetBeansProjects/Compiladores06042021/";
//Reemplazar la ruta de destino si se genera una modificacion del mismo

    public static void main(String[] args) throws Exception {
        String path = mainPath + "src/CalculadoraJflexCup/Compilador/Lexer.flex";
        String[] rutaS = {"-parser", "Sintax", mainPath + "src/CalculadoraJflexCup/Compilador/Sintax.cup"};
        init(path, "", rutaS);
    }

    private static void init(String path, String path2, String[] pathS) throws IOException, Exception {
        File mFile;

        mFile = new File(path);
        JFlex.Main.generate(mFile);

        /*mFile = new File(path2);
        JFlex.Main.generate(mFile);*/
        java_cup.Main.main(pathS);

        Path rutaSym = Paths.get(mainPath + "src/CalculadoraJflexCup/Compilador/sym.java");

        if (Files.exists(rutaSym)) {
            Files.delete(rutaSym);
        }
        Files.move(Paths.get(mainPath + "sym.java"),
                Paths.get(mainPath + "src/CalculadoraJflexCup/Compilador/sym.java"));

        Path rutaSin = Paths.get("C:/Users/Armando Santos/Documents/NetBeansProjects/Analizador/src/CalculadoraJflexCup/Compilador/Sintax.java");

        if (Files.exists(rutaSin)) {
            Files.delete(rutaSin);
        }
        Files.move(Paths.get(mainPath + "Sintax.java"),
                Paths.get(mainPath + "src/CalculadoraJflexCup/Compilador/Sintax.java"));
    }
}
