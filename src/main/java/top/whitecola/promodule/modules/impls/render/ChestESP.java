package top.whitecola.promodule.modules.impls.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import top.whitecola.animationlib.Animation;
import top.whitecola.animationlib.functions.type.CubicInOutFunction;
import top.whitecola.promodule.events.impls.event.RenderChestEvent;
import top.whitecola.promodule.injection.wrappers.IMixinRenderManager;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.*;

import java.awt.*;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Vector;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUniform1;

import static top.whitecola.promodule.utils.MCWrapper.mc;

public class ChestESP extends AbstractModule {

    //    public Vector<BlockPos> chestBlocks = new Vector<BlockPos>();
    protected Color color = new Color(246, 240, 240);

    public Framebuffer framebuffer;
    public Framebuffer outlineFrameBuffer;

    public Framebuffer glowFrameBuffer;
    private final ShaderUtil chamsShader = new ShaderUtil("chams");
    private final ShaderUtil glowShader = new ShaderUtil("glow");
    private final ShaderUtil outlineShader = new ShaderUtil("promodule/shaders/outline.frag");
    private final ArrayList<Entity> entities = new ArrayList<Entity>();
    private static final ArrayList<Framebuffer> framebufferList = new ArrayList<>();
    private Animation fadeIn = new Animation().setMin(0).setMax(1).setTotalTime(250).setFunction(new CubicInOutFunction());

    public ChestESP(){

    }


    public void createFrameBuffers() {
        framebuffer = Render3DUtils.createFrameBuffer(framebuffer);
        outlineFrameBuffer = Render3DUtils.createFrameBuffer(outlineFrameBuffer);
    }

    public void collectEntities() {
        entities.clear();
//        for (Entity entity : mc.theWorld.getLoadedEntityList()) {
//            if (!ESPUtils.isInView(entity)) continue;
//            if (entity == mc.thePlayer && mc.gameSettings.thirdPersonView == 0) continue;
//            if (entity instanceof EntityAnimal && validEntities.getSetting("animals").isEnabled()) {
//                entities.add(entity);
//            }
//
//            if (entity instanceof EntityPlayer && validEntities.getSetting("players").isEnabled()) {
//                entities.add(entity);
//            }
//
//            if (entity instanceof EntityMob && validEntities.getSetting("mobs").isEnabled()) {
//                entities.add(entity);
//            }
//        }
    }



    @Override
    public void onRenderChest(RenderChestEvent e) {

        framebuffer.bindFramebuffer(false);
        chamsShader.init();
        chamsShader.setUniformi("textureIn", 0);
        Color color = this.color;

        Render3DUtils.resetColor();
        chamsShader.setUniformf("color", color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1);
        e.drawChest();
        chamsShader.unload();

        mc.getFramebuffer().bindFramebuffer(false);
        super.onRenderChest(e);
    }

