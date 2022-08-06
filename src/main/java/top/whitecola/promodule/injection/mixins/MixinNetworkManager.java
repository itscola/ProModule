package top.whitecola.promodule.injection.mixins;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.*;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.whitecola.promodule.events.EventManager;
import top.whitecola.promodule.events.impls.event.PacketReceivedEvent;

import java.lang.reflect.Field;

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

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = { @At("HEAD") }, cancellable = true)
    private void sendPacket(final Packet<?> packet, final CallbackInfo callbackInfo) {
        if (packet instanceof FMLProxyPacket) {
            callbackInfo.cancel();
            return;
        }

        if (packet instanceof C17PacketCustomPayload) {
            final C17PacketCustomPayload packetCustomPayload = (C17PacketCustomPayload)packet;
            final String channelName = packetCustomPayload.getChannelName();
            if (!channelName.startsWith("MC|")) {
                callbackInfo.cancel();
            }
            else if (channelName.equalsIgnoreCase("MC|Brand")) {
                final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer()).writeString("vanilla");
                try {
                    final Field field = packetCustomPayload.getClass().getDeclaredField("field_149561_c");
                    field.setAccessible(true);
                    field.set(packetCustomPayload, packetBuffer);
                }
                catch (NoSuchFieldException e3) {
                    try {
                        final Field field2 = packetCustomPayload.getClass().getDeclaredField("data");
                        field2.setAccessible(true);
                        field2.set(packetCustomPayload, packetBuffer);
                    }
                    catch (NoSuchFieldException ex2) {
                        ex2.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }


}
