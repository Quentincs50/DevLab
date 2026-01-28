package com.jeudefoot.coupedumonde.entite.Character;


import com.jeudefoot.coupedumonde.SuperPower.SuperPower;
import com.jeudefoot.coupedumonde.Utils.AssetsManager;


import static com.jeudefoot.coupedumonde.Utils.Constants.*;

public class Character {

    protected AssetsManager.Race race;
    protected String name;
    protected boolean unlocked;
    protected SuperPower superPower;

    // Gameplay stats
    protected float freezingTime;
    protected float jumpForce;
    protected float shotImpulse;
    protected int maxHealth;
    protected int healthRate;
    protected int currentHealth;
    protected float acceleration;

    public Character(
        String name,
        AssetsManager.Race race,
        SuperPower superPower,
        boolean unlocked
    ) {
        super();

        this.race = race;
        this.freezingTime = FREEZING;
        this.name = name;
        this.superPower = superPower;
        this.unlocked = unlocked;

        this.jumpForce = JUMP_IMPULSE;
        this.shotImpulse = SHOT_IMPULSE;

        this.maxHealth = 100;
        this.currentHealth = 100;
        this.healthRate = 0;

    }

    public String getName() {
        return name;
    }

    public SuperPower getSuperPower() {
        return superPower;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public float getJumpForce() {
        return jumpForce;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealthRate() {
        return healthRate;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public float getShotImpulse() {
        return shotImpulse;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSuperPower(SuperPower superPower) {
        this.superPower = superPower;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public void setJumpForce(float jumpForce) {
        this.jumpForce = jumpForce;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setHealthRate(int healthRate) {
        this.healthRate = healthRate;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public float getFreezingTime() {
        return freezingTime;
    }

    public void setFreezingTime(float freezingTime) {
        this.freezingTime = freezingTime;
    }

    public void setShotImpulse(float shotImpulse) {
        this.shotImpulse = shotImpulse;
    }

    public AssetsManager.Race getRace() {
        return race;
    }

    public void setRace(AssetsManager.Race race) {
        this.race = race;
    }
}
