package top.whitecola.promodule.keybinds;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class ScaffoldKeyBind {
    private static KeyBinding scaffoldBind = new KeyBinding("key.promodule.scaffold", Keyboard.KEY_P, "key.categories.promodule");

    public static KeyBinding getInstance() {
        return scaffoldBind;
    }
}
