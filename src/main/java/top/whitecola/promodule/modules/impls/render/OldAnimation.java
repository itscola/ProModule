package top.whitecola.promodule.modules.impls.render;

import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import static top.whitecola.promodule.utils.MCWrapper.*;

public class OldAnimation extends AbstractModule {

    @ModuleSetting(name = "EatingAnim",type = "select")
    protected Boolean eatingAnim = true;

    @ModuleSetting(name = "SideOnly",type = "select")
    protected Boolean sideOnly = true;

    @Override
    public void onTick() {




        super.onTick();
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.RENDERS;
    }

    @Override
    public String getModuleName() {
        return "OldAnimation";
    }
}
