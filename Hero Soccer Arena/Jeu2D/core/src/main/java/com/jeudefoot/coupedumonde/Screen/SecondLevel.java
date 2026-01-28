package com.jeudefoot.coupedumonde.Screen;

import com.badlogic.gdx.Game;
import com.jeudefoot.coupedumonde.Utils.AssetsManager;
import com.jeudefoot.coupedumonde.entite.Character.Character;
import com.jeudefoot.coupedumonde.entite.Character.ValkiryeWood;
import com.jeudefoot.coupedumonde.entite.Character.YellowSkeleton;

public class SecondLevel extends Level {
    public SecondLevel (Game game, AssetsManager assetManager, Character character) {
        super(game, assetManager, character, new ValkiryeWood(), assetManager.backgroundForest());
        computer.setDifficultyMedium();
    }
}
