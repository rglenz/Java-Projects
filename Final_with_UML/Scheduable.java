public abstract class Scheduable extends Entity{
    protected void scheduleActions(EventScheduler scheduler,WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, Activity.createActivityAction(this,world, imageStore), actionPeriod);
    }
}
