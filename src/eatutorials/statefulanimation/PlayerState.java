package eatutorials.statefulanimation;

public enum PlayerState {
    IDLE("spr_m_traveler_idle_anim.gif"),
    WALKING("spr_m_traveler_walk_anim.gif"),
    RUNNING("spr_m_traveler_run_anim.gif"),
    JUMPING("spr_m_traveler_jump_1up_anim.gif"),
    MIDAIR("spr_m_traveler_jump_2midair_anim.gif"),
    FALLING("spr_m_traveler_jump_3down_anim.gif"),
    LANDING("spr_m_traveler_jump_4land_anim.gif");

    private String gifFileName;

    PlayerState(String gifFileName) {
        this.gifFileName = gifFileName;
    }

    public String getGifFileLocation() {
        return "eatutorials/statefulanimation/assets/" + this.gifFileName;
    }
}
