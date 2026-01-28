package com.jeudefoot.coupedumonde.HUD;

import com.badlogic.gdx.scenes.scene2d.ui.Image;



import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import static com.jeudefoot.coupedumonde.Main.assets;

public class HUD {

    private Image player1LifeLogo;
    private Image player1LifeSupport;
    private Image player1LifeBar;
    private Image player1SuperPowerLogo;
    private Image player1SuperPowerSupport;
    private Image player1SuperPowerBar;

    private Image player2LifeLogo;
    private Image player2LifeSupport;
    private Image player2LifeBar;
    private Image player2SuperPowerLogo;
    private Image player2SuperPowerSupport;
    private Image player2SuperPowerBar;

    private float windowsWidth;
    private float windowsHeight;

    private float LOGO_SIZE;
    private float SUPPORT_WIDTH;
    private float SUPPORT_HEIGHT;
    private float BAR_MAX_WIDTH;
    private float BAR_HEIGHT;

    private float P1_START_X;
    private float P1_LIFE_Y;
    private float P1_SUPER_Y;


    private float P2_START_X;
    private float P2_LIFE_Y;
    private float P2_SUPER_Y;


    private float player1MaxLife;
    private float player2MaxLife;
    private float player1CurrentLife;
    private float player2CurrentLife;
    private float player1SuperPowerCharge;
    private float player2SuperPowerCharge;

    public HUD(float windowsWidth, float windowsHeight, float player1Life, float player2Life, float player1Super, float player2Super) {

        this.windowsWidth = windowsWidth;
        this.windowsHeight = windowsHeight;

        this.player1MaxLife = player1Life;
        this.player2MaxLife = player2Life;
        this.player1CurrentLife = player1Life;
        this.player2CurrentLife = player2Life;
        this.player1SuperPowerCharge = player1Super;
        this.player2SuperPowerCharge = player2Super;

        LOGO_SIZE = windowsWidth * 0.03f;
        SUPPORT_WIDTH = windowsWidth * 0.15f;
        SUPPORT_HEIGHT = windowsHeight * 0.03f;
        BAR_MAX_WIDTH = SUPPORT_WIDTH * 0.85f;
        BAR_HEIGHT = SUPPORT_HEIGHT * 0.6f;

        P1_START_X = windowsWidth * 0.01f;
        P1_LIFE_Y = windowsHeight * 0.95f;
        P1_SUPER_Y = windowsHeight * 0.90f;

        P2_START_X = windowsWidth * 0.80f;
        P2_LIFE_Y = windowsHeight * 0.95f;
        P2_SUPER_Y = windowsHeight * 0.90f;

        initPlayer1HUD();

        initPlayer2HUD();
    }

    private void initPlayer1HUD() {

        // Logo vie + barre de vie Player 1
        player1LifeLogo = new Image(new TextureRegionDrawable(new TextureRegion(assets.lifeLogo())));
        player1LifeLogo.setSize(LOGO_SIZE, LOGO_SIZE);
        player1LifeLogo.setPosition(P1_START_X, P1_LIFE_Y);

        player1LifeSupport = new Image(new TextureRegionDrawable(new TextureRegion(assets.jaugeChamp())));
        player1LifeSupport.setSize(SUPPORT_WIDTH, SUPPORT_HEIGHT);
        player1LifeSupport.setPosition(P1_START_X + LOGO_SIZE + windowsWidth * 0.01f, P1_LIFE_Y + (LOGO_SIZE - SUPPORT_HEIGHT) / 2);

        player1LifeBar = new Image(assets.jaugeLife());
        player1LifeBar.setSize(BAR_MAX_WIDTH, BAR_HEIGHT);
        player1LifeBar.setPosition(player1LifeSupport.getX() + SUPPORT_WIDTH * 0.075f, player1LifeSupport.getY() + (SUPPORT_HEIGHT - BAR_HEIGHT) / 2);

        // Logo superpower + jauge superpower Player 1
        player1SuperPowerLogo = new Image(new TextureRegionDrawable(new TextureRegion(assets.superPowerLogo())));
        player1SuperPowerLogo.setSize(LOGO_SIZE, LOGO_SIZE);
        player1SuperPowerLogo.setPosition(P1_START_X, P1_SUPER_Y);

        player1SuperPowerSupport = new Image(new TextureRegionDrawable(new TextureRegion(assets.jaugeChamp())));
        player1SuperPowerSupport.setSize(SUPPORT_WIDTH, SUPPORT_HEIGHT);
        player1SuperPowerSupport.setPosition(P1_START_X + LOGO_SIZE + windowsWidth * 0.01f, P1_SUPER_Y + (LOGO_SIZE - SUPPORT_HEIGHT) / 2);

        player1SuperPowerBar = new Image(assets.jaugeSuperPower());
        player1SuperPowerBar.setSize(BAR_MAX_WIDTH, BAR_HEIGHT);
        player1SuperPowerBar.setPosition(player1SuperPowerSupport.getX() + SUPPORT_WIDTH * 0.075f, player1SuperPowerSupport.getY() + (SUPPORT_HEIGHT - BAR_HEIGHT) / 2);
    }

