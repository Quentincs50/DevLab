package com.jeudefoot.coupedumonde.entite.Player;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jeudefoot.coupedumonde.entite.Ball.Ball;
import com.jeudefoot.coupedumonde.entite.Character.Character;

import static com.jeudefoot.coupedumonde.Utils.Constants.*;

/**
 * IA INTELLIGENTE avec gestion complète de toutes les mécaniques :
 * - Déplacement intelligent avec anticipation
 * - Saut simple et double jump stratégique
 * - Dash pour intercepter ou esquiver
 * - Tir normal (shot) et headshot
 * - Utilisation des super pouvoirs
 * - Positionnement défensif/offensif
 */
public class IA extends Player2 {

    private Ball ball;

    // ========== PARAMÈTRES DE DIFFICULTÉ ==========
    private float reactionTime = 0.1f;          // Temps de réaction (0.05 = très rapide, 0.3 = lent)
    private float anticipationFactor = 0.6f;    // Anticipation de trajectoire (0-1)
    private float aggressiveness = 0.7f;        // Agressivité (0.3 = défensif, 1.5 = ultra agressif)
    private float shotAccuracy = 0.9f;          // Précision des tirs (0-1)
    private float dashProbability = 0.7f;       // Probabilité d'utiliser le dash
    private float doubleJumpProbability = 0.8f; // Probabilité d'utiliser le double jump

    // ========== ÉTAT INTERNE ==========
    private float aiTimer = 0f;
    private float defensivePositionX = 80f;     // Position défensive (côté droit)
    private float attackZoneX = 50f;            // Zone d'attaque
    private AIState currentAIState = AIState.DEFENSIVE;
    private boolean shouldDash = false;
    private boolean shouldDoubleJump = false;

    private enum AIState {
        DEFENSIVE,      // Se positionne défensivement
        ATTACKING,      // Poursuit la balle agressivement
        INTERCEPTING,   // Intercepte une trajectoire
        SHOOTING        // En position de tir
    }

    public IA(World world, Character character, Ball ball) {
        super(world, character);
        this.ball = ball;
        this.facingRight = false; // IA regarde vers la gauche (vers le joueur)
    }

    @Override
    public void inputMovement(Ball ball) {
        // Update du timer de réaction
        aiTimer += 0.016f; // ~60 FPS
        if (aiTimer < reactionTime) return;
        aiTimer = 0f;

        // Analyse de la situation et prise de décision
        analyzeSituation();

        // Actions selon l'état
        handleMovement();
        handleJumping();
        handleDashing();
        handleShooting();
        handleHeadshot();
        handleSuperPower();
    }

    // ========== ANALYSE DE LA SITUATION ==========
    private void analyzeSituation() {
        Vector2 ballPos = ball.getBody().getPosition();
        Vector2 myPos = body.getPosition();
        Vector2 ballVel = ball.getBody().getLinearVelocity();

        float distanceToBall = ballPos.dst(myPos);
        float ballDirectionX = ballVel.x;

        // Détermine l'état stratégique
        if (distanceToBall < 15f && isDistanceBall()) {
            currentAIState = AIState.SHOOTING;
        } else if (ballDirectionX > 0 && ballPos.x > myPos.x) {
            // Balle va vers mon but
            currentAIState = AIState.INTERCEPTING;
        } else if (distanceToBall < 30f * aggressiveness) {
            currentAIState = AIState.ATTACKING;
        } else {
            currentAIState = AIState.DEFENSIVE;
        }

        // Décisions tactiques
        shouldDash = shouldUseDash(ballPos, myPos, ballVel);
        shouldDoubleJump = shouldUseDoubleJump(ballPos, myPos);
    }

    // ========== GESTION DU DÉPLACEMENT ==========
    private void handleMovement() {
        if (isDead() || dashing) return;

        Vector2 ballPos = ball.getBody().getPosition();
        Vector2 myPos = body.getPosition();

        float targetX = calculateTargetPosition(ballPos, myPos);
        float distanceToTarget = Math.abs(targetX - myPos.x);

        // Si on est proche de la cible, on s'arrête
        if (distanceToTarget < 5f) {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
            return;
        }

        // Déplacement vers la cible
        if (targetX < myPos.x) {
            moveLeft();
            setFacingRight(false);
        } else {
            moveRight();
            setFacingRight(true);
        }
    }

