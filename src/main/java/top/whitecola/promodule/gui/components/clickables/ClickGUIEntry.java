package top.whitecola.promodule.gui.components.clickables;


import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class ClickGUIEntry extends AbstractClickGUIEntry{

    protected ModuleCategory category;

    public ClickGUIEntry(){

    }

    public ClickGUIEntry fromModule(AbstractModule module){
        this.entryName = module.getModuleName();
        this.entryDisplayName = module.getDisplayName();
        this.category = module.getModuleType();
        this.enabled = module.isEnabled();
        this.entryDescription = module.getDescription();
        return this;
    }


    public ModuleCategory getCategory() {
        return category;
    }

}
