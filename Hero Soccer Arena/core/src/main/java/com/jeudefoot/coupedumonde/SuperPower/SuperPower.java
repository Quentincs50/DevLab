package com.jeudefoot.coupedumonde.SuperPower;

import com.jeudefoot.coupedumonde.Physic.ListenerContact;
import com.jeudefoot.coupedumonde.entite.Ball.Ball;
import com.jeudefoot.coupedumonde.entite.Player.Player;
import com.jeudefoot.coupedumonde.entite.Player.Player1;

public abstract class SuperPower {
    private String name;
    private int chargeMax;
    private float currentCharge;
    private boolean ready = false;
    private boolean activated = false;
    private float chargeRate;
    protected Player player;

    public SuperPower(String name, int chargeMax, int currentCharge, int chargeRate) {
        this.name = name;
        this.chargeMax = chargeMax;
        this.currentCharge = currentCharge;
        this.chargeRate = chargeRate;
    }

    public String getName() {
        return name;
    }

    public int getChargeMax() {
        return chargeMax;
    }

    public void setChargeMax(int chargeMax) {
        this.chargeMax = chargeMax;
    }

    public void fullCharge() {
        this.currentCharge = this.chargeMax;
    }

    public void unCharge() {
        this.currentCharge = 0;
    }

    public float getChargeTotal() {
        return currentCharge;
    }


    public boolean isReady() {

        return ready;
    }

    public void setReady(boolean ready) {

        this.ready = ready;
    }

    public float getChargeRate() {
        return chargeRate;
    }

    public void activate() {
        if (!isReady()) {
            return;
        }
        this.activated = true;
    }

    public void unActivate() {
        this.activated = false;
    }

    public boolean isActivated() {
        return activated;
    }

    public abstract void specialAttack(Ball ball);

    public void execute(Ball ball) {
        if (!isActivated()) {
            return;
        }
        this.specialAttack(ball);
        this.unCharge();
        this.setReady(false);
        this.unActivate();
    }

    public void update(float delta, Ball ball) {
        return;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void charge(float deltaTime) {
        if (!this.ready) {
            this.currentCharge += this.chargeRate * deltaTime;
            if (this.currentCharge >= this.chargeMax) {
                this.currentCharge = this.chargeMax;
                this.ready = true;
            }
        }
    }
}
