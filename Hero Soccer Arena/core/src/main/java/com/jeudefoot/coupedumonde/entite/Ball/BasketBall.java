package com.jeudefoot.coupedumonde.entite.Ball;

import com.badlogic.gdx.physics.box2d.World;
import com.jeudefoot.coupedumonde.Utils.AssetsManager;

public class BasketBall extends Ball {

    public BasketBall(AssetsManager asset, World world) {
        super(asset.basketBall(), world);
        this.name = "Ballon de basket";
        this.isUnlocked = true;
    }
}
