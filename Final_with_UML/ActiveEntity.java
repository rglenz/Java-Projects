public abstract class ActiveEntity extends Scheduable{
    public abstract void execute(WorldModel world,ImageStore imageStore, EventScheduler scheduler);

}
