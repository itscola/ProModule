package top.whitecola.promodule.modules.impls.other;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import top.whitecola.promodule.gui.notification.NotificationManager;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class Notification  extends AbstractModule {
    protected NotificationManager notificationManager = new NotificationManager();

    @Override
    public void onRenderOverLay(RenderGameOverlayEvent event) {
        notificationManager.renderAll();
        super.onRenderOverLay(event);
    }


    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.OTHER;
    }

    @Override
    public String getModuleName() {
        return "Notification";
    }
}
