package top.whitecola.promodule.modules.impls.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class AutoPlace extends AbstractModule {
    private BlockPos lastBlockPos = null;
    private long last = 0;



    @Override
    public void onRender3D(int pass, float partialTicks, long finishTimeNano) {
        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            if(mc.currentScreen==null&&!mc.thePlayer.capabilities.isFlying){
                ItemStack item = mc.thePlayer.getHeldItem();
                if (item != null && item.getItem() instanceof ItemBlock) {
                    MovingObjectPosition movingObjectPosition = mc.objectMouseOver;
                    if (movingObjectPosition != null && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && movingObjectPosition.sideHit != EnumFacing.UP && movingObjectPosition.sideHit != EnumFacing.DOWN) {
                        BlockPos pos = movingObjectPosition.getBlockPos();
                        if(this.lastBlockPos == null || pos.getX() != this.lastBlockPos.getX() || pos.getY() != this.lastBlockPos.getY() || pos.getZ() != this.lastBlockPos.getZ()){
                            Block b = mc.theWorld.getBlockState(pos).getBlock();
                            if (b != null && b != Blocks.air && !(b instanceof BlockLiquid)) {
                                if (Mouse.isButtonDown(1)) {
                                    long n = System.currentTimeMillis();
                                    if (n - this.last >= 25L) {
                                        this.last = n;
                                        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, item, pos, movingObjectPosition.sideHit, movingObjectPosition.hitVec)) {

//                                            sendRightClick(true);
                                            mc.thePlayer.swingItem();
                                            mc.getItemRenderer().resetEquippedProgress();
//                                            sendRightClick(false);
                                            this.lastBlockPos = pos;
                                        }


                                    }

                                }



//                                }
                            }
                        }
                    }
                }
            }
        }





        super.onRender3D(pass, partialTicks, finishTimeNano);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.WORLD;
    }

    @Override
    public String getModuleName() {
        return "AutoPlace";

    }

    @Override
    public void onDisable() {
        this.lastBlockPos = null;
        this.last = 0;
        super.onDisable();
    }

    private void sendRightClick(boolean state){
        int keycode = mc.gameSettings.keyBindUseItem.getKeyCode();

        if (state) {
            KeyBinding.onTick(keycode);
        }
    }
}
