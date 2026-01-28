package com.jeudefoot.coupedumonde.entite.Character;

import com.jeudefoot.coupedumonde.Utils.AssetsManager;
import com.jeudefoot.coupedumonde.SuperPower.AirShot;

import static com.jeudefoot.coupedumonde.Utils.AssetsManager.Race.SKELETON_2;

public class YellowSkeleton extends Character{
    public YellowSkeleton(){
        super("Yellow Skeleton",SKELETON_2, new AirShot(),true);
        this.currentHealth = 150;
        this.maxHealth = 150;
        this.setHealthRate(10);
        this.acceleration = 10f;
        this.jumpForce = 20f;
    }
}

