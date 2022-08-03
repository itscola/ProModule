package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;

import java.util.Random;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class AutoClicker extends AbstractModule {

    @ModuleSetting(name = "MinCPS")
    protected Integer minCPS = 8;

    @ModuleSetting(name = "MaxCPS")
    protected Integer maxCPS = 10;

    @ModuleSetting(name = "OnlySwordAndTools")
    protected Boolean onlySwordAndTools = false;

    public AutoClicker(){
        super();
    }

    @Override
    public void onTick() {
        if(!mc.gameSettings.keyBindAttack.isKeyDown()){
            return;
        }

        if(onlySwordAndTools && (mc.thePlayer.getHeldItem()==null || !((mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)||(mc.thePlayer.getHeldItem().getItem() instanceof ItemTool)))){
            return;
        }

        int finalPerSecondClicks = new Random().nextInt(maxCPS-1) +minCPS;

        long delay = 1000/finalPerSecondClicks;

        super.onTick();
    }
}
