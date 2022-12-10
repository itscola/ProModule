package top.whitecola.promodule.injection.mixins;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.injection.wrappers.IMixinItemStack;

@Mixin(ItemStack.class)
public class MixinItemStack implements IMixinItemStack {
    @Shadow public int stackSize;

    @Override
    public int getStackSize() {
        return this.stackSize;
    }
}
