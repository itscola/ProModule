package top.whitecola.promodule.injection.mixins;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.*;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.events.EventManager;
import top.whitecola.promodule.events.impls.event.PacketReceivedEvent;
import top.whitecola.promodule.events.impls.event.PacketSendEvent;
import top.whitecola.promodule.injection.wrappers.IMixinNetworkManager;
import top.whitecola.promodule.modules.impls.other.AntiForge;
import top.whitecola.promodule.utils.PacketUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import static top.whitecola.promodule.utils.MCWrapper.*;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager implements IMixinNetworkManager {

    @Shadow
    private Channel channel;

    @Shadow
    private INetHandler packetListener;

    @Shadow public abstract boolean isChannelOpen();

    @Shadow protected abstract void flushOutboundQueue();

    @Shadow protected abstract void dispatchPacket(Packet p_dispatchPacket_1_, GenericFutureListener<? extends Future<? super Void>>[] p_dispatchPacket_2_);

    @Shadow @Final private ReentrantReadWriteLock field_181680_j;


//    @Shadow @Final private Queue<NetworkManager.InboundHandlerTuplePacketListener> outboundPacketsQueue;

//    @Shadow @Final private Queue<NetworkManager.InboundHandlerTuplePacketListener> outboundPacketsQueue;

//    @Shadow @Final private Queue<NetworkManager.InboundHandlerTuplePacketListener> outboundPacketsQueue;

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

        AntiForge antiForge = (AntiForge) ProModule.getProModule().getModuleManager().getModuleByName("AntiForge");

        if(mc.isSingleplayer() || antiForge==null||!antiForge.isEnabled()){
            return;
        }



        if (antiForge!=null&&antiForge.isEnabled() && antiForge.noMods&&packet instanceof FMLProxyPacket) {
            callbackInfo.cancel();
            return;
        }



        if (antiForge!=null&&antiForge.isEnabled()&&antiForge.vanillia&&packet instanceof C17PacketCustomPayload && !mc.isSingleplayer()) {
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

        PacketSendEvent packetSendEvent = new PacketSendEvent(packet);
        EventManager.getEventManager().onPacketSend(packetSendEvent);
        if(packetSendEvent.isCanceled()){
            callbackInfo.cancel();
        }

    }

    @Override
    public void addToReceiveQueue(Packet packet) {

    }

    @Override
    public void addToReceiveQueueWithoutEvent(Packet packet) {

    }

    @Override
    public void addToSendQueueWithoutEvent(Packet packet) {
//        if (this.isChannelOpen()) {
//            this.flushOutboundQueue();
//            this.dispatchPacket(packet, (GenericFutureListener<? extends Future<? super Void>>[]) null);
//        } else {
//            this.field_181680_j.writeLock().lock();
//
//            try {
//                try {
//
//                    Field f =  NetworkManager.class.getDeclaredField("outboundPacketsQueue");
//                    f.setAccessible(true);
//                    f.getClass().getMethod("add");
//
//                    Method addMethod = this.outboundPacketsQueue.getClass().getMethod("add");
//                    addMethod.invoke(this.outboundPacketsQueue,PacketUtils.InboundHandlerTuplePacketListener(packet, (GenericFutureListener[]) null));
//                } catch (Throwable e) {
//                }
//            } finally {
//                this.field_181680_j.writeLock().unlock();
//            }
//        }

    }
}
