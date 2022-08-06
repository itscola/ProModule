package top.whitecola.promodule.injection.mixins;

import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(S0CPacketSpawnPlayer.class)
public class MixinS0CPacketSpawnPlayer {
    @Inject(method = "processPacket", at = @At(value = "HEAD"), cancellable = true)
    public void processPacket(INetHandlerPlayClient p_processPacket_1_, CallbackInfo ci){
        if(p_processPacket_1_==null){
            ci.cancel();
            return;
        }
    }

}
