import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AStarPathingStrategy implements PathingStrategy {


    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {
        //initialize maps/queue
        Map<Point,Node> closed = new HashMap<>();
        Map<Point,Node> openM = new HashMap<>();
        //sorts by lowest f
        Queue<Node> openQ = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
        List<Point> path =new ArrayList<>();
        //add the starting Node
        Node first = new Node(null,start,0,computeH(end,start));
        openQ.add(first);
        openM.put(first.getP(),first);
        Node current;
        //While the open list isn't empty
        while(openQ.size()!=0){
            //find the node with the lowest f
            //set the most efficient node to current
            //pop current off open
            current=openQ.remove();
            openM.remove(current);
            //create a list of the current neighbors
            List<Point> neighbors = potentialNeighbors.apply(current.getP()).filter(p->!p.equals(start)&&!p.equals(end)).filter(canPassThrough).collect(Collectors.toList());
            //if the neighbors contain the end point then return the path
            if(withinReach.test(current.getP(),end)){
                while(current.getParent()!=null){
                    path.add(current.getP());
                    current=current.getParent();
                }
                //the list is backwards and we only want the last position
                Point temp=path.get(path.size()-1);
                path.clear();
                path.add(temp);
                return path;
            }
            //for every neighbor determine...
            for(Point p:neighbors){
                Node child = new Node(current,p,current.getG()+1,computeH(end,p));
                //if the neighbor is in closed disregard it
                if(closed.containsKey(p)){
                    continue;
                }
                //if the neighbor is already in the open list
                if(openM.containsKey(p))
                {
                    //check to see if the neighbor is better than what already exists
                    if(child.getG()<openM.get(p).getG()){
                        openQ.remove(openM.get(p));
                        openQ.add(child);
                        openM.replace(p,child);
                    }
                }
                //if not then add it to open list
                else{
                    openQ.add(child);
                    openM.put(p,child);
                }
            }
            //add current to closed
            closed.put(current.getP(),current);
        }
        //if no path
        return path;
    }
    public double computeH(Point end,Point current){
        return Math.sqrt(Math.pow(end.x - current.x, 2)+ Math.pow(end.y - current.y, 2));
    }


}
