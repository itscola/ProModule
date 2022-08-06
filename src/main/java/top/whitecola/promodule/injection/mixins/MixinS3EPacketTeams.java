package top.whitecola.promodule.injection.mixins;

import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S3EPacketTeams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(S3EPacketTeams.class)
public class MixinS3EPacketTeams {


    @Inject(method = "processPacket(Lnet/minecraft/network/play/INetHandlerPlayClient;)V", at = @At(value = "HEAD"), cancellable = true)
    public void processPacket(INetHandlerPlayClient p_processPacket_1_, CallbackInfo ci){
        if(p_processPacket_1_==null){
            ci.cancel();
            return;
        }
    }







}
