package eatutorials.statefulanimation;

import ea.FrameUpdateListener;
import ea.Game;
import ea.Vector;
import ea.actor.Animation;
import ea.actor.BodyType;
import ea.actor.StatefulAnimation;
import ea.animation.KeyFrame;
import ea.animation.KeyFrames;
import ea.event.KeyListener;

import java.awt.event.KeyEvent;

public class StatefulPlayerCharacter
extends StatefulAnimation<PlayerState>
implements KeyListener, FrameUpdateListener {

    private static final float THRESHOLD = 0.01f;
    private static final float RUNNING_THRESHOLD = 10;
    private static final float WALKING_THRESHOLD = 1;

    private static final Float MAX_SPEED = 20f;
    private static final float FORCE = 16000;
    private static final float JUMP_IMPULSE = 1100;

    private KeyFrames currentMovement = null;

    public StatefulPlayerCharacter() {
        super(3, 3); //All GIFs are 64x64 px, hence: Same width/height. In this case: 3m each
        
        setupPlayerStates();
        setupAutomaticTransitions();
        setupPhysics();
    }

    private void setupPlayerStates() {
        for(PlayerState state : PlayerState.values()) {
            Animation animationOfState = Animation.createFromAnimatedGif(state.getGifFileLocation(), 3,3);
            addState(state, animationOfState);
        }
    }

    private void setupAutomaticTransitions() {
        setStateTransition(PlayerState.MIDAIR, PlayerState.FALLING);
        setStateTransition(PlayerState.LANDING, PlayerState.IDLE);
    }

    private void setupPhysics() {
        setBodyType(BodyType.DYNAMIC);
        setRotationLocked(true);
        setRestitution(0);
        setFriction(30f);
        setLinearDamping(.3f);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                attemptJump();
                break;
        }
    }

    private void attemptJump() {
        PlayerState state = getCurrentState();
        if(state == PlayerState.IDLE || state == PlayerState.WALKING || state == PlayerState.RUNNING) {
            if(isGrounded()) {
                applyImpulse(new Vector(0, JUMP_IMPULSE));
                setState(PlayerState.JUMPING);
            }
        }
    }

    @Override
    public void onFrameUpdate(float dT) {
        Vector velocity = getVelocity();
        PlayerState state = getCurrentState();

        if(velocity.getY() < -THRESHOLD) {
            switch(state) {
                case JUMPING:
                    setState(PlayerState.MIDAIR);
                    break;
                case IDLE:
                case WALKING:
                case RUNNING:
                    setState(PlayerState.FALLING);
                    break;
                default:
                    break;
            }
        } else if(velocity.getY() < THRESHOLD && state==PlayerState.FALLING) {
            setState(PlayerState.LANDING);
        }

        if(Math.abs(velocity.getX()) > MAX_SPEED) {
            setVelocity(new Vector(Math.signum(velocity.getX()) * MAX_SPEED, velocity.getY()));
        }

        if(Game.isKeyPressed(KeyEvent.VK_A)) {
            applyForce(new Vector(-FORCE, 0));
        } else if(Game.isKeyPressed(KeyEvent.VK_D)) {
            applyForce(new Vector(FORCE, 0));
        }

        if(state == PlayerState.IDLE || state == PlayerState.WALKING || state == PlayerState.RUNNING) {
            float velXTotal = Math.abs(velocity.getX());
            if(velXTotal > RUNNING_THRESHOLD) {
                changeState(PlayerState.RUNNING);
            } else if(velXTotal > WALKING_THRESHOLD) {
                changeState(PlayerState.WALKING);
            } else {
                changeState(PlayerState.IDLE);
            }
        }

        if(velocity.getX() > 0) {
            setFlipHorizontal(false);
        } else if(velocity.getX() < 0) {
            setFlipHorizontal(true);
        }
    }
}
