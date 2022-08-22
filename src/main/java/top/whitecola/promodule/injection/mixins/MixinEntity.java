package top.whitecola.promodule.injection.mixins;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.injection.wrappers.IMixinEntity;
import top.whitecola.promodule.modules.impls.combat.HitBox;

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


    @Inject(method = "getCollisionBorderSize", at = { @At("HEAD") }, cancellable = true)
    public void getCollisionBorderSize(CallbackInfoReturnable<Float> cir){
        HitBox hitBox = (HitBox) ProModule.getProModule().getModuleManager().getModuleByName("HitBox");
        if(hitBox!=null&&hitBox.isEnabled()){
            cir.setReturnValue(hitBox.getValue());
            cir.cancel();
        }
    }

}
