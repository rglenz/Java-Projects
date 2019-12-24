import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class OreBlob extends Moveable{
    private static final String QUAKE_KEY = "quake";

    public OreBlob(String id, Point position,
                     List<PImage> images, int resourceLimit, int resourceCount,
                     int actionPeriod, int animationPeriod){
        this.id=id;
        this.position=position;
        this.images=images;

        this.actionPeriod=actionPeriod;
        this.animationPeriod=animationPeriod;
    }

    public static OreBlob createOreBlob(String id, Point position,
                                       int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new OreBlob(id, position, images,
                0, 0, actionPeriod, animationPeriod);
    }

    public void execute(WorldModel world,
                                       ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> blobTarget = world.findNearest(position, Vein.class);
        long nextPeriod = actionPeriod;

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().getPosition();

            if (moveTo(world, blobTarget.get(), scheduler))
            {
                Quake quake = Quake.createQuake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod +=actionPeriod;
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this,world, imageStore),
                nextPeriod);
    }

    public boolean moveTo(WorldModel world,
                                 Entity target, EventScheduler scheduler)
    {
        if (position.adjacent(target.getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
}
