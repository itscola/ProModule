package top.whitecola.promodule.utils;

import net.minecraft.util.ResourceLocation;
import top.whitecola.kateclient.KateClient;
import top.whitecola.kateclient.ui.components.notifiction.Notification;
import static top.whitecola.kateclient.utils.MCWrapper.*;


public class ClientUtils {
    public static void sendAClientMessage(String title,String content){
        sendAClientMessage(title,content, server);
    }

    public static void sendAClientMessage(String title, String content, ResourceLocation icon){
        Notification notification = new Notification().setIcon(icon).setTitle(title).setContent(content);
        KateClient.getKateClient().getNotificationManager().addNotification(notification);
    }
}
