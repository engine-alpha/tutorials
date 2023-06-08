package eatutorials.gameloop;

import ea.*;
import ea.actor.Circle;
import ea.actor.Text;
import ea.collision.CollisionEvent;
import ea.collision.CollisionListener;
import eatutorials.util.Util;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class ActualSnake
        extends Scene
implements FrameUpdateListener{

    private Text scoreText = new Text("Score: 0", 1.4f);
    private int score = 0;

    private Snake snakeHead = new Snake();

    private float snake_speed = 5;

    private boolean makeNewHead = false;

    public ActualSnake() {
        add(snakeHead);
        scoreText.setPosition(-9, 5);
        add(scoreText);
        placeRandomGoodie();

    }

    public void setScore(int score) {
        this.score = score;
        this.snake_speed = 5 + (score*0.1f);
        this.scoreText.setContent("Score: " + score);
    }

    public void increaseScore() {
        setScore(score+1);
    }

    public void placeRandomGoodie() {
        float x = Random.range()*10 - 5;
        float y = Random.range()*10 - 5;
        Goodie goodie = new Goodie();
        goodie.setCenter(x, y);
        add(goodie);
        goodie.addCollisionListener(snakeHead, goodie);
    }

    public static void main(String[] args) {
        Game.start(600, 400, new ActualSnake());
        Util.addScreenshotKey("Game Loop Actual Snake");
        //Game.setDebug(true);
    }

    @Override
    public void onFrameUpdate(float timeInS) {
        float dX=0, dY=0;
        if(Game.isKeyPressed(KeyEvent.VK_W)) {
            dY = snake_speed *timeInS;
        }
        if(Game.isKeyPressed(KeyEvent.VK_A)) {
            dX = -snake_speed *timeInS;
        }
        if(Game.isKeyPressed(KeyEvent.VK_S)) {
            dY = -snake_speed *timeInS;
        }
        if(Game.isKeyPressed(KeyEvent.VK_D)) {
            dX =  snake_speed *timeInS;
        }

        if(makeNewHead) {
            Snake newHead = new Snake();
            newHead.setCenter(snakeHead.getCenter());
            newHead.next = snakeHead;
            newHead.moveBy(new Vector(dX, dY).multiply(1));
            add(newHead);
            snakeHead = newHead;
            makeNewHead = false;
        } else if(dX != 0 || dY != 0) {
            snakeHead.snakeHeadMove(dX, dY);
        }
    }

    private class Snake
            extends Circle {

        private Snake next = null;

        public Snake() {
            super(1);
            setColor(Color.GREEN);
        }

        private void snakeHeadMove(float dX, float dY) {
            Vector mycenter = getCenter();
            moveBy(dX, dY);
            if(next != null) {
                next.snakeChildrenMove(mycenter);
            }
        }

        private void snakeChildrenMove(Vector newCenter) {
            Vector mycenter = getCenter();
            setCenter(newCenter);
            if(next != null) {
                next.snakeChildrenMove(mycenter);
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
            makeNewHead = true;
            this.remove();
            placeRandomGoodie();
        }
    }
}
