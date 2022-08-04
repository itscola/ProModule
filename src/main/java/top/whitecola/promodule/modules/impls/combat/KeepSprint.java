package top.whitecola.promodule.modules.impls.combat;

import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class KeepSprint extends AbstractModule {
    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "KeepSprint";

    }
}
