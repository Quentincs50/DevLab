package com.jeudefoot.coupedumonde.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class AssetsManager implements Disposable {

    AssetManager manager = new AssetManager();
    public BitmapFont titleScreenFont;
    public BitmapFont scoreFont;
    public BitmapFont timerFont;
    public BitmapFont statsFont;

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

    private static final String CHAR_PATH = "Characters/";

    public void loadAsset(){
        // Ball
        manager.load("balls/basket-ball.png", Texture.class);
        manager.load("balls/soccer-ball.png", Texture.class);
        manager.load("balls/volley-ball.png", Texture.class);

        // Goal
        manager.load("Goal/Goal.png", Texture.class);
        manager.load("Goal/Post.png", Texture.class);
        manager.load("Goal/magic_goal.png", Texture.class);

        // Background
        manager.load("backgrounds/bamboo_bridge.png", Texture.class);
        manager.load("backgrounds/castle_bridge.png", Texture.class);
        manager.load("backgrounds/forest_bridge.png", Texture.class);
        manager.load("backgrounds/sky_bridge.png", Texture.class);
        manager.load("backgrounds/titlescreen.png", Texture.class);
        manager.load("backgrounds/Lobby.png", Texture.class);
        manager.load("backgrounds/cloud.png", Texture.class);

        // Button
        manager.load("buttons/StartPlayer.png", Texture.class);
        manager.load("buttons/StartPlayer_Shinny.png", Texture.class);
        manager.load("buttons/uncompleted_lvl.png", Texture.class);
        manager.load("buttons/completed_lvl.png", Texture.class);
        manager.load("buttons/checked_lvl.png", Texture.class);
        manager.load("buttons/close.png", Texture.class);
        manager.load("buttons/menu.png", Texture.class);
        manager.load("buttons/music.png", Texture.class);
        manager.load("buttons/music_off.png", Texture.class);
        manager.load("buttons/pause.png", Texture.class);
        manager.load("buttons/play.png", Texture.class);
        manager.load("buttons/settings.png", Texture.class);

        // Selection of Character
        manager.load("Selection/pad.png", Texture.class);
        manager.load("Selection/bg.png", Texture.class);
        manager.load("Selection/table2.png", Texture.class);
        manager.load("Selection/table.png", Texture.class);
        manager.load("Selection/lock.png", Texture.class);

        loadCharactersDefault();

        loadCharacterAnimations();

        loadHUD();

        loadFonts();

        loadPanelAssets();

    }

    private String raceFolder(Race race) {
        switch (race) {
            case FALLEN_ANGEL_1: return "fallen-angel/Fallen_Angel_1";
            case FALLEN_ANGEL_2: return "fallen-angel/Fallen_Angel_2";
            case FALLEN_ANGEL_3: return "fallen-angel/Fallen_Angel_3";
            case GOBELIN: return "gobelin/Gobelin";
            case OGRE: return  "gobelin/Ogre";
            case ORC: return "gobelin/Orc";
            case GOLEM_1: return "golems/Golem_1";
            case GOLEM_2: return "golems/Golem_2";
            case GOLEM_3: return "golems/Golem_3";
            case MINOTAUR_1: return "minotaur/Minotaur_1";
            case MINOTAUR_2: return "minotaur/Minotaur_2";
            case MINOTAUR_3: return "minotaur/Minotaur_3";
            case NECROMANCER_1: return "necromancer/Necromancer_1";
            case NECROMANCER_2: return "necromancer/Necromancer_2";
            case NECROMANCER_3: return "necromancer/Necromancer_3";
            case ORACLE_1: return "oracle/Oracle_1";
            case ORACLE_2: return "oracle/Oracle_2";
            case ORACLE_3: return "oracle/Oracle_3";
            case REAPER_1: return "reaper/Reaper_1";
            case REAPER_2: return "reaper/Reaper_2";
            case REAPER_3: return "reaper/Reaper_3";
            case SKELETON_1: return "skeleton/Skeleton_1";
            case SKELETON_2: return "skeleton/Skeleton_2";
            case SKELETON_3: return "skeleton/Skeleton_3";
            case VALKYRIE_1: return "valkyrie/Valkyrie_1";
            case VALKYRIE_2: return "valkyrie/Valkyrie_2";
            case VALKYRIE_3: return "valkyrie/Valkyrie_3";
            case ZOMBIE_1: return "zombie/Zombie_1";
            case ZOMBIE_2: return "zombie/Zombie_2";
            case ZOMBIE_3: return "zombie/Zombie_3";
            default: return "";
        }
    }

    private String racePrefix(Race race) {
        switch (race) {
            case FALLEN_ANGEL_1: return "0_Fallen_Angels";
            case FALLEN_ANGEL_2: return "0_Fallen_Angels";
            case FALLEN_ANGEL_3: return "0_Fallen_Angels";
            case GOBELIN: return "0_Goblin";
            case OGRE: return  "0_Ogre";
            case ORC: return "0_Orc";
            case GOLEM_1: return "0_Golem";
            case GOLEM_2: return "0_Golem";
            case GOLEM_3: return "0_Golem";
            case MINOTAUR_1: return "0_Minotaur";
            case MINOTAUR_2: return "0_Minotaur";
            case MINOTAUR_3: return "0_Minotaur";
            case NECROMANCER_1: return "0_Necromancer_of_the_Shadow";
            case NECROMANCER_2: return "0_Necromancer_of_the_Shadow";
            case NECROMANCER_3: return "0_Necromancer_of_the_Shadow";
            case ORACLE_1: return "0_Dark_Oracle";
            case ORACLE_2: return "0_Dark_Oracle";
            case ORACLE_3: return "0_Dark_Oracle";
            case REAPER_1: return "0_Reaper_Man";
            case REAPER_2: return "0_Reaper_Man";
            case REAPER_3: return "0_Reaper_Man";
            case SKELETON_1: return "0_Skeleton_Warrior";
            case SKELETON_2: return "0_Skeleton_Warrior";
            case SKELETON_3: return "0_Skeleton_Warrior";
            case VALKYRIE_1: return "0_Valkyrie";
            case VALKYRIE_2: return "0_Valkyrie";
            case VALKYRIE_3: return "0_Valkyrie";
            case ZOMBIE_1: return "0_Zombie_Villager";
            case ZOMBIE_2: return "0_Zombie_Villager";
            case ZOMBIE_3: return "0_Zombie_Villager";
            default: return "";
        }
    }

    public void loadCharactersDefault() {
        for (Race race : Race.values()) {
            String base = CHAR_PATH + raceFolder(race) + "/Idle/";
            manager.load(
                base + racePrefix(race) + "_Idle_000.png",
                Texture.class
            );
        }
    }

    public void loadAnimation(Race race, String animName) {
        String path = CHAR_PATH + raceFolder(race) + "/" + animName + "/";
        int index = 0;

        while (true) {
            String file = path + racePrefix(race) + "_" + animName + "_" +
                String.format("%03d", index) + ".png";

            if (!Gdx.files.internal(file).exists()) break;

            manager.load(file, Texture.class);
            index++;
        }
    }

    public void loadCharacterAnimations() {
        for (Race race : Race.values()){
            loadAnimation(race, "Idle");
            loadAnimation(race, "Running");
            loadAnimation(race, "Jump Start");
            loadAnimation(race, "Kicking");
            loadAnimation(race, "Dying");
            loadAnimation(race, "Sliding");
            loadAnimation(race, "Throwing");
        }

    }

    public Texture getDefaultCharacter(Race race) {
        String path = CHAR_PATH + raceFolder(race) + "/Idle/" +
            racePrefix(race) + "_Idle_000.png";

        return manager.get(path, Texture.class);
    }

    public TextureRegion[] getAnimation(Race race, String animName) {
        Array<TextureRegion> frames = new Array<>();
        String path = CHAR_PATH + raceFolder(race) + "/" + animName + "/";
        int index = 0;

        while (true) {
            String file = path + racePrefix(race) + "_" + animName + "_" +
                String.format("%03d", index) + ".png";

            if (!manager.isLoaded(file)) break;

            Texture tex = manager.get(file, Texture.class);
            frames.add(new TextureRegion(tex));
            index++;
        }

        return frames.toArray(TextureRegion.class);
    }

    public void loadFonts() {

        // Alfa
        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(Gdx.files.internal("Fonts/AlfaSlabOne-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter4 = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter1.size = 32;
        parameter1.color = Color.WHITE;

        parameter2.size = 24;
        parameter2.color = Color.WHITE;

        parameter3.size = 20;
        parameter3.color = Color.WHITE;

        parameter4.size = 14;
        parameter4.color = Color.BLACK;


        titleScreenFont = generator.generateFont(parameter1);
        scoreFont = generator.generateFont(parameter2);
        timerFont = generator.generateFont(parameter3);
        statsFont = generator.generateFont(parameter4);

        generator.dispose();
    }

    public void loadPanelAssets(){
        manager.load("objects/panel/panel_champs.png", Texture.class);
        manager.load("objects/panel/panel_support.png", Texture.class);
        manager.load("objects/panel/clock.png", Texture.class);

    }

    public void loadHUD(){
        manager.load("HUD/jauge_champ.png", Texture.class);
        manager.load("HUD/life_jauge.png", Texture.class);
        manager.load("HUD/life_logo.png", Texture.class);
        manager.load("HUD/superpower_jauge.png", Texture.class);
        manager.load("HUD/superpower_logo.png", Texture.class);

    }


    public boolean update(){
        return manager.update();
    }

    public float progress(){
        return manager.getProgress();
    }

    public void finishLoading(){
        manager.finishLoading();
    }

    // Ball image

    public Texture basketBall(){
        return manager.get("balls/basket-ball.png", Texture.class);
    }

    public Texture soccerBall(){
        return manager.get("balls/soccer-ball.png", Texture.class);
    }

    public Texture volleyBall(){
        return manager.get("balls/volley-ball.png", Texture.class);
    }

    // Goal image

    public Texture goal(){
        return manager.get("Goal/Goal.png", Texture.class);
    }
    public Texture post() {
        return manager.get("Goal/Post.png", Texture.class);
    }

    public Texture magicGoal() {
        return manager.get("Goal/magic_goal.png", Texture.class);
    }

    // Background image

    public Texture backgroundBamboo(){
        return manager.get("backgrounds/bamboo_bridge.png", Texture.class);
    }

    public Texture backgroundCastle(){
        return manager.get("backgrounds/castle_bridge.png", Texture.class);
    }

    public Texture backgroundForest(){
        return manager.get("backgrounds/forest_bridge.png", Texture.class);
    }

    public Texture backgroundSky(){
        return manager.get("backgrounds/sky_bridge.png", Texture.class);
    }

    public Texture backgroundTitleScreen(){ return manager.get("backgrounds/titlescreen.png", Texture.class); }

    public Texture backgroundLobby(){ return manager.get("backgrounds/Lobby.png", Texture.class); }

    public Texture backgroundCloud(){ return manager.get("backgrounds/cloud.png", Texture.class); }

    // Buttons

    public Drawable buttonStartScreen() {
        Texture texture = manager.get("buttons/StartPlayer.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public Drawable buttonStartScreenShinny() {
        Texture texture = manager.get("buttons/StartPlayer_Shinny.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public Drawable buttonLvl() {
        Texture texture = manager.get("buttons/uncompleted_lvl.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public Drawable buttonLvlOnMouse() {
        Texture texture = manager.get("buttons/completed_lvl.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public Drawable buttonCheckedLvl() {
        Texture texture = manager.get("buttons/checked_lvl.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public Drawable  buttonClose() {
        Texture texture = manager.get("buttons/close.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public Drawable  buttonMenu() {
        Texture texture = manager.get("buttons/menu.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public Drawable buttonMusic() {
        Texture texture = manager.get("buttons/music.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public Drawable buttonMusicOff() {
        Texture texture = manager.get("buttons/music_off.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public Drawable buttonPause() {
        Texture texture = manager.get("buttons/pause.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public Drawable buttonPlay() {
        Texture texture = manager.get("buttons/play.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public Drawable buttonSettings() {
        Texture texture = manager.get("buttons/settings.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }


    // Panel
    public Drawable panelChamp() {
        Texture texture = manager.get("objects/panel/panel_champs.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public Texture panelSupport() {
        return manager.get("objects/panel/panel_support.png", Texture.class);
    }

    public Drawable panelClock() {
        Texture texture = manager.get("objects/panel/clock.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    // HUD
    public Texture lifeLogo() {
        return manager.get("HUD/life_logo.png", Texture.class);
    }

    public Texture superPowerLogo() {
        return manager.get("HUD/superpower_logo.png", Texture.class);
    }

    public Texture jaugeChamp() {
        return manager.get("HUD/jauge_champ.png", Texture.class);
    }

    public Drawable jaugeSuperPower() {
        Texture texture = manager.get("HUD/superpower_jauge.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public Drawable jaugeLife() {
        Texture texture = manager.get("HUD/life_jauge.png", Texture.class);
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    // Character Selection
    public Texture pad(){ return manager.get("Selection/pad.png", Texture.class);}
    public Texture lock(){ return manager.get("Selection/lock.png", Texture.class);}
    public Texture bg(){ return manager.get("Selection/bg.png", Texture.class);}
    public Texture table(){ return manager.get("Selection/table.png", Texture.class);}
    public Texture table2(){ return manager.get("Selection/table2.png", Texture.class);}

    // Dispose

    public void dispose(){
        manager.dispose();
    }
}
