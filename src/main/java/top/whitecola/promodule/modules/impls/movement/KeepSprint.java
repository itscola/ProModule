package top.whitecola.promodule.modules.impls.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.potion.Potion;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.PlayerSPUtils;
import top.whitecola.promodule.utils.RandomUtils;

import java.awt.*;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class KeepSprint extends AbstractModule {

    @ModuleSetting(name = "VanillaSprint" , type = "select")
    public Boolean vanillaSprint = false;

    @ModuleSetting(name = "VanillaChance" )
    public Float vanillaChance = 70f;


    public KeepSprint(){

    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.MOVEMENT;
    }

    @Override
    public String getModuleName() {
        return "KeepSprint";

    }

    @Override
    public void onTick() {
        if(Minecraft.getMinecraft()==null||Minecraft.getMinecraft().theWorld==null || mc.thePlayer==null){
            return;
        }

        if(!PlayerSPUtils.isMoving() || PlayerSPUtils.isSneaking() || mc.thePlayer.getFoodStats().getFoodLevel() <= 6.0F || mc.thePlayer.isPotionActive(Potion.blindness) || mc.thePlayer.isCollidedHorizontally || mc.thePlayer.isUsingItem()){
//            if(mc.thePlayer.isSprinting()) {
//                mc.thePlayer.setSprinting(false);
//            }
            return;
        }

//        if(mc.thePlayer.movementInput.moveForward >= 0.8F){
        if(mc.gameSettings.keyBindForward.isKeyDown()){
            if(!mc.thePlayer.isSprinting()) {
                boolean boo = RandomUtils.nextDouble(0,100)<vanillaChance;
                if(boo&&vanillaSprint){
                    int keycode = mc.gameSettings.keyBindSprint.getKeyCode();
                    KeyBinding.setKeyBindState(keycode,true);
                    return;
                }
                mc.thePlayer.setSprinting(true);
            }
            return;
        }

        super.onTick();
    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (G)";
    }

}
