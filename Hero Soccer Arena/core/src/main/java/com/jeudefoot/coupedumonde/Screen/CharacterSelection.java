package com.jeudefoot.coupedumonde.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jeudefoot.coupedumonde.SuperPower.AirShot;
import com.jeudefoot.coupedumonde.Utils.AssetsManager;
import com.jeudefoot.coupedumonde.entite.Character.Character;

import java.util.ArrayList;

import static com.jeudefoot.coupedumonde.Main.assets;
import static com.jeudefoot.coupedumonde.Utils.Constants.*;
import static com.jeudefoot.coupedumonde.Utils.AssetsManager.Race.*;


public class CharacterSelection implements Screen {

    private Stage stage;
    private Game game;
    private AssetsManager assets;

    // Liste des personnages
    private ArrayList<Character> characters;
    protected Character selectedCharacter;

    // UI Elements
    private ScrollPane characterScrollPane;
    private Table characterGrid;
    private Image selectedCharacterDisplay;
    private Image padImage;
    private Table statsPanel;

    float LOGO_SIZE = GAME_WIDTH * 0.03f;

    // Fade-in
    private float fadeAlpha = 0f;
    private float fadeDuration = 0.5f;
    private float fadeTimer = 0f;

    public CharacterSelection(Game game, AssetsManager assets) {
        this.game = game;
        this.assets = assets;
        this.characters = createAllCharacters(assets);

        stage = new Stage(new FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT));

