package top.whitecola.promodule.modules.impls.combat;

import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class Killaura extends AbstractModule {
    

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "Killaura";
    }
    @Override
    public String getDisplayName() {
        return super.getModuleName();
    }
}
