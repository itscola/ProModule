package top.whitecola.promodule.modules.impls.world;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.injection.wrappers.IMixinMinecraft;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.RandomUtils;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class FastPlace extends AbstractModule {

    @ModuleSetting(name = "RandomValue",type = "select")
    private Boolean randomValue = true;

    @ModuleSetting(name = "minValue",min = 0,max = 4,addValue = 1)
    private Float minValue = 0f;

    @ModuleSetting(name = "minValue",min = 0,max = 4,addValue = 1)
    private Float maxValue = 1f;

    @ModuleSetting(name = "OnlyBlock",type = "select")
    private Boolean onlyBlock = true;

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.WORLD;
    }

    @Override
    public String getModuleName() {
        return "FastPlace";

    }

    @Override
    public void onTick() {
        if(Minecraft.getMinecraft()==null||Minecraft.getMinecraft().theWorld==null || mc.thePlayer==null){
            return;
        }

        if(onlyBlock){

            ItemStack item = mc.thePlayer.getHeldItem();
            if(item==null||item.getItem()==null){
                return;
            }

            if(!(item.getItem() instanceof ItemBlock)){
                return;
            }
        }

        int value = 0;
        if(randomValue){
            value = (int)RandomUtils.nextDouble(minValue,maxValue);
        }else {
            value = maxValue.intValue();
        }

        ((IMixinMinecraft)mc).setRightClickDelayTimer(value);


        super.onTick();
    }

    @Override
    public void onDisable() {
        if(((IMixinMinecraft)mc).getRightClickDelayTimer()<4)
            ((IMixinMinecraft)mc).setRightClickDelayTimer(4);
        super.onDisable();
    }

    @Override
    public void onEnable() {
        this.randomValue = true;
        this.maxValue=1f;
        super.onEnable();
    }
}
