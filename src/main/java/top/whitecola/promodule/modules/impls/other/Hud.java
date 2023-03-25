package top.whitecola.promodule.modules.impls.other;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.ColorUtils;
import top.whitecola.promodule.utils.Render2DUtils;
import static top.whitecola.promodule.utils.MCWrapper.*;

import java.awt.*;
import java.util.Vector;

public class Hud extends AbstractModule {
    @ModuleSetting(name = "List", type = "select")
    protected Boolean list = true;

    @ModuleSetting(name = "CombatList", type = "select")
    protected Boolean combatList = true;

    @ModuleSetting(name = "MoveMentList", type = "select")
    protected Boolean movementList = true;

    @ModuleSetting(name = "RenderList", type = "select")
    protected Boolean renderList = false;

    @ModuleSetting(name = "OtherList", type = "select")
    protected Boolean othrList = false;


    @ModuleSetting(name = "ToolTip", type = "select")
    protected Boolean toolTip = false;

    protected Color color = new Color(49, 179, 236);
    protected Color color2 = new Color(38, 149, 245);

    protected Color toolBarColor = new Color(0, 0, 0);

    @ModuleSetting(name = "X",max = 0,min = 255,addValue = 1)
    protected Float x = 4f;

    @ModuleSetting(name = "Y",max = 0,min = 255,addValue = 1)
    protected Float y = 86f;


//    @Override
//    public void onRenderOverLay(RenderGameOverlayEvent event) {
//
//
//        super.onRenderOverLay(event);
//    }


    @Override
    public void onRender2D(float partialTicks) {
        ScaledResolution sc = new ScaledResolution(mc);

        if(list){

            int gap = 8;
            Vector<AbstractModule> modules = ProModule.getProModule().getModuleManager().getModules();

            Vector<AbstractModule> displays = new Vector<AbstractModule>();

            if(combatList){
                for(AbstractModule module : modules){
                    if(module.getModuleType() == ModuleCategory.COMBAT&&module.isEnabled()){
                        displays.add(module);
                    }
                }
            }

            if(movementList){
                for(AbstractModule module : modules){
                    if(module.getModuleType() == ModuleCategory.MOVEMENT&&module.isEnabled()){
                        displays.add(module);
                    }
                }
            }

            if(renderList){
                for(AbstractModule module : modules){
                    if(module.getModuleType() == ModuleCategory.RENDERS&&module.isEnabled()){
                        displays.add(module);
                    }
                }
            }
//        for(AbstractModule module : modules){
//            if(module.isEnabled()){
//                displays.add(module);
//            }
//        }



            for(int i=0;i<displays.size();i++){
                Color theColor = ColorUtils.interpolateColorsBackAndForth(15,i,this.color,this.color2,false);

                mc.fontRendererObj.drawStringWithShadow(displays.get(i).getDisplayName(),0+x,i*gap+y+i*2,theColor.getRGB());
//                char[] rchars = displays.get(i).getDisplayName().toCharArray();
//                for(int j=0;j<chars.length;j++){
//                    long theGap = j*mc.fontRendererObj.getCharWidth(chars[j]);
//                    mc.fontRendererObj.drawStringWithShadow(chars[j]+"",theGap,i*gap,theColor.getRGB());
//
//                }
            }


        }
        int lvt_4_1_ = sc.getScaledWidth() / 2;

        if(toolTip){
            float x = lvt_4_1_ - 91;
            float y = sc.getScaledHeight() - 22;
//            Render2DUtils.drawRect(lvt_4_1_ - 91,y,x+182,sc.getScaledHeight() +22,toolBarColor.getRGB());
//            Render2DUtils.drawRect(0,y,sc.getScaledWidth(),sc.getScaledHeight() +22,toolBarColor.getRGB());
//            Render2DUtils.drawRoundedRect2(lvt_4_1_ - 91,y,x+182,sc.getScaledHeight() +22,3,color.getRGB());
            Render2DUtils.drawRoundedRect2(lvt_4_1_ - 91+50+10,1,x+182-50-10,1 +15,3,toolBarColor.getRGB());

        }

//        mc.displayWidth
        super.onRender2D(partialTicks);
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
