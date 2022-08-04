package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class AimAssist extends AbstractModule {
    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "AimAssist";

    }
}
