package top.whitecola.promodule.injection.wrappers;

import net.minecraft.util.ResourceLocation;

public interface IMxinEntityRenderer {
    void runSetupCameraTransform(float partialTicks, int pass);


    void setupCameraTransform1(float partialTicks, int pass);

    void loadShader2(ResourceLocation resourceLocationIn);
}
