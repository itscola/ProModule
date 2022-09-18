package top.whitecola.promodule.config.struct;

public class ModuleSettingStruct {
    private String settingName;
    private String value;
    private String type;
    private float addValue = 0.1f;


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

    public ModuleSettingStruct setAddValue(float addValue) {
        this.addValue = addValue;
        return this;
    }

    public float getAddValue() {
        return addValue;
    }
}
