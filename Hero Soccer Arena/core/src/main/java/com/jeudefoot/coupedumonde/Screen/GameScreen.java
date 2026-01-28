package com.jeudefoot.coupedumonde.Screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jeudefoot.coupedumonde.Utils.AssetsManager;
import com.jeudefoot.coupedumonde.HUD.HUD;
import com.jeudefoot.coupedumonde.Physic.ListenerContact;
import com.jeudefoot.coupedumonde.Physic.PhysicWorld;
import com.jeudefoot.coupedumonde.entite.Ball.Ball;
import com.jeudefoot.coupedumonde.entite.Ball.SoccerBall;
import com.jeudefoot.coupedumonde.entite.Character.Character;
import com.jeudefoot.coupedumonde.entite.Character.YellowSkeleton;
import com.jeudefoot.coupedumonde.entite.Goal.Goal;
import com.jeudefoot.coupedumonde.entite.Player.IA;
import com.jeudefoot.coupedumonde.entite.Player.Player;
import com.jeudefoot.coupedumonde.entite.Player.Player1;
import com.jeudefoot.coupedumonde.entite.Player.Player2;
import com.jeudefoot.coupedumonde.entite.Stadiums.Stadium;
import com.jeudefoot.coupedumonde.pannel.PanelDesign;
import com.jeudefoot.coupedumonde.pannel.Timer;

import static com.jeudefoot.coupedumonde.Utils.Constants.WINDOW_HEIGHT;
import static com.jeudefoot.coupedumonde.Utils.Constants.WINDOW_WIDTH;

public class GameScreen implements Screen {
    private Game game;
    private FitViewport viewport;
    private SpriteBatch batch;
    private AssetsManager assets;
    private Goal goalleft;
    private Goal goalright;
    private Ball soccerball;
    private Stadium stadium;
    private PhysicWorld world;
    private Player player1;
    private Player player2;
    private Character computerCharacter;
    protected IA computer;
    private Box2DDebugRenderer debug;
    private int Score1 = 0;
    private int Score2 = 0;
    private Stage hudStage;
    private Stage stage;
    private PanelDesign panel;
    private HUD hud;
    private Label winnerLabel;

    private float WINNER_POS_X;
    private float WINNER_POS_Y;

    private Timer timer;
    private boolean finish = false;
    private TextButton titleScreen;

