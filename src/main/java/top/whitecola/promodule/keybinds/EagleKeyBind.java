package top.whitecola.promodule.keybinds;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class EagleKeyBind {
    private static KeyBinding eagleKeyBind = new KeyBinding("key.promodule.eagle", Keyboard.KEY_R, "key.categories.promodule");


    public static KeyBinding getInstance() {
        return eagleKeyBind;
    }


}
