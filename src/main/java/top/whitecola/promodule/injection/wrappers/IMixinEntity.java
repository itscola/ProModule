package top.whitecola.promodule.injection.wrappers;

public interface IMixinEntity {
    void setRotationYaw(float rotationYaw);
    void setRotationPitch(float rotationPitch);
    void setPrevRotationYaw(float prevRotationYaw);
    void setPrevRotationPitch(float prevRotationPitch);
    float getPrevRotationPitch();
    float getPrevRotationYaw();
    float getRotationPitch();
    float getRotationYaw();

}
