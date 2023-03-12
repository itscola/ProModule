package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.whitecola.promodule.events.EventManager;
import top.whitecola.promodule.events.impls.event.JumpEvent;
import top.whitecola.promodule.injection.wrappers.IMixinEntityLivingBase;
import top.whitecola.promodule.utils.wrapper.RotationPitchHead;

import java.util.Map;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase  extends Entity implements IMixinEntityLivingBase {
    public MixinEntityLivingBase(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Shadow protected abstract int getArmSwingAnimationEnd();

    @Shadow protected double newRotationPitch;

    @Shadow @Final private Map<Integer, PotionEffect> activePotionsMap;

    @Shadow public abstract boolean isServerWorld();

    @Shadow public float limbSwing;

    @Shadow public float limbSwingAmount;

    @Shadow public float prevLimbSwingAmount;

    @Shadow public abstract float getAIMoveSpeed();

    @Shadow public abstract boolean isOnLadder();

    @Shadow public float jumpMovementFactor;

    @Override
    public int runGetArmSwingAnimationEnd() {
        return this.getArmSwingAnimationEnd();
    }

    @Inject(method = "onEntityUpdate", at = @At(value = "RETURN"))
    public void onEntityUpdate(CallbackInfo ci){
        EntityLivingBase entity = (EntityLivingBase) (Object) this;
        if(entity instanceof EntityPlayerSP){
            RotationPitchHead.prevRotationPitchHead = RotationPitchHead.rotationPitchHead;
        }
    }

    @Override
    public Map<Integer, PotionEffect> getActivePotionsMap() {


        return this.activePotionsMap;
    }


    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    public void onJump(CallbackInfo ci) {
        JumpEvent jumpEvent = new JumpEvent();
        EventManager.getEventManager().onJump(jumpEvent);
        if (jumpEvent.isCanceled()) {
            ci.cancel();
        }
    }


//    /**
//     * @author White_cola
//     * @reason jumpEvent
//     */
//    @Overwrite
//    public void moveEntityWithHeading(float p_moveEntityWithHeading_1_, float p_moveEntityWithHeading_2_) {
//        double d0;
//        float f3;
//        if (this.isServerWorld()) {
//            float f5;
//            float f6;
//            if (!this.isInWater() || (Object)this instanceof EntityPlayer && ((EntityPlayer)(Object)this).capabilities.isFlying) {
//                if (this.isInLava() && (!((Object)this instanceof EntityPlayer) || !((EntityPlayer)(Object)this).capabilities.isFlying)) {
//                    d0 = this.posY;
//                    this.moveFlying(p_moveEntityWithHeading_1_, p_moveEntityWithHeading_2_, 0.02F);
//                    this.moveEntity(this.motionX, this.motionY, this.motionZ);
//                    this.motionX *= 0.5D;
//                    this.motionY *= 0.5D;
//                    this.motionZ *= 0.5D;
//                    this.motionY -= 0.02D;
//                    if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d0, this.motionZ)) {
//                        this.motionY = 0.30000001192092896D;
//                    }
//                } else {
//                    float f4 = 0.91F;
//                    if (this.onGround) {
//                        f4 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91F;
//                    }
//
//                    float f = 0.16277136F / (f4 * f4 * f4);
//                    if (this.onGround) {
//                        f5 = this.getAIMoveSpeed() * f;
//                    } else {
//                        f5 = this.jumpMovementFactor;
//                    }
//
//                    this.moveFlying(p_moveEntityWithHeading_1_, p_moveEntityWithHeading_2_, f5);
//                    f4 = 0.91F;
//                    if (this.onGround) {
//                        f4 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91F;
//                    }
//
//                    if (this.isOnLadder()) {
//                        f6 = 0.15F;
//                        this.motionX = MathHelper.clamp_double(this.motionX, (double)(-f6), (double)f6);
//                        this.motionZ = MathHelper.clamp_double(this.motionZ, (double)(-f6), (double)f6);
//                        this.fallDistance = 0.0F;
//                        if (this.motionY < -0.15D) {
//                            this.motionY = -0.15D;
//                        }
//
//                        boolean flag = this.isSneaking() && ((Object)this) instanceof EntityPlayer;
//                        if (flag && this.motionY < 0.0D) {
//                            this.motionY = 0.0D;
//                        }
//                    }
//
//                    this.moveEntity(this.motionX, this.motionY, this.motionZ);
//                    if (this.isCollidedHorizontally && this.isOnLadder()) {
//                        this.motionY = 0.2D;
//                    }
//
//                    if (this.worldObj.isRemote && (!this.worldObj.isBlockLoaded(new BlockPos((int)this.posX, 0, (int)this.posZ)) || !this.worldObj.getChunkFromBlockCoords(new BlockPos((int)this.posX, 0, (int)this.posZ)).isLoaded())) {
//                        if (this.posY > 0.0D) {
//                            this.motionY = -0.1D;
//                        } else {
//                            this.motionY = 0.0D;
//                        }
//                    } else {
//                        this.motionY -= 0.08D;
//                    }
//
//                    this.motionY *= 0.9800000190734863D;
//                    this.motionX *= (double)f4;
//                    this.motionZ *= (double)f4;
//                }
//            } else {
//                d0 = this.posY;
//                f5 = 0.8F;
//                f6 = 0.02F;
//                f3 = (float) EnchantmentHelper.getDepthStriderModifier(this);
//                if (f3 > 3.0F) {
//                    f3 = 3.0F;
//                }
//
//                if (!this.onGround) {
//                    f3 *= 0.5F;
//                }
//
//                if (f3 > 0.0F) {
//                    f5 += (0.54600006F - f5) * f3 / 3.0F;
//                    f6 += (this.getAIMoveSpeed() * 1.0F - f6) * f3 / 3.0F;
//                }
//
//                this.moveFlying(p_moveEntityWithHeading_1_, p_moveEntityWithHeading_2_, f6);
//                this.moveEntity(this.motionX, this.motionY, this.motionZ);
//                this.motionX *= (double)f5;
//                this.motionY *= 0.800000011920929D;
//                this.motionZ *= (double)f5;
//                this.motionY -= 0.02D;
//                if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d0, this.motionZ)) {
//                    this.motionY = 0.30000001192092896D;
//                }
//            }
//        }
//
//        this.prevLimbSwingAmount = this.limbSwingAmount;
//        d0 = this.posX - this.prevPosX;
//        double d3 = this.posZ - this.prevPosZ;
//
//        f3 = MathHelper.sqrt_double(d0 * d0 + d3 * d3) * 4.0F;
//        if (f3 > 1.0F) {
//            f3 = 1.0F;
//        }
//
//        this.limbSwingAmount += (f3 - this.limbSwingAmount) * 0.4F;
//        this.limbSwing += this.limbSwingAmount;
//    }

}
