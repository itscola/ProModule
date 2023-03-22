package top.whitecola.promodule.injection.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.whitecola.promodule.events.EventManager;
import top.whitecola.promodule.events.impls.event.MoveEvent;
import top.whitecola.promodule.events.impls.event.PreMotionEvent;
import top.whitecola.promodule.injection.wrappers.IMixinEntityPlayerSP;
import top.whitecola.promodule.utils.wrapper.RotationPitchHead;

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

    @Shadow public MovementInput movementInput;

    @Shadow public float prevRenderArmPitch;

    @Shadow public float prevRenderArmYaw;

    @Shadow public float renderArmYaw;

    @Shadow public float renderArmPitch;

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


//    @Inject(method = "onUpdateWalkingPlayer", at = { @At("HEAD") }, cancellable = true)
//    public void onUpdateWalkingPlayer(CallbackInfo ci){
//        PreMotionEvent preMotionEvent = new PreMotionEvent(this.rotationYaw, this.rotationPitch, this.onGround, this.posX, this.getEntityBoundingBox().minY, this.posZ);
//        EventManager.getEventManager().onPreMotion(preMotionEvent);
//
//    }

//    @Inject(method = "onUpdateWalkingPlayer", at = { @At("RETURN") }, cancellable = true)
//    public void onUpdateWalkingPlayerReturn(CallbackInfo ci){
//        RotationPitchHead.rotationPitchHead = this.rotationPitch;
//    }


    /**
     * @author white_cola
     * @reason .
     */
    @Overwrite
    public void updateEntityActionState() {
        super.updateEntityActionState();
        if (this.isCurrentViewEntity()) {
            this.moveStrafing = this.movementInput.moveStrafe;
            this.moveForward = this.movementInput.moveForward;
            this.isJumping = this.movementInput.jump;
            this.prevRenderArmYaw = this.renderArmYaw;
            this.prevRenderArmPitch = this.renderArmPitch;
            this.renderArmPitch = (float)((double)this.renderArmPitch + (double)(this.rotationPitch - this.renderArmPitch) * 0.5D);
            this.renderArmYaw = (float)((double)this.renderArmYaw + (double)(this.rotationYaw - this.renderArmYaw) * 0.5D);
            RotationPitchHead.rotationPitchHead = this.rotationPitch;
        }

    }

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    public void EntityPlayerSP(Minecraft p_i46278_1_, World p_i46278_2_, NetHandlerPlayClient p_i46278_3_, StatFileWriter p_i46278_4_, CallbackInfo ci){
        RotationPitchHead.rotationPitchHead = this.rotationPitch;
    }


    @Override
    public void moveEntity(double p_moveEntity_1_, double p_moveEntity_3_, double p_moveEntity_3_2) {
        MoveEvent moveEvent = new MoveEvent(p_moveEntity_1_,p_moveEntity_3_,p_moveEntity_3_2);
        EventManager.getEventManager().onMove(moveEvent);
        if(moveEvent.isCancelled()){
            return;
        }
        super.moveEntity(moveEvent.getX(), moveEvent.getY(), moveEvent.getZ());
    }

    /**
     * @author White_cola
     * @reason rotation.
     */
    @Overwrite
    public void onUpdateWalkingPlayer() {

        PreMotionEvent preMotionEvent = new PreMotionEvent(this.rotationYaw, this.rotationPitch, this.onGround, this.posX, this.getEntityBoundingBox().minY, this.posZ);
        preMotionEvent.setPre(true);
        EventManager.getEventManager().onPreMotion(preMotionEvent);

//        System.out.println(preMotionEvent.getYaw()+" "+preMotionEvent.getPitch());
//        float serverPitch = preMotionEvent.getPitch();
//        float serverYaw = preMotionEvent.getYaw();

        boolean flag = this.isSprinting();
        if (flag != this.serverSprintState) {
            if (flag) {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
            } else {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
            }

            this.serverSprintState = flag;
        }

        boolean flag1 = this.isSneaking();
        if (flag1 != this.serverSneakState) {
            if (flag1) {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
            } else {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }

            this.serverSneakState = flag1;
        }

        if (this.isCurrentViewEntity()) {
            double d0 = preMotionEvent.getX() - this.lastReportedPosX;
            double d1 = preMotionEvent.getZ() - this.lastReportedPosY;
            double d2 = preMotionEvent.getZ() - this.lastReportedPosZ;
            double d3 = (double)(preMotionEvent.getYaw() - this.lastReportedYaw);
            double d4 = (double)(preMotionEvent.getPitch() - this.lastReportedPitch);
            boolean flag2 = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || this.positionUpdateTicks >= 20;
            boolean flag3 = d3 != 0.0D || d4 != 0.0D;
            if (this.ridingEntity == null) {
                if (flag2 && flag3) {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(preMotionEvent.getX(), preMotionEvent.getY(), preMotionEvent.getZ(), preMotionEvent.getYaw(), preMotionEvent.getPitch(), preMotionEvent.isGround()));
                } else if (flag2) {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(preMotionEvent.getX(), preMotionEvent.getY(), preMotionEvent.getZ(), preMotionEvent.isGround()));
                } else if (flag3) {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(preMotionEvent.getYaw(), preMotionEvent.getPitch(), preMotionEvent.isGround()));
                } else {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer(preMotionEvent.isGround()));
                }
            } else {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
                flag2 = false;
            }

            ++this.positionUpdateTicks;
            if (flag2) {
                this.lastReportedPosX = preMotionEvent.getX();
                this.lastReportedPosY = preMotionEvent.getY();
                this.lastReportedPosZ = preMotionEvent.getZ();
                this.positionUpdateTicks = 0;
            }

            if (flag3) {
                this.lastReportedYaw = preMotionEvent.getYaw();
                this.lastReportedPitch = preMotionEvent.getPitch();
            }
        }

        preMotionEvent.setPre(false);
        EventManager.getEventManager().onPreMotion(preMotionEvent);

        RotationPitchHead.rotationPitchHead = this.rotationPitch;

    }

    @Override
    public void setSpeedInAir(float value) {
        this.speedInAir = value;
    }


}
