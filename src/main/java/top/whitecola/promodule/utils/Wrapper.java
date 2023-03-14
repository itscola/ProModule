package top.whitecola.promodule.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import top.whitecola.promodule.injection.wrappers.IMixinMinecraft;

public class Wrapper {
    public static Timer getTimer(){
        return ((IMixinMinecraft) Minecraft.getMinecraft()).getTimer();
    }
}
