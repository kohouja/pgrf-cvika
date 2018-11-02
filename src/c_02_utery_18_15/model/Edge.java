package c_02_utery_18_15.model;

public class Edge {
    private int x1, x2, y1, y2;

    public Edge(Point p1, Point p2) {
        x1 = p1.x;
        x2 = p2.x;
        y1 = p1.y;
        y2 = p2.y;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public boolean isHorizontal(){
        if(y1 == y2){
            return true;
        }else{
            return false;
        }

    }
    public void orientate(){

//        TODO: prohozeni kdyz y1 je vetsi nez y2
        if(y1 > y2){
            int pom = y2;
            y2 = y1;
            y1 = pom;
            pom = x2;
            x2 = x1;
            x1 = pom;
        }
    }

   public boolean intersectionExists(int y){
//        TODO:y, y1, y2
       if(y1 < y && y < y2){
           return true;
       }else {
           return false;
       }
   }
   public int getIntersection(int y){
//        TODO: vypocitat prusecik pomoci y, k ,q, (osa y)
            int dx = x2 - x1;
            int dy = y2 - y1;
            float k = dy/(float) dx;
            float q = y1 - k * x1;
            int intersection = (int)((y - q)/k);
       return intersection;
   }
}
