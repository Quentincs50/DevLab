package com.jeudefoot.coupedumonde.Controller;

import com.jeudefoot.coupedumonde.entite.Ball.Ball;

public interface Controller {
   public void inputMovement(Ball ball);
   public void useSuperPower(Ball ball);
   public void shot(Ball ball);
}
