import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Entity {
    protected String id;
    protected Point position;
    protected List<PImage> images;
    protected int actionPeriod;
    protected final Random rand = new Random();
    protected int imageIndex;

    protected List<PImage> getImages() {
        return images;
    }

    protected int getImageIndex() {
        return imageIndex;
    }

    protected Point getPosition() {
        return position;
    }
    protected void nextImage()
    {
        imageIndex = (imageIndex + 1) % images.size();
    }

    protected void setPosition(Point position) {
        this.position = position;
    }





}
