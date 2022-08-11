package top.whitecola.promodule.config.struct;

public class ModuleSettingStruct {
    private String settingName;
    private String value;
    private String type;


    public ModuleSettingStruct(String settingName,String value,String type){
        this.settingName = settingName;
        this.value = value;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
