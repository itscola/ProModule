package top.whitecola.promodule.modules.impls.other;

import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class GUIBlur extends AbstractModule {

    @ModuleSetting(name = "Blur" ,min= 1,max = 100,addValue = 1)
    public Float blur = 6f;

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.OTHER;
    }

    @Override
    public String getModuleName() {
        return "GUIBlur";

    }
}
