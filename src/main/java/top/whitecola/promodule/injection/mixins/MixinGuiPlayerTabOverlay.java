package top.whitecola.promodule.injection.mixins;

import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.modules.impls.other.HypixelPlus;

import static top.whitecola.promodule.utils.MCWrapper.*;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

@Mixin(GuiPlayerTabOverlay.class)
public abstract class MixinGuiPlayerTabOverlay extends Gui{
    @Shadow @Final private static Ordering<NetworkPlayerInfo> field_175252_a;

    @Shadow
    private IChatComponent footer;
    @Shadow
    private IChatComponent header;
    @Shadow
    private long lastTimeOpened;

    @Shadow
    private GuiIngame guiIngame;


    @Shadow public abstract String getPlayerName(NetworkPlayerInfo p_getPlayerName_1_);

    @Shadow protected abstract void drawScoreboardValues(ScoreObjective p_drawScoreboardValues_1_, int p_drawScoreboardValues_2_, String p_drawScoreboardValues_3_, int p_drawScoreboardValues_4_, int p_drawScoreboardValues_5_, NetworkPlayerInfo p_drawScoreboardValues_6_);

    /**
     * @author White_cola
     */
    @Overwrite
    public void renderPlayerlist(int p_renderPlayerlist_1_, Scoreboard p_renderPlayerlist_2_, ScoreObjective p_renderPlayerlist_3_) {

        if(System.currentTimeMillis() - this.lastTimeOpened < 1000){
            return;
        }


        NetHandlerPlayClient lvt_4_1_ = mc.thePlayer.sendQueue;
        List<NetworkPlayerInfo> lvt_5_1_ = this.field_175252_a.sortedCopy(lvt_4_1_.getPlayerInfoMap());
        int i = 0;
        int j = 0;
        Iterator lvt_8_1_ = lvt_5_1_.iterator();

        int j4;
        while(lvt_8_1_.hasNext()) {
            NetworkPlayerInfo lvt_9_1_ = (NetworkPlayerInfo)lvt_8_1_.next();
            j4 = mc.fontRendererObj.getStringWidth(this.getPlayerName(lvt_9_1_));
            i = Math.max(i, j4);
            if (p_renderPlayerlist_3_ != null && p_renderPlayerlist_3_.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                j4 = mc.fontRendererObj.getStringWidth(" " + p_renderPlayerlist_2_.getValueFromObjective(lvt_9_1_.getGameProfile().getName(), p_renderPlayerlist_3_).getScorePoints());
                j = Math.max(j, j4);
            }
        }

        lvt_5_1_ = lvt_5_1_.subList(0, Math.min(lvt_5_1_.size(), 80));
        int lvt_8_2_ = lvt_5_1_.size();
        int i4 = lvt_8_2_;

        for(j4 = 1; i4 > 20; i4 = (lvt_8_2_ + j4 - 1) / j4) {
            ++j4;
        }

        boolean lvt_11_1_ = mc.isIntegratedServerRunning() || mc.getNetHandler().getNetworkManager().getIsencrypted();
        int l;
        if (p_renderPlayerlist_3_ != null) {
            if (p_renderPlayerlist_3_.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                l = 90;
            } else {
                l = j;
            }
        } else {
            l = 0;
        }

        int lvt_13_1_ = Math.min(j4 * ((lvt_11_1_ ? 9 : 0) + i + l + 13), p_renderPlayerlist_1_ - 50) / j4;
        int lvt_14_1_ = p_renderPlayerlist_1_ / 2 - (lvt_13_1_ * j4 + (j4 - 1) * 5) / 2;
        int lvt_15_1_ = 10;
        int lvt_16_1_ = lvt_13_1_ * j4 + (j4 - 1) * 5;
        List<String> lvt_17_1_ = null;
        List<String> lvt_18_1_ = null;
        Iterator lvt_19_5_;
        String lvt_20_5_;
        if (this.header != null) {
            lvt_17_1_ = mc.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), p_renderPlayerlist_1_ - 50);

