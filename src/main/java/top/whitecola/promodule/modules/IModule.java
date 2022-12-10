package top.whitecola.promodule.modules;

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
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import top.whitecola.promodule.events.impls.event.PacketReceivedEvent;
import top.whitecola.promodule.events.impls.event.PacketSendEvent;
import top.whitecola.promodule.events.impls.event.PreMotionEvent;
import top.whitecola.promodule.events.impls.event.WorldRenderEvent;
import top.whitecola.promodule.gui.widgets.AbstractWidget;

public interface IModule {
    void onTick();

    void onRender2D(RenderWorldLastEvent e);

    void onRender(TickEvent.RenderTickEvent e);

    void onRenderOverLay(RenderGameOverlayEvent event);

    void onEntityJoinWorld(EntityJoinWorldEvent e);

    void onEnable();

    void onDisable();

    void enable();

    void disable();

    boolean isEnabled();

    @Deprecated
    void renderGameOverlayRETURN();

    void addOption(ModuleOption option);

    void removeOption(ModuleOption option);

    ModuleCategory getModuleType();

    String getModuleName();

    void optionEnable(String optionName);

    void optionDisable(String optionName);

    ModuleOption getOptionByName(String optionName);

    void onAttackEntity(AttackEntityEvent e);

    void onWordRender(RenderWorldEvent e);

    void onLoginIn(FMLNetworkEvent.ClientConnectedToServerEvent e);

    void onLoginOut(FMLNetworkEvent.ClientDisconnectionFromServerEvent e);

    void onChatReceive(ClientChatReceivedEvent e);

    void addWidget(AbstractWidget widget);

    void removeWidget(AbstractWidget widget);

    void onLivingHurt(LivingHurtEvent e);

    String getDescription();

    void onLivingAttack(LivingAttackEvent e);

    void onLivingUpdate(LivingEvent.LivingUpdateEvent e);

    void onPlayerInteract(PlayerInteractEvent e);

    void onPlayerClickBlock(BlockPos p_clickBlock_1_, EnumFacing p_clickBlock_2_);

    void onRenderPlayer(RenderPlayerEvent.Post e);

    void onRenderOverLayPre(RenderGameOverlayEvent.Pre event);

    void onRender3D(int pass, float partialTicks, long finishTimeNano);

    void onModuleSettingChanged(String name);
    void packetReceivedEvent(PacketReceivedEvent e);

    void worldRenderEvent(WorldRenderEvent e);

    void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent e);

    void onRenderBlock(int x,int y,int z, Block block);

    void onSendPacket(PacketSendEvent packetSendEvent);

    void onPreMotion(PreMotionEvent e);
}
