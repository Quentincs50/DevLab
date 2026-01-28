package com.jeudefoot.coupedumonde.entite.Ball;

import com.badlogic.gdx.physics.box2d.World;
import com.jeudefoot.coupedumonde.Utils.AssetsManager;

public class VolleyBall extends Ball {

    private AssetsManager asset;
    public VolleyBall(AssetsManager asset, World world) {
        super(asset.volleyBall(), world);
        this.name = "Ballon de volley";
        this.isUnlocked = true;
    }

}
