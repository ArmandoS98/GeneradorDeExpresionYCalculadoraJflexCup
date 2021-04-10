/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * DESARROLLADO POR
 * Kevyn Salvador Posadas Tote -  201901858
 * Brodner Morales - 201902589
 * David Alfredo Díaz Veras -  201901175
 * Armando Emmanuel Santos Coy - 201900674
 * Como proyecto para la clase de compiladores de la Universidad Da Vinci de Guatemala
 * Año 2021
 */
public class GraficadorArboles extends JPanel {

    static JFrame frame;
    static JOptionPane message = new JOptionPane();
    private Node root = null;
    private Node_v2 root2 = null;

    //La localizacion de los nodos del arbol
    private HashMap nodeLocations = null;

    //El tamaño de los sub-arboles
    private HashMap subtreeSizes = null;

    private boolean dirty = true;

    //Espacios entre los nodos por defecto
    private int parent2child = 10;
    private int child2child = 20;

    // helpers
    private Dimension empty = new Dimension(0, 0);
    private FontMetrics fm = null;

    public GraficadorArboles(Node root) {
        this.root = root;
        nodeLocations = new HashMap();
        subtreeSizes = new HashMap();
    }

    public GraficadorArboles(Node_v2 root) {
        this.root2 = root;
        nodeLocations = new HashMap();
        subtreeSizes = new HashMap();
    }

    //Metodo calcula la localizacion de los nodos
    private void calculateLocations() {
        nodeLocations.clear();
        subtreeSizes.clear();

        Node _root = root;

        if (_root != null) {
            calculateSubtreeSize(_root);
            calculateLocation(_root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
        }
    }

    //Metodo calcula la medidas d elos sub-arboles dentro de n
    private Dimension calculateSubtreeSize(Node n) {
        if (n == null) {
            return new Dimension(0, 0);
        }

        Dimension ld = calculateSubtreeSize(n.getLeft());
        Dimension rd = calculateSubtreeSize(n.getRight());
        int h = fm.getHeight() + parent2child + Math.max(ld.height, rd.height);
        int w = ld.width + child2child + rd.width;
        Dimension d = new Dimension(w, h);
        subtreeSizes.put(n, d);
        return d;
    }

    //Metodo que calcula la localizacion de los nodos en los sub-arboles los nodos dcentro de N
    private void calculateLocation(Node n, int left, int right, int top) {
        if (n == null) {
            return;
        }
        Dimension ld = (Dimension) subtreeSizes.get(n.getLeft());
        if (ld == null) {
            ld = empty;
        }
        Dimension rd = (Dimension) subtreeSizes.get(n.getRight());
        if (rd == null) {
            rd = empty;
        }
        int center = 0;
        if (right != Integer.MAX_VALUE) {
            center = right - rd.width - child2child / 2;
        } else if (left != Integer.MAX_VALUE) {
            center = left + ld.width + child2child / 2;
        }
        //int width = fm.stringWidth(Integer.toString(n.getValue()));
        int width = fm.stringWidth(n.getValue());
        Rectangle r = new Rectangle(center - width / 2 - 3, top, width + 6, fm.getHeight());
        nodeLocations.put(n, r);
        calculateLocation(n.getLeft(), Integer.MAX_VALUE, center - child2child / 2, top + fm.getHeight() + parent2child);
        calculateLocation(n.getRight(), center + child2child / 2, Integer.MAX_VALUE, top + fm.getHeight() + parent2child);
    }

    //Metodo que dibuja el arbol usando las localizacion precalculada
    private void drawTree(Graphics2D g, Node n, int px, int py, int yoffs) {
        if (n == null) {
            return;
        }
        Rectangle r = (Rectangle) nodeLocations.get(n);
        g.draw(r);
        g.drawString(String.valueOf(n.getValue()), r.x + 3, r.y + yoffs);
        if (px != Integer.MAX_VALUE) {
            g.drawLine(px, py, r.x + r.width / 2, r.y);
        }
        drawTree(g, n.getLeft(), r.x + r.width / 2, r.y + r.height, yoffs);
        drawTree(g, n.getRight(), r.x + r.width / 2, r.y + r.height, yoffs);
    }

    //Metodo que dibuja el arbol 
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        fm = g.getFontMetrics();

        //Si la localizacion no esta calculada
        if (dirty) {
            calculateLocations();
            dirty = false;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, parent2child);
        drawTree(g2d, root, Integer.MAX_VALUE, Integer.MAX_VALUE, fm.getLeading() + fm.getAscent());
        fm = null;
    }

    public static void main(String[] args) {

    }

}
