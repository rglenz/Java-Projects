import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Blacksmith extends Entity {


    public Blacksmith(String id,Point position, List<PImage> images)
    {
        this.id=id;
        this.position=position;
        this.images=images;
        this.imageIndex=0;
    }

    public static Blacksmith createBlacksmith(String id, Point position,
                                          List<PImage> images)
    {
        return new Blacksmith(id, position, images);
    }



}
