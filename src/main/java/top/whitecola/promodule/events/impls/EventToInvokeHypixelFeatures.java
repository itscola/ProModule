package top.whitecola.promodule.events.impls;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.events.EventAdapter;
import top.whitecola.promodule.utils.ServerInfoUtils;
import static top.whitecola.promodule.utils.MCWrapper.*;


public class EventToInvokeHypixelFeatures extends EventAdapter {
    private boolean isHypixel;



    public EventToInvokeHypixelFeatures() {
        super(EventToInvokeHypixelFeatures.class.getSimpleName());
    }

    @Override
    public void onLoginIn(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        isHypixel = ServerInfoUtils.checkHypixel();
        System.out.println(1111111111111L);
        System.out.println(isHypixel);

        super.onLoginIn(e);
    }

    @Override
    public void onLoginOut(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        isHypixel = false;
        super.onLoginOut(e);
    }



    @Override
    public void onChatReceive(ClientChatReceivedEvent e) {
        if(!isHypixel){
            return;
        }

        if(isHypixel&&e.message.getFormattedText().contains("Your new API key is")){
            ProModule.getProModule().getHypixelConfig().getConfig().key = e.message.getFormattedText().replace("Your new API key is ","").replace("§a§r§b","").replace("§r","");
            mc.thePlayer.addChatMessage(new ChatComponentText("[KateClient] your Hypixel API key is §a"+ProModule.getProModule().getHypixelConfig().getConfig().key));
            mc.thePlayer.addChatMessage(new ChatComponentText("[KateClient] §e Now, you can use some modules for hypixel like LevelTab."));
            ProModule.getProModule().getHypixelConfig().saveConfig();
            e.setCanceled(true);
            return;
        }

        super.onChatReceive(e);
    }



}
