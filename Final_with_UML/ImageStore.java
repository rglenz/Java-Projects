import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

final class ImageStore
{
   private Map<String, List<PImage>> images;
   private List<PImage> defaultImages;
   private static final int PROPERTY_KEY = 0;

   private static final String BGND_KEY = "background";
   private static final int BGND_NUM_PROPERTIES = 4;
   private static final int BGND_ID = 1;
   private static final int BGND_COL = 2;
   private static final int BGND_ROW = 3;

   private static final String MINER_KEY = "miner";
   private static final int MINER_NUM_PROPERTIES = 7;
   private static final int MINER_ID = 1;
   private static final int MINER_COL = 2;
   private static final int MINER_ROW = 3;
   private static final int MINER_LIMIT = 4;
   private static final int MINER_ACTION_PERIOD = 5;
   private static final int MINER_ANIMATION_PERIOD = 6;

   private static final String OBSTACLE_KEY = "obstacle";
   private static final int OBSTACLE_NUM_PROPERTIES = 4;
   private static final int OBSTACLE_ID = 1;
   private static final int OBSTACLE_COL = 2;
   private static final int OBSTACLE_ROW = 3;

   private static final String ORE_KEY = "ore";
   private static final int ORE_NUM_PROPERTIES = 5;
   private static final int ORE_ID = 1;
   private static final int ORE_COL = 2;
   private static final int ORE_ROW = 3;
   private static final int ORE_ACTION_PERIOD = 4;

   private static final String SMITH_KEY = "blacksmith";
   private static final int SMITH_NUM_PROPERTIES = 4;
   private static final int SMITH_ID = 1;
   private static final int SMITH_COL = 2;
   private static final int SMITH_ROW = 3;

   private static final String VEIN_KEY = "vein";
   private static final int VEIN_NUM_PROPERTIES = 5;
   private static final int VEIN_ID = 1;
   private static final int VEIN_COL = 2;
   private static final int VEIN_ROW = 3;
   private static final int VEIN_ACTION_PERIOD = 4;

   public ImageStore(PImage defaultImage)
   {
      this.images = new HashMap<>();
      defaultImages = new LinkedList<>();
      defaultImages.add(defaultImage);
   }
   public List<PImage> getImageList( String key)
   {
      return images.getOrDefault(key,defaultImages);
   }
   public void loadImages(Scanner in,PApplet screen)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            Functions.processImageLine(this.images, in.nextLine(), screen);
         }
         catch (NumberFormatException e)
         {
            System.out.println(String.format("Image format error on line %d",
                    lineNumber));
         }
         lineNumber++;
      }
   }
   public void load(Scanner in, WorldModel world)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!processLine(in.nextLine(), world))
            {
               System.err.println(String.format("invalid entry on line %d",
                       lineNumber));
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(String.format("invalid entry on line %d",
                    lineNumber));
         }
         catch (IllegalArgumentException e)
         {
            System.err.println(String.format("issue on line %d: %s",
                    lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }
   public boolean processLine(String line, WorldModel world)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0)
      {
         switch (properties[PROPERTY_KEY])
         {
            case BGND_KEY:
               return parseBackground(properties, world);
            case MINER_KEY:
               return parseMiner(properties, world);
            case OBSTACLE_KEY:
               return parseObstacle(properties, world);
            case ORE_KEY:
               return parseOre(properties, world);
            case SMITH_KEY:
               return parseSmith(properties, world);
            case VEIN_KEY:
               return parseVein(properties, world);
         }
      }

      return false;
   }
   public boolean parseBackground(String [] properties,
                                         WorldModel world)
   {
      if (properties.length == BGND_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                 Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         world.setBackground(pt,
                 new Background(id, getImageList(id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }

   public boolean parseMiner(String [] properties, WorldModel world)
   {
      if (properties.length == MINER_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                 Integer.parseInt(properties[MINER_ROW]));
         MinerNotFull entity = MinerNotFull.createMinerNotFull(properties[MINER_ID],
                 Integer.parseInt(properties[MINER_LIMIT]),
                 pt,
                 Integer.parseInt(properties[MINER_ACTION_PERIOD]),
                 Integer.parseInt(properties[MINER_ANIMATION_PERIOD]),
                 getImageList(MINER_KEY));
         world.tryAddEntity(entity);
      }

      return properties.length == MINER_NUM_PROPERTIES;
   }

   public boolean parseObstacle(String [] properties, WorldModel world)
   {
      if (properties.length == OBSTACLE_NUM_PROPERTIES)
      {
         Point pt = new Point(
                 Integer.parseInt(properties[OBSTACLE_COL]),
                 Integer.parseInt(properties[OBSTACLE_ROW]));
         Obstacle entity = Obstacle.createObstacle(properties[OBSTACLE_ID],
                 pt, getImageList( OBSTACLE_KEY));
         world.tryAddEntity(entity);
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }

   public boolean parseOre(String [] properties, WorldModel world)
   {
      if (properties.length == ORE_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                 Integer.parseInt(properties[ORE_ROW]));
         Ore entity = Ore.createOre(properties[ORE_ID],
                 pt, Integer.parseInt(properties[ORE_ACTION_PERIOD]),
                 getImageList(ORE_KEY));
         world.tryAddEntity(entity);
      }

      return properties.length == ORE_NUM_PROPERTIES;
   }

   public boolean parseSmith(String [] properties, WorldModel world)
   {
      if (properties.length == SMITH_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                 Integer.parseInt(properties[SMITH_ROW]));
         Blacksmith entity = Blacksmith.createBlacksmith(properties[SMITH_ID],
                 pt, getImageList(SMITH_KEY));
         world.tryAddEntity(entity);
      }

      return properties.length == SMITH_NUM_PROPERTIES;
   }

   public boolean parseVein(String [] properties, WorldModel world)
   {
      if (properties.length == VEIN_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                 Integer.parseInt(properties[VEIN_ROW]));
         Vein entity = Vein.createVein(properties[VEIN_ID],
                 pt,
                 Integer.parseInt(properties[VEIN_ACTION_PERIOD]),
                 getImageList(VEIN_KEY));
         world.tryAddEntity(entity);
      }

      return properties.length == VEIN_NUM_PROPERTIES;
   }



}
