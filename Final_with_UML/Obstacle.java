import processing.core.PImage;
import java.util.List;


public class Obstacle extends Entity{
    public Obstacle(String id,Point position, List<PImage> images)
    {
        this.id=id;
        this.position=position;
        this.images=images;
        this.imageIndex=0;
    }
    public static Obstacle createObstacle(String id, Point position,
                                        List<PImage> images)
    {
        return new Obstacle(id, position, images);
    }



}