    public GameScreen(Game game, Player player1, Player player2, AssetsManager asset, Character characterPlayer1, Character computerCharacter, Texture stadiumTheme) {
        super();
        this.game = game;
        this.player1 = player1;
        this.player2 = player2;

        viewport = new FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT);
        stage = new Stage(new FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT));
        hudStage = new Stage(new FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT));
        assets = asset;
        world = new PhysicWorld();
        // debug = new Box2DDebugRenderer();
        world.getWorld().setContactListener(new ListenerContact());
        timer = new Timer(30f, true);


        stadium = new Stadium(world.getWorld(), stadiumTheme);
        soccerball = new SoccerBall(assets, world.getWorld());


        // Initialiser les goals
        goalleft = new Goal(world.getWorld(), assets, true);
        goalright = new Goal(world.getWorld(), assets, false);

        panel = new PanelDesign(Score1, Score2, timer.getTimeRemain(), WINDOW_WIDTH, WINDOW_HEIGHT);
        hudStage.addActor(panel.getPanelSupport());
        hudStage.addActor(panel.getScorePlayer1());
        hudStage.addActor(panel.getScorePlayer2());
        hudStage.addActor(panel.getTimer());
        hudStage.addActor(panel.getClock());

        hud = new HUD(WINDOW_WIDTH, WINDOW_HEIGHT, player1.getCharacter().getCurrentHealth(), computer.getCharacter().getCurrentHealth(), player1.getCharacter().getSuperPower().getChargeTotal(), computer.getCharacter().getSuperPower().getChargeTotal());

        hudStage.addActor(hud.getPlayer1LifeLogo());
        hudStage.addActor(hud.getPlayer1LifeSupport());
        hudStage.addActor(hud.getPlayer1LifeBar());
        hudStage.addActor(hud.getPlayer1SuperPowerLogo());
        hudStage.addActor(hud.getPlayer1SuperPowerSupport());
        hudStage.addActor(hud.getPlayer1SuperPowerBar());

        hudStage.addActor(hud.getPlayer2LifeLogo());
        hudStage.addActor(hud.getPlayer2LifeSupport());
        hudStage.addActor(hud.getPlayer2LifeBar());
        hudStage.addActor(hud.getPlayer2SuperPowerLogo());
        hudStage.addActor(hud.getPlayer2SuperPowerSupport());
        hudStage.addActor(hud.getPlayer2SuperPowerBar());

        WINNER_POS_X = WINDOW_WIDTH * 0.3f;
        WINNER_POS_Y = WINDOW_HEIGHT * 0.5f;

        Label.LabelStyle winnerStyle = new Label.LabelStyle();
        winnerStyle.font = assets.titleScreenFont;
        winnerLabel = new Label("", winnerStyle);
        winnerLabel.setPosition(WINNER_POS_X, WINNER_POS_Y);
        winnerLabel.setVisible(false);
        hudStage.addActor(winnerLabel);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = assets.buttonStartScreen();
        buttonStyle.down = assets.buttonStartScreen();
        buttonStyle.over = assets.buttonStartScreenShinny();
        buttonStyle.font = assets.titleScreenFont;

        titleScreen = new TextButton("Accueil", buttonStyle);
        titleScreen.setSize(WINDOW_WIDTH / 4, WINDOW_HEIGHT / 6);
        titleScreen.setPosition(WINDOW_WIDTH / 2 - titleScreen.getWidth() / 2, WINDOW_HEIGHT / 8);
        titleScreen.setVisible(false);
        titleScreen.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TitleScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        hudStage.addActor(titleScreen);

        stage.addActor(stadium);
        stage.addActor(goalleft);
        stage.addActor(goalright);
        stage.addActor(soccerball);
        stage.addActor(player1);
        stage.addActor(computer);
    }

    public void show() {
        world.update();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(hudStage);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // --- PHYSIQUE ---
        world.update();
        timer.update(delta);

        // --- LOGIQUE DE SCORE ---
        if (goalleft.isGoalScored()) {
            Score2++;
        }
        if (goalright.isGoalScored()) {
            Score1++;
        }

        panel.getScorePlayer1().setText("Score: " + Score1);
        panel.getScorePlayer2().setText("Score: " + Score2);
        panel.getTimer().setText("" + timer.getTimeRemain());

        // --- GAMEPLAY ---
        player1.update(soccerball);
        player1.useSuperPower(soccerball);
        player1.getCharacter().getSuperPower().update(delta, soccerball);
        player1.getCharacter().getSuperPower().charge(delta);

        computer.update(soccerball);
        computer.getCharacter().getSuperPower().update(delta, soccerball);
        computer.getCharacter().getSuperPower().charge(delta);

        updateHUD();
        updateState(delta);

        // --- SCENE2D ---
        stage.act(delta);
        stage.draw();

        hudStage.act(delta);
        hudStage.draw();

        // --- FIN DU MATCH ---
        if (timer.getTimeRemain() == 0 && !finish) {
            finish = true;
            showWinner();
        }
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    public void hide() {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {
        batch.dispose();
        world.dispose();
        stage.dispose();
        hudStage.dispose();
    }

    public void showWinner() {

        if (Score1 > Score2) {
            winnerLabel.setText("The Winner is Player 1");
        } else if (Score1 < Score2) {
            winnerLabel.setText("The Winner is Player 2");

        } else if (Score1 == 0 && Score2 == 0) {
            winnerLabel.setText("They're are sucks");

        } else if (Score1 == Score2) {
            winnerLabel.setText("Tie!");

        }
        winnerLabel.setVisible(true);
        titleScreen.setVisible(true);

        // Arret du match
        player1.stopMoving();
        computer.stopMoving();


    }

    public void updateState(float delta) {
        Vector2 v = soccerball.getBody().getLinearVelocity();
        if (v.len() > 6 && player1.isHitByBall()) {
            if (!(player1.getCharacter().getCurrentHealth() <= 0)) {
                player1.getHurt(20);
                System.out.println(soccerball.getBody().getLinearVelocity());
                System.out.println("PV player1 : " + player1.getCharacter().getCurrentHealth());
            } else if (player1.getCharacter().getCurrentHealth() == 0) {
                player1.freeze(delta);
                System.out.println("PV Payer 1: " + player1.getCharacter().getCurrentHealth() + "Paralysé");
            } else {
                player1.getCharacter().setCurrentHealth(0);
            }

        } else if (v.len() > 6 && computer.isHitByBall()) {
            if (!(computer.getCharacter().getCurrentHealth() <= 0)) {
                computer.getHurt(20);
                System.out.println(soccerball.getBody().getLinearVelocity());
                System.out.println("PV player1 : " + computer.getCharacter().getCurrentHealth());
            } else if (computer.getCharacter().getCurrentHealth() == 0) {
                computer.freeze(delta);
                System.out.println("PV Payer 1: " + computer.getCharacter().getCurrentHealth() + "Paralysé");
            } else {
                computer.getCharacter().setCurrentHealth(0);
            }
        }
    }

    private void updateHUD() {
        hud.updatePlayer1Life(player1.getCharacter().getCurrentHealth());

        hud.updatePlayer2Life(computer.getCharacter().getCurrentHealth());

        float p1Charge = player1.getCharacter().getSuperPower().getChargeTotal();
        float p1Max = player1.getCharacter().getSuperPower().getChargeMax();
        hud.updatePlayer1SuperPower(p1Charge, p1Max);

        float p2Charge = computer.getCharacter().getSuperPower().getChargeTotal();
        float p2Max = computer.getCharacter().getSuperPower().getChargeMax();
        hud.updatePlayer2SuperPower(p2Charge, p2Max);
    }

    public Character getComputerCharacter() {
        return computerCharacter;
    }

    public void setComputerCharacter(Character computerCharacter) {
        this.computerCharacter = computerCharacter;
    }
}
