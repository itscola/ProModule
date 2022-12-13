package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.PacketReceivedEvent;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.ServerUtils;

import static top.whitecola.promodule.utils.MCWrapper.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class AntiBot extends AbstractModule {

    @ModuleSetting(name = "HealthC",type = "select")
    public Boolean healthC = true;

    @ModuleSetting(name = "GroundedC",type = "select")
    public Boolean groundedC = true;
//
//    @ModuleSetting(name = "SpawnC",type = "select")
//    public Boolean spawnC = true;
//
//    @ModuleSetting(name = "NameC",type = "select")
//    public Boolean nameC = true;

    @ModuleSetting(name = "SwingC",type = "select")
    public Boolean swingC = false;

    @ModuleSetting(name = "HitBefore",type = "select")
    public Boolean hitBefore = false;

    @ModuleSetting(name = "TabCheck",type = "select")
    public Boolean tagCheck = true;

//    @ModuleSetting(name = "PingCheck",type = "select")
//    public Boolean pingCheck = false;
//
//    @ModuleSetting(name = "SkinCheck",type = "select")
//    public Boolean skinCheck = false;
//
//    @ModuleSetting(name = "DulicateC",type = "select")
//    public Boolean dulicateCheck = false;

    @ModuleSetting(name = "SoundCheck",type = "select")
    public Boolean soundCheck = true;
//
//    @ModuleSetting(name = "IllegalRo",type = "select")
//    public Boolean illegalRotation = false;

    private final ArrayList<Entity> madeSound = new ArrayList<>();
    private final ArrayList<Entity> swingEntity = new ArrayList<>();
    private final ArrayList<Entity> hitBeforeEntity = new ArrayList<>();
    private final ArrayList<Entity> duplicates = new ArrayList<>();
    private Vector<Integer> grounded = new Vector<Integer>();

    public boolean isBot(EntityLivingBase entity){
        if(entity==null){
            return true;
        }


        if (healthC && !Float.isNaN(entity.getHealth()))
            return true;
        if (soundCheck && !madeSound.contains(entity))
            return true;
//        if (swingC && !swingEntity.contains(entity))
//            return true;
//        if (hitBefore && !hitBeforeEntity.contains(entity))
//            return true;
        if (tagCheck&&!ServerUtils.isInTabList(entity))
            return true;

        if (groundedC&&!grounded.contains(entity.getEntityId()) )
            return true;
//        if (!hasPing(entity) && pingCheck)
//            return true;
//        if (rotationEntity.contains(entity) && rotation.getPropertyValue())
//            return true;
//        if (groundSpawnCheck.getPropertyValue() && groundSpawnEntity.contains(entity))
//            return true;
//        if (period.getPropertyValue() && !periodEntity.contains(entity))
//            return true;

        return false;
    }

    public void checkName(EntityLivingBase entityLivingBase){

    }

    @Override
    public void packetReceivedEvent(PacketReceivedEvent e) {
        if (e.getPacket() instanceof S29PacketSoundEffect) {

            mc.theWorld.loadedEntityList.forEach(entity->{
                if (entity != mc.thePlayer && entity.getDistance
                        (((S29PacketSoundEffect) e.getPacket()).getX(), ((S29PacketSoundEffect) e.getPacket()).getY(), ((S29PacketSoundEffect) e.getPacket()).getZ()) <= 0.8) {
                    if(!madeSound.contains(entity)) {
                        madeSound.add(entity);
                    }
                }
            });

        }




        super.packetReceivedEvent(e);
    }

    @Override
    public void onTick() {
        if(mc.theWorld==null){
            return;
        }

        for(EntityLivingBase entity : mc.theWorld.playerEntities){
            if(entity==null){
                continue;
            }
            if(entity.onGround){
                if(!grounded.contains(entity.getEntityId())){
                    grounded.add(entity.getEntityId());
                }
            }



        }
        super.onTick();
    }

    @Override
    public void onEnable() {
        madeSound.clear();
        swingEntity.clear();
        hitBeforeEntity.clear();
        duplicates.clear();
        super.onEnable();
    }

    @Override
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        if(e.entity instanceof EntityPlayerSP){
            madeSound.clear();
            swingEntity.clear();
            hitBeforeEntity.clear();
            duplicates.clear();
        }

        super.onEntityJoinWorld(e);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "AntiBot";

    }


}
