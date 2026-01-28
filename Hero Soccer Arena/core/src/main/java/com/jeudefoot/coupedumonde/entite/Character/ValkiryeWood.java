package com.jeudefoot.coupedumonde.entite.Character;

import com.jeudefoot.coupedumonde.Utils.AssetsManager;
import com.jeudefoot.coupedumonde.SuperPower.AirShot;

import static com.jeudefoot.coupedumonde.Utils.AssetsManager.Race.VALKYRIE_1;

public class ValkiryeWood extends Character{
    public ValkiryeWood(){
        super("Valkirye of the Wood", VALKYRIE_1, new AirShot(), true);
        this.currentHealth = 120;
        this.maxHealth = 120;
        this.setHealthRate(15);
        this.acceleration = 30f;
        this.jumpForce = 20f;
    }
}
