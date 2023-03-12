package top.whitecola.promodule.modules.impls.other;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.PacketSendEvent;
import top.whitecola.promodule.injection.wrappers.IMixinC03PacketPlayer;
import top.whitecola.promodule.injection.wrappers.IMixinEntityPlayerSP;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.PacketUtils;
import top.whitecola.promodule.utils.ServerInfoUtils;
import top.whitecola.promodule.utils.ServerUtils;

import java.util.List;
import java.util.Timer;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class Disabler extends AbstractModule {

    @ModuleSetting(name = "SprintBypass" ,type = "select")
    public Boolean sprintBypass = true;

    @ModuleSetting(name = "SneakBypass" ,type = "select")
    public Boolean sneakBypass = true;

    @ModuleSetting(name = "HypixelBypass" ,type = "select")
    public Boolean hypixelBypass = true;


    private int key, transactions;
    private short id;
    private List<Packet> listTransactions = new Vector<>();
    private Timer timer = new Timer();
    private boolean afterFly;

    @Override
    public void onTick() {
        if(mc.theWorld==null||mc.thePlayer==null){
            return;
        }
        if(hypixelBypass) {
            if (ServerInfoUtils.checkHypixel()) {
                if (mc.thePlayer.ticksExisted % 15 == 0) {
                    if (!listTransactions.isEmpty()) {
                        listTransactions.forEach(i -> {
                            PacketUtils.sendPacketWithoutEvent(i);
                        });
                        PacketUtils.sendPacketWithoutEvent(new C00PacketKeepAlive(key));
                        listTransactions.clear();
                    }
                }
            }
        }
        super.onTick();
    }

    @Override
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        transactions = 0;
        id = 0;
        key = 0;
        listTransactions.clear();
        super.onEntityJoinWorld(e);
    }

    //    @ModuleSetting(name = "PosBypass" ,type = "select")
//    public Boolean posBypass = true;

    @Override
    public void onSendPacket(PacketSendEvent packetSendEvent) {
        Packet packet = packetSendEvent.getPacket();



        if(hypixelBypass){
            if (packet instanceof C0FPacketConfirmTransaction) {

                C0FPacketConfirmTransaction packet1 = (C0FPacketConfirmTransaction) packetSendEvent.getPacket();

                if (packet1.getUid() < 0) {
                    transactions++;

                    if (transactions > 5) {
                        id = packet1.getUid();
                        listTransactions.add(packet);
                        packetSendEvent.setCancel();
                    }
                }
            }

            if (packet instanceof C00PacketKeepAlive) {
                C00PacketKeepAlive packet1 = (C00PacketKeepAlive) packetSendEvent.getPacket();
                key = packet1.getKey();
                packetSendEvent.setCancel();
            }
        }




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


//        if (posBypass && packet instanceof C03PacketPlayer) {
//            final C03PacketPlayer c03 = (C03PacketPlayer) packet;
//            ((IMixinC03PacketPlayer)(c03)).setY(c03.getPositionY() + 0.015625);
//        }



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