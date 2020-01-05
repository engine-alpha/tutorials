package eatutorials.userinput;

import ea.Game;
import ea.Scene;
import ea.Vector;
import ea.actor.Circle;
import ea.event.MouseButton;
import ea.event.MouseClickListener;
import eatutorials.util.Util;

public class PaintingCircles
extends Scene
implements MouseClickListener {

    public PaintingCircles() {
        this.addMouseClickListener(this);
    }

    private void paintCircleAt(float mX, float mY, float diameter) {
        Circle circle = new Circle(diameter);
        circle.setCenter(mX, mY);
        this.add(circle);
    }

    public static void main(String[] args) {
        Game.start(600, 400, new PaintingCircles());
        Util.addScreenshotKey("User Input Circles Simple");
    }

    @Override
    public void onMouseDown(Vector position, MouseButton mouseButton) {
        paintCircleAt(position.getX(), position.getY(), 1);
    }
}
