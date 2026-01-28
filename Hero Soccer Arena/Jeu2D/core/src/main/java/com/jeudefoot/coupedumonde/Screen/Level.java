package com.jeudefoot.coupedumonde.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
import com.jeudefoot.coupedumonde.entite.Goal.Goal;
import com.jeudefoot.coupedumonde.entite.Player.IA;
import com.jeudefoot.coupedumonde.entite.Player.Player;
import com.jeudefoot.coupedumonde.entite.Player.Player1;
import com.jeudefoot.coupedumonde.entite.Stadiums.Stadium;
import com.jeudefoot.coupedumonde.pannel.PanelDesign;
import com.jeudefoot.coupedumonde.pannel.Timer;
import com.jeudefoot.coupedumonde.entite.Character.Character;
import static com.jeudefoot.coupedumonde.Utils.Constants.*;

public class Level implements Screen{
    private Game game;
    private FitViewport game_viewport;
    private FitViewport viewport;
    private AssetsManager assets;
    private Goal goalleft;
    private Goal goalright;
    private Ball soccerball;
    private Stadium stadium;
    private PhysicWorld world;
    private Player1 player1;
    private Character computerCharacter;
    protected IA computer;
    private Box2DDebugRenderer debug;
    private int score1 = 0;
    private int score2 = 0;
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

    public Level(Game game, AssetsManager asset, Character characterPlayer1, Character computerCharacter, Texture stadiumTheme) {
        super();
        this.game = game;
        game_viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT);
        viewport = new FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT);
        stage = new Stage(game_viewport);
        hudStage = new Stage(viewport);
        assets = asset;
        world = new PhysicWorld();
        world.getWorld().setContactListener(new ListenerContact());
        timer = new Timer(TIMER, true);
        debug = new Box2DDebugRenderer();
        stadium = new Stadium(world.getWorld(), stadiumTheme);
        soccerball = new SoccerBall(assets, world.getWorld());

        player1 = new Player1(world.getWorld(), characterPlayer1);
        computer = new IA(world.getWorld(), computerCharacter, soccerball);

        // Initialiser les goals
        goalleft = new Goal(world.getWorld(), assets, true);
        goalright = new Goal(world.getWorld(), assets, false);


        panel = new PanelDesign(score1, score2, timer.getTimeRemain(), WINDOW_WIDTH, WINDOW_HEIGHT);
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

        WINNER_POS_X = WINDOW_WIDTH * 0.5f;
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
        titleScreen.setSize(WINDOW_WIDTH/4, WINDOW_HEIGHT/6);
        titleScreen.setPosition(WINDOW_WIDTH/2 - titleScreen.getWidth()/2, WINDOW_HEIGHT/8);
        titleScreen.setVisible(false);
        titleScreen.addListener(new InputListener(){
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

        // --- LOGIQUE DE score ---
        updateScore(goalleft);
        updateScore(goalright);

        panel.getScorePlayer1().setText("score: " + score1);
        panel.getScorePlayer2().setText("score: " + score2);
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
        updateState(player1);
        updateState(computer);

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
        // debug.render(world.getWorld(), game_viewport.getCamera().combined);
    }

    public void resize(int width, int height) {
        game_viewport.update(width, height, true);
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
        world.dispose();
        stage.dispose();
        hudStage.dispose();
    }

    public void showWinner() {

        if (score1 > score2) {
            winnerLabel.setText("The Winner is Player 1");
        } else if (score1 < score2) {
            winnerLabel.setText("The Winner is Player 2");
        } else if (score1 == score2) {
            winnerLabel.setText("Tie!");
        }

        winnerLabel.setVisible(true);
        titleScreen.setVisible(true);

        // Arret du match
        player1.stopMoving();
        computer.stopMoving();
    }

    public void updateState(Player player) {
        if (player.isDead()) return;
        Vector2 v = soccerball.getBody().getLinearVelocity();
        if (v.len() < MIN_DAMAGE_SPEED){
            return;
        }
        if (player.isHitByBall()) {
            player.getHurt(DAMAGE);
            player.setHitByBall(false);
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

    private void updateScore(Goal goal){
        if (goal.isGoalScored()) {
            if (goal.isLeft()){
                score2++;
            }
            else {
                score1++;
            }
            player1.resetPosition();
            computer.resetPosition();
            soccerball.resetPosition();
        }
    }
}
