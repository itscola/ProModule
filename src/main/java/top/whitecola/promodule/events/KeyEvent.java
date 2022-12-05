package top.whitecola.promodule.events;

import net.minecraftforge.fml.common.gameevent.InputEvent;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.keybinds.AutoPlaceKeyBind;
import top.whitecola.promodule.keybinds.ClearTargetKeybind;
import top.whitecola.promodule.keybinds.EagleKeyBind;
import top.whitecola.promodule.keybinds.KillauraKeyBind;
import top.whitecola.promodule.modules.impls.combat.AimAssist;
import top.whitecola.promodule.modules.impls.combat.Killaura;
import top.whitecola.promodule.modules.impls.movement.Eagle;
import top.whitecola.promodule.modules.impls.world.AutoPlace;
import top.whitecola.promodule.utils.PlayerSPUtils;

public class KeyEvent extends EventAdapter{


    public KeyEvent() {
        super(KeyEvent.class.getSimpleName());
    }

    @Override
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if(EagleKeyBind.getInstance().isPressed()){
            Eagle eagle = (Eagle) ProModule.getProModule().getModuleManager().getModuleByName("Eagle");
            eagle.setEnabled(!eagle.isEnabled());
            PlayerSPUtils.sendMsgToSelf("Eagle: "+eagle.isEnabled());
        }

        if(AutoPlaceKeyBind.getInstance().isPressed()){
            AutoPlace autoPlace = (AutoPlace) ProModule.getProModule().getModuleManager().getModuleByName("AutoPlace");
            autoPlace.setEnabled(!autoPlace.isEnabled());
            PlayerSPUtils.sendMsgToSelf("AutoPlace: "+autoPlace.isEnabled());
        }

        if(ClearTargetKeybind.getInstance().isPressed()){
            AimAssist aimAssist = (AimAssist) ProModule.getProModule().getModuleManager().getModuleByName("AimAssist");
            if(aimAssist.getTheTarget()==null){
                PlayerSPUtils.sendMsgToSelf("No Target to clear");
                return;
            }
            PlayerSPUtils.sendMsgToSelf("Clear Target: "+aimAssist.getTheTarget().getDisplayName().getFormattedText());
            aimAssist.clearTarget();


        }




        super.onKeyInput(e);
    }
}
