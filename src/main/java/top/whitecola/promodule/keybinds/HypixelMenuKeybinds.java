package top.whitecola.promodule.keybinds;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class HypixelMenuKeybinds {
    private static KeyBinding mainMenuInGameKeybind = new KeyBinding("key.promodule.showHypixelMenu", Keyboard.KEY_I, "key.categories.promodule");

    private HypixelMenuKeybinds(){}

    public static KeyBinding getInstance() {
        return mainMenuInGameKeybind;
    }
}
