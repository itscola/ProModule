package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import static top.whitecola.promodule.utils.MCWrapper.*;

public class AutoTool extends AbstractModule {


    @ModuleSetting(name = "OnlyTools" , type = "select")
    public Boolean onlyTools = true;

    @Override
    public void onPlayerClickBlock(BlockPos blockPos, EnumFacing enumFacing) {
        if(mc.thePlayer==null){
            return;
        }

        if(mc.playerController.isInCreativeMode()){
            return;
        }


        if(!isBreakingBlockTool(mc.thePlayer.inventory.getCurrentItem())){
            return;
        }


        IBlockState blockState = mc.theWorld.getBlockState(blockPos);
        int selectSolt = mc.thePlayer.inventory.currentItem;
        float bestSpeed = 0;

        for(int i=0;i<8;i++){
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
            if(itemStack==null){
                continue;
            }
            float speed = itemStack.getStrVsBlock(blockState.getBlock());

            if(speed > bestSpeed){
                bestSpeed = speed;
                selectSolt = i;
            }
        }

        if(selectSolt==0) {
            return;
        }
        mc.thePlayer.inventory.currentItem = selectSolt;
    }


    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "AutoTool";

    }


    public boolean isBreakingBlockTool(ItemStack itemStack){
        if(itemStack==null){
            return false;
        }

        Item item = itemStack.getItem();

        if(item==null){
            return false;
        }


        if(item instanceof ItemPickaxe){
            return true;
        }else if(item instanceof ItemAxe){
            return true;
        }else if(item instanceof ItemShears){
            return true;
        }else if(item instanceof ItemSpade){
            return true;
        }

        return false;
    }
}
