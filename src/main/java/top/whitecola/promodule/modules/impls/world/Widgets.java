package top.whitecola.promodule.modules.impls.world;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import static top.whitecola.promodule.utils.MCWrapper.*;

public class Widgets  extends AbstractModule {

//    @ModuleSetting(name = "YawPitch" ,type = "select")
//    public Boolean yawPitch = false;
//
//    @ModuleSetting(name = "CPs" ,type = "select")
//    public Boolean yawPitch = false;


    @Override
    public void onEnable() {
//        mc.in
        this.disable();
        super.onEnable();
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.WORLD;
    }

    @Override
    public String getModuleName() {
        return "Widgets";
    }

    @Override
    public String getDisplayName() {
        return "WidgetsGUI";
    }
}
