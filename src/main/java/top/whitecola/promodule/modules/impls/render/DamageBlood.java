package top.whitecola.promodule.modules.impls.render;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.ParticleUtils;

import java.util.concurrent.ConcurrentHashMap;

public class DamageBlood extends AbstractModule {
    public ConcurrentHashMap<EntityLivingBase,Float> map = new ConcurrentHashMap<EntityLivingBase, Float>();

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        EntityLivingBase entity = e.entityLiving;
        if(entity instanceof EntityPlayerSP){
            return;
        }

        if(!(entity instanceof EntityPlayer)){
            return;
        }

        if(!map.containsKey(entity)){
            map.put(entity,entity.getHealth());
        }

        if(entity.getHealth()<map.get(entity)){
            map.put(entity,entity.getHealth());
            ParticleUtils.spawnBloodParticle(entity);
        }


        super.onLivingUpdate(e);
    }

    @Override
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        if(e.entity instanceof EntityPlayerSP){
            this.map.clear();
        }
        super.onEntityJoinWorld(e);
    }

    @Override
    public void onDisable() {
        this.map.clear();
        super.onDisable();
    }

    @Override
    public void onLoginOut(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        this.map.clear();
        super.onLoginOut(e);
    }


    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.RENDERS;
    }

    @Override
    public String getModuleName() {
        return "DamageBlood";
    }
}
