package top.whitecola.promodule.modules.impls.other;

import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class AntiForge extends AbstractModule {

    @ModuleSetting(name = "NoMods" ,type = "select")
    public Boolean noMods = true;

    @ModuleSetting(name = "vanillia" ,type = "select")
    public Boolean vanillia = true;

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.OTHER;
    }

    @Override
    public String getModuleName() {
        return "AntiForge";

    }
}
