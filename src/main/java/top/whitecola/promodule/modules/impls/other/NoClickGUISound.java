package top.whitecola.promodule.modules.impls.other;

import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class NoClickGUISound extends AbstractModule {
    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.OTHER;
    }

    @Override
    public String getModuleName() {
        return "NoClickGUISound";

    }
}
