package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.injection.wrappers.IMixinShaderGroup;

import java.util.List;

@Mixin(ShaderGroup.class)
public class MixinShaderGroup implements IMixinShaderGroup {
    @Shadow
    @Final
    private List<Shader> listShaders;

    @Override
    public List getShaders() {
        return this.listShaders;
    }
}
