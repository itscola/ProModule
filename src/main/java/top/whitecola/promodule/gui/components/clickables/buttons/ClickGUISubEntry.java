package top.whitecola.promodule.gui.components.clickables.buttons;

import top.whitecola.promodule.gui.components.clickables.AbstractClickGUIEntry;
import top.whitecola.promodule.gui.components.clickables.ClickGUIEntry;
import top.whitecola.promodule.gui.components.clickables.SubEntryCategory;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public class ClickGUISubEntry extends AbstractClickGUIEntry {

    protected Float max;
    protected Float min;

    protected Float value;

    protected SubEntryCategory category;

    public ClickGUISubEntry fromModule(AbstractModule module){
        this.entryName = module.getModuleName();
        this.entryDisplayName = module.getDisplayName();
        this.enabled = module.isEnabled();
        this.entryDescription = module.getDescription();
        return this;
    }

    public Float getMax() {
        return max;
    }

    public Float getMin() {
        return min;
    }

    public Float getValue() {
        return value;
    }

    public void setMin(Float min) {
        this.min = min;
    }

    public void setMax(Float max) {
        this.max = max;
    }

    public void setValue(Float value) {
        this.value = value;

    }

    public SubEntryCategory getCategory() {
        return category;
    }

    public void setCategory(SubEntryCategory category) {
        this.category = category;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
