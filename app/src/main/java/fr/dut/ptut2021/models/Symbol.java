package fr.dut.ptut2021.models;

import java.util.ArrayList;


public class Symbol {

    private ArrayList<Point> points;
    private float tolerance;

    //private Point[] cinq = { new Point(773,561), new Point(280,614), new Point(468,877), new Point(597,872), new Point(758,971), new Point(761,1225), new Point(606,1358), new Point(344,1305) };

    public Symbol(){

        this.tolerance = 30;
    }

    public Symbol(int tolerance){
        this.points = new ArrayList<>();
        this.tolerance = tolerance;
    }
    
    public Symbol(ArrayList<Point> points){
        this.points = points;
        this.tolerance = 10;
    }

    public Symbol(ArrayList<Point> p, float t){
        this.points = p;
        this.tolerance = t;
    }

    private int findIdOfNearestPoint(Point point){
        int idNearestPoint;
        if(points.isEmpty()){
            System.out.println("vide");
            return -1;
        }else {
            double dist = Math.sqrt(Math.pow(point.getX() - points.get(0).getX(), 2) + Math.pow(point.getY() - points.get(0).getY(), 2));
            idNearestPoint = 0;


            //System.out.println("dist_" + dist);
            for (int i = 0; i < points.size(); i++) {
                //System.out.println(Math.sqrt(Math.pow(point.getX() - points.get(i).getX(), 2) + Math.pow(point.getY() - points.get(i).getY(), 2)));

                if (Math.sqrt(Math.pow(point.getX() - points.get(i).getX(), 2) + Math.pow(point.getY() - points.get(i).getY(), 2)) <= dist) {
                    idNearestPoint = i;
                    dist = Math.sqrt(Math.pow(point.getX() - points.get(i).getX(), 2) + Math.pow(point.getY() - points.get(i).getY(), 2));
                }
            }
        }


        //System.out.println(idNearestPoint);

        return idNearestPoint;
    }

    private double getDistanceBetweenTwoPoints(Point p1, Point p2){
        return Math.sqrt(Math.pow( p1.getX() - p2.getX(), 2) + Math.pow( p1.getY() - p2.getY(), 2));
    }

    //p1 et p2 sont les limites de la zone
    public boolean isInArea(Point point, Point p1, Point p2){
        Point test;
        test = new Point(p1);


        //System.out.print(" x1_" + p1.getX() + " y1_" + p1.getY() + " x2_" + p2.getX() + " y2_" + p2.getY() + " ");

        double coef, coefPrime, p, pPrime;

        if((p1.getX() - p2.getX()) != 0){
            //System.out.print(" cc1 ");
            coef = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
            //System.out.print(" dx_" + (p1.getX() - p2.getX()) + " dy_" + (p1.getY() - p2.getY()) + " coef_" + coef + " ");
            if(coef == 0){
                //System.out.print(" cc1_1 ");
                coefPrime = 0;
            }else{
                //System.out.print(" cc1_2 ");
                coefPrime = -1 / (coef);
            }
            //System.out.print(" coefPrime_" + coefPrime + " ");
        }else{
            //System.out.print(" cc2 ");
            coef = (p1.getY() - p2.getY());
            coefPrime = 0;
        }

        p = p1.getY() - (coef * p1.getX());
        pPrime = point.getY() - (coefPrime * point.getX());
        //System.out.print("p_" + p + " pPrime_" + pPrime + " ");

        Point inter;

        if(coef + coefPrime == 0){
            //System.out.print(" cc3_1 ");
            inter = new Point( (pPrime - p), coef * (pPrime - p) + p);
        }else{
            //System.out.print(" cc3_2 ");
            inter = new Point( (pPrime - p)/(coef - coefPrime), coef * ((pPrime - p)/(coef - coefPrime)) + p);
        }
        //System.out.println("");
        //System.out.print(Math.sqrt(Math.pow( inter.getX() - point.getX(), 2)+Math.pow( inter.getY() - point.getY(), 2)) <= tolerance);
        //System.out.print(" intx_" + inter.getX() + " inty_" + inter.getY() + " ");
        //System.out.print(" dist_" + Math.sqrt(Math.pow( inter.getX() - point.getX(), 2)+Math.pow( inter.getY() - point.getY(), 2)) + " ");
        //System.out.println("x²_" + Math.pow( inter.getX() - point.getX(), 2) + " y²_" + Math.pow( inter.getY() - point.getY(), 2) + " ");
        //System.out.print((getDistanceBetweenTwoPoints(point, p1) > getDistanceBetweenTwoPoints(p1, p2)) + " ");
        //System.out.println((getDistanceBetweenTwoPoints(point, p2) < getDistanceBetweenTwoPoints(p1, p2)) + " ");
        //System.out.print((getDistanceBetweenTwoPoints(point, p1) <= tolerance) + " ");
        //System.out.print((getDistanceBetweenTwoPoints(point, p2) <= tolerance) + " ");
        //System.out.println();

        if(getDistanceBetweenTwoPoints(point, p1) > getDistanceBetweenTwoPoints(p1, p2) || getDistanceBetweenTwoPoints(point, p2) < getDistanceBetweenTwoPoints(p1, p2)){
            if(getDistanceBetweenTwoPoints(point, p1) <= tolerance || getDistanceBetweenTwoPoints(point, p2) <= tolerance){
                return true;
            }else if(Math.sqrt(Math.pow( inter.getX() - point.getX(), 2)+Math.pow( inter.getY() - point.getY(), 2)) <= tolerance){
                return true;
            }
            return false;
        }

        /*if(Math.sqrt(Math.pow( inter.getX() - point.getX(), 2)+Math.pow( inter.getY() - point.getY(), 2)) <= tolerance){
            return true;
        }*/

        return false;
    }

