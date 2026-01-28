package com.jeudefoot.coupedumonde.entite.Player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.jeudefoot.coupedumonde.entite.Ball.Ball;
import com.jeudefoot.coupedumonde.entite.Box2DActor;
import com.jeudefoot.coupedumonde.entite.Character.Character;
import com.jeudefoot.coupedumonde.Controller.Controller;

import static com.jeudefoot.coupedumonde.Main.assets;
import static com.jeudefoot.coupedumonde.Utils.Constants.*;

public abstract class Player extends Box2DActor implements Controller {

    protected Character character;
    protected Vector2 startPosition;
    protected boolean groundCollision;
    protected int jumpCount;
    protected boolean contactBall;
    protected boolean distanceBall;
    protected boolean contactHeadBall;
    protected boolean hitByBall = false;

    protected boolean invincible;
    protected boolean dead;
    protected boolean dashing = false;
    protected boolean spinning = false;
    protected boolean facingRight;
    protected boolean moving;
    protected float dashCooldownTimer = 0f;
    protected float dashTimer = 0f;
    protected float freezeTimer = 0;

    public enum AnimState {
        IDLE,
        RUN,
        JUMP,
        KICK,
        DYING,
        DASH,
        HEADSHOT,
    }
    protected AnimState currentState = AnimState.IDLE;

    public Player(World world, Character character, Vector2 startPosition) {
        this.character = character;
        groundCollision = false;
        jumpCount = 0;
        contactBall = false;
        distanceBall = false;
        hitByBall = false;
        invincible = false;

        this.storeAnimation("Idle", assets.getAnimation(character.getRace(), "Idle"));
        this.storeAnimation("Running", assets.getAnimation(character.getRace(), "Running"));
        this.storeAnimation("Jump Start", assets.getAnimation(character.getRace(), "Jump Start"));
        this.storeAnimation("Kicking", assets.getAnimation(character.getRace(), "Kicking"));
        this.storeAnimation("Dying", assets.getAnimation(character.getRace(), "Dying"));
        this.storeAnimation("Sliding", assets.getAnimation(character.getRace(), "Sliding"));
        this.storeAnimation("Throwing", assets.getAnimation(character.getRace(), "Throwing"));

        setSize(CHAR_WIDTH, CHAR_HEIGHT);
        setOriginCenter();
        setAnimationState(currentState);
        setDynamic();
        setFixedRotation();
        setPhysicsProperties(DENSITY, FRICTION, RESTITUTION);

        setMaxSpeedX(MAX_DASH_SPEED);
        this.startPosition = startPosition;

        this.getBodyDef().position.set(startPosition);
        initializePhysics(world);
        createFixtures();

        character.getSuperPower().setPlayer(this);
    }

    // =====================
    // GAMEPLAY METHODS
    // =====================
    public void moveLeft() {
        Vector2 vel = body.getLinearVelocity();
        body.setLinearVelocity(
            Math.max(vel.x - ACCELERATION, - MAX_SPEED),
            vel.y
        );
    }

    public void moveRight() {
        Vector2 vel = body.getLinearVelocity();
        body.setLinearVelocity(
            Math.min(vel.x + ACCELERATION, MAX_SPEED),
            vel.y
        );
    }

    public void dash() {
        if (dashCooldownTimer > 0) return;

        dashing = true;
        invincible = true;
        dashTimer = 0f;
        dashCooldownTimer = DASH_COOLDOWN;

        float direction = facingRight ? 1f : -1f;

        // DASH ACTION
        Vector2 vel = body.getLinearVelocity();
        body.setLinearVelocity(new Vector2(direction * (DASH_SPEED), vel.y)

        );
        setAnimationState(AnimState.DASH);
    }

    public void headShot(Ball ball){
        Vector2 dir = ball.getBody().getPosition().sub(body.getPosition()).nor();
        ball.getBody().applyLinearImpulse(dir.scl(body.getMass() * HEADSHOT_IMPULSE), ball.getBody().getWorldCenter(), true);
    }

