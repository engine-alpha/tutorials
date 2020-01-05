package eatutorials.userinput;


import ea.Game;
import ea.Scene;
import ea.Vector;
import ea.actor.Circle;
import ea.actor.Rectangle;
import ea.actor.Text;
import ea.event.MouseButton;
import ea.event.MouseClickListener;
import eatutorials.util.Util;

import java.awt.Color;

public class PaintingCirclesAdvanced
        extends Scene
        implements MouseClickListener {

    static Color activeColor = Color.WHITE;
    static float activeDiameter = 1;

    public PaintingCirclesAdvanced() {
        this.addMouseClickListener(this);

        ColorRect white = new ColorRect(Color.WHITE);
        ColorRect blue = new ColorRect(Color.BLUE);
        ColorRect pink = new ColorRect(Color.PINK);
        ColorRect orange = new ColorRect(Color.ORANGE);

        white.setPosition(-8, 5);
        blue.setPosition(-6, 5);
        pink.setPosition(-4, 5);
        orange.setPosition(-2, 5);

        add(white, blue, pink, orange);

        SizeText sizeText1 = new SizeText(1);
        SizeText sizeText2 = new SizeText(2);
        SizeText sizeText3 = new SizeText(3);

        sizeText1.setPosition(0, 5);
        sizeText2.setPosition(2, 5);
        sizeText3.setPosition(4, 5);

        add(sizeText1, sizeText2, sizeText3);
    }

    private void paintCircleAt(float mX, float mY, float diameter, Color color) {
        Circle circle = new Circle(diameter);
        circle.setCenter(mX, mY);
        circle.setColor(color);
        this.add(circle);
    }

    public static void main(String[] args) {
        Game.start(600, 400, new PaintingCirclesAdvanced());
        //Game.setDebug(true);
        Util.addScreenshotKey("User Input Circles Advanced");
    }

    @Override
    public void onMouseDown(Vector position, MouseButton mouseButton) {
        if(position.getY() < 5) {
            paintCircleAt(position.getX(), position.getY(), activeDiameter, activeColor);
        }
    }

    private class ColorRect
    extends Rectangle
    implements MouseClickListener {

        private final Color color;

        public ColorRect(Color color) {
            super(1, 1);
            this.color = color;
            this.setColor(color);
        }

        @Override
        public void onMouseDown(Vector position, MouseButton mouseButton) {
            if(this.contains(position)) {
                activeColor = color;
            }
        }
    }

    private class SizeText
    extends Text
    implements MouseClickListener {

        private final float diameter;

        public SizeText(float diameter) {
            super(""+diameter, 1);
            this.diameter = diameter;
            this.setColor(Color.CYAN);
        }

        @Override
        public void onMouseDown(Vector position, MouseButton mouseButton) {
            if(this.contains(position)) {
                activeDiameter = diameter;
            }
        }
    }
}
