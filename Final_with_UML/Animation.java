final class Animation extends Action {

   private int repeatCount;

   public Animation(AnimatedEntity entity, int repeatCount) {

      this.entity = (AnimatedEntity)entity;
      this.repeatCount = repeatCount;
   }

   public void execute(EventScheduler scheduler) {
      executeAnimationAction(scheduler);
   }

   public static Action createAnimationAction(AnimatedEntity entity,int repeatCount)
   {
      return new Animation(entity, repeatCount);
   }

   public void executeAnimationAction(EventScheduler scheduler) {
      ((Entity)this.entity).nextImage();

      if (this.repeatCount != 1) {
         scheduler.scheduleEvent(this.entity,
                 createAnimationAction((AnimatedEntity) entity,
                         Math.max(this.repeatCount - 1, 0)),
                 ((AnimatedEntity) entity).getAnimationPeriod());
      }
   }

}