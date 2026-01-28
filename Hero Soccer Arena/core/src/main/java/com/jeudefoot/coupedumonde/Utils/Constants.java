package com.jeudefoot.coupedumonde.Utils;


import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static final float PPM = 100f;

    // window
    public static final int GAME_WIDTH = 100;
    public static final int GAME_HEIGHT = 60;
    public static final int WINDOW_WIDTH = 1024;
    public static final int WINDOW_HEIGHT = 768;

    // Dimension
    public static final int CHAR_WIDTH = 18;
    public static final int CHAR_HEIGHT = 18;
    public static final Vector2 FOOT_DIM = new Vector2(CHAR_WIDTH * 0.04f, CHAR_HEIGHT * 0.07f);
    public static final float HEAD_RADIUS = CHAR_WIDTH * 0.23f;
    public static final Vector2 BODY_FIXTURE = new Vector2(CHAR_WIDTH * 0.10f, CHAR_HEIGHT * 0.29f);
    public static final int BALL_RADIUS = 3;
    public static final int GOAL_WIDTH = 20;
    public static final int GOAL_HEIGHT = 25;
    public static final int GOAL_IMG_WIDTH = 30;
    public static final int GOAL_IMG_HEIGHT = 30;
    public static final Vector2 GROUND_DIMENSION = new Vector2(GAME_WIDTH, 2);
    public static final Vector2 WALL_DIMENSION = new Vector2(2, WINDOW_HEIGHT);

    // Position
    public static final Vector2 POS_P1 = new Vector2(GAME_WIDTH * 0.2f, 30);
    public static final Vector2 POS_P2 = new Vector2(GAME_WIDTH * 0.8f, 30);
    public static final float FOOT_POS_X = CHAR_WIDTH * 0.15f;
    public static final float FOOT_POS_Y = - CHAR_HEIGHT * 0.27f;
    public static final Vector2 HEAD_POS = new Vector2(-0.2f, CHAR_HEIGHT * 0.05f);
    public static final Vector2 BODY_POS = new Vector2(0, -CHAR_HEIGHT * 0.05f);
    public static final Vector2 POS_BALL = new Vector2(GAME_WIDTH * 0.5f, 30);
    public static final float AIRSHOT_POS_Y = GAME_HEIGHT * 0.6f;
    public static final  Vector2 AIRSHOT_POS_PLAYER1 = new Vector2(POS_P1.x, AIRSHOT_POS_Y);
    public static final Vector2  AIRSHOT_POS_PLAYER2 = new Vector2(POS_P2.x, AIRSHOT_POS_Y);
    public static final float AIRSHOT_FORCE = 400f;
    public static final Vector2 POS_GOAL_LEFT = new Vector2(- 12, GAME_HEIGHT * 0.2f);
    public static final Vector2 POS_GOAL_RIGHT = new Vector2(GAME_WIDTH * 0.82f, GAME_HEIGHT * 0.2f);
    public static final Vector2 GROUND_POS = new Vector2(GAME_WIDTH / 2f, GAME_HEIGHT * 0.2f);
    public static final Vector2 LEFTWALL_POS = new Vector2(0 , GAME_HEIGHT);
    public static final Vector2 RIGHTWALL_POS = new Vector2(GAME_WIDTH , GAME_HEIGHT);

    // Bodies Properties
    public static final float DENSITY = 1.0f;
    public static final float FRICTION = 0.5f;
    public static final float RESTITUTION = 0.1f;

    // Stats
    public static final float SHOT_IMPULSE = 3f;
    public static final float HEADSHOT_IMPULSE = 10f;
    public static final float JUMP_IMPULSE = 20f;
    public static final float SECOND_JUMP_IMPULSE = 35f;
    public static final float SCD_JUMP_X = 10f;
    public static final float ACCELERATION = 5f;
    public static final float MAX_SPEED = 10f;
    public static final float DASH_DURATION = 0.15f;
    public static final float BACKFLIP_DURATION = 0.15f;
    public static final float DASH_COOLDOWN = 0.8f;
    public static final float DASH_SPEED = 45f;
    public static final float MAX_DASH_SPEED = 200f;
    public static final float MAX_JUMPS = 2;
    public static final float SALTO_ROTATION_SPEED = 20f;

    public static final float FREEZING = 2f;
    public static final int DAMAGE = 20;
    public static final int MIN_DAMAGE_SPEED = 15;

    // CHARACTER SELECTION
    public static final int STATS_PAD = 5;

    // TIMER
    public static final int TIMER = 60;

    // Race
    public enum Race {
        FALLEN_ANGEL_1,
        FALLEN_ANGEL_2,
        FALLEN_ANGEL_3,
        GOBELIN,
        OGRE,
        ORC,
        GOLEM_1,
        GOLEM_2,
        GOLEM_3,
        MINOTAUR_1,
        MINOTAUR_2,
        MINOTAUR_3,
        NECROMANCER_1,
        NECROMANCER_2,
        NECROMANCER_3,
        ORACLE_1,
        ORACLE_2,
        ORACLE_3,
        REAPER_1,
        REAPER_2,
        REAPER_3,
        SKELETON_1,
        SKELETON_2,
        SKELETON_3,
        VALKYRIE_1,
        VALKYRIE_2,
        VALKYRIE_3,
        ZOMBIE_1,
        ZOMBIE_2,
        ZOMBIE_3
    }
}
