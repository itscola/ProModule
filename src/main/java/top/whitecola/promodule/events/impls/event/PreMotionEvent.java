package top.whitecola.promodule.events.impls.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import top.whitecola.promodule.utils.wrapper.RotationPitchHead;

public class PreMotionEvent extends AbstractEvent{
    public float yaw, pitch;
    private boolean ground;
    private double x, y, z;
    private boolean pre;


    public PreMotionEvent(float yaw,float pitch,boolean ground,double x,double y,double z){
        this.yaw = yaw;
        this.pitch = pitch;
        this.ground = ground;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean isPre() {
        return pre;
    }

    public void setPre(boolean pre) {
        this.pre = pre;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public boolean isGround() {
        return ground;
    }

    public void setGround(boolean ground) {
        this.ground = ground;
    }

    public void setYaw(final float yaw) {
        this.yaw = yaw;
        Minecraft.getMinecraft().thePlayer.rotationYawHead = yaw;
        Minecraft.getMinecraft().thePlayer.renderYawOffset = yaw;
    }

    public void setPitch(final float pitch) {
        this.pitch = pitch;
//        Minecraft.getMinecraft().thePlayer.rotationPitch = pitch;
        RotationPitchHead.rotationPitchHead = pitch;
    }
}
