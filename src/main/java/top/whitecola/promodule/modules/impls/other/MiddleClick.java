package top.whitecola.promodule.modules.impls.other;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

import java.util.Vector;

public class MiddleClick extends AbstractModule {

    @ModuleSetting(name = "Whitelist")
    protected Boolean whitelist = true;

    @ModuleSetting(name = "Only1World")
    protected Boolean only1World = true;

    @ModuleSetting(name = "Only1")
    protected Boolean only1 = true;

    protected Vector<EntityLivingBase> entities = new Vector<EntityLivingBase>();

//    @Override
//    public void onTick() {
//        if(){
//
//        }
//        super.onTick();
//    }

    @Override
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        if(e.entity instanceof EntityPlayerSP){
            entities.clear();
        }

        super.onEntityJoinWorld(e);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.OTHER;
    }

    @Override
    public String getModuleName() {
        return "MiddleClick";

    }

}
