import java.util.Optional;

final class Point
{
   public final int x;
   public final int y;
   private static final int ORE_REACH = 1;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public String toString()
   {
      return "(" + x + "," + y + ")";
   }

   public boolean equals(Object other)
   {
      return other instanceof Point &&
         ((Point)other).x == this.x &&
         ((Point)other).y == this.y;
   }

   public int hashCode()
   {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }
   public int distanceSquared(Point p2)
   {
      int deltaX = this.x - p2.x;
      int deltaY = this.y - p2.y;

      return deltaX * deltaX + deltaY * deltaY;
   }
   public boolean adjacent(Point p1)
   {
      return (p1.x == this.x && Math.abs(p1.y - this.y) == 1) ||
              (p1.y == this.y && Math.abs(p1.x - this.x) == 1);
   }
   public Optional<Point> findOpenAround(WorldModel world)
   {
      for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++)
      {
         for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++)
         {
            Point newPt = new Point(this.x + dx, this.y + dy);
            if (world.withinBounds(newPt) &&
                    !world.isOccupied(newPt))
            {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }
}
