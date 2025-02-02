import processing.core.PImage;

import java.util.*;

final class WorldModel
{
   private int numRows;
   private int numCols;
   private Background background[][];
   private Entity occupancy[][];
   private Set<Entity> entities;

   public int getNumCols() {
      return numCols;
   }

   public int getNumRows() {
      return numRows;
   }

   public Background[][] getBackground() {
      return background;
   }

   public Entity[][] getOccupancy() {
      return occupancy;
   }

   public Set<Entity> getEntities() {
      return entities;
   }

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }
   public boolean withinBounds( Point pos)
   {
      return pos.y >= 0 && pos.y < numRows &&
              pos.x >= 0 && pos.x < numCols;
   }
   public boolean isOccupied(Point pos)
   {
      return withinBounds(pos) &&
              getOccupancyCell(pos) != null;
   }
   public Entity getOccupancyCell(Point pos)
   {
      return occupancy[pos.y][pos.x];
   }

   public void setOccupancyCell(Point pos,
                                       Entity entity)
   {
      occupancy[pos.y][pos.x] = entity;
   }
   public Optional<PImage> getBackgroundImage(Point pos)
   {
      if (withinBounds(pos))
      {
         return Optional.of(Background.getCurrentImage(getBackgroundCell(pos)));
      }
      else
      {
         return Optional.empty();
      }
   }

   public void setBackground(Point pos, Background background)
   {
      if (withinBounds(pos))
      {
         setBackgroundCell(pos, background);
      }
   }
   public Background getBackgroundCell(Point pos)
   {
      return background[pos.y][pos.x];
   }

   public void setBackgroundCell(Point pos,Background background)
   {
      this.background[pos.y][pos.x] = background;
   }
   public void tryAddEntity(Entity entity)
   {
      if (isOccupied(entity.getPosition()))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      addEntity(entity);
   }
   public void addEntity(Entity entity)
   {
      if (withinBounds(entity.getPosition()))
      {
         setOccupancyCell(entity.getPosition(), entity);
         entities.add(entity);
      }
   }

   public void moveEntity(Entity entity, Point pos)
   {
      Point oldPos = entity.getPosition();
      if (withinBounds(pos) && !pos.equals(oldPos))
      {
         setOccupancyCell(oldPos, null);
         removeEntityAt(pos);
         setOccupancyCell(pos, entity);
         entity.setPosition(pos);
      }
   }
   /*
      Assumes that there is no entity currently occupying the
      intended destination cell.
   */
   public void removeEntity(Entity entity)
   {
      removeEntityAt(entity.getPosition());
   }

   public void removeEntityAt( Point pos)
   {
      if (withinBounds(pos)
              && getOccupancyCell(pos) != null)
      {
         Entity entity = getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1)) ;
         entities.remove(entity);
         setOccupancyCell(pos, null);
      }
   }
   public Optional<Entity> getOccupant(Point pos)
   {
      if (isOccupied(pos))
      {
         return Optional.of(getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }
   public Optional<Entity> findNearest(Point pos,
                                              Class kind)
   {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : entities)
      {
         if (entity.getClass() == kind)
         {
            ofType.add(entity);
         }
      }

      return Functions.nearestEntity(ofType, pos);
   }






}
