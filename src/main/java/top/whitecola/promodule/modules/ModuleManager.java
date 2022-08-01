package top.whitecola.promodule.modules;


import java.util.Vector;

public class ModuleManager {
    private Vector<AbstractModule> modules = new Vector<AbstractModule>();

    public ModuleManager(){

    }

    public void addModule(AbstractModule module){
        this.modules.add(module);
    }

    public void removeModule(AbstractModule module){
        this.modules.remove(module);
    }

    public AbstractModule getModuleByName(String name){
        for(AbstractModule module : modules){
            if(module.getModuleName().equalsIgnoreCase(name))
                return module;
        }
        return null;
    }

    public <T extends AbstractModule> T getModuleByClass(Class<T> clazz) {
        for(AbstractModule module : modules){
            if(module.getClass() == clazz){
                return (T)module;
            }
        }
        return null;
    }

    public Vector<AbstractModule> getModules() {
        return modules;
    }

    public Vector<AbstractModule> getModulesByType(ModuleCategory type){
        Vector<AbstractModule> theModules = new Vector<AbstractModule>();
        for(AbstractModule module : modules){
            if(module.getModuleType().equals(type)){
                theModules.add(module);
            }
        }
        return theModules;
    }

}
