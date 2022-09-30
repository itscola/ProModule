package top.whitecola.promodule.modules.impls.other;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import top.whitecola.animationlib.Animation;
import top.whitecola.animationlib.functions.type.CubicOutFunction;
import top.whitecola.promodule.gui.notification.NotificationManager;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.Render2DUtils;

import java.awt.*;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class Notification  extends AbstractModule {

    protected Animation showAnimation;
    protected Animation leaveAnimation;

    private Color color = new Color(232, 232, 232);

    public Notification(){
        showAnimation = new Animation();
        showAnimation.setMin(0).setMax(100).setFunction(new CubicOutFunction()).setTotalTime(600).setLock(true);

        leaveAnimation = new Animation();
        leaveAnimation.setMin(0).setMax(100).setFunction(new CubicOutFunction()).setTotalTime(600).setLock(true);

    }

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
        return "Notification";
    }
}
