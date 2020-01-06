package eatutorials.statefulanimation;

import ea.Game;
import ea.Scene;
import ea.Vector;
import ea.actor.BodyType;
import ea.actor.Rectangle;

import java.awt.Color;

public class StatefulAnimationTestScene
extends Scene {

    public StatefulAnimationTestScene() {
        StatefulPlayerCharacter character = new StatefulPlayerCharacter();

        setupGround();
        add(character);
        getCamera().setFocus(character);

        setGravity(new Vector(0, -9.81f));
    }

    private void setupGround() {
        Rectangle ground = new Rectangle(200, 0.2f);
        ground.setCenter(0, -5);
        ground.setColor(new Color(255, 195, 150));
        ground.setBodyType(BodyType.STATIC);
        ground.setRestitution(0);
        add(ground);

        makePlatform(12, 0.3f).setCenter(6, -1);
        makePlatform(7, 0.3f).setCenter(-3, 2);
        makePlatform(20, 0.3f).setCenter(0, 6);

    }

    private Rectangle makePlatform(float w, float h) {
        Rectangle r = new Rectangle(w, h);
        r.setColor(new Color(0, 194, 255));
        r.setBodyType(BodyType.STATIC);
        r.setRestitution(0);
        add(r);
        return r;
    }

    public static void main(String[] args) {
        Game.start(1200, 820, new StatefulAnimationTestScene());
        Game.setDebug(true);
    }
}
