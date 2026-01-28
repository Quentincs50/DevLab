package com.jeudefoot.coupedumonde.entite.Ball;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jeudefoot.coupedumonde.SuperPower.SuperPower;
import com.jeudefoot.coupedumonde.entite.Box2DActor;

import static com.jeudefoot.coupedumonde.Utils.Constants.*;

public abstract class Ball extends Box2DActor {

    protected String name;
    protected Vector2 startPosition;
    protected boolean isUnlocked;
    protected boolean isLevitate;
    protected boolean isVisible = true;

    protected SuperPower activeSuperPower;

    public Ball(Texture texture, World world) {
        super();

        this.startPosition = POS_BALL;
        this.activeSuperPower = null;

        // ===== ANIMATION / TEXTURE =====
        storeAnimation("default", texture);
        setSize(BALL_RADIUS, BALL_RADIUS);
        setActiveAnimation("default");
        setOriginCenter();

        setPosition(startPosition.x, startPosition.y);
        // ===== BOX2D =====
        setDynamic();
        setShapeCircle();
        setPhysicsProperties(0.5f, 0.2f, 0.6f);
        initializeBallPhysic(world);

    }

    @Override
    public void act(float dt) {
        super.act(dt);

        if (!isVisible) {
            setColor(1, 1, 1, 0);
        } else {
            setColor(1, 1, 1, 1);
        }
    }

    // =====================
    // GAMEPLAY METHODS
    // =====================

    public void teleportTo(Vector2 position) {
        body.setTransform(position, body.getAngle());
        body.setLinearVelocity(0, 0);
    }

    public void spin(float angularSpeed) {
        body.setAngularVelocity(angularSpeed);
    }

    public void becomeKinematic() {
        body.setType(BodyDef.BodyType.KinematicBody);
    }

    public void becomeDynamic() {
        body.setType(BodyDef.BodyType.DynamicBody);
    }

    public void changeGravity(float gravityCoef) {
        body.setGravityScale(gravityCoef);
    }

    public void mouve(Vector2 impulse) {
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
    }

    public void stopMoving() {
        body.setLinearVelocity(Vector2.Zero);
        body.setAngularVelocity(0);
    }

    public void resetPosition() {
        body.setTransform(startPosition, 0);
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
    }

    // =====================
    // GETTERS / SETTERS
    // =====================

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.isUnlocked = unlocked;
    }

    public boolean isLevitate() {
        return isLevitate;
    }

    public void setLevitate(boolean levitate) {
        isLevitate = levitate;
    }

    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    public SuperPower getActiveSuperPower() {
        return activeSuperPower;
    }

    public void setActiveSuperPower(SuperPower activeSuperPower) {
        this.activeSuperPower = activeSuperPower;
    }

}
