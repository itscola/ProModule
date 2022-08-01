/*
 * Decompiled with CFR 0_132.
 */
package top.whitecola.promodule.fonts.font2;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;

public class FontLoaders {

    private final HashMap fonts = new HashMap();

//    public static FontRenderer arial14 = getArial(14, true);
//    public static FontRenderer arial16 = getArial(16, true);
//    public static FontRenderer arial18 = getArial(18, true);
//    public static FontRenderer arial22 = getArial(22, true);
//    public static FontRenderer arial24 = getArial(24, true);

    public static FontRenderer msFont14 = getSyyh(18, true);
    public static FontRenderer msFont18 = getSyyh(18, true);
    public static FontRenderer msFont19 = getSyyh(19, true);
    public static FontRenderer msFont36 = getSyyh(36, true);
    public static FontRenderer msFont72 = getSyyh(72, true);


    public static FontRenderer getSyyh(int size, boolean antiAlias) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("promodule","font/syyh.otf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return new FontRenderer(font,size,antiAlias);
    }



}

