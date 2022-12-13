package top.whitecola.promodule.keybinds;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class SpeedKeyBind {
    private static KeyBinding speedBind = new KeyBinding("key.promodule.speed", Keyboard.KEY_U, "key.categories.promodule");

    public static KeyBinding getInstance() {
        return speedBind;
    }
}
