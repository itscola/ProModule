package top.whitecola.promodule.fonts.font4;

public class Fonts {


//    public FontUtil.FontType ssyhFont;
//    public CustomFont ssyhFont12;
//    public CustomFont ssyhFont16;
//    public CustomFont ssyhFont18;
//    public CustomFont ssyhFont24;

    public FontUtil.FontType tenacityFont;

//    {tenacityFont.setup();}

    public CustomFont tenacityFont12,
            tenacityFont14,
            tenacityFont16,
            tenacityFont18,
            tenacityFont20,
            tenacityFont22,
            tenacityFont24,
            tenacityFont26,
            tenacityFont28,
            tenacityFont32,
            tenacityFont40,
            tenacityFont80;

    public Fonts(){
        FontUtil.setupFonts();
        tenacityFont = FontUtil.FontType.TENACITY;
        tenacityFont12 = tenacityFont.size(12);
        tenacityFont14 = tenacityFont.size(14);
        tenacityFont16 = tenacityFont.size(16);
        tenacityFont18 = tenacityFont.size(18);
        tenacityFont20 = tenacityFont.size(20);
        tenacityFont22 = tenacityFont.size(22);
        tenacityFont24 = tenacityFont.size(24);
        tenacityFont26 = tenacityFont.size(26);
        tenacityFont28 = tenacityFont.size(28);
        tenacityFont32 = tenacityFont.size(32);
        tenacityFont40 = tenacityFont.size(40);
        tenacityFont80 = tenacityFont.size(80);

//        ssyhFont = FontUtil.FontType.SYYH;
//        ssyhFont12 = ssyhFont.size(12);
//        ssyhFont16 = ssyhFont.size(16);
//        ssyhFont18 = ssyhFont.size(18);
//        ssyhFont24 = ssyhFont.size(24);
    }

}
