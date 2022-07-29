package top.whitecola.kateclient.injection.wrappers;

import net.minecraft.client.gui.ChatLine;

import java.util.concurrent.ConcurrentHashMap;

public class ChatLineManager {
    public ConcurrentHashMap<ChatLine,ChatLineInfo> chatLines = new ConcurrentHashMap<ChatLine,ChatLineInfo>();

    public void deleteChatLine(ChatLine chatLine){
        chatLines.remove(chatLine);
    }

    @Deprecated
    public void addChatLine(ChatLine chatLine){
        this.chatLines.put(chatLine,new ChatLineInfo());
    }

    public ChatLineInfo getChatInfo(ChatLine chatLine){
        if(!chatLines.containsKey(chatLine)){
            addChatLine(chatLine);
        }

        return chatLines.get(chatLine);
    }


}
