package top.whitecola.promodule.events.impls;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.events.EventAdapter;
import top.whitecola.promodule.gui.screens.MainClickGUIInGame2;
import top.whitecola.promodule.gui.screens.MainClickGUIInGameNoFont;
import top.whitecola.promodule.gui.screens.MainClickGUIIngame;
import top.whitecola.promodule.keybinds.MainMenuInGameKeybind;

public class MainMenuEvent extends EventAdapter {

    public MainMenuEvent() {
        super(MainMenuEvent.class.getSimpleName());

    }

    @Override
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if(MainMenuInGameKeybind.getInstance().isPressed()){
//            Minecraft.getMinecraft().displayGuiScreen(new MainClickGUIInGameNoFont());
            if(ProModule.getProModule().getModuleManager().getModuleByName("DarkMode").isEnabled()){
                Minecraft.getMinecraft().displayGuiScreen(new MainClickGUIInGame2());
            }else {
                Minecraft.getMinecraft().displayGuiScreen(new MainClickGUIInGameNoFont());
            }
        }


    }
}
