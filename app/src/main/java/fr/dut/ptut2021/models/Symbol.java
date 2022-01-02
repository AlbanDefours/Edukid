package fr.dut.ptut2021.models;

import fr.dut.ptut2021.models.Point;

import java.util.ArrayList;


public class Symbol {

    private ArrayList<Point> points;
    private int tolerance;

    public Symbol(){
        this.points = new ArrayList<>();
        this.tolerance = 3;
    }

    public Symbol(int tolerance){
        this.points = new ArrayList<>();
        this.tolerance = tolerance;
    }
    
    public Symbol(ArrayList<Point> points){
        this.points = points;
        this.tolerance = 10;
    }

    public Symbol(ArrayList<Point> points){
        this.points = points;
        this.tolerance = 10;
    }

    private int findIdOfNearestPoint(Point point){
        int idNearestPoint;
        if(points.isEmpty()){
            return -1;
        }else {
            double dist = Math.sqrt(Math.pow(point.getX() - points.get(0).getX(), 2) + Math.pow(point.getY() - points.get(0).getY(), 2));
            idNearestPoint = 0;
<<<<<<< HEAD
            //System.out.println("dist_" + dist);
            for (int i = 0; i < points.size(); i++) {
                //System.out.println(Math.sqrt(Math.pow(point.getX() - points.get(i).getX(), 2) + Math.pow(point.getY() - points.get(i).getY(), 2)));
=======
            System.out.println("dist_" + dist);
            for (int i = 0; i < points.size(); i++) {
                System.out.println(Math.sqrt(Math.pow(point.getX() - points.get(i).getX(), 2) + Math.pow(point.getY() - points.get(i).getY(), 2)));
>>>>>>> bcf7d7fe69bcfd619b174f9230b266708077ef83
                if (Math.sqrt(Math.pow(point.getX() - points.get(i).getX(), 2) + Math.pow(point.getY() - points.get(i).getY(), 2)) <= dist) {
                    idNearestPoint = i;
                    dist = Math.sqrt(Math.pow(point.getX() - points.get(i).getX(), 2) + Math.pow(point.getY() - points.get(i).getY(), 2));
                }
            }
        }
<<<<<<< HEAD
        //System.out.println(idNearestPoint);
=======
        System.out.println(idNearestPoint);
>>>>>>> bcf7d7fe69bcfd619b174f9230b266708077ef83
        return idNearestPoint;
    }

    //p1 et p2 sont les limites de la zone
    public boolean isInArea(Point point, Point p1, Point p2){
        Point test;
        test = new Point(p1);
<<<<<<< HEAD
        
        //System.out.print(" x1_" + p1.getX() + " y1_" + p1.getY() + " x2_" + p2.getX() + " y2_" + p2.getY() + " ");
        
        double coef, coefPrime, p, pPrime;
        
        if((p1.getX() - p2.getX()) != 0){
            //System.out.print(" cc1 ");
            coef = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
            //System.out.print(" dx_" + (p1.getX() - p2.getX()) + " dy_" + (p1.getY() - p2.getY()) + " coef_" + coef + " ");
            if(coef == 0){
               //System.out.print(" cc1_1 ");
                coefPrime = 0.001;
                coef = 0.001;
            }else{
               //System.out.print(" cc1_2 ");
                coefPrime = -1 / (coef);
            }
            //System.out.print(" coefPrime_" + coefPrime + " ");
        }else{
            //System.out.print(" cc2 ");
            coef = (p1.getY() - p2.getY());
            coefPrime = 0.001;
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
        //System.out.print("x²_" + Math.pow( inter.getX() - point.getX(), 2) + " y²_" + Math.pow( inter.getY() - point.getY(), 2) + " ");
        
        
        if(Math.sqrt(Math.pow( inter.getX() - point.getX(), 2)+Math.pow( inter.getY() - point.getY(), 2)) <= tolerance){
            return true;
        }
        
=======

        System.out.print(" x1_" + p1.getX() + " y1_" + p1.getY() + " x2_" + p2.getX() + " y2_" + p2.getY() + " ");

        double coef, coefPrime, p, pPrime;

        if((p1.getX() - p2.getX()) != 0){
            System.out.print(" cc1 ");
            coef = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
            System.out.print(" dx_" + (p1.getX() - p2.getX()) + " dy_" + (p1.getY() - p2.getY()) + " coef_" + coef + " ");
            if(coef == 0){
                System.out.print(" cc1_1 ");
                coefPrime = 0;
            }else{
                System.out.print(" cc1_2 ");
                coefPrime = -1 / (coef);
            }
            System.out.print(" coefPrime_" + coefPrime + " ");
        }else{
            System.out.print(" cc2 ");
            coef = (p1.getY() - p2.getY());
            coefPrime = 0;
        }

        p = p1.getY() - (coef * p1.getX());
        pPrime = point.getY() - (coefPrime * point.getX());
        System.out.print("p_" + p + " pPrime_" + pPrime + " ");

        Point inter;

        if(coef + coefPrime == 0){
            System.out.print(" cc3_1 ");
            inter = new Point( (pPrime - p), coef * (pPrime - p) + p);
        }else{
            System.out.print(" cc3_2 ");
            inter = new Point( (pPrime - p)/(coef - coefPrime), coef * ((pPrime - p)/(coef - coefPrime)) + p);
        }
        System.out.println("");
        System.out.print(Math.sqrt(Math.pow( inter.getX() - point.getX(), 2)+Math.pow( inter.getY() - point.getY(), 2)) <= tolerance);
        System.out.print(" intx_" + inter.getX() + " inty_" + inter.getY() + " ");
        System.out.print(" dist_" + Math.sqrt(Math.pow( inter.getX() - point.getX(), 2)+Math.pow( inter.getY() - point.getY(), 2)) + " ");
        System.out.print("x²_" + Math.pow( inter.getX() - point.getX(), 2) + " y²_" + Math.pow( inter.getY() - point.getY(), 2) + " ");


        if(Math.sqrt(Math.pow( inter.getX() - point.getX(), 2)+Math.pow( inter.getY() - point.getY(), 2)) <= tolerance){
            return true;
        }

>>>>>>> bcf7d7fe69bcfd619b174f9230b266708077ef83
        return false;
    }

    public boolean isInSymbol(Point point){
        int idNearestPoint = findIdOfNearestPoint(point);
        int maxId = 0;
<<<<<<< HEAD
        
        if(idNearestPoint == -1){
            return false;
        }
        
        if(idNearestPoint >= 1) {
            //System.out.println("c1");
            if(isInArea(point, points.get(idNearestPoint - 1), points.get(idNearestPoint))){
                //System.out.println("c1 1");
=======

        if(idNearestPoint == -1){
            return false;
        }

        if(idNearestPoint >= 1) {
            System.out.println("c1");
            if(isInArea(point, points.get(idNearestPoint - 1), points.get(idNearestPoint))){
                System.out.println("c1 1");
>>>>>>> bcf7d7fe69bcfd619b174f9230b266708077ef83
                return true;
            }
        }
        if(idNearestPoint + 1 < points.size()){
<<<<<<< HEAD
            //System.out.println("c2");
            if(isInArea(point, points.get(idNearestPoint), points.get(idNearestPoint + 1))){
            //System.out.println("c2 1");
=======
            System.out.println("c2");
            if(isInArea(point, points.get(idNearestPoint), points.get(idNearestPoint + 1))){
                System.out.println("c2 1");
>>>>>>> bcf7d7fe69bcfd619b174f9230b266708077ef83
                return true;
            }
        }
        return false;
    }
}
