package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.RandomUtils;

import java.awt.*;
import java.util.Random;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class AutoClicker extends AbstractModule {

    @ModuleSetting(name = "MinCPS")
    public Float minCPS = 8f;

    @ModuleSetting(name = "MaxCPS")
    public Float maxCPS = 15f;

    @ModuleSetting(name = "OnlySwordAndTools")
    public Boolean onlySwordAndTools = false;

    @ModuleSetting(name = "LeftClick")
    public Boolean leftClick = true;

    @ModuleSetting(name = "RightClick")
    public Boolean rightClick  = true;

    protected long leftLastSwing;
    protected long rightLastSwing;

    protected int delay;

//    private Robot robot;


    public AutoClicker(){
        super();

    }

    @Override
    public void onTick() {
        if(!mc.gameSettings.keyBindAttack.isKeyDown()){
            return;
        }

        if(onlySwordAndTools && (mc.thePlayer.getHeldItem()==null || !((mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)||(mc.thePlayer.getHeldItem().getItem() instanceof ItemTool)))){
            return;
        }



        if(leftClick && System.currentTimeMillis() - this.leftLastSwing >= delay){
            if(mc.thePlayer.isEating()){
                return;
            }

            this.leftLastSwing = System.currentTimeMillis();
            delay = (int) RandomUtils.nextFloat(1000f/minCPS,1000f/maxCPS);
            if (mc.objectMouseOver.entityHit != null) {
                sendLeftClick(true);
                sendLeftClick(false);
            }else {
                mc.thePlayer.swingItem();
            }
            return;

        }

        super.onTick();
    }

    @Override
    public void onModuleSettingChanged(String name) {
        super.onModuleSettingChanged(name);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "AutoClicker";

    }

    private void sendLeftClick(boolean state){
        int keycode = mc.gameSettings.keyBindAttack.getKeyCode();

        if (state) {
            KeyBinding.onTick(keycode);
        }
    }

    private void sendRightClick(boolean state){
        int keycode = mc.gameSettings.keyBindUseItem.getKeyCode();

        if (state) {
            KeyBinding.onTick(keycode);
        }
    }

}
