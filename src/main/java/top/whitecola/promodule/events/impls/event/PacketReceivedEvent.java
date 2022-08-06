package top.whitecola.promodule.events.impls.event;

import net.minecraft.network.Packet;

public class PacketReceivedEvent extends AbstractEvent{
    private Packet packet;

    public PacketReceivedEvent(Packet packet){
        this.packet = packet;
    }


    public Packet getPacket() {
        return packet;
    }
}
