package top.whitecola.promodule.modules.impls.other;

import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class BetterChatLine extends AbstractModule {
    @ModuleSetting(name = "NoBlack",type = "select")
    protected Boolean noBackground = true;

    @ModuleSetting(name = "Font",type = "select")
    public Boolean font = false;

    @Override
    public String getModuleName() {
        return "BetterChatLine";
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.OTHER;
    }

    public Boolean getNoBackground() {
        return noBackground;
    }
}
