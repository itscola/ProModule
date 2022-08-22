package top.whitecola.promodule.modules.impls.combat;

import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class HitBox extends AbstractModule {
    @ModuleSetting(name = "Value")
    public Float value = 0.1f;

    public Float getValue() {
        return value;
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "HitBox";

    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName()+" (G)";
    }
}
