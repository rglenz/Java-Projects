public abstract class Action {
   protected Entity entity;

   protected abstract void execute(EventScheduler scheduler);

}

