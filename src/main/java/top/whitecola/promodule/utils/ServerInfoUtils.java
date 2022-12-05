package top.whitecola.promodule.utils;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class ServerInfoUtils {
    public static boolean checkHypixel(){
        System.out.println(22222222);

        if(mc.getCurrentServerData()==null || mc.getCurrentServerData().serverIP==null){
            return false;
        }
        if(mc.getCurrentServerData().serverIP.contains("hypixel")){
            return true;
        }

        System.out.println(mc.getCurrentServerData().serverIP+"!!!!");
        return false;
    }

}