    @Override
    public void onRender2D(float partialTicks) {
        createFrameBuffers();
        collectEntities();

        ScaledResolution sr = new ScaledResolution(mc);
        if (framebuffer != null && outlineFrameBuffer != null ) {
            Render3DUtils.setAlphaLimit(0);
            GLUtils.startBlend();

    /*        RenderUtil.bindTexture(framebuffer.framebufferTexture);
            ShaderUtil.drawQuads();
            framebuffer.framebufferClear();
            mc.getFramebuffer().bindFramebuffer(false);
            if(true) return;*/


            outlineFrameBuffer.framebufferClear();
            outlineFrameBuffer.bindFramebuffer(false);
            outlineShader.init();
            setupOutlineUniforms(0, 1);
            Render3DUtils.bindTexture(framebuffer.framebufferTexture);
            ShaderUtil.drawQuads();
            outlineShader.init();
            setupOutlineUniforms(1, 0);
            Render3DUtils.bindTexture(framebuffer.framebufferTexture);
            ShaderUtil.drawQuads();
            outlineShader.unload();
            outlineFrameBuffer.unbindFramebuffer();


//            if (kawaseGlow.isEnabled()) {
//                int offset = offsetSetting.getValue().intValue();
//                int iterations = 3;
//
//                if (framebufferList.isEmpty() || currentIterations != iterations || (framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight)) {
//                    initFramebuffers(iterations);
//                    currentIterations = iterations;
//                }
//                RenderUtil.setAlphaLimit(0);
//
//                glBlendFunc(GL_ONE, GL_ONE);
//
//                GL11.glClearColor(0, 0, 0, 0);
//                renderFBO(framebufferList.get(1), outlineFrameBuffer.framebufferTexture, kawaseGlowShader, offset);
//
//                //Downsample
//                for (int i = 1; i < iterations; i++) {
//                    renderFBO(framebufferList.get(i + 1), framebufferList.get(i).framebufferTexture, kawaseGlowShader, offset);
//                }
//
//                //Upsample
//                for (int i = iterations; i > 1; i--) {
//                    renderFBO(framebufferList.get(i - 1), framebufferList.get(i).framebufferTexture, kawaseGlowShader2, offset);
//                }
//
//                Framebuffer lastBuffer = framebufferList.get(0);
//                lastBuffer.framebufferClear();
//                lastBuffer.bindFramebuffer(false);
//                kawaseGlowShader2.init();
//                kawaseGlowShader2.setUniformf("offset", offset, offset);
//                kawaseGlowShader2.setUniformi("inTexture", 0);
//                kawaseGlowShader2.setUniformi("check", seperate.isEnabled() ? 1 : 0);
//                kawaseGlowShader2.setUniformf("lastPass", 1);
//                kawaseGlowShader2.setUniformf("exposure", (float) (exposure.getValue().floatValue() * fadeIn.getOutput().floatValue()));
//                kawaseGlowShader2.setUniformi("textureToCheck", 16);
//                kawaseGlowShader2.setUniformf("halfpixel", 1.0f / lastBuffer.framebufferWidth, 1.0f / lastBuffer.framebufferHeight);
//                kawaseGlowShader2.setUniformf("iResolution", lastBuffer.framebufferWidth, lastBuffer.framebufferHeight);
//                GL13.glActiveTexture(GL13.GL_TEXTURE16);
//                RenderUtil.bindTexture(framebuffer.framebufferTexture);
//                GL13.glActiveTexture(GL13.GL_TEXTURE0);
//                RenderUtil.bindTexture(framebufferList.get(1).framebufferTexture);
//
//                ShaderUtil.drawQuads();
//                kawaseGlowShader2.unload();
//
//                GL11.glClearColor(0, 0, 0, 0);
//                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//                framebuffer.framebufferClear();
//                RenderUtil.resetColor();
//                mc.getFramebuffer().bindFramebuffer(true);
//                RenderUtil.bindTexture(framebufferList.get(0).framebufferTexture);
//                ShaderUtil.drawQuads();
//                RenderUtil.setAlphaLimit(0);
//                GlStateManager.bindTexture(0);
//            } else {
            if (!framebufferList.isEmpty()) {
                for (Framebuffer framebuffer : framebufferList) {
                    framebuffer.deleteFramebuffer();
                }
                glowFrameBuffer = null;
                framebufferList.clear();
            }

            glowFrameBuffer = Render3DUtils.createFrameBuffer(glowFrameBuffer);

            GL11.glClearColor(0, 0, 0, 0);
            glowFrameBuffer.framebufferClear();
            glowFrameBuffer.bindFramebuffer(false);
            glowShader.init();
            setupGlowUniforms(1f, 0);
            Render3DUtils.bindTexture(outlineFrameBuffer.framebufferTexture);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            ShaderUtil.drawQuads();
            glowShader.unload();

            mc.getFramebuffer().bindFramebuffer(false);

            GL11.glClearColor(0, 0, 0, 0);
            glowShader.init();
            setupGlowUniforms(0, 1f);
//            if (seperate.isEnabled()) {
//                GL13.glActiveTexture(GL13.GL_TEXTURE16);
//                RenderUtil.bindTexture(framebuffer.framebufferTexture);
//            }
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            Render3DUtils.bindTexture(glowFrameBuffer.framebufferTexture);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            ShaderUtil.drawQuads();
            glowShader.unload();

            framebuffer.framebufferClear();
            mc.getFramebuffer().bindFramebuffer(false);


//            }
        }
        super.onRender2D(partialTicks);
    }



