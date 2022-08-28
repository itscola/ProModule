package top.whitecola.promodule.modules.impls.movement;

import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.PlayerSPUtils;

import static top.whitecola.promodule.utils.MCWrapper.*;



public class LegitSafeWalk extends AbstractModule {




    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.MOVEMENT;
    }

    @Override
    public String getModuleName() {
        return "LegitSafeWalk";

    }

    @Override
    public String getDisplayName() {
        return this.getModuleName()+" (G)";
    }
}
