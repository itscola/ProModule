package top.whitecola.promodule.utils;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Timer;
import top.whitecola.promodule.injection.wrappers.IMixinMinecraft;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class AimUtils {
    public static float[] getRotationsDelta(EntityLivingBase entity) {
        if (entity == null)
            return null;

        EntityLivingBase entityLivingBase = entity;

        float renderPartialTicks = ((IMixinMinecraft)mc).getTimer().renderPartialTicks;


        RenderManager renderManager = mc.getRenderManager();

        double diffX = (entityLivingBase.lastTickPosX + (entityLivingBase.posX - entityLivingBase.lastTickPosX) * renderPartialTicks) - renderManager.viewerPosX,
                diffZ = (entityLivingBase.lastTickPosZ + (entityLivingBase.posZ - entityLivingBase.lastTickPosZ) * renderPartialTicks) - renderManager.viewerPosZ,
                diffY = ((entityLivingBase.lastTickPosY + (entityLivingBase.posY - entityLivingBase.lastTickPosY) * renderPartialTicks) + entityLivingBase.getEyeHeight()) - (renderManager.viewerPosY + mc.thePlayer.getEyeHeight()),
                dist = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) (Math.atan2(diffZ, diffX) * 180d / Math.PI) - 90f,
                pitch = (float) -(Math.atan2(diffY, dist) * 180d / Math.PI);

        return new float[]{
                wrapAngleTo180_float(mc.thePlayer.rotationYaw - yaw),
                wrapAngleTo180_float(mc.thePlayer.rotationPitch - pitch)
        };



    }

    public static float wrapAngleTo180_float(float n) {
        n %= 360.0F;

        if (n >= 180.0F) {
            n -= 360.0F;
        }

        if (n < -180.0F) {
            n += 360.0F;
        }

        return n;
    }
}
