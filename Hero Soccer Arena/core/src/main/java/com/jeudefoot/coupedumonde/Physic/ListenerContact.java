package com.jeudefoot.coupedumonde.Physic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.jeudefoot.coupedumonde.entite.Ball.Ball;
import com.jeudefoot.coupedumonde.entite.Goal.Goal;
import com.jeudefoot.coupedumonde.entite.Player.Player;

import static com.jeudefoot.coupedumonde.Utils.Constants.JUMP_IMPULSE;

public class ListenerContact implements ContactListener {

    @Override
    public void beginContact(Contact contact){
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        Object dataA = a.getBody().getUserData();
        Object dataB = b.getBody().getUserData();

        Object tagA = a.getUserData();
        Object tagB = b.getUserData();

        if (dataA == null || dataB == null) return;

        // Détection du sol et player
        if (dataA instanceof Player && tagB.equals("ground")){
            ((Player) dataA).setGroundCollision(true);
            ((Player) dataA).setJumpCount(0);
            ((Player) dataA).getCharacter().setJumpForce(JUMP_IMPULSE);
        } else if (dataB instanceof Player && tagA.equals("ground")) {
            ((Player)dataB).setGroundCollision(true);
            ((Player) dataB).setJumpCount(0);
            ((Player) dataB).getCharacter().setJumpForce(JUMP_IMPULSE);
        }

        // Debut du contact du player et ball
        if (dataA instanceof Player && dataB instanceof Ball) {
            ((Player) dataA).setContactBall(true);
        } else if (dataB instanceof Player && dataA instanceof Ball) {
            ((Player) dataB).setContactBall(true);
            ((Player) dataB).setHitByBall(true);
        }

        // Détection du pied et ball
        if ("foot".equals(tagA) && dataB instanceof Ball){
            if (dataA instanceof Player) {
                ((Player) dataA).setDistanceBall(true);
            }
        } else if ("foot".equals(tagB) && dataA instanceof Ball) {
            if (dataB instanceof Player) {
                ((Player) dataB).setDistanceBall(true);
            }
        }


        if ("head".equals(tagA) && dataB instanceof Ball){
            if (dataA instanceof Player) {
                ((Player) dataA).setContactHeadBall(true);
            }
        } else if ("head".equals(tagB) && dataA instanceof Ball) {
            if (dataB instanceof Player) {
                ((Player) dataB).setContactHeadBall(true);
            }
        }

        if ("goal_sensor".equals(tagA) && dataB instanceof Ball) {
            if (dataA instanceof Goal) {
                ((Goal) dataA).goalScore(true);
            }
        } else if("goal_sensor".equals(tagB) && dataA instanceof Ball){
            if(dataB instanceof Goal){
                ((Goal) dataB).goalScore(true);
            }
        }
    }

    @Override
    public void endContact(Contact contact){

        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        Object dataA = a.getBody().getUserData();
        Object dataB = b.getBody().getUserData();

        Object tagA = a.getUserData();
        Object tagB = b.getUserData();

        if (dataA == null || dataB == null) return;

        if (dataA instanceof Player && tagB.equals("ground")){
            ((Player)dataA).setGroundCollision(false);
        } else if (dataB instanceof Player && tagA.equals("ground")) {
            ((Player)dataB).setGroundCollision(false);
        }

        if (dataA instanceof Player && dataB instanceof Ball) {
            ((Player) dataA).setContactBall(false);
        } else if (dataB instanceof Player && dataA instanceof Ball) {
            ((Player) dataB).setContactBall(false);
            ((Player) dataB).setHitByBall(false);
        }

        if ("foot".equals(tagA) && dataB instanceof Ball){
            if (dataA instanceof Player) {
                ((Player) dataA).setDistanceBall(false);
            }
        } else if ("foot".equals(tagB) && dataA instanceof Ball) {
            if (dataB instanceof Player) {
                ((Player) dataB).setDistanceBall(false);
            }
        }

        if ("head".equals(tagA) && dataB instanceof Ball){
            if (dataA instanceof Player) {
                ((Player) dataA).setContactHeadBall(false);
            }
        } else if ("head".equals(tagB) && dataA instanceof Ball) {
            if (dataB instanceof Player) {
                ((Player) dataB).setContactHeadBall(false);
            }
        }


        if ("goal_sensor".equals(tagA) && dataB instanceof Ball) {
            if (dataA instanceof Goal) {
                ((Goal) dataA).goalScore(false);
            }
        } else if("goal_sensor".equals(tagB) && dataA instanceof Ball){
            if(dataB instanceof Goal){
                ((Goal) dataB).goalScore(false);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
