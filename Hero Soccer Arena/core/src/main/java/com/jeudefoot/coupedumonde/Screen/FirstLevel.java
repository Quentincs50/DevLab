package com.jeudefoot.coupedumonde.Screen;

import com.badlogic.gdx.Game;
import com.jeudefoot.coupedumonde.Utils.AssetsManager;
import com.jeudefoot.coupedumonde.entite.Character.GreySkeleton;
import com.jeudefoot.coupedumonde.entite.Character.Character;

public class FirstLevel extends Level{
    protected Character character;
    public FirstLevel(Game game, AssetsManager assetsManager, Character character){
        super(game, assetsManager, character,  new GreySkeleton(), assetsManager.backgroundBamboo());
        computer.setDifficultyEasy();
    }
}
