package top.whitecola.promodule.injection.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.injection.wrappers.IMixinEntity;
import top.whitecola.promodule.modules.impls.combat.HitBox;
import top.whitecola.promodule.utils.Vector3d;

@Mixin(Entity.class)
public abstract class MixinEntity implements IMixinEntity {


    @Shadow public float rotationYaw;

    @Shadow public float rotationPitch;

    @Shadow public float prevRotationYaw;

    @Shadow public float prevRotationPitch;


    @Shadow protected abstract Vec3 getVectorForRotation(float p_getVectorForRotation_1_, float p_getVectorForRotation_2_);

    @Shadow public abstract Vec3 getPositionEyes(float p_getPositionEyes_1_);

    @Shadow public World worldObj;

    @Shadow public double posX;

    @Shadow public double posY;

    @Shadow public double posZ;

    @Override
    public void setRotationYaw(float rotationYaw) {
        this.rotationYaw = rotationYaw;
    }

    @Override
    public void setRotationPitch(float rotationPitch) {
        this.rotationPitch = rotationPitch;
    }

    @Override
    public void setPrevRotationYaw(float prevRotationYaw) {
        this.prevRotationYaw = prevRotationYaw;
    }

    @Override
    public void setPrevRotationPitch(float prevRotationPitch) {
        this.prevRotationPitch = prevRotationPitch;
    }

    @Override
    public float getPrevRotationPitch() {
        return this.prevRotationPitch;
    }

    @Override
    public float getPrevRotationYaw() {
        return this.prevRotationYaw;
    }

    @Override
    public float getRotationPitch() {
        return this.rotationPitch;
    }

    @Override
    public float getRotationYaw() {
        return this.rotationYaw;
    }

    @Override
    public Vec3 getVectorForRotation1(float pitch, float yaw) {
        return this.getVectorForRotation(pitch,yaw);
    }


    @Inject(method = "getCollisionBorderSize", at = { @At("HEAD") }, cancellable = true)
    public void getCollisionBorderSize(CallbackInfoReturnable<Float> cir){
        HitBox hitBox = (HitBox) ProModule.getProModule().getModuleManager().getModuleByName("HitBox");
        if(hitBox!=null&&hitBox.isEnabled()){
            cir.setReturnValue(hitBox.getValue());
            cir.cancel();
        }
    }
    @Override
    public MovingObjectPosition rayTraceCustom(double blockReachDistance, float yaw, float pitch) {
        final Vec3 vec3 = this.getPositionEyes(1.0F);
        final Vec3 vec31 = this.getLookCustom(yaw, pitch);
        final Vec3 vec32 = vec3.addVector(vec31.xCoord * blockReachDistance, vec31.yCoord * blockReachDistance, vec31.zCoord * blockReachDistance);
        return this.worldObj.rayTraceBlocks(vec3, vec32, false, false, true);
    }

    @Override
    public Vec3 getLookCustom(float yaw, float pitch) {
        return this.getVectorForRotation(pitch, yaw);
    }

    @Override
    public Vector3d getCustomPositionVector() {
        return new Vector3d(this.posX, this.posY, this.posZ);
    }
}
