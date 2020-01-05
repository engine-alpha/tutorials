package eatutorials.gameloop;

import ea.*;
import ea.actor.Circle;
import ea.actor.Text;
import ea.collision.CollisionEvent;
import ea.collision.CollisionListener;
import ea.event.KeyListener;
import eatutorials.util.Util;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class SnakeHead
extends Scene {

    private Text scoreText = new Text("Score: 0", 1.4f);
    private int score = 0;

    private Snake snake = new Snake();

    public SnakeHead() {
        add(snake);

        scoreText.setPosition(-9, 5);
        add(scoreText);
        placeRandomGoodie();

    }

    public void setScore(int score) {
        this.score = score;
        this.scoreText.setContent("Score: " + score);
    }

    public void increaseScore() {
        setScore(score+1);
    }

    public void placeRandomGoodie() {
        float x = Random.nextFloat()*10 - 5;
        float y = Random.nextFloat()*10 - 5;
        Goodie goodie = new Goodie();
        goodie.setCenter(x, y);
        add(goodie);
        goodie.addCollisionListener(snake, goodie);
    }

    public static void main(String[] args) {
        Game.start(600, 400, new SnakeHead());
        Util.addScreenshotKey("User Input Circles Simple");
        //Game.setDebug(true);
    }

    private class Snake
    extends Circle
    implements FrameUpdateListener, KeyListener {
        private Vector v_per_s = new Vector(0,0);

        public Snake() {
            super(1);
            setColor(Color.GREEN);
        }

        @Override
        public void onFrameUpdate(float timeInS) {
            this.moveBy(v_per_s.multiply(timeInS));
        }

        @Override
        public void onKeyDown(KeyEvent keyEvent) {
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_W:
                    v_per_s = new Vector(0, 5);
                    break;
                case KeyEvent.VK_A:
                    v_per_s = new Vector(-5, 0);
                    break;
                case KeyEvent.VK_S:
                    v_per_s = new Vector(0, -5);
                    break;
                case KeyEvent.VK_D:
                    v_per_s = new Vector(5, 0);
                    break;
            }
        }
    }

    private class Goodie
    extends Text
    implements CollisionListener<Snake> {
        public Goodie() {
            super("Eat Me!",1);
            setColor(Color.RED);
        }

        @Override
        public void onCollision(CollisionEvent<Snake> collisionEvent) {
            increaseScore();
            this.remove();
            placeRandomGoodie();
        }
    }
}
