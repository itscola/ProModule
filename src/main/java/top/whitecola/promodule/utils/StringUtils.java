package top.whitecola.promodule.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.util.UUIDTypeAdapter;

import java.util.UUID;
import java.util.regex.Pattern;

public class StringUtils {


    public static UUID uuidFromString(String uuidStr) {
        if (!uuidStr.contains("-")) {
            uuidStr = uuidStr.substring(0, 8) + "-"
                    + uuidStr.substring(8, 12) + "-"
                    + uuidStr.substring(12, 16) + "-"
                    + uuidStr.substring(16, 20) + "-"
                    + uuidStr.substring(20, 32);

        }
        return UUID.fromString(uuidStr);
    }



}
