package com.jeudefoot.coupedumonde.SuperPower;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.jeudefoot.coupedumonde.entite.Ball.Ball;
import com.jeudefoot.coupedumonde.pannel.Timer;

public class InvisibleShot extends SuperPower {

    private float Timer = 0f;
    private float invisibleDuration = 2f; // 2 secondes de levitation
    private Vector2 shotImpulse = new Vector2(8, 2);

    public InvisibleShot() {

        super("air shot", 100, 100, 2);
        this.isReady();
    }

    @Override
    public void specialAttack(Ball ball) {

        ball.setVisible(false);

    }

    @Override
    public void update(float deltaTime, Ball ball) {

        if (!ball.isVisible()) {

            ball.mouve(shotImpulse);

            Timer += deltaTime; // Le timer se met en route dès que la balle est invisible

            if (Timer > invisibleDuration) {

                ball.setVisible(true);
            }
        }
    }
}