        // Background
        createCharacterSelectionPanel();
        createPreviewPanel();
        createStatsPanel();
        createValidateButton();
    }

    private void createCharacterSelectionPanel() {
        // Conteneur principal pour la grille de personnages
        Table leftContainer = new Table();
        leftContainer.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        leftContainer.setPosition(0, WINDOW_HEIGHT * 0.3f);

        // Titre
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = assets.statsFont;
        Label title = new Label("Choix du personnage", titleStyle);
        leftContainer.add(title).padTop(20).padBottom(20).row();

        // Grille de personnages avec ScrollPane
        characterGrid = new Table();
        characterGrid.pad(5);

        // Créer la grille 10 colonnes x 3 lignes (30 personnages)
        int index = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 10; col++) {
                if (index < characters.size()) {
                    Character character = characters.get(index);
                    Table characterCard = createCharacterCard(character, index);
                    characterGrid.add(characterCard).size(WINDOW_WIDTH * 0.09f, WINDOW_WIDTH * 0.09f).pad(3);
                    index++;
                } else {
                    // Remplir avec des cartes vides si moins de 30 personnages
                    characterGrid.add(new Table()).size(WINDOW_WIDTH * 0.09f, WINDOW_WIDTH * 0.09f).pad(3);
                }
            }
            characterGrid.row();
        }

        // ScrollPane pour pouvoir défiler
        characterScrollPane = new ScrollPane(characterGrid);
        characterScrollPane.setFadeScrollBars(false);
        leftContainer.add(characterScrollPane).expand().fill();

        stage.addActor(leftContainer);
    }

    private Table createCharacterCard(Character character, int index) {
        Table card = new Table();

        Image frame = new Image(new TextureRegionDrawable(new TextureRegion(assets.table())));
        frame.setSize(WINDOW_WIDTH * 0.10f, WINDOW_WIDTH * 0.10f);
        card.setBackground(frame.getDrawable());

        // Stack pour superposer les éléments
        Stack stack = new Stack();

        // Image du personnage
        Image characterImage = new Image(assets.getDefaultCharacter(character.getRace()));
        characterImage.setSize(WINDOW_WIDTH * 0.08f, WINDOW_WIDTH * 0.08f);
        stack.add(characterImage);

        // Si bloqué, ajouter le cadenas
        if (!character.isUnlocked()) {
            Table lockContainer = new Table();
            lockContainer.align(Align.bottomRight);
            Image lock = new Image(new TextureRegionDrawable(new TextureRegion(assets.lock())));
            lockContainer.add(lock).size(WINDOW_WIDTH * 0.03f, WINDOW_WIDTH * 0.03f)
                .padRight(5).padBottom(5);
            stack.add(lockContainer);
        }

        card.add(stack).expand().fill();

        // Listener de clic
        final int charIndex = index;
        card.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (character.isUnlocked()) {
                    selectCharacter(character);
                }
                return true;
            }
        });

        return card;
    }

    private void createPreviewPanel() {
        // Conteneur pour l'aperçu du personnage
        Table previewContainer = new Table();
        previewContainer.setSize(WINDOW_WIDTH * 0.5f, WINDOW_HEIGHT * 0.5f);
        previewContainer.setPosition(WINDOW_WIDTH * 0.2f, WINDOW_HEIGHT * 0.1f);

        // Pad (plateforme)
        padImage = new Image(new TextureRegionDrawable(new TextureRegion(assets.pad())));
        padImage.setSize(WINDOW_WIDTH * 0.3f, WINDOW_WIDTH * 0.15f);
        padImage.setPosition(WINDOW_WIDTH * 0.03f, WINDOW_HEIGHT * 0.05f);
        stage.addActor(padImage);

        // Personnage sélectionné (sera mis à jour)
        selectedCharacterDisplay = new Image();
        selectedCharacterDisplay.setSize(WINDOW_WIDTH * 0.25f, WINDOW_WIDTH * 0.25f);
        selectedCharacterDisplay.setPosition(WINDOW_WIDTH * 0.06f, WINDOW_HEIGHT * 0.12f);
        stage.addActor(selectedCharacterDisplay);
    }

    private void createStatsPanel() {
        // Panel des statistiques (agrandi car plus de panel couleur)
        Table statsContainer = new Table();
        statsContainer.setSize(WINDOW_WIDTH * 0.47f, WINDOW_HEIGHT * 0.38f);
        statsContainer.setPosition(WINDOW_WIDTH * 0.5f, WINDOW_HEIGHT * 0.15f);

        // Frame pour les stats
        Image statsFrame = new Image(new TextureRegionDrawable(new TextureRegion(assets.table2())));
        statsFrame.setSize(WINDOW_WIDTH * 0.2f, WINDOW_HEIGHT * 0.3f);
        statsContainer.setBackground(statsFrame.getDrawable());

        statsPanel = new Table();
        statsPanel.pad(20);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assets.statsFont;

        Label statsTitle = new Label("Statistiques", labelStyle);
        statsPanel.add(statsTitle).colspan(2).padBottom(10).row();

        // Les stats seront mises à jour lors de la sélection
        statsContainer.add(statsPanel);
        stage.addActor(statsContainer);
    }

    private void createValidateButton() {
        // Bouton de validation
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = assets.buttonStartScreen();
        buttonStyle.down = assets.buttonStartScreen();
        buttonStyle.over = assets.buttonStartScreenShinny();
        buttonStyle.font = assets.titleScreenFont;

        TextButton validateButton = new TextButton("Valider", buttonStyle);
        validateButton.setSize(WINDOW_WIDTH * 0.2f, WINDOW_HEIGHT * 0.08f);
        validateButton.setPosition(WINDOW_WIDTH * 0.63f, WINDOW_HEIGHT * 0.05f);

        validateButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (selectedCharacter != null && selectedCharacter.isUnlocked()) {
                    // Passer à l'écran suivant avec le personnage sélectionné
                    System.out.println("Personnage sélectionné: " + selectedCharacter.getName());
                    game.setScreen(new WorldMap(game, selectedCharacter));
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(validateButton);
    }

    public static ArrayList<Character> createAllCharacters(AssetsManager assets) {
        ArrayList<Character> characters = new ArrayList<>();

        // === 10 PERSONNAGES DÉBLOQUÉS ===
        characters.add(new Character("Skeleton", SKELETON_1,
            new AirShot(), true));
        characters.add(new Character("Skeleton Warrior", SKELETON_2,
            new AirShot(), true));
        characters.add(new Character("Skeleton Templar", SKELETON_3,
            new AirShot(), true));
        characters.add(new Character("Valkyrie", VALKYRIE_1,
            new AirShot(), true));
        characters.add(new Character("Valkyrie of the Wood", VALKYRIE_2,
            new AirShot(), true));
        characters.add(new Character("Valkyrie Warrior", VALKYRIE_3,
            new AirShot(), true));

        // === 20 PERSONNAGES BLOQUÉS ===
        characters.add(new Character("Zombie", ZOMBIE_1,
            new AirShot(), true));
        characters.add(new Character("Zombie rotten", ZOMBIE_2,
            new AirShot(), false));
        characters.add(new Character("Zombie stamped", ZOMBIE_3,
            new AirShot(), true));
        characters.add(new Character("Gobelin", GOBELIN,
            new AirShot(), true));
        characters.add(new Character("Ogre", OGRE,
            new AirShot(), false));
        characters.add(new Character("Orc", ORC,
            new AirShot(), true));
        characters.add(new Character("Ice Golem", GOLEM_1,
            new AirShot(), false));
        characters.add(new Character("Plant Golem ", GOLEM_2,
            new AirShot(), true));
        characters.add(new Character("Lava Golem", GOLEM_3,
            new AirShot(), false));
        characters.add(new Character("Young Minotaur", MINOTAUR_1,
            new AirShot(), true));
        characters.add(new Character("Strong Minotaur", MINOTAUR_2,
            new AirShot(), true));
        characters.add(new Character("Veteran Minotaur", MINOTAUR_3,
            new AirShot(), true));
        characters.add(new Character("Cold Necromancer", NECROMANCER_1,
            new AirShot(), false));
        characters.add(new Character("Dark Necromancer", NECROMANCER_2,
            new AirShot(), true));
        characters.add(new Character("Raven Necromancer", NECROMANCER_3,
            new AirShot(), true));
        characters.add(new Character("Oracle", ORACLE_1,
            new AirShot(), true));
        characters.add(new Character("Mysterion", ORACLE_2,
            new AirShot(), true));
        characters.add(new Character("Mystic", ORACLE_3,
            new AirShot(), false));
        characters.add(new Character("Jack the Reaper", REAPER_1,
            new AirShot(), true));
        characters.add(new Character("Rotten Reaper", REAPER_2,
            new AirShot(), true));
        characters.add(new Character("Dark Reaper", REAPER_3,
            new AirShot(), true));
        characters.add(new Character("Fallen King", FALLEN_ANGEL_1,
            new AirShot(), true));
        characters.add(new Character("Impostor", FALLEN_ANGEL_2,
            new AirShot(), true));
        characters.add(new Character("Fallen Angel", FALLEN_ANGEL_3,
            new AirShot(), true));

        return characters;
    }

    private void selectCharacter(Character character) {
        selectedCharacter = character;

        // Mettre à jour l'affichage du personnage
        selectedCharacterDisplay.setDrawable(new TextureRegionDrawable(
            new TextureRegion(assets.getDefaultCharacter(character.getRace()))));

        // Mettre à jour les statistiques
        updateStats(character);
    }

    private void updateStats(Character character) {
        statsPanel.clear();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assets.scoreFont;
        labelStyle.fontColor = Color.BLACK;

        Label statsTitle = new Label("Statistiques", labelStyle);
        statsPanel.add(statsTitle).colspan(2).padBottom(STATS_PAD).row();

        // Nom
        Label nameValue = new Label(character.getName(), labelStyle);
        statsPanel.add(nameValue).align(Align.left).expandX().fillX().row();

        // Vie
        Label health = new Label("Vie:", labelStyle);
        Label healthValue = new Label(String.valueOf(character.getMaxHealth()), labelStyle);
        statsPanel.add(health).align(Align.left).pad(STATS_PAD).expandX().fill();
        statsPanel.add(healthValue).align(Align.left).expandX().fillX().row();

        // Rapidité
        Label speedLabel = new Label("Rapidité:", labelStyle);
        Label speedValue = new Label(String.valueOf(ACCELERATION), labelStyle);
        statsPanel.add(speedLabel).align(Align.left).pad(STATS_PAD).expandX().fillX();
        statsPanel.add(speedValue).align(Align.left).expandX().fillX().row();

        Label jumpLabel = new Label("Force de saut:", labelStyle);
        Label jumpValue = new Label(String.valueOf((int)character.getJumpForce()), labelStyle);
        statsPanel.add(jumpLabel).align(Align.left).pad(STATS_PAD).expandX().fillX();
        statsPanel.add(jumpValue).align(Align.left).expandX().fillX().row();

        // Super Power
        Label powerLabel = new Label("Super Power:", labelStyle);
        Label powerValue = new Label(character.getSuperPower().getName(), labelStyle);
        statsPanel.add(powerLabel).align(Align.left).pad(STATS_PAD).expandX().fillX();
        statsPanel.add(powerValue).align(Align.left).expandX().fillX().row();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Fade-in
        if (fadeTimer < fadeDuration) {
            fadeTimer += delta;
            fadeAlpha = Math.min(fadeTimer / fadeDuration, 1f);
            stage.getRoot().getColor().a = fadeAlpha;
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
