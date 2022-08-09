package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.WorldRenderEvent;
import top.whitecola.promodule.injection.wrappers.IMixinEntity;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.AimUtils;
import top.whitecola.promodule.utils.ServerUtils;

import java.util.Vector;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class AimAssist extends AbstractModule {

    @ModuleSetting(name = "FOV" ,min= 1,max = 180)
    public Float fieldOfView = 90f;

    @ModuleSetting(name = "Range" ,min= 1,max = 6)
    public Float range = 4f;

    @ModuleSetting(name = "Speed" ,min= 1,max = 100)
    public Float speed = 50f;

    @ModuleSetting(name = "WhileAttack",type = "select")
    public Boolean whileAttack = true;

    @ModuleSetting(name = "Vertical",type = "select")
    public Boolean vertical = false;

    @ModuleSetting(name = "AttackFirst",type = "select")
    public Boolean attackMode = false;

    @ModuleSetting(name = "Color4Team",type = "select")
    public Boolean checkHatColor = false;

    public long delta, lastTime;

    private EntityLivingBase theTarget;

    private Vector<Integer> grounded = new Vector<Integer>();

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "AimAssist";

    }

    @Override
    public void worldRenderEvent(WorldRenderEvent e) {


        EntityPlayerSP player = mc.thePlayer;

        if(player==null){
            return;
        }

        long time = System.nanoTime() / 100000;
        delta = time - lastTime;
        lastTime = time;

        if(whileAttack && !mc.gameSettings.keyBindAttack.isKeyDown()){
            return;
        }


        if(!attackMode) {
            theTarget = getClosest(this.range);
            if (theTarget == null) {
                return;
            }
        }

        if(attackMode && theTarget!=null){
            if(mc.thePlayer.getDistanceToEntity(theTarget)>6){
                return;
            }
        }



//        System.out.println(closest.getName());

        float[] rotations = AimUtils.getRotationsDelta(theTarget);
        if(rotations==null){
            return;
        }
        float targetYaw = rotations[0];
        float targetPitch = rotations[1];

        float speed = (1f/delta)*10000 * (1 / (float)this.speed*10f);

        if(speed > 0) {
            float addYaw = (-targetYaw / speed);
            float addPitch = (-targetPitch / speed);

            if(vertical){
                addPitch = 0;
            }

            IMixinEntity playerEntity = (IMixinEntity)mc.thePlayer;


            playerEntity.setRotationYaw(playerEntity.getRotationYaw() + addYaw);
            playerEntity.setPrevRotationYaw(playerEntity.getRotationYaw());

            playerEntity.setRotationPitch(playerEntity.getRotationPitch() + addPitch);
            playerEntity.setPrevRotationPitch(playerEntity.getRotationPitch());

        }

        super.worldRenderEvent(e);
    }

    @Override
    public void onEnable() {
        lastTime = 0;
        delta = 0;

        vertical = true;
        fieldOfView = 50f;
        speed= 70f;
        super.onEnable();
    }

    private EntityLivingBase getClosest(float range) {
        if (mc.theWorld == null) {
            return null;
        }

        if(theTarget!=null&& (mc.thePlayer.getDistanceToEntity(theTarget)<range) && !theTarget.isDead){
            return theTarget;
        }

        double distance = range;
        EntityLivingBase target = null;

        for (Entity entity : mc.theWorld.playerEntities) {
            if (!(entity instanceof EntityLivingBase)) {
                continue;
            }



            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;

            if(!shouldAttack(entityLivingBase)){
                continue;
            }

            double currentDistance = mc.thePlayer.getDistanceToEntity(entityLivingBase);

            if (currentDistance <= distance) {
                distance = currentDistance;
                target = entityLivingBase;
            }
        }
        return target;
    }

    private boolean shouldAttack(EntityLivingBase entity){
        if(AimUtils.getRotationsDelta(entity)[0]>fieldOfView && AimUtils.getRotationsDelta(entity)[1]>fieldOfView){
            return false;
        }

        if(entity.isInvisible()){
            return false;
        }

        if(entity==mc.thePlayer){
            return false;
        }


        if(checkHatColor){
            if(entity.getEquipmentInSlot(4)==null||entity.getEquipmentInSlot(4).getTagCompound().getCompoundTag("display").getInteger("color")==0){
                return false;
            }

            if(entity.getEquipmentInSlot(4)!=null&&mc.thePlayer.getEquipmentInSlot(4)!=null) {
                Integer targetLeatherHatColor = entity.getEquipmentInSlot(4).getTagCompound().getCompoundTag("display").getInteger("color");
                Integer playerLeatherHatColor = mc.thePlayer.getEquipmentInSlot(4).getTagCompound().getCompoundTag("display").getInteger("color");


                if (targetLeatherHatColor != null&&playerLeatherHatColor!=null) {
                    if (targetLeatherHatColor.equals(playerLeatherHatColor)) {
                        return false;
                    }
                }
            }
        }


        if(entity.isOnSameTeam(mc.thePlayer)){
            return false;
        }


        if(isBot(entity)){
            return false;
        }


        return true;
    }


    //skid : temp
    public boolean isBot(EntityLivingBase entity){
        if (entity == null) {
            return true;
        }

        if (entity.isPlayerSleeping()) {
            return true;
        }

        if (entity.getEntityId() > 1000000) {
            return true;
        }

        if (!ServerUtils.isInTabList(entity)) {
            return true;
        }

        if (((EntityPlayer) entity).ticksExisted <= 80) {
            return true;
        }

        if (!this.grounded.contains(entity.getEntityId())) {
            return true;
        }

        if(entity.getDisplayName().getFormattedText().contains("[NPC]")){
            return true;
        }



        return false;
    }


    @Override
    public void onDisable() {

        super.onDisable();
    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (G)";
    }

    @Override
    public void onAttackEntity(AttackEntityEvent e) {

        if(!attackMode){
            return;
        }

        if(e.target==null){
            return;
        }

        if(e.target.isDead){
            return;
        }



        if(e.target instanceof EntityPlayer){
            EntityLivingBase target = (EntityLivingBase) e.target;
            if(shouldAttack(target)){
                this.theTarget = target;
                return;
            }
        }

        super.onAttackEntity(e);
    }

    @Override
    public void onLivingHurt(LivingHurtEvent e) {
        if(!attackMode){
            return;
        }

        if(e.entity.isDead){
            if(e.entity instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer) e.entity;
                if(theTarget.getUniqueID().equals(player.getUniqueID())){
                    theTarget = null;
                }
            }
        }
        super.onLivingHurt(e);
    }


    @Override
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        if(e.entity instanceof EntityPlayerSP){
            this.theTarget = null;
            this.grounded.clear();
        }


        super.onEntityJoinWorld(e);
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
}
