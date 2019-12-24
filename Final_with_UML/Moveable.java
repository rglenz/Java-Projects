import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Moveable extends AnimatedEntity{

    protected Point nextPosition(WorldModel world, Point destPos){
        AStarPathingStrategy strat = new AStarPathingStrategy();
        List<Point> path = strat.computePath(this.getPosition(),destPos,canPass(world),withinReach(),strat.CARDINAL_NEIGHBORS );
        if(path.size()==0){
            return this.getPosition();
        }

        return path.get(0);

    }
    private static Predicate<Point> canPass(WorldModel world){ return point->(!world.isOccupied(point)&&world.withinBounds(point)); }
    private static BiPredicate<Point,Point> withinReach(){
        return Point::adjacent;
    }
    protected abstract boolean moveTo(WorldModel world,Entity target, EventScheduler scheduler);
}
