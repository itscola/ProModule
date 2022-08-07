package top.whitecola.promodule.events;

import net.minecraftforge.fml.common.gameevent.InputEvent;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.keybinds.EagleKeyBind;
import top.whitecola.promodule.modules.impls.movement.Eagle;
import top.whitecola.promodule.utils.PlayerSPUtils;
import top.whitecola.promodule.utils.PlayerUtils;

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
        super.onKeyInput(e);
    }
}
