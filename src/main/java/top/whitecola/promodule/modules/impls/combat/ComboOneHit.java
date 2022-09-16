package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.utils.PacketUtils;
import top.whitecola.promodule.utils.RandomUtils;

import static top.whitecola.promodule.utils.MCWrapper.mc;

public class ComboOneHit extends AbstractModule {

    @ModuleSetting(name = "MinPacket",addValue = 1f)
    public Float minPacket = 1f;

    @ModuleSetting(name = "MaxPacket",addValue = 1f)
    public Float maxPacket = 2f;

    @ModuleSetting(name = "Chance" ,type = "select")
    public Boolean chance  = true;

    @ModuleSetting(name = "ChanceVal",addValue = 1f)
    public Float chanceValue = 70f;


    @Override
    public void onAttackEntity(AttackEntityEvent e) {
        if(e.target.equals(mc.thePlayer)){
            return;
        }

        if(e.target==null){
            return;
        }

        if(chance&&RandomUtils.nextDouble(0,100)>chanceValue){
            return;
        }

        double packets = RandomUtils.nextDouble(minPacket,maxPacket);

        for (int i = 0; i < (int) packets; i++) {
            PacketUtils.sendPacket(new C02PacketUseEntity(e.target, C02PacketUseEntity.Action.ATTACK));
        }


        super.onAttackEntity(e);
    }

    @Override
    public String getModuleName() {
        return "ComboOneHit";

    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName();
    }
}
