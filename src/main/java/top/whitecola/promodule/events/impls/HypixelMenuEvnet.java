package top.whitecola.promodule.events.impls;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import top.whitecola.promodule.events.EventAdapter;
import top.whitecola.promodule.gui.screens.HypixelMenu;
import top.whitecola.promodule.gui.screens.MainClickGUIIngame;
import top.whitecola.promodule.keybinds.HypixelMenuKeybinds;
import top.whitecola.promodule.keybinds.MainMenuInGameKeybind;

public class HypixelMenuEvnet extends EventAdapter {
    public HypixelMenuEvnet(){
        super(HypixelMenuEvnet.class.getSimpleName());
    }

    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if(HypixelMenuKeybinds.getInstance().isPressed()){
            Minecraft.getMinecraft().displayGuiScreen(new HypixelMenu());
        }


    }
}
