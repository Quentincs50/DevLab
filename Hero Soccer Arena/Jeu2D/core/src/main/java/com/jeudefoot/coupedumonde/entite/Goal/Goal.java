package com.jeudefoot.coupedumonde.entite.Goal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.jeudefoot.coupedumonde.Utils.AssetsManager;
import com.jeudefoot.coupedumonde.entite.Box2DActor;

import static com.jeudefoot.coupedumonde.Utils.Constants.*;

public class Goal extends Box2DActor {

    protected boolean isLeft;
    protected boolean goalScored;

    public Goal(World world, AssetsManager assets, boolean left) {
        super();

        this.isLeft = left;

        // ===== VISUEL =====
        storeAnimation("default", assets.magicGoal());
        setSize(GOAL_IMG_WIDTH, GOAL_IMG_HEIGHT);
        setActiveAnimation("default");

        setOriginCenter();

        // Position depuis les constantes
        if (isLeft) {
            setPosition(POS_GOAL_LEFT.x, POS_GOAL_LEFT.y);
        } else {
            setPosition(POS_GOAL_RIGHT.x, POS_GOAL_RIGHT.y);
            setScaleX(-1);
        }

        // ===== PHYSIQUE =====
        setStatic();
        setShapeRectangle();
        setPhysicsProperties(0f, 0f, 0f);
        initializePhysics(world);

        createGoalFixtures();

        body.setUserData(this);
    }

    private void createGoalFixtures() {
        float hw = GOAL_WIDTH / 2f;
        float hh = GOAL_HEIGHT / 2f;

        // SENSOR pour détecter les buts (zone intérieure)
        PolygonShape sensor = new PolygonShape();
        sensor.setAsBox(hw, hh * 0.7f, new Vector2(0, - hh * 0.5f), 0);

        FixtureDef sensorDef = new FixtureDef();
        sensorDef.shape = sensor;
        sensorDef.isSensor = true;

        Fixture f = body.createFixture(sensorDef);
        f.setUserData("goal_sensor");
        sensor.dispose();

        // BARRE HORIZONTALE (crossbar)
        PolygonShape bar = new PolygonShape();
        bar.setAsBox(hw * 1.3f, hh * 0.15f, new Vector2(0, hh * 0.4f), 0);

        FixtureDef barDef = new FixtureDef();
        barDef.shape = bar;
        barDef.restitution = 0.7f;
        barDef.friction = 0.1f;

        Fixture barFixture = body.createFixture(barDef);
        barFixture.setUserData("goal_bar");
        bar.dispose();

    }

    // =====================
    // LOGIQUE DE BUT
    // =====================

    public void goalScore(boolean value) {
        this.goalScored = value;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public boolean isGoalScored() {
        return this.goalScored;
    }
}
