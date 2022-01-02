package fr.dut.ptut2021.game;

import fr.dut.ptut2021.models.Symbol;
import fr.dut.ptut2021.models.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrawOnIt {

    private Map<String, Symbol> symbols = new HashMap<>();
    ArrayList<Point> points = new ArrayList<>();

    public DrawOnIt(){
        symbols.put("0", new Symbol());

        symbols.put("1", new Symbol());

        symbols.put("2", new Symbol());

        symbols.put("3", new Symbol());

        symbols.put("4", new Symbol());

        symbols.put("5", new Symbol());

        symbols.put("6", new Symbol());

        symbols.put("7", new Symbol());

        symbols.put("8", new Symbol());

        symbols.put("9", new Symbol());

        points.add(new Point(10,10));
        points.add(new Point(30,50));
        points.add(new Point(50,90));
        points.add(new Point(70,130));
        points.add(new Point(90,170));
        points.add(new Point(110,130));
        points.add(new Point(130,90));
        points.add(new Point(150,50));
        points.add(new Point(170,10));
        points.add(new Point(130,90));
        points.add(new Point(90,90));
        symbols.put("A", new Symbol(points));
        points.clear();


        points.add(new Point(10,10));
        points.add(new Point(150,10));
        points.add(new Point(170,50));
        points.add(new Point(150,90));
        points.add(new Point(10,90));
        points.add(new Point(150,90));
        points.add(new Point(170,130));
        points.add(new Point(150,170));
        points.add(new Point(10,170));
        points.add(new Point(10,10));
        symbols.put("B", new Symbol(points));
        points.clear();

        points.add(new Point(170,20));
        points.add(new Point(80,10));
        points.add(new Point(10,10));
        points.add(new Point(10,10));
        points.add(new Point(10,10));
        points.add(new Point(10,10));
        points.add(new Point(10,10));
        symbols.put("C", new Symbol(points));
        points.clear();

        symbols.put("D", new Symbol());

        symbols.put("E", new Symbol());

        symbols.put("F", new Symbol());

        symbols.put("G", new Symbol());

        symbols.put("H", new Symbol());

        symbols.put("I", new Symbol());

        symbols.put("J", new Symbol());

        symbols.put("K", new Symbol());

        symbols.put("L", new Symbol());

        symbols.put("M", new Symbol());

        symbols.put("N", new Symbol());

        symbols.put("O", new Symbol());

        symbols.put("P", new Symbol());

        symbols.put("Q", new Symbol());

        symbols.put("R", new Symbol());

        symbols.put("S", new Symbol());

        symbols.put("T", new Symbol());

        symbols.put("U", new Symbol());

        symbols.put("V", new Symbol());

        symbols.put("W", new Symbol());

        symbols.put("X", new Symbol());

        symbols.put("Y", new Symbol());

        symbols.put("Z", new Symbol());

    }

}
