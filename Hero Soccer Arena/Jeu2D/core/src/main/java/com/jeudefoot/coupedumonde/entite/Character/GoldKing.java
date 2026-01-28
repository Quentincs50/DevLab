package com.jeudefoot.coupedumonde.entite.Character;

import com.jeudefoot.coupedumonde.Utils.AssetsManager;
import com.jeudefoot.coupedumonde.SuperPower.AirShot;

import static com.jeudefoot.coupedumonde.Utils.AssetsManager.Race.MINOTAUR_2;

public class GoldKing extends Character{
    public GoldKing(AssetsManager assets) {
        super("Gold King Warrior", MINOTAUR_2, new AirShot(), true);
        this.currentHealth = 200;
        this.maxHealth = 200;
        this.setHealthRate(0);
        this.acceleration = 30f;
        this.jumpForce = 20f;
    }
}
