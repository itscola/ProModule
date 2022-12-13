package top.whitecola.promodule.modules.impls.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.MoveEvent;
import top.whitecola.promodule.events.impls.event.PreMotionEvent;
import top.whitecola.promodule.injection.wrappers.IMixinEntityPlayerSP;
import top.whitecola.promodule.injection.wrappers.IMixinMinecraft;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.modules.impls.combat.Killaura;
import top.whitecola.promodule.utils.MathUtils;
import top.whitecola.promodule.utils.PlayerSPUtils;
import top.whitecola.promodule.utils.PlayerUtils;
import top.whitecola.promodule.utils.TimerUtils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class Speed extends AbstractModule {

//    private final ModeSetting mode = new ModeSetting("Mode", "Watchdog", "Watchdog", "Matrix");
//    private final ModeSetting watchdogMode = new ModeSetting("Watchdog Mode", "Hop", "Hop", "Low Hop", "Ground");


    @ModuleSetting(name = "fastfall",type = "select")
    public Boolean fastFall = false;

    @ModuleSetting(name = "Timer",addValue = 1f)
    public Float timer = 1f;

    private final TimerUtils timerUtil = new TimerUtils();


    private float speed;
    private int stage;


    @Override
    public void onMove(MoveEvent e) {

        super.onMove(e);
    }

    @Override
    public void onPreMotion(PreMotionEvent e) {
        ((IMixinMinecraft)mc).getTimer().timerSpeed = timer;
        if (e.isPre()) {
            if (mc.thePlayer.onGround) {
                if (PlayerSPUtils.isMoving()) {
                    mc.thePlayer.jump();
                    stage = 0;
                    speed = 1.10f;
                }
            } else {
                if (fastFall) {
                    stage++;
                    if (stage == 3) {
                        mc.thePlayer.motionY -= 0.05;
                    }
                    if (stage == 5) {
                        mc.thePlayer.motionY -= 0.184;
                    }
                }
                speed -= 0.004;
                PlayerSPUtils.setSpeed(PlayerSPUtils.getBaseMoveSpeed() * speed);
            }
        }
        super.onPreMotion(e);
    }

    @Override
    public void onEnable() {
        if(mc.thePlayer!=null) {
            speed = 1.1f;
            timerUtil.reset();
        }

        super.onEnable();
    }

    @Override
    public void onDisable() {
        if(mc.thePlayer!=null){
            mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
            ((IMixinMinecraft)mc).getTimer().timerSpeed = 1;
        }

        super.onDisable();
    }




    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.MOVEMENT;
    }

    @Override
    public String getModuleName() {
        return "Speed";

    }

    //    private void getHypixelBest() {
//        EntityPlayer player = mc.thePlayer;
//
//        if (player == null) return;
//
//        switch (stage) {
//            case 1: {
//                break;
//            }
//            case 2: {
//                if (player.onGround && PlayerSPUtils.isMoving()){
//                    mc.thePlayer.motionY += 0.005;
//                    speed *= 1.9;
//                }
//                speed *= bhopSpeed;
//                break;
//            }
//            case 3: {
//                speed += new Random().nextDouble() / 4800;
//                double difference = 0.66 * (lastDist - getBaseSpeed());
//                speed = lastDist - difference;
//                break;
//            }
//            default: {
//                if ((mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
//                        mc.thePlayer.getEntityBoundingBox().offset(0.0, mc.thePlayer.motionY, 0.0)).size() > 0
//                        || mc.thePlayer.isCollidedVertically) && stage > 0) {
//                    stage = ((mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f) ? 1 : 0);
//                }
//
////                if (Friction.isCurrentMode("Normal")) {
//                speed = lastDist - lastDist / 159;
////                } else if (Friction.isCurrentMode("Fix")) {
////                    speed = MoveUtils.calculateFriction(speed, lastDist, getBaseSpeed());
////                }
//                break;
//            }
//        }
//
//
//        speed = Math.max(speed - 0.02 * lastDist, getBaseSpeed());
//    }
//
//
//    private double getBaseSpeed() {
//        final EntityPlayerSP player = mc.thePlayer;
//        double base = 0.2895;
//        final PotionEffect moveSpeed = player.getActivePotionEffect(Potion.moveSpeed);
//        final PotionEffect moveSlowness = player.getActivePotionEffect(Potion.moveSlowdown);
//        if (moveSpeed != null)
//            base *= 1.0 + 0.19 * (moveSpeed.getAmplifier() + 1);
//
//        if (moveSlowness != null)
//            base *= 1.0 - 0.13 * (moveSlowness.getAmplifier() + 1);
//
//        if (player.isInWater()) {
//            base *= 0.5203619984250619;
//            final int depthStriderLevel = EnchantmentHelper.getDepthStriderModifier(mc.thePlayer);
//            if (depthStriderLevel > 0) {
//                double[] DEPTH_STRIDER_VALUES = new double[]{1.0, 1.4304347400741908, 1.7347825295420374,
//                        1.9217391028296074};
//                base *= DEPTH_STRIDER_VALUES[depthStriderLevel];
//            }
//        } else if (player.isInLava()) {
//            base *= 0.5203619984250619;
//        }
//        return base;
//    }
//
    public static void setMotion(MoveEvent em, double speed) {
        Minecraft mc = Minecraft.getMinecraft();
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0 && strafe == 0) {
            em.setX(0);
            em.setZ(0);
        } else {
            if (forward != 0) {
                if (strafe > 0) {
                    yaw += (float) (forward > 0 ? -45 : 45);
                } else if (strafe < 0) {
                    yaw += (float) (forward > 0 ? 45 : -45);
                }
                strafe = 0;
                if (forward > 0) {
                    forward = 1;
                } else if (forward < 0) {
                    forward = -1;
                }
            }
            em.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90)));
            em.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90)));
        }


    }
}