    public void jump() {
        setAnimationState(AnimState.JUMP);
        body.setLinearVelocity(body.getLinearVelocity().x, 0);

        float dirX = 0f;

        if(jumpCount > 1){
            character.setJumpForce(SECOND_JUMP_IMPULSE);
            dirX = SCD_JUMP_X;
            body.setFixedRotation(false);
            backflip();
        }

        float impulse = body.getMass() * character.getJumpForce();

        body.applyLinearImpulse(
            new Vector2(dirX, impulse),
            body.getWorldCenter(),
            true
        );

    }

    public void backflip() {
        spinning = true;
        float direction = facingRight ? -1f : 1f; // Salto arrière
        float torque = body.getInertia() * SALTO_ROTATION_SPEED * direction;
        body.applyAngularImpulse(torque, true);
    }

    public void stopRotation() {
        body.setFixedRotation(true);
        body.setTransform(body.getPosition(), 0);
    }
    public void shot(Ball ball) {
        setAnimationState(AnimState.KICK);
        Vector2 dir = new Vector2(body.getMass() * character.getShotImpulse(), body.getMass() * 2f);
        ball.getBody().applyLinearImpulse(dir, ball.getBody().getWorldCenter(), true);
    }

    public void useSuperPower(Ball ball) {
        if (contactBall && character.getSuperPower().isActivated()) {
            character.getSuperPower().execute(ball);
        }
    }

    // =====================
    // BODY METHODS
    // =====================
    public void createFixtures() {
        createBodyFixture();
        createHeadFixture();
        createFootSensor();
    }

    public void createFootSensor() {
        PolygonShape foot = new PolygonShape();
        foot.setAsBox(
            FOOT_DIM.x, FOOT_DIM.y,
            new Vector2(FOOT_POS_X, FOOT_POS_Y),
            0
        );

        FixtureDef def = new FixtureDef();
        def.shape = foot;
        def.isSensor = true;

        Fixture fixture = body.createFixture(def);
        fixture.setUserData("foot");
        foot.dispose();
    }

    public void createHeadFixture() {
        CircleShape head = new CircleShape();
        head.setRadius(HEAD_RADIUS);
        head.setPosition(HEAD_POS);

        FixtureDef def = new FixtureDef();
        def.shape = head;
        def.restitution = 0.6f;

        Fixture fixture = body.createFixture(def);
        fixture.setUserData("head");
        head.dispose();
    }

