package top.whitecola.promodule.keybinds;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class AutoPlaceKeyBind {
    private static KeyBinding eagleKeyBind = new KeyBinding("key.promodule.autoplace", Keyboard.KEY_M, "key.categories.promodule");


    public static KeyBinding getInstance() {
        return eagleKeyBind;
    }
}
