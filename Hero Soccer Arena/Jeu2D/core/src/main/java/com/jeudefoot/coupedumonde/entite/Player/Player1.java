package com.jeudefoot.coupedumonde.entite.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import com.jeudefoot.coupedumonde.entite.Ball.Ball;
import com.jeudefoot.coupedumonde.entite.Character.Character;

import static com.jeudefoot.coupedumonde.Utils.Constants.*;


public class Player1 extends Player {

    public Player1(World world, Character character) {
        super(world, character, POS_P1);
        this.facingRight = true;
    }
    @Override
    public void inputMovement(Ball ball) {

        if (isDead() || dashing) return;

        moving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveRight();
            setFacingRight(true);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveLeft();
            setFacingRight(false);
            moving = true;
        }
        if (( Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.A)) && !isGroundCollision() && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.dash();
            return;
        }
        // STOP inertia from previous inputs
        if (!moving && !dashing) {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && jumpCount < MAX_JUMPS) {
            jumpCount++;
            jump();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            setAnimationState(AnimState.KICK);
            if (isDistanceBall() && facingRight) shot(ball);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
            setAnimationState(AnimState.HEADSHOT);
            if (isContactHeadBall() && facingRight) headShot(ball);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)){
            this.character.getSuperPower().activate();
        }
    }
}

