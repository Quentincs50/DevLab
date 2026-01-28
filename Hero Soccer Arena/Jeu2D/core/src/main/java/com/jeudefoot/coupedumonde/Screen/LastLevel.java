package com.jeudefoot.coupedumonde.Screen;

import com.badlogic.gdx.Game;
import com.jeudefoot.coupedumonde.Utils.AssetsManager;
import com.jeudefoot.coupedumonde.entite.Character.Character;
import com.jeudefoot.coupedumonde.entite.Character.GoldKing;
import com.jeudefoot.coupedumonde.entite.Character.YellowSkeleton;

public class LastLevel extends Level {
    private AssetsManager assetManager;

    public LastLevel(Game game, AssetsManager assetManager, Character character) {
        super(game, assetManager, character, new GoldKing(assetManager), assetManager.backgroundCastle());
        computer.setDifficultyHard();
    }
}
