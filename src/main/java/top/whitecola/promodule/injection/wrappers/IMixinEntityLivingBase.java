package top.whitecola.promodule.injection.wrappers;

import net.minecraft.potion.PotionEffect;

import java.util.Map;

public interface IMixinEntityLivingBase {
    int runGetArmSwingAnimationEnd();
    Map<Integer, PotionEffect> getActivePotionsMap();

}
