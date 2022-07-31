package top.whitecola.promodule.keybinds;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class MainMenuInGameKeybind {
    private static KeyBinding mainMenuInGameKeybind = new KeyBinding("key.promodule.showMenuInGame", Keyboard.KEY_RSHIFT, "key.categories.promodule");

    private MainMenuInGameKeybind(){}

    public static KeyBinding getInstance() {
        return mainMenuInGameKeybind;
    }
}
