package eatutorials.scenes;

import ea.*;
import ea.actor.Rectangle;
import ea.actor.Text;
import ea.animation.CircleAnimation;
import ea.event.KeyListener;
import ea.event.MouseButton;
import ea.event.MouseClickListener;
import eatutorials.util.Util;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class MainScene
extends Scene
implements KeyListener {

    private PauseMenu pauseMenu;

    public MainScene() {
        pauseMenu = new PauseMenu(this);

        Rectangle toAnimate = new Rectangle(5, 2);
        toAnimate.setCenter(0, -5);
        toAnimate.setColor(Color.ORANGE);

        CircleAnimation animation = new CircleAnimation(toAnimate, new Vector(0,0), 8, true, true);
        addFrameUpdateListener(animation);

        add(toAnimate);
        addKeyListener(this);

        Text info = new Text("Pause mit P", 0.5f);
        info.setCenter(-7, -5);
        add(info);
    }


    public static void main(String[] args) {
        Game.start(600, 400, new MainScene());
        Util.addScreenshotKey("Scenes");
        //Game.setDebug(true);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_P) {
            gotoPause();
        }
    }

    private void gotoPause() {
        Game.transitionToScene(pauseMenu);
    }

    private class PauseMenu
    extends Scene {

        private Scene mainScene;

        public PauseMenu(Scene mainScene) {
            this.mainScene = mainScene;

            MenuItem back = new MenuItem(new Vector(0,-5), "Zurück");
            add(back, back.label);

            Text headline = new Text("Mach mal Pause.", 2.5f);
            headline.setCenter(0, 3);
            add(headline);

        }

        private class MenuItem
        extends Rectangle
        implements MouseClickListener, FrameUpdateListener{

            private Text label;

            public MenuItem(Vector center, String labelText) {
                super(10, 1.5f);

                label = new Text(labelText, 1);
                label.setLayerPosition(1);
                label.setColor(Color.BLACK);
                label.setCenter(center);

                setLayerPosition(0);
                setColor(Color.cyan);
                setCenter(center);
            }

            @Override
            public void onMouseDown(Vector clickLoc, MouseButton mouseButton) {
                if(contains(clickLoc)) {
                    Game.transitionToScene(mainScene);
                }
            }

            @Override
            public void onFrameUpdate(float v) {
                if(contains(Game.getMousePositionInCurrentScene())) {
                    this.setColor(Color.MAGENTA);
                } else {
                    this.setColor(Color.CYAN);
                }
            }
        }
    }
}
