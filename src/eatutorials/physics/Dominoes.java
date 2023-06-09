package eatutorials.physics;

import ea.FrameUpdateListener;
import ea.Game;
import ea.Scene;
import ea.Vector;
import ea.actor.BodyType;
import ea.actor.Circle;
import ea.actor.Rectangle;
import ea.event.MouseButton;
import ea.event.MouseClickListener;

import java.awt.Color;

public class Dominoes extends Scene implements FrameUpdateListener, MouseClickListener {

    private Rectangle ground;
    private Rectangle wall;
    private Circle ball;
    private Rectangle angle;

    public Dominoes() {
        setupBasicObjects();
        setupPhysics();
        setupAngle();
        makeDominoes(20, 0.4f, 3f);
    }

    private void setupBasicObjects() {
        ground = new Rectangle(200, 2);
        ground.setCenter(0, -5);
        ground.setColor(Color.WHITE);
        add(ground);

        ball = new Circle(0.5f);
        ball.setColor(Color.RED);
        ball.setPosition(-10, -2);
        add(ball);

        wall = new Rectangle(1, 40);
        wall.setPosition(-14, -4);
        wall.setColor(Color.WHITE);
        add(wall);
    }

    private void setupAngle() {
        angle = new Rectangle(1, 0.25f);
        angle.setColor(Color.GRAY);
        add(angle);
    }

    private void setupPhysics() {
        ground.setBodyType(BodyType.STATIC);
        wall.setBodyType(BodyType.STATIC);
        ball.setBodyType(BodyType.DYNAMIC);

        this.setGravity(new Vector(0, -9.81f));
    }

    private void makeDominoes(int num, float beamWidth, float beamHeight) {
        for(int i=0; i<num; i++) {
            Rectangle beam = new Rectangle(beamWidth, beamHeight);
            beam.setPosition(i*3*(beamWidth), -4);
            beam.setBodyType(BodyType.DYNAMIC);
            beam.setColor(Color.BLUE);
            add(beam);
        }
    }

    public static void main(String[] args) {
        Dominoes dominoes = new Dominoes();
        Game.start(800, 300, dominoes);
    }

    @Override
    public void onFrameUpdate(float deltaSeconds) {
        Vector mousePosition = getMousePosition();
        Vector ballCenter = ball.getCenter();

        Vector distance = ballCenter.getDistance(mousePosition);
        angle.setPosition(ball.getCenter());
        angle.setSize(distance.getLength(), 0.25f);
        float rot = Vector.RIGHT.getAngle(distance);
        angle.setRotation(rot);
    }

    @Override
    public void onMouseDown(Vector position, MouseButton button) {
        Vector impulse = ball.getCenter().getDistance(position).multiply(5);
        ball.applyImpulse(impulse);
    }
}
