package top.whitecola.promodule.utils;

import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.gui.components.clickables.ClickGUIEntry;
import top.whitecola.promodule.gui.components.clickables.SubEntryCategory;
import top.whitecola.promodule.gui.components.clickables.buttons.ClickGUISubEntry;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleManager;

import java.lang.reflect.Field;
import java.util.Vector;

public class ModuleUtils {

    public Vector<ClickGUISubEntry> subEntriesfromModuleName(String name) throws Throwable {
        AbstractModule module = ProModule.getProModule().getModuleManager().getModuleByName(name);
        if(module==null){
            return null;
        }
        return subEntriesfromModule(module);
    }

        public Vector<ClickGUISubEntry> subEntriesfromModule(AbstractModule module) throws Throwable{
        Vector<ClickGUISubEntry> subEntries = new Vector<ClickGUISubEntry>();

        for(Field field : module.getClass().getFields()){
            if(!field.isAnnotationPresent(ModuleSetting.class)){
                continue;
            }

            ModuleSetting moduleSetting = field.getAnnotation(ModuleSetting.class);
            String name = moduleSetting.name();
            SubEntryCategory category;
            Float value = 0f;
            Float max = 0f;
            Float min = 0f;

            if(moduleSetting.type().equalsIgnoreCase("select")){
                category = SubEntryCategory.Boolean;

            }else if(moduleSetting.type().equalsIgnoreCase("value")){
                category = SubEntryCategory.Value;
                    max = moduleSetting.max();
                    min = moduleSetting.min();

            }else {
                continue;
            }

            ClickGUISubEntry entry = new ClickGUISubEntry();

            entry.setEntryName(name);


            try {
                if(category==SubEntryCategory.Boolean){
                    entry.setEnabled((Boolean) field.get(module));
                    entry.setCategory(SubEntryCategory.Boolean);
                }else if(category==SubEntryCategory.Value){
                    value = (Float) field.get(module);
                    entry.setEnabled(true);
                    entry.setValue(value);
                    entry.setMax(max);
                    entry.setMin(min);
                    entry.setCategory(SubEntryCategory.Value);
                }
            }catch (ClassCastException e){
                System.out.println("Warn: the category of "+name+" is not "+category+" from module: "+module.getModuleName());
                continue;
            }

            entry.setModule(module);
            subEntries.add(entry);
        }

        return subEntries;
    }
}
