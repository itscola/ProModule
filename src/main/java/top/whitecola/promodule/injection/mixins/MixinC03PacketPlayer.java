package top.whitecola.promodule.injection.mixins;

import net.minecraft.network.play.client.C03PacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.injection.wrappers.IMixinC03PacketPlayer;

@Mixin(C03PacketPlayer.class)
public class MixinC03PacketPlayer implements IMixinC03PacketPlayer {
    @Shadow protected double x;


    @Shadow protected double y;

    @Shadow protected double z;

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void setZ(double z) {
        this.z = z;
    }
}
