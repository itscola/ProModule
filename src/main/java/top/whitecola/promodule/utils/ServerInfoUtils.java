package top.whitecola.promodule.utils;

import static top.whitecola.kateclient.utils.MCWrapper.mc;

public class ServerInfoUtils {
    public static boolean checkHypixel(){
        if(mc.getCurrentServerData()==null || mc.getCurrentServerData().serverIP==null){
            return false;
        }
        if(mc.getCurrentServerData().serverIP.contains("hypixel")){
            return true;
        }

        return false;
    }

}
