package top.whitecola.promodule.config.struct;

import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.modules.AbstractModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleConfig {
    protected ArrayList<ModuleConfigStruct> moduleConfigs = new ArrayList<ModuleConfigStruct>();

    protected void modulesToConfig(List<AbstractModule> modules){
        for(AbstractModule module : modules){
            ModuleConfigStruct moduleConfigStruct = getModuleConfigByModule(module);
            if(moduleConfigStruct==null) {
                moduleConfigs.add(new ModuleConfigStruct(module.getModuleName(), module.isEnabled()/*, module.getOptions()*/));
                continue;
            }
            moduleConfigStruct.enabled = module.isEnabled();
//            moduleConfigStruct.moduleOptions = module.getOptions();
        }
    }

    public ModuleConfigStruct getModuleConfigByModule(AbstractModule module){
        for(ModuleConfigStruct moduleConfigStruct : moduleConfigs){
            if(moduleConfigStruct.moduleName.equalsIgnoreCase(module.getModuleName())){
                return  moduleConfigStruct;
            }
        }
        return null;
    }

    public void modulesToConfig(){
        modulesToConfig(ProModule.getProModule().getModuleManager().getModules());
    }

    public void loadConfigForModules(){
        loadConfigForModules(ProModule.getProModule().getModuleManager().getModules());
    }

    protected void loadConfigForModules(List<AbstractModule> modules){
        for(AbstractModule module : modules){
            ModuleConfigStruct moduleConfigStruct = getModuleConfigByModule(module);

            if(moduleConfigStruct==null)
                continue;

            module.setEnabled(moduleConfigStruct.isEnabled());
//            module.setOptions(moduleConfigStruct.moduleOptions);

        }
    }





    protected ModuleConfigStruct getModuleConfigStructByModuleName(String moduleName){
        for(ModuleConfigStruct struct : moduleConfigs){
            if(struct.moduleName.equalsIgnoreCase(moduleName)){
                return struct;
            }
        }
        return null;
    }


    class ModuleConfigStruct {
        public String moduleName;
        public boolean enabled;
        public Vector<ModuleSettingStruct> settings = new Vector<ModuleSettingStruct>();
//        public Vector<ModuleOption> moduleOptions = new Vector<ModuleOption>();


        public ModuleConfigStruct(String moduleName, boolean enabled/*,Vector<ModuleOption> moduleOptions*/){
            this.moduleName = moduleName;
            this.enabled = enabled;
//            this.moduleOptions = moduleOptions;
        }

        public String getModuleName() {
            return moduleName;
        }

        public boolean isEnabled() {
            return enabled;
        }

//        public Vector<ModuleOption> getModuleOptions() {
//            return moduleOptions;
//        }

//        public void setModuleOptions(Vector<ModuleOption> moduleOptions) {
//            this.moduleOptions = moduleOptions;
//        }
    }
}
