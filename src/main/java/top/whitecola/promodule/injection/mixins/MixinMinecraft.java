package top.whitecola.promodule.injection.mixins;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.*;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.SplashProgress;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ProgressManager;
import org.apache.commons.io.IOUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.opengl.PixelFormat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.events.EventManager;
import top.whitecola.promodule.gui.screens.MainClickGUIInGameNoFont;
import top.whitecola.promodule.injection.wrappers.IMixinMinecraft;
import top.whitecola.promodule.modules.impls.combat.NoClickDelay;
import top.whitecola.promodule.utils.Render2DUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Logger;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IMixinMinecraft {
    @Shadow private int rightClickDelayTimer;

    @Shadow private Timer timer;

    @Shadow private int leftClickCounter;

    @Shadow private boolean fullscreen;

    @Shadow protected abstract void updateDisplayMode() throws LWJGLException;

    @Shadow @Final public DefaultResourcePack mcDefaultResourcePack;

    @Shadow private ResourceLocation mojangLogo;

    @Shadow public int displayHeight;

    @Shadow public abstract void func_181536_a(int p_181536_1_, int p_181536_2_, int p_181536_3_, int p_181536_4_, int p_181536_5_, int p_181536_6_, int p_181536_7_, int p_181536_8_, int p_181536_9_, int p_181536_10_);

    @Shadow public abstract void updateDisplay();

    @Shadow public int displayWidth;

    @Shadow @Final private static ResourceLocation locationMojangPng;


    @Shadow public GameSettings gameSettings;
    @Shadow @Final public File mcDataDir;
    @Shadow @Final private List<IResourcePack> defaultResourcePacks;

    @Shadow protected abstract void startTimerHackThread();

    @Shadow protected abstract void setWindowIcon();

    @Shadow protected abstract void setInitialDisplayMode() throws LWJGLException;

    @Shadow private Framebuffer framebufferMc;

    @Shadow protected abstract void registerMetadataSerializers();

    @Shadow private ResourcePackRepository mcResourcePackRepository;
    @Shadow private IReloadableResourceManager mcResourceManager;
    @Shadow private LanguageManager mcLanguageManager;
    @Shadow @Final private IMetadataSerializer metadataSerializer_;
    @Shadow public TextureManager renderEngine;

    @Shadow protected abstract void initStream();

    @Shadow private SkinManager skinManager;
    @Shadow private ISaveFormat saveLoader;
    @Shadow private SoundHandler mcSoundHandler;
    @Shadow public GuiScreen currentScreen;
    @Shadow public WorldClient theWorld;
    @Shadow public EntityPlayerSP thePlayer;
    @Shadow public GuiIngame ingameGUI;

    @Shadow public abstract void setIngameFocus();

    @Shadow public abstract void setIngameNotInFocus();

    @Shadow public boolean skipRenderWorld;
    protected ResourceLocation background = new ResourceLocation("promodule","uis/tbg.png");


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


    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;sendClickBlockToController()V", shift = At.Shift.BEFORE))
    public void runTickSendClickBlockToController(CallbackInfo ci){
        EventManager.getEventManager().BlockPlaceableEvent();
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


    /**
     * @author White_cola
     * @reason splash
     */
    @Overwrite
    public void drawSplashScreen(TextureManager p_drawSplashScreen_1_) throws LWJGLException {
//        ScaledResolution scaledresolution = new ScaledResolution((Minecraft) ((Object)this));
//        int i = scaledresolution.getScaleFactor();
//        Framebuffer framebuffer = new Framebuffer(scaledresolution.getScaledWidth() * i, scaledresolution.getScaledHeight() * i, true);
//        framebuffer.bindFramebuffer(false);
//        GlStateManager.matrixMode(5889);
//        GlStateManager.loadIdentity();
//        GlStateManager.ortho(0.0D, (double)scaledresolution.getScaledWidth(), (double)scaledresolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
//        GlStateManager.matrixMode(5888);
//        GlStateManager.loadIdentity();
//        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
//        GlStateManager.disableLighting();
//        GlStateManager.disableFog();
//        GlStateManager.disableDepth();
//        GlStateManager.enableTexture2D();
//        InputStream inputstream = null;
//
//        try {
//            inputstream = this.mcDefaultResourcePack.getInputStream(locationMojangPng);
//            this.mojangLogo = p_drawSplashScreen_1_.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read(inputstream)));
//            p_drawSplashScreen_1_.bindTexture(this.mojangLogo);
//        } catch (IOException var12) {
//        } finally {
//            IOUtils.closeQuietly(inputstream);
//        }
//
//        Tessellator tessellator = Tessellator.getInstance();
//        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
//        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
//        worldrenderer.pos(0.0D, (double)this.displayHeight, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
//        worldrenderer.pos((double)this.displayWidth, (double)this.displayHeight, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
//        worldrenderer.pos((double)this.displayWidth, 0.0D, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
//        worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
//        tessellator.draw();
//        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//        int j = 256;
//        int k = 256;
//        this.func_181536_a((scaledresolution.getScaledWidth() - j) / 2, (scaledresolution.getScaledHeight() - k) / 2, 0, 0, j, k, 255, 255, 255, 255);
//        GlStateManager.disableLighting();
//        GlStateManager.disableFog();
//        framebuffer.unbindFramebuffer();
//        framebuffer.framebufferRender(scaledresolution.getScaledWidth() * i, scaledresolution.getScaledHeight() * i);
//        GlStateManager.enableAlpha();
//        GlStateManager.alphaFunc(516, 0.1F);
//        System.out.println(1111111111111111L);
        Render2DUtils.drawCustomImage(0,0,this.displayWidth,this.displayHeight,background);

//        Render2DUtils.drawFullscreenImage(background);
//        Gui
        this.updateDisplay();
    }


    @Inject(method = "startGame", at = { @At("HEAD") }, cancellable = true)
    public void startGame(CallbackInfo ci){
//        SplashProgress.start();
    }




    /**
     * @author White_cola
     * @reason for some GUI close animation.
     */
    @Overwrite
    public void displayGuiScreen(GuiScreen p_displayGuiScreen_1_) {

        GuiScreen old = this.currentScreen;

        // GUIHandler here later
        if(old instanceof MainClickGUIInGameNoFont){
            MainClickGUIInGameNoFont mainClickUIIngame = (MainClickGUIInGameNoFont) old;
            if(!mainClickUIIngame.isClosed()){
                mainClickUIIngame.setNeedClose(true);
                return;
            }
        }


        if (p_displayGuiScreen_1_ == null && this.theWorld == null) {
            p_displayGuiScreen_1_ = new GuiMainMenu();
        } else if (p_displayGuiScreen_1_ == null && this.thePlayer.getHealth() <= 0.0F) {
            p_displayGuiScreen_1_ = new GuiGameOver();
        }


        GuiOpenEvent event = new GuiOpenEvent((GuiScreen)p_displayGuiScreen_1_);
        if (!MinecraftForge.EVENT_BUS.post(event)) {
            p_displayGuiScreen_1_ = event.gui;
            if (old != null && p_displayGuiScreen_1_ != old) {
                old.onGuiClosed();
            }

            if (p_displayGuiScreen_1_ instanceof GuiMainMenu) {
                this.gameSettings.showDebugInfo = false;
                this.ingameGUI.getChatGUI().clearChatMessages();
            }


            this.currentScreen = p_displayGuiScreen_1_;
            if (p_displayGuiScreen_1_ != null) {
                this.setIngameNotInFocus();
                ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
                int i = scaledresolution.getScaledWidth();
                int j = scaledresolution.getScaledHeight();
                p_displayGuiScreen_1_.setWorldAndResolution(Minecraft.getMinecraft(), i, j);
                this.skipRenderWorld = false;
            } else {




                this.mcSoundHandler.resumeSounds();
                this.setIngameFocus();
            }

        }
    }


//    /**
//     * @author White_cola
//     * @reason a
//     */
//    @Overwrite
//    private void startGame() throws LWJGLException, IOException {
//        this.gameSettings = new GameSettings(this, this.mcDataDir);
//        this.defaultResourcePacks.add(this.mcDefaultResourcePack);
//        this.startTimerHackThread();
//        if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
//            this.displayWidth = this.gameSettings.overrideWidth;
//            this.displayHeight = this.gameSettings.overrideHeight;
//        }
//
//        this.setWindowIcon();
//        this.setInitialDisplayMode();
//        this.createDisplay();
//        OpenGlHelper.initializeTextures();
//        this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, true);
//        this.framebufferMc.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
//        this.registerMetadataSerializers();
//        this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings);
//        this.mcResourceManager = new SimpleReloadableResourceManager(this.metadataSerializer_);
//        this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.language);
//        this.mcResourceManager.registerReloadListener(this.mcLanguageManager);
//        FMLClientHandler.instance().beginMinecraftLoading(this, this.defaultResourcePacks, this.mcResourceManager);
//        this.renderEngine = new TextureManager(this.mcResourceManager);
//        this.mcResourceManager.registerReloadListener(this.renderEngine);
//        SplashProgress.drawVanillaScreen(this.renderEngine);
//        this.initStream();
//        this.skinManager = new SkinManager(this.renderEngine, new File(this.fileAssets, "skins"), this.sessionService);
//        this.saveLoader = new AnvilSaveConverter(new File(this.mcDataDir, "saves"));
//        this.mcSoundHandler = new SoundHandler(this.mcResourceManager, this.gameSettings);
//        this.mcResourceManager.registerReloadListener(this.mcSoundHandler);
//        this.mcMusicTicker = new MusicTicker(this);
//        this.fontRendererObj = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);
//        if (this.gameSettings.language != null) {
//            this.fontRendererObj.setUnicodeFlag(this.isUnicode());
//            this.fontRendererObj.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
//        }
//
//        this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
//        this.mcResourceManager.registerReloadListener(this.fontRendererObj);
//        this.mcResourceManager.registerReloadListener(this.standardGalacticFontRenderer);
//        this.mcResourceManager.registerReloadListener(new GrassColorReloadListener());
//        this.mcResourceManager.registerReloadListener(new FoliageColorReloadListener());
//        AchievementList.openInventory.setStatStringFormatter(new IStatStringFormat() {
//            public String formatString(String p_formatString_1_) {
//                try {
//                    return String.format(p_formatString_1_, GameSettings.getKeyDisplayString(Minecraft.this.gameSettings.keyBindInventory.getKeyCode()));
//                } catch (Exception var3) {
//                    return "Error: " + var3.getLocalizedMessage();
//                }
//            }
//        });
//        this.mouseHelper = new MouseHelper();
//        ProgressManager.ProgressBar bar = ProgressManager.push("Rendering Setup", 5, true);
//        bar.step("GL Setup");
//        this.checkGLError("Pre startup");
//        GlStateManager.enableTexture2D();
//        GlStateManager.shadeModel(7425);
//        GlStateManager.clearDepth(1.0D);
//        GlStateManager.enableDepth();
//        GlStateManager.depthFunc(515);
//        GlStateManager.enableAlpha();
//        GlStateManager.alphaFunc(516, 0.1F);
//        GlStateManager.cullFace(1029);
//        GlStateManager.matrixMode(5889);
//        GlStateManager.loadIdentity();
//        GlStateManager.matrixMode(5888);
//        this.checkGLError("Startup");
//        bar.step("Loading Texture Map");
//        this.textureMapBlocks = new TextureMap("textures", true);
//        this.textureMapBlocks.setMipmapLevels(this.gameSettings.mipmapLevels);
//        this.renderEngine.loadTickableTexture(TextureMap.locationBlocksTexture, this.textureMapBlocks);
//        this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
//        this.textureMapBlocks.setBlurMipmapDirect(false, this.gameSettings.mipmapLevels > 0);
//        bar.step("Loading Model Manager");
//        this.modelManager = new ModelManager(this.textureMapBlocks);
//        this.mcResourceManager.registerReloadListener(this.modelManager);
//        bar.step("Loading Item Renderer");
//        this.renderItem = new RenderItem(this.renderEngine, this.modelManager);
//        this.renderManager = new RenderManager(this.renderEngine, this.renderItem);
//        this.itemRenderer = new ItemRenderer(this);
//        this.mcResourceManager.registerReloadListener(this.renderItem);
//        bar.step("Loading Entity Renderer");
//        this.entityRenderer = new EntityRenderer(this, this.mcResourceManager);
//        this.mcResourceManager.registerReloadListener(this.entityRenderer);
//        this.blockRenderDispatcher = new BlockRendererDispatcher(this.modelManager.getBlockModelShapes(), this.gameSettings);
//        this.mcResourceManager.registerReloadListener(this.blockRenderDispatcher);
//        this.renderGlobal = new RenderGlobal(this);
//        this.mcResourceManager.registerReloadListener(this.renderGlobal);
//        this.guiAchievement = new GuiAchievement(this);
//        GlStateManager.viewport(0, 0, this.displayWidth, this.displayHeight);
//        this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);
//        ProgressManager.pop(bar);
//        FMLClientHandler.instance().finishMinecraftLoading();
//        this.checkGLError("Post startup");
//        this.ingameGUI = new GuiIngameForge(this);
//        if (this.serverName != null) {
//            FMLClientHandler.instance().connectToServerAtStartup(this.serverName, this.serverPort);
//        } else {
//            this.displayGuiScreen(new GuiMainMenu());
//        }
//
//        SplashProgress.clearVanillaResources(this.renderEngine, this.mojangLogo);
//        this.mojangLogo = null;
//        this.loadingScreen = new LoadingScreenRenderer(this);
//        FMLClientHandler.instance().onInitializationComplete();
//        if (this.gameSettings.fullScreen && !this.fullscreen) {
//            this.toggleFullscreen();
//        }
//
//        try {
//            Display.setVSyncEnabled(this.gameSettings.enableVsync);
//        } catch (OpenGLException var3) {
//            this.gameSettings.enableVsync = false;
//            this.gameSettings.saveOptions();
//        }
//
//        this.renderGlobal.makeEntityOutlineShader();
//    }
}
