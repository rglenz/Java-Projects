import processing.core.PImage;

import java.util.List;

public class Quake extends AnimatedEntity{
    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Quake(String id,Point position, List<PImage> images,
                 int actionPeriod, int animationPeriod)
    {
        this.id=id;
        this.position=position;
        this.images=images;
        this.actionPeriod=actionPeriod;
        this.animationPeriod=animationPeriod;
    }
    public static Quake createQuake(Point position, List<PImage> images)
    {
        return new Quake(QUAKE_ID, position, images, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }
    protected void scheduleActions(EventScheduler scheduler,WorldModel world, ImageStore imageStore)
    {

        scheduler.scheduleEvent(this, Activity.createActivityAction(this,world, imageStore), actionPeriod);
        scheduler.scheduleEvent(this, Animation.createAnimationAction(this,QUAKE_ANIMATION_REPEAT_COUNT), this.getAnimationPeriod());
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

}
