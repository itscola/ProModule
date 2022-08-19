package top.whitecola.promodule.modules.impls.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import top.whitecola.promodule.injection.wrappers.IMixinRenderManager;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.Render3DUtils;

import java.awt.*;
import java.util.Vector;

import static top.whitecola.promodule.utils.MCWrapper.mc;

public class ChestESP extends AbstractModule {

    public Vector<BlockPos> chestBlocks = new Vector<BlockPos>();
    protected Color color = new Color(186, 134, 109);


    @Override
    public void onRender3D(int pass, float partialTicks, long finishTimeNano) {
        for(BlockPos blockPos : chestBlocks){
            Render3DUtils.drawSolidBlockESP(
                    blockPos.getX() - ((IMixinRenderManager) mc.getRenderManager()).getRenderPosX(),
                    blockPos.getY() - ((IMixinRenderManager) mc.getRenderManager()).getRenderPosY(),
                    blockPos.getZ()- ((IMixinRenderManager) mc.getRenderManager()).getRenderPosZ(),
                    color.getRed(),color.getGreen(),color.getBlue(),0.2f
            );

        }
        super.onRender3D(pass, partialTicks, finishTimeNano);
    }

    @Override
    public void onRenderBlock(int x, int y, int z, Block block) {
        if(block instanceof BlockChest){
            BlockPos pos = new BlockPos(x,y,z);
            if(!chestBlocks.contains(pos)){
                this.chestBlocks.add(pos);
            }
        }
        super.onRenderBlock(x, y, z, block);
    }

    @Override
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        if(e.entity instanceof EntityPlayerSP){
            chestBlocks.clear();
        }
        super.onEntityJoinWorld(e);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.RENDERS;
    }

    @Override
    public String getModuleName() {
        return "ChestESP";
    }
}
