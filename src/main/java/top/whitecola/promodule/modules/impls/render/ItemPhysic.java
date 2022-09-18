package top.whitecola.promodule.modules.impls.render;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.ClientPhysic;

public class ItemPhysic extends AbstractModule {
    @ModuleSetting(name = "RotationSpeed",max = 0,min = 255,addValue = 1)
    protected Float rotationSpeed = 0.2f;

    @Override
    public void onRender(TickEvent.RenderTickEvent e) {
        if (e.phase == TickEvent.Phase.END)
            ClientPhysic.tick = System.nanoTime();
        super.onRender(e);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.RENDERS;
    }

    @Override
    public String getModuleName() {
        return "ItemPhysic";
    }

    public Float getRotationSpeed() {
        return rotationSpeed;
    }
}
