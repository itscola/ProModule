package top.whitecola.promodule.config.struct;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleSettingStruct {
    private String settingName;
    private String value;
    private String category;


    public ModuleSettingStruct(String settingName,String value,String category){
        this.settingName = settingName;
        this.value = value;
        this.category = category;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
