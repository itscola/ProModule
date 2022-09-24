package top.whitecola.promodule.modules.impls.other;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.Render2DUtils;
import static top.whitecola.promodule.utils.MCWrapper.*;

import java.awt.*;
import java.util.Vector;

public class Hud extends AbstractModule {
    @ModuleSetting(name = "List", type = "select")
    protected Boolean list = false;

    @ModuleSetting(name = "CombatList", type = "select")
    protected Boolean combatList = true;

    @ModuleSetting(name = "MoveMentList", type = "select")
    protected Boolean movementList = true;

    @ModuleSetting(name = "RenderList", type = "select")
    protected Boolean renderList = true;

    @ModuleSetting(name = "OtherList", type = "select")
    protected Boolean othrList = false;

    protected Color color = new Color(236, 236, 236);

    @Override
    public void onRenderOverLay(RenderGameOverlayEvent event) {
        if(!list){
            return;
        }
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
            mc.fontRendererObj.drawString(displays.get(i).getDisplayName(),0,i*gap,color.getRGB());
        }
//        mc.displayWidth
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
