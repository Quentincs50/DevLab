package com.jeudefoot.coupedumonde.entite.Character;

import com.jeudefoot.coupedumonde.Utils.AssetsManager;
import com.jeudefoot.coupedumonde.SuperPower.AirShot;

import static com.jeudefoot.coupedumonde.Utils.AssetsManager.Race.VALKYRIE_2;

public class ValkiryeWarrior extends Character{
    public ValkiryeWarrior(){
        super("Valkirye Warrior", VALKYRIE_2, new AirShot(), true);
        this.currentHealth = 150;
        this.maxHealth = 150;
        this.setHealthRate(0);
        this.acceleration = 23f;
        this.jumpForce = 35f;
    }
}
