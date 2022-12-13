package top.whitecola.promodule.injection.wrappers;

public interface IMixinEntityPlayerSP {
    boolean isServerSprintState();

    void setServerSprintState(boolean state);

    boolean isServerSneakState();
    void setServerSneakState(boolean state);
    void setSpeedInAir(float value);

}
