package top.whitecola.promodule.events.impls;

import net.minecraft.client.Minecraft;
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
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.events.EventAdapter;
import top.whitecola.promodule.events.impls.event.PacketReceivedEvent;
import top.whitecola.promodule.events.impls.event.WorldRenderEvent;
import top.whitecola.promodule.modules.AbstractModule;

import java.util.Vector;
import static top.whitecola.promodule.utils.MCWrapper.*;

public class EventToInvokeModules extends EventAdapter {
    private Vector<AbstractModule> modules;


    public EventToInvokeModules() {
        super(MainMenuEvent.class.getSimpleName());
        modules = ProModule.getProModule().getModuleManager().getModules();
    }

    @Override
    public void onRender2D(RenderWorldLastEvent event) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onRender2D(event);
        }

        super.onRender2D(event);
    }

    @Override
    public void onRender(TickEvent.RenderTickEvent event) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onRender(event);
        }
        super.onRender(event);
    }

    @Override
    public void tickEvent(TickEvent event) {

        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onTick();
        }

        super.tickEvent(event);
    }

    @Override
    public void onRenderOverLay(RenderGameOverlayEvent event) {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().theWorld == null || mc.thePlayer == null) {
            return;
        }

        if (event.type != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }

        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onRenderOverLay(event);
        }
        super.onRenderOverLay(event);
    }

    @Override
    public void renderGameOverlayRETURN() {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.renderGameOverlayRETURN();
        }
        super.renderGameOverlayRETURN();
    }

    @Override
    public void onAttackEntity(AttackEntityEvent e) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onAttackEntity(e);
        }
        super.onAttackEntity(e);
    }


    @Override
    public void onWordRender(RenderWorldEvent e) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onWordRender(e);
        }
        super.onWordRender(e);
    }

    @Override
    public void onLoginIn(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onLoginIn(e);
        }
        super.onLoginIn(e);
    }

    @Override
    public void onLoginOut(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onLoginOut(e);
        }

        super.onLoginOut(e);
    }

    @Override
    public void onChatReceive(ClientChatReceivedEvent e) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onChatReceive(e);
        }
        super.onChatReceive(e);
    }

    @Override
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onEntityJoinWorld(e);
        }
        super.onEntityJoinWorld(e);
    }

    @Override
    public void onLivingHurtEvent(LivingHurtEvent e) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onLivingHurt(e);
        }
        super.onLivingHurtEvent(e);
    }

    @Override
    public void onLivingAttack(LivingAttackEvent e) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onLivingAttack(e);
        }
        super.onLivingAttack(e);
    }


    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onLivingUpdate(e);
        }
        super.onLivingUpdate(e);
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent e) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onPlayerInteract(e);
        }
        super.onPlayerInteract(e);
    }

    public void onPlayerClickBlock(BlockPos p_clickBlock_1_, EnumFacing p_clickBlock_2_) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onPlayerClickBlock(p_clickBlock_1_,p_clickBlock_2_);
        }
        super.onPlayerClickBlock(p_clickBlock_1_,p_clickBlock_2_);
    }


    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Post e){
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onRenderPlayer(e);
        }
        super.onRenderPlayer(e);
    }

    @Override
    public void onRenderOverLayPre(RenderGameOverlayEvent.Pre e) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.onRenderOverLayPre(e);
        }

        super.onRenderOverLayPre(e);
    }

    @Override
    public void packetReceivedEvent(PacketReceivedEvent e) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.packetReceivedEvent(e);
        }
        super.packetReceivedEvent(e);
    }

    @Override
    public void worldRenderEvent(WorldRenderEvent e) {
        for (AbstractModule module : modules) {
            if (!module.isEnabled())
                continue;
            module.worldRenderEvent(e);
        }
        super.worldRenderEvent(e);
    }
}
