package top.whitecola.promodule.injection.mixins;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.injection.wrappers.IMixinMinecraft;
import top.whitecola.promodule.modules.impls.combat.NoClickDelay;

import java.util.List;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IMixinMinecraft {
    @Shadow private int rightClickDelayTimer;

    @Shadow private Timer timer;

    @Shadow private int leftClickCounter;

    @Shadow private boolean fullscreen;

    @Shadow protected abstract void updateDisplayMode() throws LWJGLException;

    @Override
    public Timer getTimer() {
        return timer;
    }

    @Override
    public void setRightClickDelayTimer(int value) {
        this.rightClickDelayTimer = value;
    }

    @Override
    public int getRightClickDelayTimer() {
        return this.rightClickDelayTimer;
    }

    @Inject(method = "clickMouse", at = { @At("HEAD") }, cancellable = true)
    private void clickMouse(CallbackInfo ci){
        NoClickDelay noClickDelay = (NoClickDelay) ProModule.getProModule().getModuleManager().getModuleByName("NoClickDelay");
        if(noClickDelay!=null&noClickDelay.isEnabled()){
            this.leftClickCounter = 0;
        }
    }


    /**
     * @author White_cola
     * @reason title
     */
    @Overwrite
    private void createDisplay() throws LWJGLException {
        Display.setResizable(true);
        Display.setTitle("ProModule Ver. 1.1 [1.8.9] - Powered by White_cola");

        try {
            Display.create((new PixelFormat()).withDepthBits(24));
        } catch (LWJGLException var4) {

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var3) {
            }

            if (this.fullscreen) {
                this.updateDisplayMode();
            }


            Display.create();
        }

    }




}
