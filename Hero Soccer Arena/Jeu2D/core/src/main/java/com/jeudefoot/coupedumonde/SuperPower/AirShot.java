package com.jeudefoot.coupedumonde.SuperPower;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jeudefoot.coupedumonde.entite.Ball.Ball;

import static com.jeudefoot.coupedumonde.Utils.Constants.*;

public class AirShot extends SuperPower {

    private float levitationTimer = 0f;
    private float LEVITATION_DURATION = 1.5f;
    private float randomY;
    private Vector2 shotImpulse;
    private Vector2 shotImpulsePlayer1;
    private Vector2 shotImpulsePlayer2;
    private boolean isExecuting = false;

    public AirShot() {
        super("air shot", 100, 100, 5);
    }

    @Override
    public void specialAttack(Ball ball) {
        levitationTimer = 0;
        ball.setActiveSuperPower(this);
        isExecuting = true;  //  Ce superpower est en cours

        Vector2 airShotPosition = new Vector2(this.player.getStartPosition().x > GAME_WIDTH / 2f ? AIRSHOT_POS_PLAYER2 : AIRSHOT_POS_PLAYER1);

        this.player.setDistanceBall(false);
        this.player.setContactBall(false);

        ball.stopMoving();
        ball.teleportTo(airShotPosition);
        ball.becomeKinematic();
        ball.setLevitate(true);

        randomY = MathUtils.random(- GAME_HEIGHT * 0.4f, - GAME_HEIGHT * 0.2f);
        shotImpulsePlayer1 = new Vector2(AIRSHOT_FORCE, randomY);
        shotImpulsePlayer2 = new Vector2(-AIRSHOT_FORCE, randomY);
        shotImpulse = new Vector2(this.player.getStartPosition().x > GAME_WIDTH / 2f ? shotImpulsePlayer2 : shotImpulsePlayer1);
    }

    @Override
    public void update(float deltaTime, Ball ball) {
        if (ball.isLevitate() && isExecuting && ball.getActiveSuperPower() == this) {  // Vérifier que c'est le superpower du player.
            levitationTimer += deltaTime;

            if (levitationTimer < LEVITATION_DURATION) {
                spinLevitation(ball);
            } else {
                ball.becomeDynamic();
                ball.mouve(shotImpulse);
                levitationTimer = 0;
                ball.setActiveSuperPower(null);
                isExecuting = false;
                ball.setLevitate(false);
            }
        }
    }

    private void spinLevitation(Ball ball) {
        float spinSpeed = 10f;
        ball.spin(spinSpeed);
    }
}
