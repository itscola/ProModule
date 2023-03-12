package top.whitecola.promodule.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import top.whitecola.promodule.events.impls.event.MoveEvent;
import top.whitecola.promodule.injection.wrappers.IMixinEntityLivingBase;

import java.util.Map;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class PlayerSPUtils {

    public static Map<Integer, PotionEffect> getActivePotionsMap(){
        return ((IMixinEntityLivingBase)mc.thePlayer).getActivePotionsMap();
    }

    public static boolean isPotionActive(int var1) {

        return getActivePotionsMap().containsKey(Integer.valueOf(var1));
    }

    public static boolean isPotionActive(Potion var1) {
        return getActivePotionsMap().containsKey(Integer.valueOf(var1.getId()));
    }

    public static PotionEffect getActivePotionEffect(Potion var1) {
        return (PotionEffect)getActivePotionsMap().get(Integer.valueOf(var1.getId()));
    }

    public static double getLastTickDistance() {
        return Math.hypot(mc.thePlayer.posX - mc.thePlayer.prevPosX, mc.thePlayer.posZ - mc.thePlayer.prevPosZ);
    }

    public static double getBaseMotionY(double motionY) {
        return isPotionActive(Potion.jump) ? motionY + 0.1 * (getActivePotionEffect(Potion.jump).getAmplifier() + 1) : motionY;
    }


    public static double getBaseMoveSpeed(double multiplier) {
        double baseSpeed = getBySprinting();
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = getActivePotionEffect(Potion.moveSpeed).getAmplifier() +
                    1 - (mc.thePlayer.isPotionActive(Potion.moveSlowdown) ? getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1 : 0);
            baseSpeed *= 1.0 + multiplier * amplifier;
        }

        return baseSpeed;
    }

    public static double getBySprinting() {
        return mc.thePlayer.isSprinting() ? 0.28700000047683716 : 0.22300000488758087;
    }


    public static double getBaseMotionY() {
        return isPotionActive(Potion.jump) ? 0.419999986886978 + 0.1 * (getActivePotionEffect(Potion.jump).getAmplifier() + 1) : 0.419999986886978;
    }




    public static double getBaseMoveSpeed() {
        double baseSpeed = mc.thePlayer.capabilities.getWalkSpeed() * 2.873;
        if (mc.thePlayer.isPotionActive(Potion.moveSlowdown)) {
            baseSpeed /= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1);
        }
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }

    public static void setSpeed(double moveSpeed, float yaw, double strafe, double forward) {
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += ((forward > 0.0D) ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += ((forward > 0.0D) ? 45 : -45);
            }
            strafe = 0.0D;
            if (forward > 0.0D) {
                forward = 1.0D;
            } else if (forward < 0.0D) {
                forward = -1.0D;
            }
        }
        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        mc.thePlayer.motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        mc.thePlayer.motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }

    public static void setSpeed(double moveSpeed) {
        setSpeed(moveSpeed, mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward);
    }

    public static void setSpeed(MoveEvent moveEvent, double moveSpeed, float yaw, double strafe, double forward) {
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += ((forward > 0.0D) ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += ((forward > 0.0D) ? 45 : -45);
            }
            strafe = 0.0D;
            if (forward > 0.0D) {
                forward = 1.0D;
            } else if (forward < 0.0D) {
                forward = -1.0D;
            }
        }
        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        moveEvent.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }

    public static void setSpeed(MoveEvent moveEvent, double moveSpeed) {
        setSpeed(moveEvent, moveSpeed, mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward);
    }


    public static boolean isMoving() {



        if(mc==null || mc.thePlayer==null || mc.thePlayer.movementInput==null){
            return false;
        }

        if (mc.thePlayer.movementInput.moveForward != 0f || mc.thePlayer.movementInput.moveStrafe != 0f) {
            return true;
        }
        return false;
    }

    public static double getBaseMoveSpeed(double value , double effect) {
        //0.2875
        double baseSpeed = value;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1 + effect * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }

    public static boolean isOnGround(double height) {
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
                mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
    }

    public static boolean isInLiquid(){
        return mc.thePlayer.isInWater() || mc.thePlayer.isInLava();
    }

    public static int getJumpEffect() {
        return mc.thePlayer.isPotionActive(Potion.jump) ? mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1 : 0;
    }

    public static boolean isSneaking() {

        if(mc==null || mc.thePlayer==null){
            return false;
        }

        if ((mc.thePlayer.isSneaking())) {
            return true;
        }
        return false;
    }

    public static BlockPos getCurrentFrontBlock(){
        BlockPos blockPos = mc.objectMouseOver.getBlockPos();
        return blockPos;
    }

//    public static void sendClick(int keycode){
//
//    }

    public static void sendMsgToSelf(String content){
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(content));
    }






}
