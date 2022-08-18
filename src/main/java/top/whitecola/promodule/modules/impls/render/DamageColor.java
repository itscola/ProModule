package top.whitecola.promodule.modules.impls.render;

import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

import java.awt.*;

public class DamageColor extends AbstractModule {
    @ModuleSetting(name = "Red",max = 0,min = 255)
    protected Float red = 172f;

    @ModuleSetting(name = "Green",max = 0,min = 255)
    protected Float green = 246f;

    @ModuleSetting(name = "Blue",max = 0,min = 255)
    protected Float blue = 165f;

    @ModuleSetting(name = "Alpha",max = 0,min = 255)
    protected Float alpha = 78f;


    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.RENDERS;
    }

    @Override
    public String getModuleName() {
        return "DamageColor";
    }

    public Float getRed() {
        return red /255;
    }

    public Float getGreen() {
        return green / 255;
    }

    public Float getBlue() {
        return blue /255;
    }

    public Float getAlpha() {
        return alpha /255;
    }


}
