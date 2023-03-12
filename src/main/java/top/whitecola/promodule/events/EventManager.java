package top.whitecola.promodule.events;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import top.whitecola.promodule.events.impls.event.*;

import java.util.Vector;

public class EventManager {
    private static EventManager eventManager = new EventManager();
    protected Vector<EventAdapter> events = new Vector<EventAdapter>();


    public EventManager() {

    }

    public static EventManager getEventManager() {
        return eventManager;
    }


    public void addEvent(EventAdapter adapter) {
        this.events.add(adapter);
    }

    public void removeEvent(EventAdapter adapter) {
        this.events.remove(adapter);
    }

    public <T extends EventAdapter> T getEvent(Class<T> clazz) {
        for (EventAdapter eventAdapter : events) {
            if (eventAdapter.getClass() == clazz) {
                return (T) eventAdapter;
            }
        }

        return null;
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onKeyInput(e);
        }
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent e) {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onRender(e);
        }
    }

    @SubscribeEvent
    public void tickEvent(TickEvent e) {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.tickEvent(e);
        }
    }

    @SubscribeEvent
    public void onRender2D(RenderWorldLastEvent e) {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onRender2D(e);
        }
    }

    @SubscribeEvent
    public void onRenderOverLay(RenderGameOverlayEvent e) {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onRenderOverLay(e);
        }
    }

    public void renderGameOverlayRETURN() {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.renderGameOverlayRETURN();
        }
    }


    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent e) {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onAttackEntity(e);
        }
    }

    @SubscribeEvent
    public void onWordRender(RenderWorldEvent e) {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onWordRender(e);
        }
    }

    @SubscribeEvent
    public void onLoginIn(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onLoginIn(e);
        }
    }

    @SubscribeEvent
    public void onLoginOut(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onLoginOut(e);
        }
    }

    @SubscribeEvent
    public void onChatReceive(ClientChatReceivedEvent e) {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onChatReceive(e);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onEntityJoinWorld(e);
        }
    }

    @Deprecated
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent e) {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onLivingHurtEvent(e);
        }
    }


    @Deprecated
    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent e) {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onLivingAttack(e);
        }
    }


    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onLivingUpdate(e);
        }
    }


    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent e){
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onPlayerInteract(e);
        }
    }

    public void onPlayerClickBlock(BlockPos p_clickBlock_1_, EnumFacing p_clickBlock_2_){
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onPlayerClickBlock(p_clickBlock_1_,p_clickBlock_2_);
        }
    }

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Post e){
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onRenderPlayer(e);
        }
    }


    @SubscribeEvent
    public void onRenderOverLayPre(RenderGameOverlayEvent.Pre e) {
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onRenderOverLayPre(e);
        }
    }

    public void onRender3D(int pass, float partialTicks, long finishTimeNano){
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onRender3D(pass,partialTicks,finishTimeNano);
        }
    }

    public void packetReceivedEvent(PacketReceivedEvent e){
        for (EventAdapter eventAdapter : events) {
            eventAdapter.packetReceivedEvent(e);
        }
    }

    public void worldRenderEvent(WorldRenderEvent e){
        for (EventAdapter eventAdapter : events) {
            eventAdapter.worldRenderEvent(e);
        }
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent e){
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onPlayerRespawn(e);
        }
    }

    public void onRenderBlock(int x,int y,int z, Block block){
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onRenderBlock(x,y,z,block);
        }
    }

    public void onPacketSend(PacketSendEvent event){
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onSendPacket(event);
        }
    }


    public void onPreMotion(PreMotionEvent e){
//        System.out.println(1111112);
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onPreMotion(e);
        }
    }

    public void onMove(MoveEvent e){
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onMove(e);
        }
    }

    public void onJump(JumpEvent e){
        for (EventAdapter eventAdapter : events) {
            eventAdapter.onJump(e);
        }
    }
}
