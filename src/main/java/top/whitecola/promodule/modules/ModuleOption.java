package top.whitecola.promodule.modules;


public class ModuleOption {
    private String optionName;
    private boolean enabled;
    private float level;

    public ModuleOption setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void enable(){
        setEnabled(true);
    }

    public void disable(){
        setEnabled(false);
    }

    public ModuleOption setOptionName(String optionName) {
        this.optionName = optionName;
        return this;
    }

    public ModuleOption setLevel(float level) {
        this.level = level;
        return this;
    }
}