    public void createBodyFixture() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
            BODY_FIXTURE.x, BODY_FIXTURE.y,
            new Vector2(BODY_POS),
            0
        );

        FixtureDef def = new FixtureDef();
        def.shape = shape;
        def.density = 1f;
        def.friction = 0.2f;
        def.restitution = 0f;

        Fixture fixture = body.createFixture(def);
        fixture.setUserData("body");
        shape.dispose();
    }

    // ================= DAMAGE & FREEZE =================
    public void stopMoving() {
        Vector2 v = body.getLinearVelocity();
        body.setLinearVelocity(0, v.y);
        body.setAngularVelocity(0);
    }

    private void stopHorizontalControl() {
        body.setLinearVelocity(
            body.getLinearVelocity().x * 0.95f,
            0
        );
    }

    public void getHurt(int damage) {
        if (isInvincible()) return;

        character.setCurrentHealth(character.getCurrentHealth() - damage);
        if (character.getCurrentHealth() <= 0) {
            character.setCurrentHealth(0);
            death();
        }
    }

    public void freeze(float delta) {
        if (!isDead()) {
            return;
        }

        freezeTimer += delta;
        if (freezeTimer >= character.getFreezingTime()) {
            character.setCurrentHealth(character.getMaxHealth());
            setDead(false);
            setAnimationState(AnimState.IDLE);
            freezeTimer = 0;
        }
    }

    public void death() {
        if (isDead()){
            return;
        }
        setDead(true);
        setFreezeTimer(0);
        setAnimationState(AnimState.DYING);
    }

    private void updateDash(float delta) {
        if (!dashing) return;

        dashTimer += delta;

        if (dashTimer >= DASH_DURATION) {
            dashing = false;
            invincible = false;
            dashTimer = 0f;
        }

    }

    private void updateBackflip(float delta) {
        if (!spinning) return;

        dashTimer += delta;

        if (dashTimer >= BACKFLIP_DURATION) {
            spinning = false;
            stopRotation();
            dashTimer = 0f;
        }
    }

    private void updateCooldowns(float delta) {
        if (dashCooldownTimer > 0) {
            dashCooldownTimer -= delta;
        }
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void resetPosition() {
        body.setTransform(startPosition, 0);
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
        body.setAwake(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);


        updateCooldowns(delta);
        updateDash(delta);
        updateBackflip(delta);

        // Gestion du gel
        freeze(delta);

        animate();
    }

    protected void setAnimationState(AnimState newState) {
        if (currentState == newState) return;

        currentState = newState;

        switch (newState) {
            case IDLE:
                setActiveAnimation("Idle");
                break;
            case RUN:
                setActiveAnimation("Running");
                break;
            case JUMP:
                setActiveAnimation("Jump Start");
                break;
            case KICK:
                setActiveAnimation("Kicking");
                break;
            case DYING:
                setActiveAnimation("Dying");
                break;
            case DASH:
                setActiveAnimation("Sliding");
                break;
            case HEADSHOT:
                setActiveAnimation("Throwing");
        }
    }

    protected void animate() {
        switch (this.getAnimationName()) {
            case "Idle":
                if (!groundCollision){
                    this.setAnimationState(AnimState.JUMP);
                }
                if (Math.abs(this.body.getLinearVelocity().x) > 0.1 && this.groundCollision) {
                    this.setAnimationState(AnimState.RUN);
                }
                if (this.getSpeed() < 0.1f){
                    this.setAnimationState(AnimState.IDLE);
                }
                break;
            case "Running":
                if (this.getSpeed() < 0.1) {
                    this.setAnimationState(AnimState.IDLE);
                }
                break;
            case "Kicking":
                if (this.isAnimationFinished()) {
                    this.setAnimationState(AnimState.IDLE);
                }
                break;
            case "Jump Start":
                if (this.isGroundCollision()) {
                    this.setAnimationState(AnimState.IDLE);
                }
                break;
            case "Sliding":
                if (!this.dashing) {
                    this.setAnimationState(AnimState.IDLE);
                }
            case "Throwing":
                if(this.isAnimationFinished()){
                    this.setAnimationState(AnimState.IDLE);
                }
        }
    }

    // ================= GETTERS / SETTERS =================
    public boolean isGroundCollision() {
        return groundCollision;
    }

    public void setGroundCollision(boolean value) {
        groundCollision = value;
    }

    public boolean isContactBall() {
        return contactBall;
    }

    public void setContactBall(boolean value) {
        contactBall = value;
    }

    public boolean isContactHeadBall() {
        return contactHeadBall;
    }

    public void setContactHeadBall(boolean contactHeadBall) {
        this.contactHeadBall = contactHeadBall;
    }

    public boolean isHitByBall() {
        return hitByBall;
    }

    public void setHitByBall(boolean value) {
        hitByBall = value;
    }

    public Character getCharacter() {
        return character;
    }

    public Vector2 getStartPosition() {
        return startPosition;
    }

    public boolean isDistanceBall() {
        return distanceBall;
    }

    public void setDistanceBall(boolean distanceBall) {
        this.distanceBall = distanceBall;
    }

    public void update(Ball ball) { this.inputMovement(ball); }

    public float getFreezeTimer() {
        return freezeTimer;
    }

    public void setFreezeTimer(float freezeTimer) {
        this.freezeTimer = freezeTimer;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public void setFacingRight(boolean value){
        facingRight = value;
        if (facingRight){
            setScaleX(1);
        }
        else {
            setScaleX(-1);
        }
    }

    public int getJumpCount() {
        return jumpCount;
    }

    public void setJumpCount(int jumpCount) {
        this.jumpCount = jumpCount;
    }
}
