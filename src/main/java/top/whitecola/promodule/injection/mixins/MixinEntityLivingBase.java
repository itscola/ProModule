package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.whitecola.promodule.injection.wrappers.IMixinEntityLivingBase;
import top.whitecola.promodule.utils.wrapper.RotationPitchHead;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase implements IMixinEntityLivingBase {
    @Shadow protected abstract int getArmSwingAnimationEnd();

    @Shadow protected double newRotationPitch;

    @Override
    public int runGetArmSwingAnimationEnd() {
        return this.getArmSwingAnimationEnd();
    }

    @Inject(method = "onEntityUpdate", at = @At(value = "RETURN"))
    public void onEntityUpdate(CallbackInfo ci){
        EntityLivingBase entity = (EntityLivingBase) (Object) this;
        if(entity instanceof EntityPlayerSP){
            RotationPitchHead.prevRotationPitchHead = RotationPitchHead.rotationPitchHead;
        }
    }


}
