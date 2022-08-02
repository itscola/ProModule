package top.whitecola.promodule.modules.impls.render;

import net.minecraft.client.Minecraft;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class FullBright extends AbstractModule {
    float defaulttGammaSetting = 100;

    @Override
    public void onEnable() {
        super.onEnable();
        Minecraft.getMinecraft().gameSettings.gammaSetting = 260;

    }

    @Override
    public void onDisable() {
        super.onDisable();
        Minecraft.getMinecraft().gameSettings.gammaSetting = defaulttGammaSetting;
    }

    @Override
    public String getModuleName() {
        return "FullBright";

    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.RENDERS;
    }
}
