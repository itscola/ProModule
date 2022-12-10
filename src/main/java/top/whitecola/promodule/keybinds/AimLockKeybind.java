package top.whitecola.promodule.keybinds;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class AimLockKeybind {
    private static KeyBinding aimLockKeyBind = new KeyBinding("key.promodule.aimlock", Keyboard.KEY_CAPITAL, "key.categories.promodule");

    public static KeyBinding getInstance() {
        return aimLockKeyBind;
    }
}
