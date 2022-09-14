package top.whitecola.promodule.events.impls.event;

import net.minecraft.network.Packet;

public class PacketSendEvent extends AbstractEvent{
    private Packet packet;
    private boolean cancle;

    public PacketSendEvent(Packet packet){
        this.packet = packet;
    }


    public Packet getPacket() {
        return packet;
    }

    @Override
    public void setCancel() {
        super.setCancel();
    }

    @Override
    public boolean isCanceled() {
        return super.isCanceled();
    }
}
