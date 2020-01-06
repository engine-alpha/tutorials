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
    private static final Float ACC_TIME = 1.5f;
    private static final Float DEC_TIME = 0.8f;

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
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                attemptJump();
                break;
            case KeyEvent.VK_A:
                initiateMovement(false);
                break;
            case KeyEvent.VK_D:
                initiateMovement(true);
                break;
        }
    }

    @Override
    public void onKeyUp(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_A:
                stopMovement();
                break;
            case KeyEvent.VK_D:
                stopMovement();
                break;
        }
    }

    private void attemptJump() {
        PlayerState state = getCurrentState();
        if(state == PlayerState.IDLE || state == PlayerState.WALKING || state == PlayerState.RUNNING) {
            if(isGrounded()) {
                applyImpulse(new Vector(0, 850));
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

    private void initiateMovement(boolean toTheRight) {
        KeyFrames movementSpeed = new KeyFrames((speed) -> setVelocity(new Vector(speed, getVelocity().getY())));
        movementSpeed.addKeyframe(new KeyFrame<>(getVelocity().getX(), KeyFrame.Type.LINEAR, 0));
        movementSpeed.addKeyframe(new KeyFrame<>(MAX_SPEED * (toTheRight ? 1 : -1), KeyFrame.Type.SMOOTHED_SIN, ACC_TIME));
        movementSpeed.setInifinite(true);
        setNewMovement(movementSpeed);
    }

    private void stopMovement() {
        if(Game.isKeyPressed(KeyEvent.VK_A) || Game.isKeyPressed(KeyEvent.VK_D)) {
            return;
        }
        KeyFrames movementSpeed = new KeyFrames((speed) -> setVelocity(new Vector(speed, getVelocity().getY())));
        movementSpeed.addKeyframe(new KeyFrame<>(getVelocity().getX(), KeyFrame.Type.SMOOTHED_SIN, 0));
        movementSpeed.addKeyframe(new KeyFrame<>(0f, KeyFrame.Type.SMOOTHED_SIN, DEC_TIME));
        movementSpeed.setInifinite(false);
        setNewMovement(movementSpeed);
    }

    private void setNewMovement(KeyFrames movementInterpolation) {
        if(currentMovement != null) {
            removeFrameUpdateListener(currentMovement);
        }
        currentMovement = movementInterpolation;
        addFrameUpdateListener(movementInterpolation);
    }
}
