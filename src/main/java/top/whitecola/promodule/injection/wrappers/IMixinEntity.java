package top.whitecola.promodule.injection.wrappers;

import net.minecraft.util.Vec3;

public interface IMixinEntity {
    void setRotationYaw(float rotationYaw);
    void setRotationPitch(float rotationPitch);
    void setPrevRotationYaw(float prevRotationYaw);
    void setPrevRotationPitch(float prevRotationPitch);
    float getPrevRotationPitch();
    float getPrevRotationYaw();
    float getRotationPitch();
    float getRotationYaw();

    Vec3 getVectorForRotation1(float pitch, float yaw);

}
