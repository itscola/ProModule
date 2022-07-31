package top.whitecola.promodule.utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import static top.whitecola.kateclient.utils.MCWrapper.*;

public class ParticleUtils {

    public static void spawnBloodParticle(Entity entity){
        if(entity==null){
            return;
        }
        BlockPos blockPos = new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
        spawnBloodParticle(blockPos);
    }

    public static void spawnBloodParticle(BlockPos blockPos){
        IBlockState blockState = Blocks.redstone_block.getDefaultState();
        if(mc.renderGlobal==null){
            return;
        }
        mc.renderGlobal.playAuxSFX(null,2001,blockPos, Block.getStateId(blockState));
    }

}
