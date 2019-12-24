import processing.core.PImage;

import java.util.List;

public abstract class Miner extends Moveable{

    protected int resourceLimit;
    protected int resourceCount;

    protected abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);
}
