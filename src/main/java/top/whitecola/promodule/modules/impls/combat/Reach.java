package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.PacketUtils;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class Reach extends AbstractModule {
    @ModuleSetting(name = "MinRange")
    public Float minRange = 3.1f;

    @ModuleSetting(name = "MaxRange")
    public Float maxRange = 3.3f;

    @ModuleSetting(name = "ReachRandomer" ,type = "select")
    public Boolean reachRandomer = true;

    @ModuleSetting(name = "PosBypass" ,type = "select")
    public Boolean posBypass = true;

    @ModuleSetting(name = "BypassDist")
    public Float bypassDistance = 2.9f;

    @ModuleSetting(name = "UseChance" ,type = "select")
    public Boolean useChance = true;
    @ModuleSetting(name = "Chance")
    public Float chance = 70f;



    public boolean use;


    @Override
    public void onAttackEntity(AttackEntityEvent e) {
        if(!posBypass){
            return;
        }

        if(e.target==null){
            return;
        }

        if(useChance&&!use){
            return;
        }

        use = false;

        float distance = mc.thePlayer.getDistanceToEntity(e.target);
        if(distance > bypassDistance){
            if (mc.thePlayer.canEntityBeSeen(e.target))
                PacketUtils.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(e.target.posX, e.target.posY,e.target.posZ, false));
            else
                e.setCanceled(true);
        }

        super.onAttackEntity(e);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "Reach";

    }

    @Override
    public void onEnable() {
        super.onEnable();
    }


    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (G)";
    }
}
