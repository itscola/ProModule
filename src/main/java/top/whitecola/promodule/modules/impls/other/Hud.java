package top.whitecola.promodule.modules.impls.other;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class Hud extends AbstractModule {
//    @ModuleSetting(name = "Whitelist")
//    protected Boolean whitelist = true;


    @Override
    public void onRenderOverLay(RenderGameOverlayEvent event) {

        super.onRenderOverLay(event);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.OTHER;
    }

    @Override
    public String getModuleName() {
        return "Hud";

    }
}
