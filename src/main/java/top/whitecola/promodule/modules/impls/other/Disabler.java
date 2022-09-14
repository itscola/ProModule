package top.whitecola.promodule.modules.impls.other;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.PacketSendEvent;
import top.whitecola.promodule.injection.wrappers.IMixinC03PacketPlayer;
import top.whitecola.promodule.injection.wrappers.IMixinEntityPlayerSP;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.PacketUtils;
import static top.whitecola.promodule.utils.MCWrapper.*;

public class Disabler extends AbstractModule {

    @ModuleSetting(name = "SprintBypass" ,type = "select")
    public Boolean sprintBypass = true;

    @ModuleSetting(name = "SneakBypass" ,type = "select")
    public Boolean sneakBypass = true;

    @ModuleSetting(name = "PosBypass" ,type = "select")
    public Boolean posBypass = true;

    @Override
    public void onSendPacket(PacketSendEvent packetSendEvent) {
        Packet packet = packetSendEvent.getPacket();
        if (sprintBypass) {

            if (packet.getClass().isAssignableFrom(C0BPacketEntityAction.class)) {
                final C0BPacketEntityAction c0B = (C0BPacketEntityAction) packet;

                if (c0B.getAction().equals(C0BPacketEntityAction.Action.START_SPRINTING)) {
                    if (((IMixinEntityPlayerSP)(mc.thePlayer)).isServerSprintState()) {
                        PacketUtils.sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                        ((IMixinEntityPlayerSP)(mc.thePlayer)).setServerSprintState(false);
                    }
                    packetSendEvent.setCancel();
                }

                if (c0B.getAction().equals(C0BPacketEntityAction.Action.STOP_SPRINTING)) {
                    packetSendEvent.setCancel();
                }


                if(c0B.getAction().equals(C0BPacketEntityAction.Action.START_SNEAKING)){
                    if (((IMixinEntityPlayerSP)(mc.thePlayer)).isServerSneakState()) {
                        PacketUtils.sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                        ((IMixinEntityPlayerSP)(mc.thePlayer)).setServerSneakState(false);
                    }


                    packetSendEvent.setCancel();

                }

                if (c0B.getAction().equals(C0BPacketEntityAction.Action.STOP_SNEAKING)) {
                    packetSendEvent.setCancel();
                }

            }
        }


        if (posBypass && packet instanceof C03PacketPlayer) {
            final C03PacketPlayer c03 = (C03PacketPlayer) packet;
            ((IMixinC03PacketPlayer)(c03)).setY(c03.getPositionY() + 0.015625);
        }



        super.onSendPacket(packetSendEvent);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.OTHER;
    }

    @Override
    public String getModuleName() {
        return "Disabler";

    }
}