    private float calculateTargetPosition(Vector2 ballPos, Vector2 myPos) {
        Vector2 ballVel = ball.getBody().getLinearVelocity();

        switch (currentAIState) {
            case DEFENSIVE:
                // Retourne en position défensive
                return defensivePositionX;

            case ATTACKING:
                // Poursuit la balle avec anticipation
                return ballPos.x + (ballVel.x * anticipationFactor);

            case INTERCEPTING:
                // Intercepte la trajectoire
                float interceptTime = Math.abs(ballPos.x - myPos.x) / MAX_SPEED;
                return ballPos.x + (ballVel.x * interceptTime * anticipationFactor);

            case SHOOTING:
                // Se rapproche pour tirer
                return ballPos.x + (facingRight ? -5f : 5f);

            default:
                return defensivePositionX;
        }
    }

    // ========== GESTION DES SAUTS ==========
    private void handleJumping() {
        if (isDead() || !isGroundCollision()) return;

        Vector2 ballPos = ball.getBody().getPosition();
        Vector2 myPos = body.getPosition();

        boolean ballAbove = ballPos.y > myPos.y + 8f;
        boolean ballClose = Math.abs(ballPos.x - myPos.x) < 15f;

        // Saut simple pour atteindre la balle en l'air
        if (ballAbove && ballClose && jumpCount < 1) {
            jumpCount++;
            jump();
        }
    }

    private boolean shouldUseDoubleJump(Vector2 ballPos, Vector2 myPos) {
        if (jumpCount >= MAX_JUMPS) return false;
        if (isGroundCollision()) return false;

        boolean ballVeryHigh = ballPos.y > myPos.y + 12f;
        boolean ballInRange = Math.abs(ballPos.x - myPos.x) < 10f;
        boolean randomChance = MathUtils.random() < doubleJumpProbability;

        return ballVeryHigh && ballInRange && randomChance;
    }

    // ========== GESTION DU DASH ==========
    private void handleDashing() {
        if (!shouldDash || dashing || dashCooldownTimer > 0) return;
        if (isDead() || isGroundCollision()) return;

        dash();
    }

    private boolean shouldUseDash(Vector2 ballPos, Vector2 myPos, Vector2 ballVel) {
        if (isGroundCollision()) return false;
        if (dashCooldownTimer > 0) return false;

        float distanceToBall = ballPos.dst(myPos);
        boolean ballMovingFast = ballVel.len() > 20f;
        boolean needQuickInterception = distanceToBall > 15f && distanceToBall < 30f;
        boolean randomChance = MathUtils.random() < dashProbability;

        return (ballMovingFast || needQuickInterception) && randomChance;
    }

    // ========== GESTION DES TIRS ==========
    private void handleShooting() {
        if (!isDistanceBall()) return;
        if (isDead() || dashing) return;

        Vector2 ballPos = ball.getBody().getPosition();
        Vector2 myPos = body.getPosition();

        // Vérifie si on est en bonne position pour tirer
        boolean ballInFront = facingRight ? (ballPos.x > myPos.x) : (ballPos.x < myPos.x);
        if (ballInFront) {
            shot(ball);
        }
    }

    @Override
    public void shot(Ball ball) {
        setAnimationState(AnimState.KICK);

        // Applique l'impulsion
        Vector2 shotImpulse = new Vector2(- (character.getShotImpulse() * body.getMass()), body.getMass() * 2f);
        ball.getBody().applyLinearImpulse(shotImpulse, ball.getBody().getWorldCenter(), true);
    }

    // ========== GESTION DU HEADSHOT ==========
    private void handleHeadshot() {
        if (!isContactHeadBall()) return;
        if (isDead() || dashing) return;

        Vector2 ballPos = ball.getBody().getPosition();
        Vector2 myPos = body.getPosition();

        // Headshot si la balle est au-dessus
        boolean ballAbove = ballPos.y > myPos.y;
        boolean shouldHeadshot = MathUtils.random() < 0.8f; // 80% de chance

        if (ballAbove && shouldHeadshot) {
            headShot(ball);
        }
    }