    public void setupGlowUniforms(float dir1, float dir2) {
        glowShader.setUniformi("texture", 0);

        glowShader.setUniformf("radius", 4);
        glowShader.setUniformf("texelSize", 1.0f / mc.displayWidth, 1.0f / mc.displayHeight);
        glowShader.setUniformf("direction", dir1, dir2);
        glowShader.setUniformf("exposure", (float) (2.2 * fadeIn.update()));
        glowShader.setUniformi("avoidTexture",  0);

        final FloatBuffer buffer = BufferUtils.createFloatBuffer(256);
        for (int i = 1; i <= 4; i++) {
            buffer.put(MathUtils.calculateGaussianValue(i, 4 / 2));
        }
        buffer.rewind();

        glUniform1(glowShader.getUniform("weights"), buffer);
    }

    //
//    @Override
//    public void onRender3D(int pass, float partialTicks, long finishTimeNano) {
//        try {
//            for(BlockPos blockPos : chestBlocks){
//                Render3DUtils.drawSolidBlockESP(
//                        blockPos.getX() - ((IMixinRenderManager) mc.getRenderManager()).getRenderPosX(),
//                        blockPos.getY() - ((IMixinRenderManager) mc.getRenderManager()).getRenderPosY(),
//                        blockPos.getZ()- ((IMixinRenderManager) mc.getRenderManager()).getRenderPosZ(),
//                        color.getRed(),color.getGreen(),color.getBlue(),0.2f
//                );
//
//            }
//        }catch (Throwable e){
//
//        }
//
//        super.onRender3D(pass, partialTicks, finishTimeNano);
//    }
//
//    @Override
//    public void onRenderBlock(int x, int y, int z, Block block) {
//        if(block instanceof BlockChest){
//            BlockPos pos = new BlockPos(x,y,z);
//            if(!chestBlocks.contains(pos)){
//                this.chestBlocks.add(pos);
//            }
//        }
//        super.onRenderBlock(x, y, z, block);
//    }
//
//    @Override
//    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
//        if(e.entity instanceof EntityPlayerSP){
//            chestBlocks.clear();
//        }
//        super.onEntityJoinWorld(e);
//    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.RENDERS;
    }

    @Override
    public String getModuleName() {
        return "ChestESP";
    }

//    @Override
//    public void onEnable() {
////        mc.renderGlobal.loadRenderers();
//        chestBlocks.clear();
//        super.onEnable();
//    }



    //    private Color getColor(Object entity) {
//        Color color = Color.WHITE;
//        if (entity instanceof EntityPlayer) {
//            color = playerColor.getColor();
//        }
//        if (entity instanceof EntityMob) {
//            color = mobColor.getColor();
//        }
//        if (entity instanceof EntityAnimal) {
//            color = animalColor.getColor();
//        }
//        if (entity instanceof TileEntityChest) {
//            color = chestColor.getColor();
//        }
//
//
//    }
    public void setupOutlineUniforms(float dir1, float dir2) {
        outlineShader.setUniformi("textureIn", 0);
        float iterations =4 * 1.5f;
        outlineShader.setUniformf("radius", iterations);
        outlineShader.setUniformf("texelSize", 1.0f / mc.displayWidth, 1.0f / mc.displayHeight);
        outlineShader.setUniformf("direction", dir1, dir2);
    }
}
