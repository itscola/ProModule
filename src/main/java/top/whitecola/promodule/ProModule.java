package top.whitecola.promodule;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import top.whitecola.promodule.events.EventManager;
import top.whitecola.promodule.events.impls.MainMenuEvent;
import top.whitecola.promodule.fonts.FontUtil;
import top.whitecola.promodule.gui.widgets.WidgetManager;
import top.whitecola.promodule.keybinds.MainMenuInGameKeybind;

@Mod(modid = ProModule.MODID, version = ProModule.VERSION)
public class ProModule {
    public static final String MODID = "promodule";
    public static final String VERSION = "1.0";
    private static ProModule proModule = null; {
        proModule = this;
    }
    private EventManager eventManager;
    private WidgetManager widgetManager = new WidgetManager();
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        FontUtil.bootstrap();
        registerEvent();
        registerKeyBinds();
    }

    public void registerEvent(){
        MinecraftForge.EVENT_BUS.register(eventManager = EventManager.getEventManager());
        EventManager.getEventManager().addEvent(new MainMenuEvent());
    }

    public void registerKeyBinds(){
        ClientRegistry.registerKeyBinding(MainMenuInGameKeybind.getInstance());

    }



    public static ProModule getProModule() {
        return proModule;
    }

    public WidgetManager getWidgetManager() {
        return widgetManager;
    }
}
