package top.whitecola.promodule.modules.impls.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.potion.Potion;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.PlayerSPUtils;
import top.whitecola.promodule.utils.RandomUtils;

import static top.whitecola.promodule.utils.MCWrapper.mc;

public class Sprint extends AbstractModule {


    @Override
    public void onTick() {
        if(Minecraft.getMinecraft()==null||Minecraft.getMinecraft().theWorld==null || mc.thePlayer==null){
            return;
        }

        if(!PlayerSPUtils.isMoving() || PlayerSPUtils.isSneaking() || mc.thePlayer.getFoodStats().getFoodLevel() <= 6.0F || mc.thePlayer.isPotionActive(Potion.blindness) || mc.thePlayer.isCollidedHorizontally || mc.thePlayer.isUsingItem()){
            return;
        }

        int keycode = mc.gameSettings.keyBindSprint.getKeyCode();

        if(mc.gameSettings.keyBindForward.isKeyDown()){
            if(!mc.thePlayer.isSprinting()) {
                KeyBinding.setKeyBindState(keycode,true);
                return;
            }
        }else {
            KeyBinding.setKeyBindState(keycode,false);

        }

        super.onTick();
    }


    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.MOVEMENT;
    }

    @Override
    public String getModuleName() {
        return "Sprint";

    }
}
