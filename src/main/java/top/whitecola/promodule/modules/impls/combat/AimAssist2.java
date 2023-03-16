package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.PreMotionEvent;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.*;

import java.util.List;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class AimAssist2 extends AbstractModule {
    @ModuleSetting(name = "onRotate",type = "select")
    public Boolean onRotate = false;

    @ModuleSetting(name = "strength", addValue = 5f)
    public Float strength = 30f;

    private Vector2f rotations, lastRotations;

    @Override
    public void onPreMotion(PreMotionEvent e) {
        lastRotations = rotations;
        rotations = null;

        if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) return;

//        final List<EntityLivingBase> entities = Client.INSTANCE.getTargetManager().getTargets(5);
//
//        if (entities.isEmpty()) {
//            return;
//        }

        final EntityLivingBase target = getClosest(5);

        if(target==null){
            return;
        }

        rotations = RotationUtils.calculate(target);
        super.onPreMotion(e);
    }


    private EntityLivingBase getClosest(float range) {
        if (mc.theWorld == null) {
            return null;
        }


        double distance = range;
        EntityLivingBase target = null;

        for (Entity entity : mc.theWorld.playerEntities) {
            if (!(entity instanceof EntityLivingBase)) {
                continue;
            }



            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;

            if(entityLivingBase==null){
                continue;
            }

            if(entityLivingBase == mc.thePlayer){
                continue;
            }

//            if(entityLivingBase instanceof Ply){
//
//            }

//            if(!shouldAttack(entityLivingBase)){
//                continue;
//            }

            double currentDistance = mc.thePlayer.getDistanceToEntity(entityLivingBase);

            if (currentDistance <= distance) {
                distance = currentDistance;
                target = entityLivingBase;
            }
        }
        return target;
    }


    @Override
    public void onRender2D(float partialTicks) {
        if (rotations == null || lastRotations == null ) {
            return;
        }

        Vector2f rotations = new Vector2f(this.lastRotations.x + (this.rotations.x - this.lastRotations.x) * Wrapper.getTimer().renderPartialTicks, 0);
        final float strength = (float) MathUtils.getRandomInRange(this.strength, this.strength);

        final float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        final float gcd = f * f * f * 8.0F;

        int i = mc.gameSettings.invertMouse ? -1 : 1;
        float f2 = mc.mouseHelper.deltaX + ((rotations.x - mc.thePlayer.rotationYaw) * (strength / 100) -
                mc.mouseHelper.deltaX) * gcd;
        float f3 = mc.mouseHelper.deltaY - mc.mouseHelper.deltaY * gcd;

        mc.thePlayer.setAngles(f2, f3 * (float) i);
        super.onRender2D(partialTicks);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "AimAssist2";

    }
}
