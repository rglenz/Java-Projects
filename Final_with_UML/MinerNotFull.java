import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerNotFull extends Miner{


    public MinerNotFull(String id, Point position,
                     List<PImage> images, int resourceLimit, int resourceCount,
                     int actionPeriod, int animationPeriod){
        this.id=id;
        this.position=position;
        this.images=images;
        this.resourceCount=resourceCount;
        this.resourceLimit=resourceLimit;
        this.actionPeriod=actionPeriod;
        this.animationPeriod=animationPeriod;
    }
    public static MinerNotFull createMinerNotFull(String id, int resourceLimit,
                                            Point position, int actionPeriod, int animationPeriod,
                                            List<PImage> images)
    {
        return new MinerNotFull(id, position, images,
                resourceLimit, 0, actionPeriod, animationPeriod);
    }
    public void scheduleActions(EventScheduler scheduler,WorldModel world, ImageStore imageStore)
    {

        scheduler.scheduleEvent(this, Activity.createActivityAction(this,world, imageStore), actionPeriod);
        scheduler.scheduleEvent(this, Animation.createAnimationAction(this,0), this.getAnimationPeriod());
    }
    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(position,
                Ore.class);

        if (!notFullTarget.isPresent() ||
                !moveTo(world, notFullTarget.get(), scheduler) ||
                !transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this,world, imageStore),
                    actionPeriod);
        }
    }

    public boolean moveTo(WorldModel world,
                          Entity target, EventScheduler scheduler)
    {
        if (position.adjacent(target.getPosition()))
        {
            resourceCount += 1;
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
    public boolean transformNotFull(WorldModel world,
                                    EventScheduler scheduler, ImageStore imageStore)
    {
        if (resourceCount >= resourceLimit)
        {
            MinerFull miner=MinerFull.createMinerFull(id, resourceLimit,
                    position, actionPeriod, animationPeriod,
                    images);

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

}
