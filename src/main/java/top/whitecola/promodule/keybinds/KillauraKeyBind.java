package top.whitecola.promodule.keybinds;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KillauraKeyBind {
    private static KeyBinding killauraKeybind = new KeyBinding("key.promodule.killaura", Keyboard.KEY_H, "key.categories.promodule");

    private KillauraKeyBind(){}

    public static KeyBinding getInstance() {
        return killauraKeybind;
    }
}
