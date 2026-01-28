package com.jeudefoot.coupedumonde.Screen;

import com.badlogic.gdx.Game;
import com.jeudefoot.coupedumonde.Utils.AssetsManager;
import com.jeudefoot.coupedumonde.entite.Character.Character;
import com.jeudefoot.coupedumonde.entite.Character.ValkiryeWarrior;
import com.jeudefoot.coupedumonde.entite.Character.YellowSkeleton;

public class ThirdLevel extends Level {
    private AssetsManager assetManager;

    public ThirdLevel(Game game, AssetsManager assetManager, Character character) {
        super(game, assetManager, character, new ValkiryeWarrior(), assetManager.backgroundSky());
        computer.setDifficultyEasy();
    }
}
