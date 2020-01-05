package eatutorials.userinput;

import ea.Game;
import ea.Scene;
import ea.actor.Text;
import ea.event.KeyListener;
import eatutorials.util.Util;

import java.awt.event.KeyEvent;

public class TastenCounter
extends Scene {

    public TastenCounter() {
        this.add(new CounterText());
    }

    public static void main(String[] args) {
        Game.start(500, 200, new TastenCounter());
    }

    private class CounterText
    extends Text
    implements KeyListener {

        private int counter = 0;

        public CounterText() {
            super("You pressed 0 keys.", 2);
            this.setCenter(0,0);
        }

        @Override
        public void onKeyDown(KeyEvent keyEvent) {
            counter++;
            this.setContent("You pressed " + counter + " keys.");
            this.setCenter(0,0);
            if(keyEvent.getKeyCode() == KeyEvent.VK_1) {
                Util.makeScreenshot("counter");
            }
        }
    }
}
