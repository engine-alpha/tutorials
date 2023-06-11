package eatutorials.collision;

import ea.*;
import ea.actor.BodyType;
import ea.actor.Image;
import ea.actor.Rectangle;
import ea.actor.Text;
import ea.collision.CollisionEvent;
import ea.collision.CollisionListener;
import ea.event.KeyListener;

import java.awt.event.KeyEvent;


public class FroggyJump extends Scene {

    private Frog frog;

    public FroggyJump() {
        frog = new Frog();
        add(frog);
        setGravity(Vector.DOWN.multiply(10));
        Camera cam = getCamera();
        cam.setFocus(frog);
        cam.setOffset(new Vector(0, 4));
        //makeLevel(40);
        makePlatforms(10);
    }

    public static void main(String[] args) {
        FroggyJump fj = new FroggyJump();
        Game.start(400, 600, fj);
        //Game.setDebug(true);
    }

    private void makePlatforms(int heightLevel) {
        for(int i=0; i < heightLevel; i++) {
            Platform platform = new Platform(5, 1);
            platform.setPosition(0, i*4);
            add(platform);
        }
    }

    private void makeLevel(int heightLevel) {
        for (int i = 0; i < heightLevel; i++) {
            int numPlatforms = Random.range(2)+1;
            for(int j = 0; j < numPlatforms; j++) {
                Platform platform = new Platform(6/numPlatforms, 1);
                platform.setPosition(numPlatforms*(j+1)*i*Random.range(), i*4);
                add(platform);
            }

            if(i>3) {
                for (int j = 0; j < Random.range(3); j++) {
                    SpikeBall ball = SpikeBall.setupSpikeBall(Random.range()*(4+j)*i, Random.range()*4 + 0.5f + 5*i, getMainLayer());
                }
            }
        }
    }
}

class DeathScreen extends Scene implements KeyListener {
    public DeathScreen() {
        Text message = new Text("You Died. Press any button to try again", .6f);
        message.setCenter(getCamera().getPosition());
        add(message);
    }

    @Override
    public void onKeyDown(KeyEvent e) {
        Game.transitionToScene(new FroggyJump());
    }
}

class Frog extends Image implements FrameUpdateListener {

    private boolean canJump = true;
    private static float MAX_SPEED = 4;

    public Frog() {
        super("eatutorials/collision/assets/Jump (32x32).png", 25f);
        setBodyType(BodyType.DYNAMIC);
        setRotationLocked(true);
    }

    public void setJumpEnabled(boolean jumpEnabled) {
        this.canJump = jumpEnabled;
    }

    public void kill() {
        Game.transitionToScene(new DeathScreen());
    }

    @Override
    public void onFrameUpdate(float deltaSeconds) {
        Vector velocity = this.getVelocity();
        //A: Image direction
        if(velocity.getX() < 0) {
            setFlipHorizontal(true);
        } else {
            setFlipHorizontal(false);
        }

        //B: Horizontal Movement
        if (Game.isKeyPressed(KeyEvent.VK_A)) {
            if(velocity.getX() > 0) {
                setVelocity(new Vector(0, velocity.getY()));
            }
            applyForce(Vector.LEFT.multiply(600));
        } else if (Game.isKeyPressed(KeyEvent.VK_D)) {
            if(velocity.getX() < 0) {
                setVelocity(new Vector(0, velocity.getY()));
            }
            applyForce(Vector.RIGHT.multiply(600));
        }
        if(Math.abs(velocity.getX()) > MAX_SPEED) {
            setVelocity(new Vector(MAX_SPEED * Math.signum(velocity.getX()) , velocity.getY()));
        }

        //C: Jump if possible
        if (isGrounded() && velocity.getY() <= 0 && canJump) {
            this.setVelocity(new Vector(velocity.getX(), 0));
            this.applyImpulse(Vector.UP.multiply(180));
        }
    }
}

class Platform extends Rectangle implements CollisionListener<Frog> {

    public Platform(float width, float height) {
        super(width, height);
        setBodyType(BodyType.STATIC);
        this.addCollisionListener(Frog.class, this);
    }

    @Override
    public void onCollision(CollisionEvent<Frog> collisionEvent) {
        float frogY = collisionEvent.getColliding().getPosition().getY();
        if(frogY<this.getY()) {
            collisionEvent.ignoreCollision();
            collisionEvent.getColliding().setJumpEnabled(false);
        }
    }

    @Override
    public void onCollisionEnd(CollisionEvent<Frog> collisionEvent) {
        collisionEvent.getColliding().setJumpEnabled(true);
    }
}

class SpikeBall extends Image implements CollisionListener<Frog> {

    private static class SpikeSensor extends Rectangle implements CollisionListener<Frog> {

        private SpikeBall ball;

        public SpikeSensor(SpikeBall ball) {
            super(2, 8);
            this.ball = ball;
            setVisible(false);
            setBodyType(BodyType.SENSOR);
            this.addCollisionListener(Frog.class, this);
            setGravityScale(0);
        }

        @Override
        public void onCollision(CollisionEvent<Frog> collisionEvent) {
            ball.setGravityScale(1);
        }
    }

    public SpikeBall() {
        super("eatutorials/collision/assets/Spiked Ball.png", 40);
        setGravityScale(0);
        this.addCollisionListener(Frog.class, this);
    }

    public static SpikeBall setupSpikeBall(float x, float y, Layer layer) {
        SpikeBall ball = new SpikeBall();
        ball.setCenter(x, y);
        SpikeSensor sensor = new SpikeSensor(ball);
        sensor.setPosition(x-1, y-8);
        layer.add(ball, sensor);
        return ball;
    }

    @Override
    public void onCollision(CollisionEvent<Frog> collisionEvent) {
        collisionEvent.getColliding().kill();
    }
}