package com.jeudefoot.coupedumonde.entite;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.badlogic.gdx.physics.box2d.BodyDef.*;
import static com.jeudefoot.coupedumonde.Utils.Constants.ACCELERATION;

public class Box2DActor extends AnimatedActor {
    protected Body body;
    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;

    protected float physicsWidth;
    protected float physicsHeight;

    protected float acceleration;
    protected Float maxSpeed;
    protected Float maxSpeedX;
    protected Float maxSpeedY;

    public Box2DActor() {
        super();
        body = null;
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        acceleration = ACCELERATION;
        maxSpeed = null;
        maxSpeedX = null;
        maxSpeedY = null;
    }

    public void setPhysicsSize(float width, float height) {
        this.physicsWidth = width;
        this.physicsHeight = height;
    }

    public float getPhysicsWidth() {
        return physicsWidth;
    }

    public float getPhysicsHeight() {
        return physicsHeight;
    }

    public void setStatic() {
        bodyDef.type = BodyType.StaticBody;
    }

    public void setDynamic() {
        bodyDef.type = BodyType.DynamicBody;
    }

    public void setFixedRotation() {
        bodyDef.fixedRotation = true;
    }

    public void setShapeRectangle() {
        setOriginCenter();
        bodyDef.position.set(getX() + getOriginX(), getY() + getOriginY());

        PolygonShape rect = new PolygonShape();
        rect.setAsBox(getWidth() / 2f, getHeight() / 2f);
        fixtureDef.shape = rect;
    }

    public void setShapeCircle() {
        setOriginCenter();
        bodyDef.position.set(getX() + getOriginX(), getY() + getOriginY());

        CircleShape circle = new CircleShape();
        circle.setRadius(getWidth() / 2f);
        fixtureDef.shape = circle;
    }

    public void setPhysicsProperties(float density, float friction, float restitution) {
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
    }

    public float getDensity() {
        return fixtureDef.density;
    }

    public float getFriction() {
        return fixtureDef.friction;
    }

    public float getRestitution() {
        return fixtureDef.restitution;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public BodyDef getBodyDef() {
        return bodyDef;
    }

    public void setMaxSpeed(float f) {
        maxSpeed = f;
    }

    public float getMaxSpeedX() {
        return maxSpeedX;
    }

    public void setMaxSpeedX(float f) {
        maxSpeedX = f;
    }

    public float getMaxSpeedY() {
        return maxSpeedY;
    }

    public void setMaxSpeedY(float f) {
        maxSpeedY = f;
    }

    public Vector2 getVelocity() {
        return body.getLinearVelocity();
    }

    public float getSpeed() {
        return getVelocity().len();
    }

    public void setVelocity(float vx, float vy) {
        body.setLinearVelocity(vx, vy);
    }

    public void setVelocity(Vector2 v) {
        body.setLinearVelocity(v);
    }

    public void setSpeed(float s) {
        setVelocity(getVelocity().setLength(s));
    }

    public void initializePhysics(World w) {
        body = w.createBody(bodyDef);
        body.setUserData(this);
    }

    public void initializeStadiumPhysics(World w) {
        body = w.createBody(bodyDef);
        body.setUserData(this);
    }

    public void setDampling(float d){
        bodyDef.linearDamping = d;
    }

    public void initializeBallPhysic(World w){
        body = w.createBody(bodyDef);
        Fixture fixture = body.createFixture(fixtureDef);
        body.setUserData(this);
        fixture.setUserData("ball");
        fixtureDef.shape.dispose();
    }

    public Body getBody() {
        return body;
    }

    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }


    @Override
    public void act(float dt) {
        super.act(dt);

        if (body == null) return;

        // cap max speeds, if they have been set
        if (maxSpeedX != null) {
            Vector2 v = getVelocity();
            v.x = MathUtils.clamp(v.x, -maxSpeedX, maxSpeedX);
            setVelocity(v);
        }
        if (maxSpeedY != null) {
            Vector2 v = getVelocity();
            v.y = MathUtils.clamp(v.y, -maxSpeedY, maxSpeedY);
            setVelocity(v);
        }
        if (maxSpeed != null) {
            float s = getSpeed();
            if (s > maxSpeed)
                setSpeed(maxSpeed);
        }

        // update image data, position&rotation, based on physics data
        Vector2 center = body.getPosition();
        setPosition(center.x - getOriginX(), center.y - getOriginY());

        float a = body.getAngle();      // angle in radians
        setRotation(a * MathUtils.radiansToDegrees); // convert to degrees
    }
}
