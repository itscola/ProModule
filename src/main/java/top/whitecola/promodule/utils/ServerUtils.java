package top.whitecola.promodule.utils;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import static top.whitecola.promodule.utils.MCWrapper.*;

public class ServerUtils {
    public static boolean isInTabList(EntityLivingBase entity){
        for (NetworkPlayerInfo item : mc.getNetHandler().getPlayerInfoMap()) {

            if (item != null && item.getGameProfile() != null && item.getGameProfile().getName().contains(entity.getName())) {
                return true;
            }
        }
        return false;
    }


}
