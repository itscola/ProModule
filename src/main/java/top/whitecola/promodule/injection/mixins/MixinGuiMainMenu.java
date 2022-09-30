package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.whitecola.promodule.gui.components.clickables.buttons.LongRectButton;
import top.whitecola.promodule.utils.Common;
import top.whitecola.promodule.utils.Render2DUtils;

import java.util.Calendar;
import java.util.Date;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu extends GuiScreen {
    @Shadow
    private DynamicTexture viewportTexture;
    @Shadow
    private ResourceLocation backgroundTexture;

    @Shadow private String splashText;

//    @Shadow
//    private float updateCounter;
//
//    @Shadow
//    private String openGLWarning1;
//    @Shadow
//    private String openGLWarning2;
//    @Shadow
//    private String openGLWarningLink;

//    @Shadow protected abstract void addDemoButtons(int p_addDemoButtons_1_, int p_addDemoButtons_2_);

//    @Shadow protected abstract void addSingleplayerMultiplayerButtons(int p_addSingleplayerMultiplayerButtons_1_, int p_addSingleplayerMultiplayerButtons_2_);

//    @Shadow @Final private Object threadLock;
//    @Shadow private int field_92019_w;
//    @Shadow private int field_92023_s;
//    @Shadow private int field_92024_r;
//    @Shadow private int field_92022_t;
//    @Shadow private int field_92021_u;
//    @Shadow private int field_92020_v;
//    @Shadow private GuiScreen field_183503_M;
//    @Shadow private boolean field_183502_L;
//
//    @Shadow protected abstract boolean func_183501_a();
//
//    private boolean welcomed;


    @Mutable
    @Shadow @Final private static ResourceLocation minecraftTitleTextures;
    protected ResourceLocation background = new ResourceLocation("promodule","background/bg1.jpg");
    protected ResourceLocation mcLogo = new ResourceLocation("promodule","uis/minecraft.png");
    protected ResourceLocation logo = new ResourceLocation("promodule","uis/ProModule.png");

    /**
     * @author White_cola
     * @reason Change the background.
     */
    @Overwrite
    private void renderSkybox(int p_renderSkybox_1_, int p_renderSkybox_2_, float p_renderSkybox_3_) {
        Render2DUtils.drawCustomImage(0,0,width,height,background);
    }

    /**
     * @author White_cola
     * @reason Change the background.
     */
    @Overwrite
    public void initGui() {
        minecraftTitleTextures = mcLogo;
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        this.splashText = "";


        int j = this.height / 4 + 48;

        this.buttonList.add(new LongRectButton(1, this.width / 2 - 100, j, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new LongRectButton(2, this.width / 2 - 100, j + 24 * 1, I18n.format("menu.multiplayer", new Object[0])));

        this.buttonList.add(new LongRectButton(14, this.width / 2 + 2, j + 24 * 2, 98, 20, I18n.format("menu.online", new Object[0]).replace("Minecraft", "").trim()));
        this.buttonList.add(new LongRectButton(0, this.width / 2 - 100 -3, j + 72 + 12, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new LongRectButton(4, this.width / 2 + 2 +3, j + 72 + 12, 98, 20, I18n.format("menu.quit", new Object[0])));
        this.buttonList.add(new LongRectButton(6, this.width / 2 - 100, j + 24 * 2, 98, 20, I18n.format("fml.menu.mods", new Object[0])));

    }

    @Inject(method = "drawScreen", at = { @At("RETURN") }, cancellable = true)
    public void drawScren(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_, CallbackInfo ci){
        Common.drawLogo(width,logo);
    }

}
