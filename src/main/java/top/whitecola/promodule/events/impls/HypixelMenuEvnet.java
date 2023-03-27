package top.whitecola.promodule.events.impls;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import top.whitecola.promodule.events.EventAdapter;
import top.whitecola.promodule.gui.screens.simple.HypixelMenu;
import top.whitecola.promodule.keybinds.HypixelMenuKeybinds;

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
