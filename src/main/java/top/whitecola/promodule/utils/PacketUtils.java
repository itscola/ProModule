package top.whitecola.promodule.utils;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import top.whitecola.promodule.injection.wrappers.IMixinNetworkManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class PacketUtils {

    public static void sendPacket(final Packet<?> packet) {
        mc.getNetHandler().addToSendQueue(packet);
    }

    public static void sendPacketWithoutEvent(final Packet<?> packet) {
//        ((IMixinNetworkManager)mc.getNetHandler()).addToSendQueueWithoutEvent(packet);
        sendPacket(packet);
    }

    public static void receivePacket(final Packet<?> packet) {
        ((IMixinNetworkManager)mc.getNetHandler()).addToReceiveQueue(packet);
    }

    public static void receivePacketWithoutEvent(final Packet<?> packet) {
        ((IMixinNetworkManager)mc.getNetHandler()).addToReceiveQueueWithoutEvent(packet);
    }


}
