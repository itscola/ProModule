package top.whitecola.promodule.modules.impls.other;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.Render2DUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class ScoreBoardGUI extends AbstractModule {
    @ModuleSetting(name = "NoBlack",type = "select")
    protected Boolean noBlackBackground = false;

    @ModuleSetting(name = "NoScore",type = "select")
    protected Boolean noScore = true;

    @ModuleSetting(name = "Font",type = "select")
    public Boolean font = false;

    private Color color = new Color(1,1,1, 34);

//    @ModuleSetting(name = "RoundRect",type = "select")
//    protected Boolean roundRect = true;


//    @Override
//    public void onRenderOverLay(RenderGameOverlayEvent event) {
//        Scoreboard scoreBoard = mc.theWorld.getScoreboard();
//        //some codes from vanillia source.
//        ScaledResolution sc = new ScaledResolution(mc);
//
//        ScorePlayerTeam team = scoreBoard.getPlayersTeam(mc.thePlayer.getName());
//
//        ScoreObjective scoreObjective = null;
//
//        if(team!=null&&team.getChatFormat().getColorIndex()>=0){
//            scoreObjective = scoreBoard.getObjectiveInDisplaySlot(3 +team.getChatFormat().getColorIndex());
//        }
//
//        scoreObjective = scoreObjective != null ? scoreObjective : scoreBoard.getObjectiveInDisplaySlot(1);
//
//        if(scoreObjective==null){
//            return;
//        }
//
//
//        Collection<Score> lvt_4_1_ = scoreBoard.getSortedScores(scoreObjective);
//
//        List<Score> arr = Lists.newArrayList(Iterables.filter(lvt_4_1_, new Predicate<Score>() {
//            public boolean apply(Score p_apply_1_) {
//                return p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#");
//            }
//        }));
//        int lvt_6_1_ = mc.fontRendererObj.getStringWidth(scoreObjective.getDisplayName());
//
//        int lvt_7_2_ = lvt_4_1_.size() * mc.fontRendererObj.FONT_HEIGHT;
//        int lvt_8_2_ = sc.getScaledHeight() / 2 + lvt_7_2_ / 3;
//
//        int lvt_9_2_ = 3;
//        int lvt_10_2_ = sc.getScaledWidth() - lvt_6_1_ - lvt_9_2_;
//
//
//        int y1 = lvt_8_2_ - ((lvt_4_1_.size() + 1) * mc.fontRendererObj.FONT_HEIGHT);
//        int y2 = y1 + mc.fontRendererObj.FONT_HEIGHT * (lvt_4_1_.size() + 1);
//
//        int x1 = lvt_10_2_ - 2;
//        int x2 = sc.getScaledWidth() - lvt_9_2_ + 2;
//
//
//        if(!noBlackBackground) {
//            Render2DUtils.drawRect(x1, y1, x2, y2, color.getRGB());
//        }
//
//        String displayName = scoreObjective.getDisplayName();
//
//        FontRenderer fontRenderer = mc.fontRendererObj;
//
//        drawCenteredString(displayName,x1+(x2-x1)/2,y1,0xffffffff);
//
//
//
//        for(int i=0;i<arr.size();i++){
//            Score score = arr.get(i);
//            String lvt_15_1_ = ScorePlayerTeam.formatPlayerName(team, score.getPlayerName());
//
//
//            int lvt_18_1_ = lvt_8_2_ - i * mc.fontRendererObj.FONT_HEIGHT;
//
//            mc.fontRendererObj.drawString(lvt_15_1_, lvt_10_2_, lvt_18_1_, 0xffffffff);
//
//        }
//
//
//        super.onRenderOverLay(event);
//    }


    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.OTHER;
    }

    @Override
    public String getModuleName() {
        return "ScoreBoardGUI";

    }

    public Boolean isNoBlackBackground() {
        return noBlackBackground;
    }

    public Boolean isNoScore() {
        return noScore;
    }

    public void drawCenteredString(String text, int x, int y, int col) {
        mc.fontRendererObj.drawStringWithShadow(text, x - (mc.fontRendererObj.getStringWidth(text) / 2f), y, col);
    }

}
