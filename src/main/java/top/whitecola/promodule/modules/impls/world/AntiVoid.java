package top.whitecola.promodule.modules.impls.world;

import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class AntiVoid extends AbstractModule {


    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.WORLD;
    }

    @Override
    public String getModuleName() {
        return "AutoArmor";
    }
}
