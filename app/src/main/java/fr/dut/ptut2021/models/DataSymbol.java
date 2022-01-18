package fr.dut.ptut2021.models;

import java.util.ArrayList;

import fr.dut.ptut2021.models.Point;

public final class DataSymbol {

    public static ArrayList<Point> cinq = new ArrayList<>();

    public static void init(int width, int height) {

        cinq.add(new Point(0.65, 0.2938));
        cinq.add(new Point(0.5610657, 0.2939));
        cinq.add(new Point(0.41384888, 0.2997758));
        cinq.add(new Point(0.27035522, 0.3071867));
        cinq.add(new Point(0.26480103, 0.38064557));
        cinq.add(new Point(0.27035522, 0.46151534));
        cinq.add(new Point(0.38424683, 0.44721362));
        cinq.add(new Point(0.5203247, 0.4486763));
        cinq.add(new Point(0.625885, 0.46743107));
        cinq.add(new Point(0.70736694, 0.5192098));
        cinq.add(new Point(0.7314453, 0.578367));
        cinq.add(new Point(0.69348145, 0.6291706));
        cinq.add(new Point(0.6119995, 0.674026));
        cinq.add(new Point(0.4981079, 0.6957061));
        cinq.add(new Point(0.3703308, 0.6853699));
        cinq.add(new Point(0.28329468, 0.6553038));

        for(Point p : cinq){
            p.setX(p.getX()*width);
            p.setY(p.getY()*height);
        }

    }


}