    private int PointIsOnTheUpperRightSideOfTheLine(Point p, Point p1, Point p2){
        double m;

        if(p2.getX() - p1.getX() == 0){
            m = 0;
        }else{
            m = (p2.getY() - p1.getY())/(p2.getX() - p1.getX());
        }

        double b = p1.getY() - (m * p1.getX());

        System.out.print("  p1_x " + p1.getX() + " p1_y " + p1.getY());
        System.out.print(" p2_x " + p2.getX() + " p2_y " + p2.getY());
        System.out.println(" y = " + (m * p.getX() + b) + " ");

        if(m * p.getX() + b > 0){
            return 1;
        }else if(m * p.getX() + b < 0){
            return -1;
        }else {
            return 0;
        }
    }

    public boolean pointIsInRectangle(Point p, Point[] rect){
        int somme = 0;
        for(int i = 0; i < 4; i++){
            somme += PointIsOnTheUpperRightSideOfTheLine(p, rect[i], rect[(i + 1) % 4]);
            //System.out.print(PointIsOnTheUpperRightSideOfTheLine(p, rect[i], rect[(i + 1) % 4]));
        }

        if(somme == 0){
            return true;
        }

        return false;
    }

    public boolean isInArea2(Point p, Point p1, Point p2){
        Point[] rect = new Point[4];

        System.out.print("ref_x " + p.getX() + " ref_y " + p.getY());

        double coef = (tolerance/getDistanceBetweenTwoPoints(p1, p2));

        double x = coef * (p1.getX()-p2.getX());
        double y = coef * (p1.getY()-p2.getY());

        rect[0] = new Point(p1.getX() + x, p1.getY() - y);
        rect[1] = new Point(p1.getX() - x, p1.getY() + y);
        rect[2] = new Point(p2.getX() + x, p2.getY() - y);
        rect[3] = new Point(p2.getX() - x, p2.getY() + y);

        return pointIsInRectangle(p, rect);

    }

    public boolean isInArea3(Point point, Point p1, Point p2){

        Point inter;
        double m, b, m2, b2, x;

        if(p2.getX() - p1.getX() == 0){
            m = 0;
            m2 = 0;
        }else{
            m = (p2.getY() - p1.getY())/(p2.getX() - p1.getX());
            m2 = 1 / (m * (-1));
        }
        b = p1.getY() - (m * p1.getX());
        b2 = point.getY() - (m2 * point.getX());

        if(m - m2 == 0){
            x = b2 - b;
        }else{
            x = (b2 - b) / (m - m2);
        }

        //System.out.println("y1 = " + (m*x+b) + " y2 = " + (m2*x+b2));

        /*
        if(m*x+b != m2*x+b2){
            System.out.println("---------------------------------------------");
            System.out.println("Erreur lors du calcul du point d'intersection");
            System.out.println("---------------------------------------------");
            return false;
        }
        */

        inter = new Point(x, (m*x+b));

        if(getDistanceBetweenTwoPoints(inter, point) <= tolerance){
            if(getDistanceBetweenTwoPoints(point, p1) < getDistanceBetweenTwoPoints(p1, p2) + tolerance && getDistanceBetweenTwoPoints(point, p2) < getDistanceBetweenTwoPoints(p1, p2) + tolerance){
                return true;
            }
        }
        return false;
    }

    public boolean isInSymbol(Point point){
        int idNearestPoint = findIdOfNearestPoint(point);
        int maxId = 0;

        if(idNearestPoint == -1){
            return false;
        }

        if(idNearestPoint >= 1) {
            //System.out.println("c1");
            if(isInArea3(point, points.get(idNearestPoint - 1), points.get(idNearestPoint))){

                //System.out.println("c1 1");

                return true;
            }
        }

        if(idNearestPoint + 1 < points.size()){

            //System.out.println("c2");
            if(isInArea3(point, points.get(idNearestPoint), points.get(idNearestPoint + 1))){
            //System.out.println("c2 1");

                return true;
            }
        }

        return false;
    }
}
