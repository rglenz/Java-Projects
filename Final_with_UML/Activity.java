final class Activity extends Action
{
   private WorldModel world;
   private ImageStore imageStore;


   public Activity(Entity entity, WorldModel world,
                   ImageStore imageStore)
   {
      this.entity = entity;
      this.world = world;
      this.imageStore = imageStore;
   }
   public void execute(EventScheduler scheduler)
   {
       executeActivityAction(scheduler);
   }

   public static Action createActivityAction(Entity entity, WorldModel world, ImageStore imageStore)
   {
      return new Activity(entity, world, imageStore);
   }

   public void executeActivityAction(EventScheduler scheduler)
   {

      if(entity instanceof MinerFull){
      ((MinerFull) this.entity).execute(this.world,
              this.imageStore, scheduler);
      }
      else if(entity instanceof MinerNotFull){
         ((MinerNotFull) this.entity).execute(this.world, this.imageStore, scheduler);
      }

      else if(entity instanceof Ore) {
         ((Ore) this.entity).execute(this.world, this.imageStore,
                 scheduler);
      }
      else if(entity instanceof OreBlob) {
         ((OreBlob) this.entity).execute(this.world,
                 this.imageStore, scheduler);
      }

      else if(entity instanceof Quake) {
         ((Quake) this.entity).execute(this.world, this.imageStore,
                 scheduler);
      }
      else if( entity instanceof Vein) {
         ((Vein) this.entity).execute(this.world, this.imageStore,
                 scheduler);
      }

       else{
         throw new UnsupportedOperationException(
            String.format("executeActivityAction not supported for %s"));
      }
   }
}
