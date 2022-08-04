package top.whitecola.promodule.injection.wrappers;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.spongepowered.asm.mixin.Mixin;

public interface IMixinS12PacketEntityVelocity {
    void setMotionX(int motionX);
    void setMotionY(int motionY);
    void setMotionZ(int motionZ);

}
