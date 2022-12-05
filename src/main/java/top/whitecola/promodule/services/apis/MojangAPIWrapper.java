package top.whitecola.promodule.services.apis;

import com.google.gson.Gson;
import top.whitecola.promodule.utils.UrlUtil;

import java.io.IOException;

public class MojangAPIWrapper {
    private static Gson gson = new Gson();
    public final static String api = "https://api.mojang.com/";

    public static String getUUIDByPlayerName(String playerName) throws IOException {
        String address = api+"users/profiles/minecraft/"+playerName;
        String content = UrlUtil.readURL(address);
        UUIDJson uuidJson = gson.fromJson(content, UUIDJson.class);
        String beforeUUID = uuidJson.id;
        return beforeUUID;
    }

    public class UUIDJson {
        public String id;
        public String name;
    }


}