    @Override
    public void headShot(Ball ball) {
        setAnimationState(AnimState.HEADSHOT);

        // Direction intelligente (vers le but ou vers le haut)
        Vector2 myPos = body.getPosition();
        Vector2 targetGoal = new Vector2(0, 20f);
        Vector2 dir = targetGoal.sub(myPos).nor();

        ball.getBody().applyLinearImpulse(
            dir.scl(body.getMass() * HEADSHOT_IMPULSE),
            ball.getBody().getWorldCenter(),
            true
        );
    }

    // ========== GESTION DES SUPER POUVOIRS ==========
    private void handleSuperPower() {
        if (!character.getSuperPower().isReady()) return;
        if (character.getSuperPower().isActivated()) return;

        // Active le super pouvoir dans des situations critiques
        boolean criticalSituation = isCriticalSituation();
        boolean randomActivation = MathUtils.random() < 0.15f; // 15% de chance aléatoire

        if (criticalSituation || randomActivation) {
            character.getSuperPower().activate();
        }

        // Utilise le super pouvoir si activé et balle proche
        if (contactBall && character.getSuperPower().isActivated()) {
            useSuperPower(ball);
        }
    }

    private boolean isCriticalSituation() {
        Vector2 ballPos = ball.getBody().getPosition();
        Vector2 ballVel = ball.getBody().getLinearVelocity();

        // Balle rapide vers mon but
        boolean dangerousBall = ballVel.x > 15f && ballPos.x > 70f;

        // Balle très proche du but adverse (opportunité)
        boolean scoringOpportunity = ballPos.x < 20f && isDistanceBall();

        return dangerousBall || scoringOpportunity;
    }

    // ========== SETTERS POUR AJUSTER LA DIFFICULTÉ ==========
    public void setReactionTime(float reactionTime) {
        this.reactionTime = reactionTime;
    }

    public void setAnticipationFactor(float anticipationFactor) {
        this.anticipationFactor = MathUtils.clamp(anticipationFactor, 0f, 1f);
    }

    public void setAggressiveness(float aggressiveness) {
        this.aggressiveness = MathUtils.clamp(aggressiveness, 0.3f, 2f);
    }

    public void setShotAccuracy(float shotAccuracy) {
        this.shotAccuracy = MathUtils.clamp(shotAccuracy, 0f, 1f);
    }

    public void setDashProbability(float dashProbability) {
        this.dashProbability = MathUtils.clamp(dashProbability, 0f, 1f);
    }

    public void setDoubleJumpProbability(float doubleJumpProbability) {
        this.doubleJumpProbability = MathUtils.clamp(doubleJumpProbability, 0f, 1f);
    }

    // Présets de difficulté
    public void setDifficultyEasy() {
        reactionTime = 0.3f;
        anticipationFactor = 0.3f;
        aggressiveness = 0.4f;
        shotAccuracy = 0.5f;
        dashProbability = 0.3f;
        doubleJumpProbability = 0.4f;
    }

    public void setDifficultyMedium() {
        reactionTime = 0.15f;
        anticipationFactor = 0.6f;
        aggressiveness = 0.7f;
        shotAccuracy = 0.75f;
        dashProbability = 0.6f;
        doubleJumpProbability = 0.7f;
    }

    public void setDifficultyHard() {
        reactionTime = 0.05f;
        anticipationFactor = 0.9f;
        aggressiveness = 1.2f;
        shotAccuracy = 0.95f;
        dashProbability = 0.9f;
        doubleJumpProbability = 0.95f;
    }

    // Override pour le footSensor (hérite de Player2)
    @Override
    public void createFootSensor() {
        PolygonShape foot = new PolygonShape();
        foot.setAsBox(
            FOOT_DIM.x, FOOT_DIM.y,
            new Vector2(-FOOT_POS_X, FOOT_POS_Y),
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
