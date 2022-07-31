package top.whitecola.promodule.fonts.font;

import java.awt.*;

public class CustomFont {
    private static CustomFont customFont = new CustomFont();
    public GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("BebasNeueBold",20,true,false,false);

    private CustomFont(){

    }

    public static CustomFont getCustomFont() {
        return customFont;
    }
}
