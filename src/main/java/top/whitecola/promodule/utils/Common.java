package top.whitecola.promodule.utils;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class Common {
    public static Color color = new Color(81, 115, 232);
    public static Color color2 = new Color(134, 81, 232);


    public static void drawLogo(float width, ResourceLocation logo){
//        mc.fontRendererObj.drawStringWithShadow("ProModule 1.1",1,0,color.getRGB());
//        mc.fontRendererObj.drawStringWithShadow("Author by White_cola",1,10,color2.getRGB());
//        mc.fontRendererObj.drawStringWithShadow("A Powerful Minecraft Ghost Client.",0,18,color.getRGB());

        Render2DUtils.drawCustomImage((int) (width / 2-148),10,300,100,logo);

    }
}
