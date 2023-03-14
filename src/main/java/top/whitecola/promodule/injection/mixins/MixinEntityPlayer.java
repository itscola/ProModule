package top.whitecola.promodule.injection.mixins;

import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.injection.wrappers.IMixinEntityPlayer;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer implements IMixinEntityPlayer {

    @Shadow private int itemInUseCount;

    @Override
    public void setItemInUseCount(int i) {
        this.itemInUseCount = i;
    }
}
