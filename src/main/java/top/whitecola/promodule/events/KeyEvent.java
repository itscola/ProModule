package top.whitecola.promodule.events;

import net.minecraftforge.fml.common.gameevent.InputEvent;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.keybinds.*;
import top.whitecola.promodule.modules.impls.combat.AimAssist;
import top.whitecola.promodule.modules.impls.combat.Killaura;
import top.whitecola.promodule.modules.impls.movement.Eagle;
import top.whitecola.promodule.modules.impls.movement.Speed;
import top.whitecola.promodule.modules.impls.world.AutoPlace;
import top.whitecola.promodule.modules.impls.world.Scaffold2;
import top.whitecola.promodule.modules.impls.world.Scaffold3;
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

        if(KillauraKeyBind.getInstance().isPressed()){
            Killaura killaura = (Killaura) ProModule.getProModule().getModuleManager().getModuleByName("Killaura");
            killaura.setEnabled(!killaura.isEnabled());
            PlayerSPUtils.sendMsgToSelf("Killaura: "+killaura.isEnabled());
        }

        if(ScaffoldKeyBind.getInstance().isPressed()){
            Scaffold2 scaffold2 = (Scaffold2) ProModule.getProModule().getModuleManager().getModuleByName("Scaffold");
            scaffold2.setEnabled(!scaffold2.isEnabled());

//            Scaffold3 scaffold3 = ProModule.getProModule().getModuleManager().getModuleByClass(Scaffold3.class);
//            scaffold3.setEnabled(!scaffold3.isEnabled());
            PlayerSPUtils.sendMsgToSelf("scaffold3: "+scaffold2.isEnabled());
        }

        if(AutoPlaceKeyBind.getInstance().isPressed()){
            AutoPlace autoPlace = (AutoPlace) ProModule.getProModule().getModuleManager().getModuleByName("AutoPlace");
            autoPlace.setEnabled(!autoPlace.isEnabled());
            PlayerSPUtils.sendMsgToSelf("AutoPlace: "+autoPlace.isEnabled());
        }

        if(SpeedKeyBind.getInstance().isPressed()){
            Speed speed = (Speed) ProModule.getProModule().getModuleManager().getModuleByName("Speed");
            speed.setEnabled(!speed.isEnabled());
            PlayerSPUtils.sendMsgToSelf("Speed: "+speed.isEnabled());
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
