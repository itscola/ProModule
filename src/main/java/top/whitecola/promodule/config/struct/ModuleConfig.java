package top.whitecola.promodule.config.struct;

import org.lwjgl.Sys;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.gui.components.clickables.wrapper.Setting;
import top.whitecola.promodule.modules.AbstractModule;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleConfig {
    protected String ConfigName = "NewConfig";
    public float uix = 90;
    public float uiy = 16;
    protected ArrayList<ModuleConfigStruct> moduleConfigs = new ArrayList<ModuleConfigStruct>();

    protected void modulesToConfig(List<AbstractModule> modules){
        for(AbstractModule module : modules){
            ModuleConfigStruct moduleConfigStruct = getModuleConfigByModule(module);

            Vector<ModuleSettingStruct> settings = getModuleSettings(module);
            if(settings==null){
                continue;
            }

            if(moduleConfigStruct==null) {

                moduleConfigs.add(new ModuleConfigStruct(module.getModuleName(), module.isEnabled(),settings));
                continue;
            }
            moduleConfigStruct.enabled = module.isEnabled();
            moduleConfigStruct.settings = settings;


//            moduleConfigStruct.moduleOptions = module.getOptions();
        }
    }

    public ModuleConfigStruct getModuleConfigByModule(AbstractModule module){
        for(ModuleConfigStruct moduleConfigStruct : moduleConfigs){
            if(moduleConfigStruct.moduleName.equalsIgnoreCase(module.getModuleName())){
                return moduleConfigStruct;
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
            for(Field field : module.getClass().getFields()){
                if (!field.isAnnotationPresent(ModuleSetting.class)) {
                    continue;
                }

                ModuleSetting moduleSetting = field.getAnnotation(ModuleSetting.class);
                for(ModuleSettingStruct struct : moduleConfigStruct.settings){
                    if(!moduleSetting.name().equalsIgnoreCase(struct.getSettingName())){
                        continue;
                    }

                    if(!moduleSetting.type().equalsIgnoreCase(struct.getType())){
                        continue;
                    }

                    if(moduleSetting.type().equalsIgnoreCase("select")){
                        module.setBooleanSetting(struct.getSettingName(),Boolean.valueOf(struct.getValue()));
                        continue;
                    }else if(moduleSetting.type().equalsIgnoreCase("value")){
                        module.setFloatSetting(struct.getSettingName(),Float.valueOf(struct.getValue()));
//                        module.setFloatSetting(struct.getSettingName(),Float.valueOf(struct.getAddValue()));
                    }

                }
            }

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


        public ModuleConfigStruct(String moduleName, boolean enabled,Vector<ModuleSettingStruct> settings){
            this.moduleName = moduleName;
            this.enabled = enabled;
            this.settings = settings;
        }

        public String getModuleName() {
            return moduleName;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public Vector<ModuleSettingStruct> getSettings() {
            return settings;
        }

        public void setSettings(Vector<ModuleSettingStruct> settings) {
            this.settings = settings;
        }

        //        public Vector<ModuleOption> getModuleOptions() {
//            return moduleOptions;
//        }

//        public void setModuleOptions(Vector<ModuleOption> moduleOptions) {
//            this.moduleOptions = moduleOptions;
//        }
    }

    protected Vector<ModuleSettingStruct> getModuleSettings(AbstractModule module){
        Vector<Setting> settings = module.getSettings();
        if(settings==null){
            return null;
        }

        Vector<ModuleSettingStruct> structs = new Vector<ModuleSettingStruct>();

        for(Setting setting : settings){
            Object obj = null;
            try {
                obj = setting.getField().get(module);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                continue;
            }
            if(obj==null){
                continue;
            }

            String value = "";
            if(obj instanceof Boolean){
                value = ((Boolean)obj).toString();
            }else if(obj instanceof Float){
//                System.out.println(new BigDecimal(obj.toString()).floatValue());
                value = ((Float)obj).toString();
            }



            structs.add(new ModuleSettingStruct(setting.getModuleSetting().name(),value,setting.getModuleSetting().type()).setAddValue(setting.getModuleSetting().addValue()));
        }
        return structs;
    }
}
