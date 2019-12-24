import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Ore extends ActiveEntity{
    private static final String BLOB_KEY = "blob";
    private static final String BLOB_ID_SUFFIX = " -- blob";
    private static final int BLOB_PERIOD_SCALE = 4;
    private static final int BLOB_ANIMATION_MIN = 50;
    private static final int BLOB_ANIMATION_MAX = 150;


    public Ore(String id, Point position, int actionPeriod, List<PImage> images)
    {
        this.id=id;
        this.position=position;
        this.images=images;
        this.actionPeriod=actionPeriod;
    }
    public static Ore createOre(String id, Point position, int actionPeriod,
                                   List<PImage> images)
    {
        return new Ore(id, position, actionPeriod,images);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = position;  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        OreBlob blob = OreBlob.createOreBlob(id + BLOB_ID_SUFFIX,
                pos, actionPeriod / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN +
                        rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
                imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }

}
