package top.whitecola.promodule.services.apis;

import com.google.gson.Gson;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.services.apis.structs.hypixelapi.HypixelPlayer;
import top.whitecola.promodule.services.apis.structs.hypixelapi.LevelUtils;
import top.whitecola.promodule.utils.UrlUtil;

import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static top.whitecola.promodule.utils.MCWrapper.*;


public class HypixelAPIWrapper {
    public static final String api = "https://api.hypixel.net/";
    public static Gson gson = new Gson();
    private ConcurrentHashMap<String,Integer> playerLevelCache = new ConcurrentHashMap<String,Integer>();

    private ExecutorService executorService = Executors.newFixedThreadPool(5);


    public HypixelAPIWrapper(){
    }




    public String needPlayerLevel(NetworkPlayerInfo networkPlayerInfo){
        final String name = networkPlayerInfo.getGameProfile().getName();
        if(playerLevelCache.get(name)==null){
            addLevelForPlayer(networkPlayerInfo);
            return "";
        }

        if(playerLevelCache.get(name)==-1){
            return "";
        }

        return playerLevelCache.get(name)+"";
    }

    public String needPlayerLevel(String name, UUID uuid){
        if(playerLevelCache.get(name)==null){
            addLevelForPlayer(name,uuid.toString().replace("-",""));
            return "";
        }

        if(playerLevelCache.get(name)==-1){
            return "";
        }

        return playerLevelCache.get(name)+"";
    }

    public String needPlayerLevelFromCache(String name){
        if(playerLevelCache.get(name)==null){
            return "";
        }

        if(playerLevelCache.get(name)==-1){
            return "";
        }

        return playerLevelCache.get(name)+"";
    }



    private void addLevelForPlayer(final NetworkPlayerInfo networkPlayerInfo){
        final String name = networkPlayerInfo.getGameProfile().getName();
        playerLevelCache.put(name,-1);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    playerLevelCache.put(name,getHypixelPlayerLevelByUUID(networkPlayerInfo.getGameProfile().getId().toString().replace("-","")));
                } catch (Throwable e) { }
            }
        });
    }

    private void addLevelForPlayer(final String name, final String uuid){
        playerLevelCache.put(name,-1);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    playerLevelCache.put(name,getHypixelPlayerLevelByUUID(uuid));
                } catch (Throwable e) { }
            }
        });
    }






    public int getHypixelPlayerLevelByName(String playerName) throws Throwable {
        HypixelPlayer hypixelPlayer = getHypixelPlayerName(playerName);
        if(hypixelPlayer==null){
            return 0;
        }
        return (int) LevelUtils.getLevel(hypixelPlayer.getPlayer().getNetworkExp());
    }

    public int getHypixelPlayerLevelByUUID(String uuid) throws Throwable {
        HypixelPlayer hypixelPlayer = getHypixelPlayerByUUID(uuid);
        if(hypixelPlayer==null){
            return 0;
        }
        return (int) LevelUtils.getLevel(hypixelPlayer.getPlayer().getNetworkExp());
    }

    public HypixelPlayer getHypixelPlayerName(String name) throws Throwable {
        return getHypixelPlayerByUUID(MojangAPIWrapper.getUUIDByPlayerName(name));
    }

    public HypixelPlayer getHypixelPlayerByUUID(String uuid) throws Throwable {

        if(ProModule.getProModule().getHypixelConfig().config.key==null&&ProModule.getProModule().getHypixelConfig().config.key.equals("")){
            return null;
        }

        String url = api+"player?key="+ProModule.getProModule().getHypixelConfig().config.key+"&uuid="+uuid;

        String content = UrlUtil.readURL(url);
        if(content==null){
            return null;
        }
        HypixelPlayer hypixelPlayer = gson.fromJson(content,HypixelPlayer.class);
        return hypixelPlayer;
    }

    public boolean sendGettingKeyRequest(){
        if(ProModule.getProModule().getHypixelConfig().getConfig().key.equals("")){
            mc.thePlayer.addChatMessage(new ChatComponentText("[KateClient] We need your hypixel API key to enable some modules like levelTab, The key won't share with any other one including us."));
            mc.thePlayer.sendChatMessage("/api new");
            return true;
        }
        return false;
    }


    public ConcurrentHashMap<String, Integer> getPlayerLevelCache() {
        return playerLevelCache;
    }


    public ExecutorService getExecutorService() {
        return executorService;
    }
}
