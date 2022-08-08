package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.PlayerSPUtils;

import java.awt.*;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class KeepSprint extends AbstractModule {
    private Robot robot;

    public KeepSprint(){
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
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
            if(mc.thePlayer.isSprinting()) {
                mc.thePlayer.setSprinting(false);
            }
            return;
        }






//        if(mc.thePlayer.movementInput.moveForward >= 0.8F){
        if(mc.gameSettings.keyBindForward.isKeyDown()){


            if(!mc.thePlayer.isSprinting()) {
                robot.keyPress(mc.gameSettings.keyBindForward.getKeyCode());
                robot.keyRelease(mc.gameSettings.keyBindForward.getKeyCode());
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