    private void initPlayer2HUD() {
        // Logo vie + barre de vie Player 2
        player2LifeLogo = new Image(new TextureRegionDrawable(new TextureRegion(assets.lifeLogo())));
        player2LifeLogo.setSize(LOGO_SIZE, LOGO_SIZE);
        player2LifeLogo.setPosition(P2_START_X, P2_LIFE_Y);

        player2LifeSupport = new Image(new TextureRegionDrawable(new TextureRegion(assets.jaugeChamp())));
        player2LifeSupport.setSize(SUPPORT_WIDTH, SUPPORT_HEIGHT);
        player2LifeSupport.setPosition(P2_START_X + LOGO_SIZE + windowsWidth * 0.01f, P2_LIFE_Y + (LOGO_SIZE - SUPPORT_HEIGHT) / 2);

        player2LifeBar = new Image(assets.jaugeLife());
        player2LifeBar.setSize(BAR_MAX_WIDTH, BAR_HEIGHT);
        player2LifeBar.setPosition(player2LifeSupport.getX() + SUPPORT_WIDTH * 0.075f, player2LifeSupport.getY() + (SUPPORT_HEIGHT - BAR_HEIGHT) / 2);

        // Logo superpower + jauge superpower Player 2
        player2SuperPowerLogo = new Image(new TextureRegionDrawable(new TextureRegion(assets.superPowerLogo())));
        player2SuperPowerLogo.setSize(LOGO_SIZE, LOGO_SIZE);
        player2SuperPowerLogo.setPosition(P2_START_X, P2_SUPER_Y);

        player2SuperPowerSupport = new Image(new TextureRegionDrawable(new TextureRegion(assets.jaugeChamp())));
        player2SuperPowerSupport.setSize(SUPPORT_WIDTH, SUPPORT_HEIGHT);
        player2SuperPowerSupport.setPosition(P2_START_X + LOGO_SIZE + windowsWidth * 0.01f, P2_SUPER_Y + (LOGO_SIZE - SUPPORT_HEIGHT) / 2);

        player2SuperPowerBar = new Image(assets.jaugeSuperPower());
        player2SuperPowerBar.setSize(BAR_MAX_WIDTH, BAR_HEIGHT);
        player2SuperPowerBar.setPosition(player2SuperPowerSupport.getX() + SUPPORT_WIDTH * 0.075f, player2SuperPowerSupport.getY() + (SUPPORT_HEIGHT - BAR_HEIGHT) / 2);
    }


    public void updatePlayer1Life(float currentLife) {
        this.player1CurrentLife = Math.max(0, Math.min(currentLife, player1MaxLife));
        float percentage = player1CurrentLife / player1MaxLife;
        player1LifeBar.setWidth(BAR_MAX_WIDTH * percentage);
    }

    public void updatePlayer2Life(float currentLife) {
        this.player2CurrentLife = Math.max(0, Math.min(currentLife, player2MaxLife));
        float percentage = player2CurrentLife / player2MaxLife;
        player2LifeBar.setWidth(BAR_MAX_WIDTH * percentage);
    }

    public void updatePlayer1SuperPower(float charge, float chargeMax) {
        float percentage = Math.max(0, Math.min(charge / chargeMax, 1f));
        player1SuperPowerBar.setWidth(BAR_MAX_WIDTH * percentage);
    }

    public void updatePlayer2SuperPower(float charge, float chargeMax) {
        float percentage = Math.max(0, Math.min(charge / chargeMax, 1f));
        player2SuperPowerBar.setWidth(BAR_MAX_WIDTH * percentage);
    }

    public Image getPlayer1LifeLogo() { return player1LifeLogo; }
    public Image getPlayer1LifeSupport() { return player1LifeSupport; }
    public Image getPlayer1LifeBar() { return player1LifeBar; }
    public Image getPlayer1SuperPowerLogo() { return player1SuperPowerLogo; }
    public Image getPlayer1SuperPowerSupport() { return player1SuperPowerSupport; }
    public Image getPlayer1SuperPowerBar() { return player1SuperPowerBar; }


    public Image getPlayer2LifeLogo() { return player2LifeLogo; }
    public Image getPlayer2LifeSupport() { return player2LifeSupport; }
    public Image getPlayer2LifeBar() { return player2LifeBar; }
    public Image getPlayer2SuperPowerLogo() { return player2SuperPowerLogo; }
    public Image getPlayer2SuperPowerSupport() { return player2SuperPowerSupport; }
    public Image getPlayer2SuperPowerBar() { return player2SuperPowerBar; }
}
