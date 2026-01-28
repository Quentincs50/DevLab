package com.jeudefoot.coupedumonde.entite.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jeudefoot.coupedumonde.entite.Ball.Ball;
import com.jeudefoot.coupedumonde.entite.Character.Character;

import static com.jeudefoot.coupedumonde.Utils.Constants.*;


public class Player2 extends Player{

    public Player2(World world, Character character){
        super(world, character, POS_P2);
        this.facingRight = false;
    }
    @Override
    public void inputMovement(Ball ball){
        boolean move = false;

        if (isDead() || dashing) return;

        moving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveRight();
            setFacingRight(true);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveLeft();
            setFacingRight(false);
            moving = true;
        }
        if (( Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) && !isGroundCollision() && Gdx.input.isKeyPressed(Input.Keys.M)) {
            this.dash();
            return;
        }
        // STOP inertia from previous inputs
        if (!moving && !dashing) {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && jumpCount < MAX_JUMPS) {
            jumpCount++;
            jump();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            setAnimationState(AnimState.KICK);
            if (isDistanceBall() && !facingRight) shot(ball);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.L)) {
            setAnimationState(AnimState.HEADSHOT);
            if (isContactHeadBall() && facingRight) headShot(ball);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            this.character.getSuperPower().activate();
        }

    }

    @Override
    public void createFootSensor(){
        PolygonShape foot = new PolygonShape();
        foot.setAsBox(
            FOOT_DIM.x, FOOT_DIM.y,
            new Vector2(- FOOT_POS_X, FOOT_POS_Y),
            0
        );

        FixtureDef def = new FixtureDef();
        def.shape = foot;
        def.isSensor = true;

        Fixture fixture = body.createFixture(def);
        fixture.setUserData("foot");
        foot.dispose();
    }

}
