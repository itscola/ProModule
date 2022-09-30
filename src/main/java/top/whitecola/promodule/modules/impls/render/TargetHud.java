package top.whitecola.promodule.modules.impls.render;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import top.whitecola.promodule.fonts.font2.FontRenderer;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.Render2DUtils;

import java.awt.*;

import static top.whitecola.promodule.utils.MCWrapper.mc;

public class TargetHud extends AbstractModule {
    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.RENDERS;
    }

    @Override
    public String getModuleName() {
        return "TargetHud";
    }
    private Color color = new Color(232, 232, 232);
    private int phase;


    @Override
    public void onRenderOverLay(RenderGameOverlayEvent event) {



        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int value = -100;
        int x = scaledResolution.getScaledWidth() - scaledResolution.getScaledWidth()/5 -7 -75 +3;
        int y = scaledResolution.getScaledHeight() - scaledResolution.getScaledWidth()/14 -7-50;

        Render2DUtils.drawRoundedRect2(x,y,x+scaledResolution.getScaledWidth()/5+6,y+scaledResolution.getScaledWidth()/14,2,color.getRGB());

        if(mc==null || mc.objectMouseOver==null){
            return;
        }




                super.onRenderOverLay(event);
    }
}
