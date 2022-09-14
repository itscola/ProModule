package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.injection.wrappers.IMixinEntityLivingBase;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.RandomUtils;

import java.awt.*;
import java.util.Random;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class AutoClicker extends AbstractModule {

    @ModuleSetting(name = "MinCPS",addValue = 1f)
    public Float minCPS = 10f;

    @ModuleSetting(name = "MaxCPS",addValue = 1f)
    public Float maxCPS = 11f;

    @ModuleSetting(name = "OnlyTools" , type = "select")
    public Boolean onlySwordAndTools = false;

    @ModuleSetting(name = "LeftClick" ,type = "select" )
    public Boolean leftClick = true;

    @ModuleSetting(name = "RightClick" ,type = "select")
    public Boolean rightClick  = true;

    @ModuleSetting(name = "DelayRandomer" ,type = "select")
    public Boolean randomDelay = true;

    @ModuleSetting(name = "OnlyPointed" ,type = "select")
    public Boolean onlyPointed = true;

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
            delay = (int) RandomUtils.nextDouble(1000f/minCPS,1000f/maxCPS);

            if(randomDelay){
                delay = delay - (int) RandomUtils.nextDouble(1,25);
            }

            if(mc==null || mc.objectMouseOver==null){
                return;
            }

            if (mc.objectMouseOver.entityHit != null) {
                sendLeftClick(true);
                sendLeftClick(false);
            }else {
                if(onlyPointed) {
                    swingItemNoPacket();
                    return;
                }
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

    public void swingItemNoPacket() {
        if (!mc.thePlayer.isSwingInProgress ||mc.thePlayer.swingProgressInt < 0|| mc.thePlayer.swingProgressInt >= ((IMixinEntityLivingBase) mc.thePlayer).runGetArmSwingAnimationEnd() / 2) {
            mc.thePlayer.swingProgressInt = -1;
            mc.thePlayer.isSwingInProgress = true;
        }
    }

    @Override
    public void onEnable() {
        this.leftLastSwing = 0;
        this.rightLastSwing = 0;
        this.delay = 0;

        super.onEnable();
    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (AG)";

    }

}