            for(lvt_19_5_ = lvt_17_1_.iterator(); lvt_19_5_.hasNext(); lvt_16_1_ = Math.max(lvt_16_1_, mc.fontRendererObj.getStringWidth(lvt_20_5_))) {
                lvt_20_5_ = (String)lvt_19_5_.next();
            }
        }

        if (this.footer != null) {
            lvt_18_1_ = mc.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), p_renderPlayerlist_1_ - 50);

            for(lvt_19_5_ = lvt_18_1_.iterator(); lvt_19_5_.hasNext(); lvt_16_1_ = Math.max(lvt_16_1_, mc.fontRendererObj.getStringWidth(lvt_20_5_))) {
                lvt_20_5_ = (String)lvt_19_5_.next();
            }
        }

        int i5;
        if (lvt_17_1_ != null) {
            drawRect(p_renderPlayerlist_1_ / 2 - lvt_16_1_ / 2 - 1, lvt_15_1_ - 1, p_renderPlayerlist_1_ / 2 + lvt_16_1_ / 2 + 1, lvt_15_1_ + lvt_17_1_.size() * mc.fontRendererObj.FONT_HEIGHT, -2147483648);

            for(lvt_19_5_ = lvt_17_1_.iterator(); lvt_19_5_.hasNext(); lvt_15_1_ += mc.fontRendererObj.FONT_HEIGHT) {
                lvt_20_5_ = (String)lvt_19_5_.next();


                i5 = mc.fontRendererObj.getStringWidth(lvt_20_5_);
                mc.fontRendererObj.drawStringWithShadow(lvt_20_5_, (float)(p_renderPlayerlist_1_ / 2 - i5 / 2), (float)lvt_15_1_, -1);
            }

            ++lvt_15_1_;
        }

        drawRect(p_renderPlayerlist_1_ / 2 - lvt_16_1_ / 2 - 1, lvt_15_1_ - 1, p_renderPlayerlist_1_ / 2 + lvt_16_1_ / 2 + 1, lvt_15_1_ + i4 * 9, -2147483648);

        for(int lvt_19_4_ = 0; lvt_19_4_ < lvt_8_2_; ++lvt_19_4_) {




            int l4 = lvt_19_4_ / i4;
            i5 = lvt_19_4_ % i4;
            int j2 = lvt_14_1_ + l4 * lvt_13_1_ + l4 * 5;
            int k2 = lvt_15_1_ + i5 * 9;
            drawRect(j2, k2, j2 + lvt_13_1_, k2 + 8, 553648127);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            if (lvt_19_4_ < lvt_5_1_.size()) {
                NetworkPlayerInfo lvt_24_1_ = (NetworkPlayerInfo)lvt_5_1_.get(lvt_19_4_);
                String lvt_25_1_;


                String playerName = this.getPlayerName(lvt_24_1_);
                String level = "";

                int ping = lvt_24_1_.getResponseTime();
                boolean canDisplayPing = !(ping==0||ping==1||ping==-1);
                String display = playerName;

                HypixelPlus hypixelPlus = (HypixelPlus)ProModule.getProModule().getModuleManager().getModuleByName("HypixelPlus");

                if(!canDisplayPing) {

                    if (hypixelPlus.levelHead && ProModule.getProModule().getHypixelConfig().config.key != null && !ProModule.getProModule().getHypixelConfig().config.key.equals("")) {
                        level = ProModule.getProModule().hypixelAPIWrapper.needPlayerLevel(lvt_24_1_);
                        display += EnumChatFormatting.BLUE+" ["+level+"]";
                    }else {
                        display += EnumChatFormatting.BLUE+" ["+"Null"+"]";

                    }
                }
                if(hypixelPlus.pings && canDisplayPing){
                    lvt_25_1_ = display +(EnumChatFormatting.LIGHT_PURPLE +" ["+lvt_24_1_.getResponseTime()+"ms]");
                }else {
                    lvt_25_1_ = display;
                }



                GameProfile lvt_26_1_ = lvt_24_1_.getGameProfile();
                if (lvt_11_1_) {
                    EntityPlayer lvt_27_1_ = mc.theWorld.getPlayerEntityByUUID(lvt_26_1_.getId());
                    boolean lvt_28_1_ = lvt_27_1_ != null && lvt_27_1_.isWearing(EnumPlayerModelParts.CAPE) && (lvt_26_1_.getName().equals("Dinnerbone") || lvt_26_1_.getName().equals("Grumm"));
                    mc.getTextureManager().bindTexture(lvt_24_1_.getLocationSkin());
                    int lvt_29_1_ = 8 + (lvt_28_1_ ? 8 : 0);
                    int lvt_30_1_ = 8 * (lvt_28_1_ ? -1 : 1);
                    Gui.drawScaledCustomSizeModalRect(j2, k2, 8.0F, (float)lvt_29_1_, 8, lvt_30_1_, 8, 8, 64.0F, 64.0F);
                    if (lvt_27_1_ != null && lvt_27_1_.isWearing(EnumPlayerModelParts.HAT)) {
                        int lvt_31_1_ = 8 + (lvt_28_1_ ? 8 : 0);
                        int lvt_32_1_ = 8 * (lvt_28_1_ ? -1 : 1);
                        Gui.drawScaledCustomSizeModalRect(j2, k2, 40.0F, (float)lvt_31_1_, 8, lvt_32_1_, 8, 8, 64.0F, 64.0F);
                    }

                    j2 += 9;
                }

                if (lvt_24_1_.getGameType() == WorldSettings.GameType.SPECTATOR) {
                    lvt_25_1_ = EnumChatFormatting.ITALIC + lvt_25_1_;
                    mc.fontRendererObj.drawStringWithShadow(lvt_25_1_, (float)j2, (float)k2, -1862270977);
                } else {
                    mc.fontRendererObj.drawStringWithShadow(lvt_25_1_, (float)j2, (float)k2, -1);
                }

                if (p_renderPlayerlist_3_ != null && lvt_24_1_.getGameType() != WorldSettings.GameType.SPECTATOR) {
                    int lvt_27_2_ = j2 + i + 1;
                    int lvt_28_2_ = lvt_27_2_ + l;
                    if (lvt_28_2_ - lvt_27_2_ > 5) {
                        this.drawScoreboardValues(p_renderPlayerlist_3_, k2, lvt_26_1_.getName(), lvt_27_2_, lvt_28_2_, lvt_24_1_);
                    }


                }





            }
        }

        if (lvt_18_1_ != null) {
            lvt_15_1_ += i4 * 9 + 1;
            drawRect(p_renderPlayerlist_1_ / 2 - lvt_16_1_ / 2 - 1, lvt_15_1_ - 1, p_renderPlayerlist_1_ / 2 + lvt_16_1_ / 2 + 1, lvt_15_1_ + lvt_18_1_.size() * mc.fontRendererObj.FONT_HEIGHT, -2147483648);

            for(lvt_19_5_ = lvt_18_1_.iterator(); lvt_19_5_.hasNext(); lvt_15_1_ += mc.fontRendererObj.FONT_HEIGHT) {
                lvt_20_5_ = (String)lvt_19_5_.next();
                i5 = mc.fontRendererObj.getStringWidth(lvt_20_5_);
                mc.fontRendererObj.drawStringWithShadow(lvt_20_5_, (float)(p_renderPlayerlist_1_ / 2 - i5 / 2 -8), (float)lvt_15_1_, -1);
            }
        }

    }









}
