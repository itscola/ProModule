package top.whitecola.promodule.injection.mixins;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.injection.wrappers.IMixinS12PacketEntityVelocity;

@Mixin(S12PacketEntityVelocity.class)
public class MixinS12PacketEntityVelocity implements IMixinS12PacketEntityVelocity {
    @Shadow
    private int motionX;

    @Shadow
    private int motionY;

    @Shadow
    private int motionZ;


    @Override
    public void setMotionX(int motionX) {

        this.motionX = motionX;
    }

    @Override
    public void setMotionY(int motionY) {
        this.motionY = motionY;
    }

    @Override
    public void setMotionZ(int motionZ) {
        this.motionZ = motionZ;
    }
}
