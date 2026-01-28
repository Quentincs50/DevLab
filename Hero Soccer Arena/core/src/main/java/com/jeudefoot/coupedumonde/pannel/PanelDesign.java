package com.jeudefoot.coupedumonde.pannel;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import static com.jeudefoot.coupedumonde.Main.assets;

public class PanelDesign{
    private float CHAMPS_SCORE_1_X;
    private float CHAMPS_SCORE_2_X;
    private float CHAMPS_SCORE_1_Y;
    private float PANEL_SUPPORT_POS_Y;
    private float TIMER_POS_X;
    private float TIMER_POS_Y;

    private int score1;
    private int score2;
    private int time;

    private Image panelSupport;
    private TextButton scorePlayer1;
    private TextButton scorePlayer2;
    private TextButton timer;
    private Button clock;

    public PanelDesign(int score1, int score2, int time, float windows_width, float windows_height) {

        this.score1 = score1;
        this.score2 = score2;
        this.time = time;

        PANEL_SUPPORT_POS_Y = windows_height * 0.9f;
        CHAMPS_SCORE_1_X = windows_width * 0.25f;
        CHAMPS_SCORE_2_X = windows_width * 0.65f;
        CHAMPS_SCORE_1_Y = windows_height * 0.9f;

        TIMER_POS_X = windows_width * 0.42f;
        TIMER_POS_Y = windows_height * 0.87f;

        panelSupport = new Image(new TextureRegionDrawable(new TextureRegion(assets.panelSupport())));
        panelSupport.setSize(windows_width, windows_height* 0.15f);
        panelSupport.setPosition(0, PANEL_SUPPORT_POS_Y);


        TextButton.TextButtonStyle panelChamp = new TextButton.TextButtonStyle();
        panelChamp.up = assets.panelChamp();
        panelChamp.down = assets.panelChamp();
        panelChamp.font = assets.scoreFont;

        TextButton.TextButtonStyle healthChamp = new TextButton.TextButtonStyle();
        healthChamp.up = assets.panelChamp();
        healthChamp.down = assets.panelChamp();
        healthChamp.font = assets.scoreFont;

        TextButton.TextButtonStyle timerChamp = new TextButton.TextButtonStyle();
        timerChamp.up = assets.panelChamp();
        timerChamp.down = assets.panelChamp();
        timerChamp.font = assets.timerFont;

        Button.ButtonStyle clockStyle = new Button.ButtonStyle();
        clockStyle.up = assets.panelClock();
        clockStyle.down = assets.panelClock();

        scorePlayer1 = new TextButton("Score : " + score1, panelChamp);
        scorePlayer1.setWidth(windows_width/8);
        scorePlayer1.setHeight(windows_height/7);
        scorePlayer1.setPosition(CHAMPS_SCORE_1_X,CHAMPS_SCORE_1_Y);

        scorePlayer2 = new TextButton("Score : " + score2, panelChamp);
        scorePlayer2.setWidth(windows_width/8);
        scorePlayer2.setHeight(windows_height/7);
        scorePlayer2.setPosition(CHAMPS_SCORE_2_X,CHAMPS_SCORE_1_Y);

        timer = new TextButton("" + time, timerChamp);
        timer.setWidth(windows_width/6);
        timer.setHeight(windows_height/7);
        timer.setPosition(TIMER_POS_X, TIMER_POS_Y);

        clock = new Button(clockStyle);
        clock.setWidth(windows_width/20);
        clock.setHeight(windows_height/17);
        clock.setPosition(TIMER_POS_X * 1.03f, TIMER_POS_Y * 1.05f);

    }

    public Button getClock() {
        return clock;
    }

    public Image getPanelSupport() {
        return panelSupport;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }


    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public TextButton getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(TextButton scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public TextButton getScorePlayer2() {
        return scorePlayer2;
    }


    public TextButton getTimer() {
        return timer;
    }

    public void setTimer(TextButton timer) {
        this.timer = timer;
    }
}
