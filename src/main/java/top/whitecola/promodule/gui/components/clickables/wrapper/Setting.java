package top.whitecola.promodule.gui.components.clickables.wrapper;

import top.whitecola.promodule.annotations.ModuleSetting;

import java.lang.reflect.Field;

public class Setting {
    Field field;
    ModuleSetting moduleSetting;

    public Setting(Field field,ModuleSetting moduleSetting){
        this.field = field;
        this.moduleSetting = moduleSetting;
    }

    public Field getField() {
        return field;
    }

    public ModuleSetting getModuleSetting() {
        return moduleSetting;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setModuleSetting(ModuleSetting moduleSetting) {
        this.moduleSetting = moduleSetting;
    }
}
