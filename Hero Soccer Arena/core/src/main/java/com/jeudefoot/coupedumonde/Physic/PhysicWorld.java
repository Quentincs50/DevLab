package com.jeudefoot.coupedumonde.Physic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


public class PhysicWorld {
    public World world;

    public PhysicWorld(){
        world = new World(new Vector2(0,- 30f), true);
        world.setContactListener(new ListenerContact());
    }

    public World getWorld(){
        return world;
    }

    public void update(){
        world.step(1/60f, 6,2);
    }

    public void dispose(){
        world.dispose();
    }
}


