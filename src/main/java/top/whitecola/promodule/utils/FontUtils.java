package top.whitecola.promodule.utils;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class FontUtils {
    public static int drawCenteredString(String name, float x, float y, int color) {
        return mc.fontRendererObj.drawString(name, (int)(x - (mc.fontRendererObj.getStringWidth(name) / 2)), (int)y, color);
    }
}
