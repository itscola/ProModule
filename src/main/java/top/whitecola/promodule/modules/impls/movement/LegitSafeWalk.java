package top.whitecola.promodule.modules.impls.movement;

import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.lwjgl.Sys;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.PlayerSPUtils;

import static top.whitecola.promodule.utils.MCWrapper.*;



public class LegitSafeWalk extends AbstractModule {

    @Override
    public void onTick() {
        if (mc.theWorld == null || mc.thePlayer==null) {
            return;
        }
        EntityPlayerSP thePlayer = mc.thePlayer;
        if(thePlayer.getEntityWorld().getBlockState(new BlockPos(thePlayer).add(0, -1, 0)).getBlock() == Blocks.air && thePlayer.onGround){
            thePlayer.motionX = ((thePlayer.motionX > 0.0) ? 1 : -1) * 0;
            thePlayer.motionZ = ((thePlayer.motionZ > 0.0) ? 1 : -1) * 0;
        }

        super.onTick();
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.MOVEMENT;
    }

    @Override
    public String getModuleName() {

        return "LegitSafeWalk";

    }

    @Override
    public String getDisplayName() {
        return this.getModuleName()+" (AG)";
    }
}
