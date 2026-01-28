package com.jeudefoot.coupedumonde.entite.Ball;

import com.badlogic.gdx.physics.box2d.World;
import com.jeudefoot.coupedumonde.Utils.AssetsManager;

public class SoccerBall  extends Ball {

    private AssetsManager asset;
    public SoccerBall(AssetsManager asset,World world) {
        super(asset.soccerBall(),world);
        this.name = "Ballon de foot";
        this.isUnlocked = true;
    }
}
