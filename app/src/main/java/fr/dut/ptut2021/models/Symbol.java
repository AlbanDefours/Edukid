package fr.dut.ptut2021.models;

import java.util.ArrayList;


public class Symbol {

    private ArrayList<Point> points;
    private float tolerance;
    private int lastId = -1;
    private int lastIdCustom = -1;
    private boolean isPastByTheMiddle = false;

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

    private int findIdOfNearestPointExept(Point point, int exception){
        int idNearestPoint, idSecondNearestPoint = 0;
        if(points.isEmpty() || points.size() == 1){
            return -1;
        }else {
            double dist = Math.sqrt(Math.pow(point.getX() - points.get(0).getX(), 2) + Math.pow(point.getY() - points.get(0).getY(), 2));
            idNearestPoint = 0;


            //System.out.println("dist_" + dist);
            for (int i = 0; i < points.size(); i++) {
                //System.out.println(Math.sqrt(Math.pow(point.getX() - points.get(i).getX(), 2) + Math.pow(point.getY() - points.get(i).getY(), 2)));
                idSecondNearestPoint = idNearestPoint;
                if (Math.sqrt(Math.pow(point.getX() - points.get(i).getX(), 2) + Math.pow(point.getY() - points.get(i).getY(), 2)) <= dist) {
                    idNearestPoint = i;
                    dist = Math.sqrt(Math.pow(point.getX() - points.get(i).getX(), 2) + Math.pow(point.getY() - points.get(i).getY(), 2));
                }
            }
        }


        //System.out.println(idNearestPoint);

        return idSecondNearestPoint;
    }

    public double getDistanceBetweenTwoPoints(Point p1, Point p2){
        return Math.sqrt(Math.pow( p1.getX() - p2.getX(), 2) + Math.pow( p1.getY() - p2.getY(), 2));
    }



    public boolean isInArea(Point point, Point p1, Point p2){

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
        int idOfSecondNearestPoint = findIdOfNearestPointExept(point, idNearestPoint);

        if(lastId == -1){
            lastId = idNearestPoint;
        }else if(lastId != idNearestPoint - 1 && lastId != idNearestPoint && lastId != idOfSecondNearestPoint - 1 && lastId != idOfSecondNearestPoint){
            return false;
        }

        if(idNearestPoint == points.size()/2){
            isPastByTheMiddle = true;
        }

        lastId = idNearestPoint;

        if(idNearestPoint == -1){
            return false;
        }

        if(idNearestPoint >= 1) {
            if(isInArea(point, points.get(idNearestPoint - 1), points.get(idNearestPoint))){
                return true;
            }
        }

        if(idOfSecondNearestPoint >= 1){
            if(isInArea(point, points.get(idOfSecondNearestPoint - 1), points.get(idOfSecondNearestPoint))){
                return true;
            }
        }

        if(idNearestPoint + 1 < points.size()){
            if(isInArea(point, points.get(idNearestPoint), points.get(idNearestPoint + 1))){
                return true;
            }
        }

        if(idOfSecondNearestPoint + 1 < points.size()){
            if(isInArea(point, points.get(idOfSecondNearestPoint), points.get(idOfSecondNearestPoint + 1))){
                return true;
            }
        }

        return false;
    }

    //surchage pour avoir une tolerance customisé
    public boolean isInArea(Point point, Point p1, Point p2, float tol){

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

        inter = new Point(x, (m*x+b));

        if(getDistanceBetweenTwoPoints(inter, point) <= tol){
            if(getDistanceBetweenTwoPoints(point, p1) < getDistanceBetweenTwoPoints(p1, p2) + tol && getDistanceBetweenTwoPoints(point, p2) < getDistanceBetweenTwoPoints(p1, p2) + tol){
                return true;
            }
        }
        return false;
    }

    //surchage pour avoir la tolerance customisé
    public boolean isInSymbol(Point point, float tol){
        int idNearestPoint = findIdOfNearestPoint(point);
        int idOfSecondNearestPoint = findIdOfNearestPointExept(point, idNearestPoint);

        if(lastIdCustom == -1){
            lastIdCustom = idNearestPoint;
        }else if(lastIdCustom != idNearestPoint - 1 && lastIdCustom != idNearestPoint && lastIdCustom != idOfSecondNearestPoint - 1 && lastIdCustom != idOfSecondNearestPoint){
            return false;
        }

        lastIdCustom = idNearestPoint;

        if(idNearestPoint == points.size()/2){
            isPastByTheMiddle = true;
        }

        if(idNearestPoint == -1){
            return false;
        }

        if(idNearestPoint >= 1) {
            if(isInArea(point, points.get(idNearestPoint - 1), points.get(idNearestPoint),tol)){
                return true;
            }
        }

        if(idOfSecondNearestPoint >= 1){
            if(isInArea(point, points.get(idOfSecondNearestPoint - 1), points.get(idOfSecondNearestPoint),tol)){
                return true;
            }
        }

        if(idNearestPoint + 1 < points.size()){
            if(isInArea(point, points.get(idNearestPoint), points.get(idNearestPoint + 1), tol)){
                return true;
            }
        }

        if(idOfSecondNearestPoint + 1 < points.size()){
            if(isInArea(point, points.get(idOfSecondNearestPoint), points.get(idOfSecondNearestPoint + 1), tol)){
                return true;
            }
        }

        return false;
    }

    public void clearAllLastId(){
        lastId = -1;
        lastIdCustom = -1;
        isPastByTheMiddle = false;
    }

    public Point getFirstPoint(){
        return points.get(0);
    }
    public Point getLastPoint(){
        return points.get(points.size() - 1);
    }

    public boolean isPastByTheMiddle(){
        return isPastByTheMiddle;
    }

    public float getTolerance(){
        return tolerance;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public int getLastId(){return lastId;}
    public int getLastIdCustom(){return lastIdCustom;}

}
