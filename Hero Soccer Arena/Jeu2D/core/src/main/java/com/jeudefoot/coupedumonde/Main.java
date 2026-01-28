package com.jeudefoot.coupedumonde;

import com.badlogic.gdx.Game;
import com.jeudefoot.coupedumonde.Screen.TitleScreen;
import com.jeudefoot.coupedumonde.Utils.AssetsManager;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public static AssetsManager assets;

    @Override
    public void create() {
        assets = new AssetsManager();
        assets.loadAsset();
        assets.finishLoading();
        this.setScreen(new TitleScreen(this));
    }
    public void render(){
        super.render();
    }

    public void dispose(){
        getScreen().dispose();
        assets.dispose();
        super.dispose();
    }
}
