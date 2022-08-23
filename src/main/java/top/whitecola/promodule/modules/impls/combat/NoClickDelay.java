package top.whitecola.promodule.modules.impls.combat;

import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class NoClickDelay extends AbstractModule {
    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "NoClickDelay";
    }

    @Override
    public String getDisplayName() {
        return this.getModuleName()+" (AG)";
    }



}
