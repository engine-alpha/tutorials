package eatutorials.userinput;

import ea.Game;
import ea.Scene;
import ea.actor.Rectangle;
import ea.event.KeyListener;
import eatutorials.util.Util;

import java.awt.Color;
import java.awt.event.KeyEvent;


public class MovingRectangle
extends Scene {

    public MovingRectangle() {
        this.add(new Character());
    }

    public static void main(String[] args) {
        Game.start(600, 400, new MovingRectangle());
        Util.addScreenshotKey("rectA");
    }

    private class Character
    extends Rectangle
    implements KeyListener {

        public Character() {
            super(2, 2);
            this.setCenter(0,0);
            setColor(Color.RED);
        }

        @Override
        public void onKeyDown(KeyEvent keyEvent) {
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_W:
                    this.moveBy(0, 0.5f);
                    break;
                case KeyEvent.VK_A:
                    this.moveBy(-0.5f, 0);
                    break;
                case KeyEvent.VK_S:
                    this.moveBy(0, -0.5f);
                    break;
                case KeyEvent.VK_D:
                    this.moveBy(0.5f, 0);
                    break;
            }
        }
    }

}
