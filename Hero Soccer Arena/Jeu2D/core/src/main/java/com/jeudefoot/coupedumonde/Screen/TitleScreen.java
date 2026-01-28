package com.jeudefoot.coupedumonde.Screen;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;


import java.util.ArrayList;

import static com.jeudefoot.coupedumonde.Main.assets;
import static com.jeudefoot.coupedumonde.Utils.Constants.WINDOW_HEIGHT;
import static com.jeudefoot.coupedumonde.Utils.Constants.WINDOW_WIDTH;

public class TitleScreen implements Screen {

    protected Stage stage;
    private Game game;
    protected World world;

    public TitleScreen(Game theGame) {

        game = theGame;
        stage = new Stage(new FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT));

        Image bg = new Image(new TextureRegionDrawable(new TextureRegion(assets.backgroundTitleScreen())));
        bg.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);  // Force la taille exacte
        bg.setPosition(0, 0);

        stage.addActor(bg);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = assets.buttonStartScreen();
        buttonStyle.down = assets.buttonStartScreen();
        buttonStyle.over = assets.buttonStartScreenShinny();
        buttonStyle.font = assets.titleScreenFont;

        Button.ButtonStyle settingsButtonStyle = new Button.ButtonStyle();
        settingsButtonStyle.up = assets.buttonSettings();
        settingsButtonStyle.down = assets.buttonSettings();

        TextButton onePlayerButton = new TextButton("1 Player", buttonStyle);
        onePlayerButton.setWidth(WINDOW_WIDTH/3);
        onePlayerButton.setHeight(WINDOW_HEIGHT /6);
        onePlayerButton.setPosition(WINDOW_WIDTH/2 - onePlayerButton.getWidth()/2, WINDOW_HEIGHT /2.5f - onePlayerButton.getHeight()/3);
        onePlayerButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    game.setScreen(new CharacterSelection(game, assets));

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(onePlayerButton);

        TextButton twoPlayersButton = new TextButton("2 Players", buttonStyle);
        twoPlayersButton.setWidth(WINDOW_WIDTH/3);
        twoPlayersButton.setHeight(WINDOW_HEIGHT /6);
        twoPlayersButton.setPosition(WINDOW_WIDTH/2- twoPlayersButton.getWidth()/2, WINDOW_HEIGHT /6- twoPlayersButton.getHeight()/3);
        twoPlayersButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new CharacterSelection(game, assets));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(twoPlayersButton);

        Button settingsButton = new Button(settingsButtonStyle);
        settingsButton.setWidth(WINDOW_WIDTH/17);
        settingsButton.setHeight(WINDOW_HEIGHT /17);
        settingsButton.setPosition(WINDOW_WIDTH * 0.93f, WINDOW_HEIGHT * 0.93f);
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

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
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
