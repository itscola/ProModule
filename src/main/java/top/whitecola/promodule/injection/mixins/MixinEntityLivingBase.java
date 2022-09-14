package top.whitecola.promodule.injection.mixins;

import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.injection.wrappers.IMixinEntityLivingBase;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase implements IMixinEntityLivingBase {
    @Shadow protected abstract int getArmSwingAnimationEnd();

    @Override
    public int runGetArmSwingAnimationEnd() {
        return this.getArmSwingAnimationEnd();
    }
}
