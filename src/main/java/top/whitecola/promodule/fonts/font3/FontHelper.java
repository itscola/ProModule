package top.whitecola.promodule.fonts.font3;

public class FontHelper {

    private String font;

    public GlyphPageFontRenderer size15;
    public GlyphPageFontRenderer size20;
    public GlyphPageFontRenderer size30;
    public GlyphPageFontRenderer size40;

    public FontHelper() {
        init();
    }

    public void init() {
        font = "Arial";
        size15 = GlyphPageFontRenderer.create(font, 15, false, false, false);
        size20 = GlyphPageFontRenderer.create(font, 20, false, false, false);
        size30 = GlyphPageFontRenderer.create(font, 30, false, false, false);
        size40 = GlyphPageFontRenderer.create(font, 40, false, false, false);
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
}