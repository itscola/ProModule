package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.entity.EntityLivingBase;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

import java.util.Vector;

public class AntiBot extends AbstractModule {
    public Vector<EntityLivingBase> entities = new Vector<EntityLivingBase>();


    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "AntiBot";

    }
}
