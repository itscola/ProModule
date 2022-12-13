package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.PacketReceivedEvent;
import top.whitecola.promodule.injection.wrappers.IMixinS12PacketEntityVelocity;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.RandomUtils;
import static top.whitecola.promodule.utils.MCWrapper.*;

public class Velocity extends AbstractModule {
    @ModuleSetting(name = "minHorizontal",addValue = 1)
    public Float minHorizontal = 90f;

    @ModuleSetting(name = "minHorizontal",addValue = 1)
    public Float maxHorizontal = 100f;

    @ModuleSetting(name = "minVertical",addValue = 1)
    public Float minVertical = 90f;

    @ModuleSetting(name = "maxVertical",addValue = 1)
    public Float maxVertical = 100f;

    @ModuleSetting(name = "UseChance",type = "select")
    public Boolean useChance = true;

    @ModuleSetting(name = "Chance")
    public Float chance = 90f;

    @ModuleSetting(name = "NoRandom",type = "select")
    public Boolean noRandom = true;

    @ModuleSetting(name = "WhenSprinting",type = "select")
    public Boolean whenSprinting = true;

    @ModuleSetting(name = "WhenClicking",type = "select")
    public Boolean whenClicking = false;

    @ModuleSetting(name = "DelayOnly",type = "select")
    public Boolean delayOnly = true;

    @ModuleSetting(name = "onlyAir",type = "select")
    public Boolean onlyAir = true;

    @Override
    public void packetReceivedEvent(PacketReceivedEvent e) {
        if(!e.isCanceled() ){
            if(e.getPacket() instanceof S27PacketExplosion) {
                S27PacketExplosion packet = (S27PacketExplosion) e.getPacket();
            }else if(e.getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity) e.getPacket()).getEntityID()== mc.thePlayer.getEntityId()){

                if(mc.thePlayer.onGround && onlyAir){
                    return;
                }

                if(whenSprinting && !mc.thePlayer.isSprinting()){
                    return;
                }

                S12PacketEntityVelocity packet = (S12PacketEntityVelocity) e.getPacket();

                double randomHorizontal;
                double randomVertical;

                if(noRandom){
                    randomHorizontal = maxHorizontal;
                    randomVertical = maxVertical;

                }else {
                    randomHorizontal = RandomUtils.nextDouble(minHorizontal,maxHorizontal);
                    randomVertical = RandomUtils.nextDouble(minVertical,maxVertical);

                }

//                System.out.println(randomHorizontal);

//                System.out.println(packet.getMotionX()+" "+packet.getMotionY()+" "+ packet.getMotionZ());

                if(useChance){
                    double value = RandomUtils.nextDouble(1,100);
                    if(value>chance){
                        ((IMixinS12PacketEntityVelocity)packet).setMotionX((packet.getMotionX()));
                        ((IMixinS12PacketEntityVelocity)packet).setMotionY((packet.getMotionY()));
                        ((IMixinS12PacketEntityVelocity)packet).setMotionZ((packet.getMotionZ()));
                        return;
                    }
                }



                ((IMixinS12PacketEntityVelocity)packet).setMotionX((int)(packet.getMotionX() * randomHorizontal / 100.0));
                ((IMixinS12PacketEntityVelocity)packet).setMotionY((int) (packet.getMotionY() * randomVertical / 100.0));
                ((IMixinS12PacketEntityVelocity)packet).setMotionZ((int) (packet.getMotionZ() * randomHorizontal / 100.0));

//                ((IMixinS12PacketEntityVelocity)packet).setMotionX(0);
//                ((IMixinS12PacketEntityVelocity)packet).setMotionY(0);
//                ((IMixinS12PacketEntityVelocity)packet).setMotionZ(0);

//                System.out.println(packet.getMotionX()+" "+packet.getMotionY()+" "+ packet.getMotionZ());
//
//                System.out.println(111111);

            }
        }
        super.packetReceivedEvent(e);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "Velocity";

    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName()+" (AG)";
    }

    @Override
    public void onEnable() {
//        whenSprinting = true;



        super.onEnable();
    }
}
