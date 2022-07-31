package top.whitecola.promodule.events.impls;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import top.whitecola.promodule.events.EventAdapter;
import top.whitecola.promodule.gui.screens.MainClickGUIIngame;
import top.whitecola.promodule.keybinds.MainMenuInGameKeybind;

public class MainMenuEvent extends EventAdapter {

    public MainMenuEvent() {
        super(MainMenuEvent.class.getSimpleName());

    }

    @Override
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if(MainMenuInGameKeybind.getInstance().isPressed()){
            Minecraft.getMinecraft().displayGuiScreen(new MainClickGUIIngame());
        }


    }
}
