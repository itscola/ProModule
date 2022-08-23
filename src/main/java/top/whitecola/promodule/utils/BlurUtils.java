package top.whitecola.promodule.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import top.whitecola.promodule.injection.wrappers.IMixinMinecraft;
import top.whitecola.promodule.injection.wrappers.IMixinShaderGroup;

public class BlurUtils {
    private static ShaderGroup blurShader;
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static Framebuffer buffer;
    private static int lastScale;
    private static int lastScaleWidth;
    private static int lastScaleHeight;
    private static final ResourceLocation shader = new ResourceLocation("shaders/post/blur.json");


    public static void doBlur(float intensity) {
        GL11.glPushMatrix();

        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null
                || blurShader == null) {
            initFboAndShader();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        setShaderConfigs(intensity, 0, 1);
        buffer.bindFramebuffer(true);
        blurShader.loadShaderGroup(((IMixinMinecraft)mc).getTimer().renderPartialTicks);
        mc.getFramebuffer().bindFramebuffer(true);

        GL11.glPopMatrix();
    }

    public static void doBlur(float x, float y, float width, float height, float intensity, float blurWidth, float blurHeight) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null || blurShader == null) {
            initFboAndShader();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        GL11.glScissor((int)(x * factor), (int)((mc.displayHeight - (y * factor) - height * factor)) +1, (int)(width * factor), (int)(height * factor));
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        setShaderConfigs(intensity, blurWidth, blurHeight);
        buffer.bindFramebuffer(true);
        blurShader.loadShaderGroup(((IMixinMinecraft)mc).getTimer().renderPartialTicks);
        mc.getFramebuffer().bindFramebuffer(true);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);


    }

    public static void initFboAndShader() {
        try {
            blurShader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), shader);
            blurShader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
            buffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
            buffer.setFramebufferColor(1,1,1,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setShaderConfigs(float intensity, float blurWidth, float blurHeight) {
        ((Shader) ((IMixinShaderGroup)blurShader).getShaders().get(0)).getShaderManager().getShaderUniform("Radius").set(intensity);
        ((Shader) ((IMixinShaderGroup)blurShader).getShaders().get(1)).getShaderManager().getShaderUniform("Radius").set(intensity);
        ((Shader) ((IMixinShaderGroup)blurShader).getShaders().get(0)).getShaderManager().getShaderUniform("BlurDir").set(blurWidth, blurHeight);
        ((Shader) ((IMixinShaderGroup)blurShader).getShaders().get(1)).getShaderManager().getShaderUniform("BlurDir").set(blurHeight, blurWidth);
    }

}
