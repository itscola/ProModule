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

public abstract class EventAdapter {
    private final String id;

    public EventAdapter(String id){
        this.id = id;
    }

    public void onJump(JumpEvent e){

    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e){

    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {

    }

    @SubscribeEvent
    public void onRender2D(RenderWorldLastEvent event) {

    }


    @SubscribeEvent
    public void tickEvent(TickEvent event) {

    }

    @SubscribeEvent
    public void onRenderOverLay(RenderGameOverlayEvent event) {

    }


    public String getId() {
        return id;
    }

    @SubscribeEvent
    public void renderGameOverlayRETURN() {

    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent e) {

    }

    @SubscribeEvent
    public void onWordRender(RenderWorldEvent e){

    }

    @SubscribeEvent
    public void onLoginIn(FMLNetworkEvent.ClientConnectedToServerEvent e){

    }

    @SubscribeEvent
    public void onLoginOut(FMLNetworkEvent.ClientDisconnectionFromServerEvent e){

    }

    @SubscribeEvent
    public void onChatReceive(ClientChatReceivedEvent e){

    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent e){

    }

    @SubscribeEvent
    public void onLivingHurtEvent(LivingHurtEvent e){

    }


    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent e){

    }



    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {

    }


    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent e){

    }

    public void onPlayerClickBlock(BlockPos p_clickBlock_1_, EnumFacing p_clickBlock_2_){

    }

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Post event){

    }


    @SubscribeEvent
    public void onRenderOverLayPre(RenderGameOverlayEvent.Pre event) {

    }

    public void onRender3D(int pass, float partialTicks, long finishTimeNano){

    }

    public void packetReceivedEvent(PacketReceivedEvent e){

    }

    public void worldRenderEvent(WorldRenderEvent e){

    }

    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent e){

    }

    public void onRenderBlock(int x,int y,int z, Block block){

    }

    public void onSendPacket(PacketSendEvent packetSendEvent){

    }

    public void onPreMotion(PreMotionEvent e){

    }

    public void onMove(MoveEvent e){

    }
}
