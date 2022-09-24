package top.whitecola.promodule.injection.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.injection.wrappers.IMixinEntityPlayerSP;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP  extends AbstractClientPlayer implements IMixinEntityPlayerSP {
    @Shadow private boolean serverSprintState;
    @Shadow private boolean serverSneakState;

    @Shadow @Final public NetHandlerPlayClient sendQueue;

    @Shadow protected abstract boolean isCurrentViewEntity();

    @Shadow private double lastReportedPosX;

    @Shadow private double lastReportedPosY;

    @Shadow private double lastReportedPosZ;

    @Shadow private float lastReportedYaw;

    @Shadow private float lastReportedPitch;

    @Shadow private int positionUpdateTicks;

    public MixinEntityPlayerSP(World p_i45074_1_, GameProfile p_i45074_2_) {
        super(p_i45074_1_, p_i45074_2_);
    }

    @Override
    public boolean isServerSprintState() {
        return this.serverSprintState;
    }

    @Override
    public void setServerSprintState(boolean state) {
        this.serverSprintState = state;
    }

    @Override
    public boolean isServerSneakState() {
        return this.serverSneakState;
    }

    @Override
    public void setServerSneakState(boolean state) {
        this.serverSprintState = state;
    }

//    /**
//     * @author White_cola
//     * @reason sprint.
//     */
//    @Overwrite
//    public void onUpdateWalkingPlayer() {
//        boolean flag = this.isSprinting();
//        KeepSprint keepSprint = (KeepSprint) ProModule.getProModule().getModuleManager().getModuleByName("KeepSprint");
//        if(keepSprint.isEnabled()&&keepSprint.vanillaSprint){
//            flag = true;
//        }
//        if (flag != this.serverSprintState) {
//            if (flag) {
//                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
//            } else {
//                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
//            }
//
//            this.serverSprintState = flag;
//        }
//
//        boolean flag1 = this.isSneaking();
//        if (flag1 != this.serverSneakState) {
//            if (flag1) {
//                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
//            } else {
//                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
//            }
//
//            this.serverSneakState = flag1;
//        }
//
//        if (this.isCurrentViewEntity()) {
//            double d0 = this.posX - this.lastReportedPosX;
//            double d1 = this.getEntityBoundingBox().minY - this.lastReportedPosY;
//            double d2 = this.posZ - this.lastReportedPosZ;
//            double d3 = (double)(this.rotationYaw - this.lastReportedYaw);
//            double d4 = (double)(this.rotationPitch - this.lastReportedPitch);
//            boolean flag2 = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4 || this.positionUpdateTicks >= 20;
//            boolean flag3 = d3 != 0.0 || d4 != 0.0;
//            if (this.ridingEntity == null) {
//                if (flag2 && flag3) {
//                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
//                } else if (flag2) {
//                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.onGround));
//                } else if (flag3) {
//                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
//                } else {
//                    this.sendQueue.addToSendQueue(new C03PacketPlayer(this.onGround));
//                }
//            } else {
//                this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
//                flag2 = false;
//            }
//
//            ++this.positionUpdateTicks;
//            if (flag2) {
//                this.lastReportedPosX = this.posX;
//                this.lastReportedPosY = this.getEntityBoundingBox().minY;
//                this.lastReportedPosZ = this.posZ;
//                this.positionUpdateTicks = 0;
//            }
//
//            if (flag3) {
//                this.lastReportedYaw = this.rotationYaw;
//                this.lastReportedPitch = this.rotationPitch;
//            }
//        }
//
//    }


}
