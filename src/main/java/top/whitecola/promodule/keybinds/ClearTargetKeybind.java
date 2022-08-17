package top.whitecola.promodule.keybinds;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class ClearTargetKeybind {
    private static KeyBinding eagleKeyBind = new KeyBinding("key.promodule.cleartarget", Keyboard.KEY_G, "key.categories.promodule");


    public static KeyBinding getInstance() {
        return eagleKeyBind;
    }

}
