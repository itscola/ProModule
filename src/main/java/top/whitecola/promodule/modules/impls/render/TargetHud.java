package top.whitecola.promodule.modules.impls.render;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class TargetHud extends AbstractModule {
    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.RENDERS;
    }

    @Override
    public String getModuleName() {
        return "TargetHud";
    }

    @Override
    public void onRender(TickEvent.RenderTickEvent e) {

        super.onRender(e);
    }
}
