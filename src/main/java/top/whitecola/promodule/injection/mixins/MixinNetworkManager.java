package top.whitecola.promodule.injection.mixins;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.events.EventManager;
import top.whitecola.promodule.events.impls.PacketReceivedEvent;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {

    @Shadow
    private Channel channel;

    @Shadow
    private INetHandler packetListener;

    /**
     * @author White_cola
     * @reason for PacketReceivedEvent.
     */
    @Overwrite
    protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet p_channelRead0_2_) throws Exception {
        if (this.channel.isOpen()) {
            try {
                PacketReceivedEvent event =  new PacketReceivedEvent(p_channelRead0_2_);
                EventManager.getEventManager().packetReceivedEvent(event);

                if(event.isCanceled()){
                    return;
                }
                p_channelRead0_2_.processPacket(this.packetListener);
            } catch (ThreadQuickExitException var4) {
            }
        }

    }
}
