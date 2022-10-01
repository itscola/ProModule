package top.whitecola.promodule.modules.impls.render;

import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.Render2DUtils;

import java.awt.*;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class Keystroke extends AbstractModule {
    private Color color = new Color(234, 234, 234);
    private Color color2 = new Color(56, 56, 56);
    private Color color3 = new Color(31, 31, 31);

    @Override
    public void onRenderOverLay(RenderGameOverlayEvent event) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
//        int x = scaledResolution.getScaledWidth() - scaledResolution.getScaledWidth()+5;
//        int y = scaledResolution.getScaledHeight() - scaledResolution.getScaledWidth()+5;
        float x=26;
        float y=6+8;
        float height = 20;
        float weight = 20;

        //down
        Render2DUtils.drawRoundedRect2(x,y,x+weight,y+height,2,color.getRGB());

        float x1=x;
        float y1=y+21.8f;

        //up
        Render2DUtils.drawRoundedRect2(x1,y1,x1+weight,y1+height,2,color.getRGB());

        float x2=x1-21.8f;
        float y2=y1;

        //left

        Render2DUtils.drawRoundedRect2(x2,y2,x2+weight,y2+height,2,color.getRGB());

        float x3=x1+21.8f;
        float y3=y1;

        //right

        Render2DUtils.drawRoundedRect2(x3,y3,x3+weight,y3+height,2,color.getRGB());

//        float x4 =

        if(mc.gameSettings.keyBindForward.isKeyDown()){
            if(mc.thePlayer.isSprinting()){
                Render2DUtils.drawRoundedRect2(x,y,x+weight,y+height,1,color3.getRGB());

            }else {
                Render2DUtils.drawRoundedRect2(x,y,x+weight,y+height,1,color2.getRGB());
            }

        }

        if(mc.gameSettings.keyBindBack.isKeyDown()){
            Render2DUtils.drawRoundedRect2(x1,y1,x1+weight,y1+height,1,color2.getRGB());

        }

        if(mc.gameSettings.keyBindLeft.isKeyDown()){
            Render2DUtils.drawRoundedRect2(x2,y2,x2+weight,y2+height,1,color2.getRGB());

        }

        if(mc.gameSettings.keyBindRight.isKeyDown()){
            Render2DUtils.drawRoundedRect2(x3,y3,x3+weight,y3+height,1,color2.getRGB());

        }



        super.onRenderOverLay(event);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.RENDERS;
    }

    @Override
    public String getModuleName() {
        return "Keystroke";
    }
}
