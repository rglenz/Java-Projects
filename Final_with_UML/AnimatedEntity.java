public abstract class AnimatedEntity extends ActiveEntity{
    protected int animationPeriod;
    protected int getAnimationPeriod(){
        return animationPeriod;
    }
    protected void nextImage()
    {
        imageIndex = (imageIndex + 1) % images.size();
    }
    protected void scheduleActions(EventScheduler scheduler,WorldModel world, ImageStore imageStore)
    {

        scheduler.scheduleEvent(this, Activity.createActivityAction(this,world, imageStore), actionPeriod);
        scheduler.scheduleEvent(this, Animation.createAnimationAction(this,0), this.getAnimationPeriod());
    }
}
