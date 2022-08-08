package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.WorldRenderEvent;
import top.whitecola.promodule.injection.wrappers.IMixinEntity;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.AimUtils;

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


    public long delta, lastTime;

    private EntityLivingBase theTarget;

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

        theTarget = getClosest(this.range);
        if(theTarget==null){
            return;
        }



//        System.out.println(closest.getName());

        float[] rotations = AimUtils.getRotationsDelta(theTarget);
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


//         if(entity.getEquipmentInSlot(4)!=null && mc.thePlayer.getEquipmentInSlot(4)!=null){
//             if(entity.getEquipmentInSlot(4).getItem().equals(mc.thePlayer.getEquipmentInSlot(4).getItem())){
//                 System.out.println(entity.getEquipmentInSlot(4).getItem().getRegistryName()+" "+mc.thePlayer.getEquipmentInSlot(4).getItem().getRegistryName());
//                 return false;
//             }
//         }

        if(entity.isOnSameTeam(mc.thePlayer)){
            return false;
        }

        return true;
    }


    @Override
    public void onDisable() {

        super.onDisable();
    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (G)";
    }


}
