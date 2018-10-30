package c_02_utery_18_15.model;

public class Edge {
    private int x1, x2, y1, y2;

    public Edge(Point p1, Point p2) {
        x1 = p1.x;
        x2 = p2.x;
        y1 = p1.y;
        y2 = p2.y;
    }


    public boolean isHorizontal(){
//        TODO: y1 = y2
        return false;
    }
    public void orientate(){
//        TODO: prohozeni kdyz y1 je vetsi nez y2
    }

   public boolean intersectionExists(int y){
//        TODO:y, y1, y2
       return false;
   }
   public int getIntersection(int y){
//        TODO: vypocitat prusecik pomoci y, k ,q, (osa y)
       return 0;
   }
}
