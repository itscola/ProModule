package top.whitecola.promodule;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import top.whitecola.promodule.events.EventManager;
import top.whitecola.promodule.events.impls.MainMenuEvent;
import top.whitecola.promodule.gui.widgets.WidgetManager;
import top.whitecola.promodule.keybinds.MainMenuInGameKeybind;
import top.whitecola.promodule.modules.ModuleManager;

@Mod(modid = ProModule.MODID, version = ProModule.VERSION)
public class ProModule {
    public static final String MODID = "promodule";
    public static final String VERSION = "1.0";
    private static ProModule proModule = null; {
        proModule = this;
    }
    private EventManager eventManager;
    private WidgetManager widgetManager = new WidgetManager();
    private ModuleManager moduleManager = new ModuleManager();
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        registerEvent();
        registerKeyBinds();
        registerModules();
    }

    public void registerEvent(){
        MinecraftForge.EVENT_BUS.register(eventManager = EventManager.getEventManager());
        EventManager.getEventManager().addEvent(new MainMenuEvent());
    }

    public void registerKeyBinds(){
        ClientRegistry.registerKeyBinding(MainMenuInGameKeybind.getInstance());

    }

    public void registerModules(){

    }



    public static ProModule getProModule() {
        return proModule;
    }

    public WidgetManager getWidgetManager() {
        return widgetManager;
    }


    public ModuleManager getModuleManager() {
        return moduleManager;
    }
}
