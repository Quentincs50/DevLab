package com.jeudefoot.coupedumonde.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jeudefoot.coupedumonde.entite.Character.Character;


import java.util.ArrayList;

import static com.jeudefoot.coupedumonde.Main.assets;
import static com.jeudefoot.coupedumonde.Utils.Constants.WINDOW_HEIGHT;
import static com.jeudefoot.coupedumonde.Utils.Constants.WINDOW_WIDTH;

public class WorldMap extends CharacterSelection {

    private Stage stage;
    private Game game;

    protected Character character;

    private float fadeAlpha = 0f;
    private int fadeDuration = 1; // Durée en secondes
    private float fadeTimer = 0f;

    private class Cloud {
        Image image;
        float baseX;
        float baseY;
        float time;
        float speedX;
        float speedY;
        float rangeX;
        float rangeY;

        Cloud(float x, float y, float speedX, float speedY, float rangeX, float rangeY, float size) {
            this.baseX = x;
            this.baseY = y;
            this.speedX = speedX;
            this.speedY = speedY;
            this.rangeX = rangeX;
            this.rangeY = rangeY;
            this.time = (float) (Math.random() * Math.PI * 2); //

            // Créer l'image du nuage (ajustez le nom de la méthode selon votre AssetManager)
            image = new Image(new TextureRegionDrawable(new TextureRegion(assets.backgroundCloud())));
            image.setSize(size, size); // Proportion nuage
            image.setOrigin(image.getWidth(), image.getHeight());
            updatePosition();
        }

        void update(float delta) {
            time += delta;
            updatePosition();
        }

        void updatePosition() {
            // Mouvement sinusoïdal léger autour de la position de base
            float offsetX = (float) Math.sin(time * speedX) * rangeX;
            float offsetY = (float) Math.cos(time * speedY) * rangeY;
            image.setPosition(baseX + offsetX, baseY + offsetY);

        }
    }
    private ArrayList<Cloud> clouds;

    public WorldMap(Game theGame, Character character) {
        super(theGame, assets);
        game = theGame;
        stage = new Stage(new FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.character = character;

        Image bg = new Image(new TextureRegionDrawable(new TextureRegion(assets.backgroundLobby())));
        bg.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        bg.setPosition(0,0);
        stage.addActor(bg);

        // Initialiser les nuages
        clouds = new ArrayList<Cloud>();

        // Créer plusieurs nuages : (posX, posY, vitesseX, vitesseY, amplitudeX, amplitudeY, taille)
        clouds.add(new Cloud(WINDOW_WIDTH * 0.2f, WINDOW_HEIGHT * 0.2f, 0.3f, 0.5f, 9f, 10f, WINDOW_WIDTH * 0.17f));
        clouds.add(new Cloud(WINDOW_WIDTH * 0.7f, WINDOW_HEIGHT * 0.2f, 0.3f, 0.5f, 8f, 5f, WINDOW_WIDTH * 0.17f));
        clouds.add(new Cloud(WINDOW_WIDTH * 0.70f, WINDOW_HEIGHT * 0.80f, 0.3f, 0.5f, 4f, 5f, WINDOW_WIDTH * 0.1f));
        clouds.add(new Cloud(WINDOW_WIDTH * 0.78f, WINDOW_HEIGHT * 0.90f, 0.3f, 0.2f, 5f, 4f, WINDOW_WIDTH * 0.09f));
        clouds.add(new Cloud(WINDOW_WIDTH * 0.50f, WINDOW_HEIGHT * 0.60f, 0.3f, 0.5f, 10f, 10f, WINDOW_WIDTH * 0.11f));
        clouds.add(new Cloud(WINDOW_WIDTH * 0.3f, WINDOW_HEIGHT * 0.80f, 0.3f, 0.5f, 10f, 12f, WINDOW_WIDTH * 0.1f));

        // Ajouter les nuages au stage
        for (Cloud cloud : clouds) {
            stage.addActor(cloud.image);
        }

        // ==== SETTINGS & CLOSE ==== //
        Button.ButtonStyle settingsButtonStyle = new Button.ButtonStyle();
        settingsButtonStyle.up = assets.buttonSettings();
        settingsButtonStyle.down = assets.buttonSettings();

        Button settingsButton = new Button(settingsButtonStyle);
        settingsButton.setWidth(WINDOW_WIDTH/20);
        settingsButton.setHeight(WINDOW_HEIGHT /20);
        settingsButton.setPosition(WINDOW_WIDTH * 0.90f, WINDOW_HEIGHT * 0.93f);
        settingsButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("bouton settings !");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(settingsButton);

        Button.ButtonStyle closeButtonStyle = new Button.ButtonStyle();
        closeButtonStyle.up = assets.buttonClose();
        closeButtonStyle.down = assets.buttonClose();

        Button closeButton = new Button(closeButtonStyle);
        closeButton.setWidth(WINDOW_WIDTH/20);
        closeButton.setHeight(WINDOW_HEIGHT /20);
        closeButton.setPosition(WINDOW_WIDTH * 0.95f, WINDOW_HEIGHT * 0.93f);
        closeButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("bouton close !");
                game.setScreen(new TitleScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(closeButton);


        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = assets.buttonLvl();
        buttonStyle.down = assets.buttonLvlOnMouse();
        buttonStyle.over = assets.buttonLvlOnMouse();

        Button lvl_1_Button = new Button(buttonStyle);
        lvl_1_Button.setSize(WINDOW_WIDTH/15, WINDOW_HEIGHT/15);
        lvl_1_Button.setPosition(WINDOW_WIDTH * 0.10f,WINDOW_HEIGHT * 0.42f);
        lvl_1_Button.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new FirstLevel(game, assets, character));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(lvl_1_Button);

        Button lvl_2_Button = new Button(buttonStyle);
        lvl_2_Button.setSize(WINDOW_WIDTH/15, WINDOW_HEIGHT/15);
        lvl_2_Button.setPosition(WINDOW_WIDTH* 0.45f,WINDOW_HEIGHT * 0.3f);
        lvl_2_Button.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SecondLevel(game, assets, character));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(lvl_2_Button);

        Button lvl_3_Button = new Button(buttonStyle);
        lvl_3_Button.setSize(WINDOW_WIDTH/15, WINDOW_HEIGHT/15);
        lvl_3_Button.setPosition(WINDOW_WIDTH * 0.60f,WINDOW_HEIGHT * 0.7f);
        lvl_3_Button.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ThirdLevel(game, assets, character));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(lvl_3_Button);

        Button lvl_4_Button = new Button(buttonStyle);
        lvl_4_Button.setSize(WINDOW_WIDTH/15, WINDOW_HEIGHT/15);
        lvl_4_Button.setPosition(WINDOW_WIDTH * 0.8f,WINDOW_HEIGHT * 0.65f);
        lvl_4_Button.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LastLevel(game, assets, character));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(lvl_4_Button);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Mise à jour du fade-in
        if (fadeTimer < fadeDuration) {
            fadeTimer += delta;
            fadeAlpha = Math.min(fadeTimer / fadeDuration, 1f);
            stage.getRoot().getColor().a = fadeAlpha;
        }

        // Mise à jour des nuages
        for (Cloud cloud : clouds) {
            cloud.update(delta);
        }

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
