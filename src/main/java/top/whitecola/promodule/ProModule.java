package top.whitecola.promodule;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import top.whitecola.promodule.events.EventManager;
import top.whitecola.promodule.events.impls.EventToInvokeModules;
import top.whitecola.promodule.events.impls.MainMenuEvent;
import top.whitecola.promodule.fonts.font2.FontLoaders;
import top.whitecola.promodule.gui.widgets.WidgetManager;
import top.whitecola.promodule.keybinds.MainMenuInGameKeybind;
import top.whitecola.promodule.modules.ModuleManager;
import top.whitecola.promodule.modules.impls.combat.*;
import top.whitecola.promodule.modules.impls.movement.Eagle;
import top.whitecola.promodule.modules.impls.movement.WTap;
import top.whitecola.promodule.modules.impls.render.*;

@Mod(modid = ProModule.MODID, version = ProModule.VERSION)
public class ProModule {
    public static final String MODID = "promodule";
    public static final String VERSION = "1.0";
    private static ProModule proModule = null; {
        proModule = this;
    }
    private EventManager eventManager = EventManager.getEventManager();
    private WidgetManager widgetManager = new WidgetManager();
    private ModuleManager moduleManager = new ModuleManager();
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        FontLoaders.loadAllFonts();
        registerEvent();
        registerKeyBinds();
        registerModules();
    }

    public void registerEvent(){
        MinecraftForge.EVENT_BUS.register(eventManager);
        EventManager.getEventManager().addEvent(new MainMenuEvent());
        EventManager.getEventManager().addEvent(new EventToInvokeModules());
    }

    public void registerKeyBinds(){
        ClientRegistry.registerKeyBinding(MainMenuInGameKeybind.getInstance());

    }

    public void registerModules(){
        //render
        getModuleManager().addModule(new Chams());
        getModuleManager().addModule(new FullBright());
        getModuleManager().addModule(new OldAnimation());
        getModuleManager().addModule(new DamageBlood());
        getModuleManager().addModule(new NoFov());
        getModuleManager().addModule(new ItemPhysic());
        getModuleManager().addModule(new DamageColor());
        getModuleManager().addModule(new TargetHud());

        //combat
        getModuleManager().addModule(new AutoClicker());
        getModuleManager().addModule(new Reach());
        getModuleManager().addModule(new Velocity());
        getModuleManager().addModule(new KeepSprint());
        getModuleManager().addModule(new AntiBot());

        //movement
        getModuleManager().addModule(new Eagle());
        getModuleManager().addModule(new WTap());


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
