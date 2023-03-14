package top.whitecola.promodule.injection.mixins;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.events.EventManager;
import top.whitecola.promodule.fonts.font2.FontLoaders;
import top.whitecola.promodule.injection.wrappers.HasNameTag;
import top.whitecola.promodule.modules.impls.other.ScoreBoardGUI;
import top.whitecola.promodule.utils.Render2DUtils;

import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Mixin(GuiIngame.class)
public abstract class MixinGuiInGame {
    @Shadow public abstract FontRenderer getFontRenderer();

    private HasNameTag hasNameTag = new HasNameTag();
    private Color color = new Color(1,1,1, 34);

    /**
     * @author White_cola
     * @reason ScoreBoardGUI module.
     */
    @Overwrite
    protected void renderScoreboard(ScoreObjective p_renderScoreboard_1_, ScaledResolution p_renderScoreboard_2_) {
        Scoreboard lvt_3_1_ = p_renderScoreboard_1_.getScoreboard();
        Collection<Score> lvt_4_1_ = lvt_3_1_.getSortedScores(p_renderScoreboard_1_);
        List<Score> lvt_5_1_ = Lists.newArrayList(Iterables.filter(lvt_4_1_, hasNameTag));
        if (lvt_5_1_.size() > 15) {
            lvt_4_1_ = Lists.newArrayList(Iterables.skip(lvt_5_1_, lvt_4_1_.size() - 15));
        } else {
            lvt_4_1_ = lvt_5_1_;
        }


        int lvt_6_1_ = this.getFontRenderer().getStringWidth(p_renderScoreboard_1_.getDisplayName());

        String lvt_10_1_;
        for(Iterator lvt_7_1_ = lvt_4_1_.iterator(); lvt_7_1_.hasNext(); lvt_6_1_ = Math.max(lvt_6_1_, this.getFontRenderer().getStringWidth(lvt_10_1_))) {
            Score lvt_8_1_ = (Score)lvt_7_1_.next();
            ScorePlayerTeam lvt_9_1_ = lvt_3_1_.getPlayersTeam(lvt_8_1_.getPlayerName());
            lvt_10_1_ = ScorePlayerTeam.formatPlayerName(lvt_9_1_, lvt_8_1_.getPlayerName()) + ": " + EnumChatFormatting.RED + lvt_8_1_.getScorePoints();
        }

        int lvt_7_2_ = lvt_4_1_.size() * this.getFontRenderer().FONT_HEIGHT;
        int lvt_8_2_ = p_renderScoreboard_2_.getScaledHeight() / 2 + lvt_7_2_ / 3;
        int lvt_9_2_ = 3;
        int lvt_10_2_ = p_renderScoreboard_2_.getScaledWidth() - lvt_6_1_ - lvt_9_2_;
        int lvt_11_1_ = 0;
        Iterator lvt_12_1_ = lvt_4_1_.iterator();




        ScoreBoardGUI scoreBoardGUI = (ScoreBoardGUI) ProModule.getProModule().getModuleManager().getModuleByName("ScoreBoardGUI");

        if((scoreBoardGUI==null|| (!scoreBoardGUI.isEnabled()||!scoreBoardGUI.isNoBlackBackground()))) {
            int y1 = lvt_8_2_ - ((lvt_4_1_.size() + 1) * this.getFontRenderer().FONT_HEIGHT);
            int y2 = y1 + this.getFontRenderer().FONT_HEIGHT * (lvt_4_1_.size() + 1);

            int x1 = lvt_10_2_ - 2;
            int x2 = p_renderScoreboard_2_.getScaledWidth() - lvt_9_2_ + 2;

//            BlurUtils.doBlur(x1,y1,x2-x1,y2-y1,1,4,4);
//            GlStateManager.enableBlend();
//            GlStateManager.enableDepth();

            Render2DUtils.drawRect(x1, y1, x2, y2, color.getRGB());

        }


        Gui.drawRect(-100, -100, -100, -100, 0xffffffff);
        FontLoaders.msFont14.drawString("", -100, -100, 0xffffffff);



        while(lvt_12_1_.hasNext()) {

            Score lvt_13_1_ = (Score)lvt_12_1_.next();
            ++lvt_11_1_;
            ScorePlayerTeam lvt_14_1_ = lvt_3_1_.getPlayersTeam(lvt_13_1_.getPlayerName());
            String lvt_15_1_ = ScorePlayerTeam.formatPlayerName(lvt_14_1_, lvt_13_1_.getPlayerName());
            String lvt_16_1_ = EnumChatFormatting.RED + "" + lvt_13_1_.getScorePoints();
            int lvt_18_1_ = lvt_8_2_ - lvt_11_1_ * this.getFontRenderer().FONT_HEIGHT;
            int lvt_19_1_ = p_renderScoreboard_2_.getScaledWidth() - lvt_9_2_ + 2;

            //main

//            if(useScoreBoardModule&&!scoreBoardGUI.isNoBlackBackground()){
//                Gui.drawRect(lvt_10_2_ - 2, lvt_18_1_, lvt_19_1_, lvt_18_1_ + this.getFontRenderer().FONT_HEIGHT, 0xffffffff);
//            }


            lvt_15_1_ = lvt_15_1_.replace("\247l","");
            this.getFontRenderer().drawString(lvt_15_1_, lvt_10_2_, lvt_18_1_, 0xffffffff);



            if(scoreBoardGUI==null||(scoreBoardGUI.isEnabled()&&!scoreBoardGUI.isNoScore())) {

                this.getFontRenderer().drawString(lvt_16_1_, lvt_19_1_ - this.getFontRenderer().getStringWidth(lvt_16_1_), lvt_18_1_, 0xffffffff);


            }


            if (lvt_11_1_ == lvt_4_1_.size()) {
                String lvt_20_1_ = p_renderScoreboard_1_.getDisplayName();


                //top

//                if(useScoreBoardModule&&!scoreBoardGUI.isNoBlackBackground()) {
//                    if(scoreBoardGUI.isRoundRect()){
//                        Render2DUtils.drawRoundedRect2(lvt_10_2_ - 2, lvt_18_1_ - this.getFontRenderer().FONT_HEIGHT - 1, lvt_19_1_, lvt_18_1_ - 1, 2,0xffffffff );
//                        Render2DUtils.drawRoundedRect2(lvt_10_2_ - 2, lvt_18_1_ - 1, lvt_19_1_, lvt_18_1_, 2,0xffffffff );
//
//                    }else{
//                Gui.drawRect(lvt_10_2_ - 2, lvt_18_1_ - this.getFontRenderer().FONT_HEIGHT - 1, lvt_19_1_, lvt_18_1_ - 1, 1610612736);
                Gui.drawRect(lvt_10_2_ - 2, lvt_18_1_ - 1, lvt_19_1_, lvt_18_1_, 0xffffffff);
//                    }
//
//                }


                this.getFontRenderer().drawString(lvt_20_1_, lvt_10_2_ + lvt_6_1_ / 2 - this.getFontRenderer().getStringWidth(lvt_20_1_) / 2, lvt_18_1_ - this.getFontRenderer().FONT_HEIGHT, 0xffffffff);
            }
        }

    }

    @Inject(method = "renderTooltip", at = @At("HEAD"), cancellable = true)
    public void renderTooltip(ScaledResolution p_renderTooltip_1_, float p_renderTooltip_2_, CallbackInfo ci){
        EventManager.getEventManager().onRender2D(p_renderTooltip_2_);
        GlStateManager.color(1, 1, 1);
    }

}
