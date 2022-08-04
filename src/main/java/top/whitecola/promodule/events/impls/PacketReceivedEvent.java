package top.whitecola.promodule.events.impls;

import net.minecraft.network.Packet;

public class PacketReceivedEvent {
    private Packet packet;
    private boolean canceled;

    public PacketReceivedEvent(Packet packet){
        this.packet = packet;
    }

    public void setCancel(){
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public Packet getPacket() {
        return packet;
    }
}
