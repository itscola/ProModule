package top.whitecola.promodule.modules.impls.other;

import net.minecraft.client.gui.GuiIngame;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class ScoreBoardGUI extends AbstractModule {
    @ModuleSetting(name = "NoBlack",type = "select")
    protected Boolean noBlackBackground = false;

    @ModuleSetting(name = "NoScore",type = "select")
    protected Boolean noScore = true;

    @ModuleSetting(name = "Font",type = "select")
    public Boolean font = false;

//    @ModuleSetting(name = "RoundRect",type = "select")
//    protected Boolean roundRect = true;


    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.OTHER;
    }

    @Override
    public String getModuleName() {
        return "ScoreBoardGUI";

    }

    public Boolean isNoBlackBackground() {
        return noBlackBackground;
    }

    public Boolean isNoScore() {
        return noScore;
    }

}
