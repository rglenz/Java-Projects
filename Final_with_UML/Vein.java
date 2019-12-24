import processing.core.PImage;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Vein extends ActiveEntity{
    private static final String ORE_ID_PREFIX = "ore -- ";
    private static final int ORE_CORRUPT_MIN = 20000;
    private static final int ORE_CORRUPT_MAX = 30000;
    private static final String ORE_KEY = "ore";

    public Vein(String id, Point position, int actionPeriod, List<PImage> images)
    {
        this.id=id;
        this.position=position;
        this.images=images;
        this.actionPeriod=actionPeriod;

    }
    public static Vein createVein(String id, Point position, int actionPeriod,
                                    List<PImage> images)
    {
        return new Vein(id, position, actionPeriod,images);
    }
    public void execute(WorldModel world,
                                    ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = position.findOpenAround(world);

        if (openPt.isPresent())
        {
            Ore ore = new Ore(ORE_ID_PREFIX,
                    openPt.get(), ORE_CORRUPT_MIN +
                            rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                    imageStore.getImageList(ORE_KEY));
            world.addEntity(ore);
            ore.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this,world, imageStore),
                actionPeriod);
    }

}
