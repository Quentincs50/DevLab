package com.jeudefoot.coupedumonde.entite.Character;

import com.jeudefoot.coupedumonde.SuperPower.AirShot;

import static com.jeudefoot.coupedumonde.Utils.AssetsManager.Race.SKELETON_1;

public class GreySkeleton extends Character{
    public GreySkeleton(){
        super("Grey Skeleton", SKELETON_1, new AirShot(), true);
        this.currentHealth = 100;
        this.maxHealth = 100;
        this.setHealthRate(0);
        this.acceleration = 15f;
        this.jumpForce = 30f;
    }
}
