package top.whitecola.promodule.modules.impls.other;

import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class DarkMode extends AbstractModule {
    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.OTHER;
    }

    @Override
    public String getModuleName() {
        return "DarkMode";

    }

    @Override
    public void onEnable() {
//        Minecraft.getMinecraft().displayGuiScreen(null);
//        Minecraft.getMinecraft().displayGuiScreen(new MainClickGUIInGame2());

        super.onEnable();
    }

    @Override
    public void onDisable() {
//        Minecraft.getMinecraft().displayGuiScreen(null);
//        Minecraft.getMinecraft().displayGuiScreen(new MainClickGUIInGameNoFont());


        super.onDisable();
    }
}
