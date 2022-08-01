package top.whitecola.promodule.fonts.font2.ttfr;

public interface IBFFontRenderer
{
    StringCache getStringCache();

    void setStringCache(StringCache value);

    boolean isDropShadowEnabled();

    void setDropShadowEnabled(boolean value);

    boolean isEnabled();

    void setEnabled(boolean value);
}
