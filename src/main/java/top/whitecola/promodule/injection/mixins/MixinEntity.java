package top.whitecola.promodule.injection.mixins;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.injection.wrappers.IMixinEntity;

@Mixin(Entity.class)
public class MixinEntity implements IMixinEntity {


    @Shadow public float rotationYaw;

    @Shadow public float rotationPitch;

    @Shadow public float prevRotationYaw;

    @Shadow public float prevRotationPitch;


    @Override
    public void setRotationYaw(float rotationYaw) {
        this.rotationYaw = rotationYaw;
    }

    @Override
    public void setRotationPitch(float rotationPitch) {
        this.rotationPitch = rotationPitch;
    }

    @Override
    public void setPrevRotationYaw(float prevRotationYaw) {
        this.prevRotationYaw = prevRotationYaw;
    }

    @Override
    public void setPrevRotationPitch(float prevRotationPitch) {
        this.prevRotationPitch = prevRotationPitch;
    }

    @Override
    public float getPrevRotationPitch() {
        return this.prevRotationPitch;
    }

    @Override
    public float getPrevRotationYaw() {
        return this.prevRotationYaw;
    }

    @Override
    public float getRotationPitch() {
        return this.rotationPitch;
    }

    @Override
    public float getRotationYaw() {
        return this.rotationYaw;
    }


}
