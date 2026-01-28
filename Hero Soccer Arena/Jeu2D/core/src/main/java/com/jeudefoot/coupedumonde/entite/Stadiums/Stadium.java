package com.jeudefoot.coupedumonde.entite.Stadiums;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import static com.jeudefoot.coupedumonde.Utils.Constants.*;

public class Stadium extends Group {
    protected Body body;
    protected BodyDef bodyDef;
    private Image backgroundImage;

    public Stadium(World world, Texture background) {
        super();

        // ===== VISUEL ===== //
        backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(background)));
        backgroundImage.setSize(GAME_WIDTH, GAME_HEIGHT);
        backgroundImage.setPosition(0, 0);

        // IMPORTANT : Ajouter l'image au Group !
        addActor(backgroundImage);

        // ===== PHYSIC =====
        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.StaticBody;
        this.body = world.createBody(bodyDef);
        this.body.setUserData(this);
        createGround();
        createWalls();
    }

    // ===================== //
    //      PHYSIQUE         //
    // ===================== //

    private void createGround() {
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(
            GROUND_DIMENSION.x / 2, GROUND_DIMENSION.y / 2,
            GROUND_POS,  // Position corrigée
            0
        );

        FixtureDef fGround = new FixtureDef();
        fGround.shape = groundShape;
        fGround.friction = 0.4f;
        fGround.restitution = 0f;

        body.createFixture(fGround).setUserData("ground");
        groundShape.dispose();
    }

    private void createWalls() {
        // Mur gauche
        PolygonShape leftWall = new PolygonShape();
        leftWall.setAsBox(
            WALL_DIMENSION.x / 2, WALL_DIMENSION.y / 2,
            LEFTWALL_POS,
            0
        );

        FixtureDef fLeft = new FixtureDef();
        fLeft.shape = leftWall;
        fLeft.friction = 0f;

        body.createFixture(fLeft).setUserData("leftWall");
        leftWall.dispose();

        // Mur droit
        PolygonShape rightWall = new PolygonShape();
        rightWall.setAsBox(
            WALL_DIMENSION.x / 2, WALL_DIMENSION.y / 2,
            RIGHTWALL_POS,
            0
        );

        FixtureDef fRight = new FixtureDef();
        fRight.shape = rightWall;
        fRight.friction = 0f;

        body.createFixture(fRight).setUserData("rightWall");
        rightWall.dispose();
    }
}
