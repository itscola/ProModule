package top.whitecola.promodule.injection.mixins;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.animationlib.functions.AbstractAnimationFunction;
import top.whitecola.animationlib.functions.type.BounceOutFunction;
import top.whitecola.animationlib.functions.type.CubicOutFunction;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.fonts.font2.FontLoaders;
import top.whitecola.promodule.injection.wrappers.ChatLineInfo;
import top.whitecola.promodule.injection.wrappers.ChatLineManager;
import top.whitecola.promodule.modules.impls.other.BetterChatLine;

import static net.minecraft.client.gui.GuiNewChat.calculateChatboxHeight;
import static net.minecraft.client.gui.GuiNewChat.calculateChatboxWidth;
import static top.whitecola.promodule.utils.MCWrapper.*;

import java.util.Iterator;
import java.util.List;

@Mixin(GuiNewChat.class)
public class MixinGuiNewChat extends Gui {
    @Shadow
    private final List<ChatLine> field_146253_i = Lists.newArrayList();

    @Shadow
    private int scrollPos;
    @Shadow
    private boolean isScrolled;

    @Shadow @Final
    private List<ChatLine> chatLines;
    protected ChatLineManager chatLineManager = new ChatLineManager();

    // it will be different animation later
    AbstractAnimationFunction animationFunction = new CubicOutFunction();
    AbstractAnimationFunction animationFunction2 = new BounceOutFunction();

    /**
     * @author White_cola
     * @reason BetterChatLine.
     */
    @Overwrite
    public void drawChat(int p_drawChat_1_) {




        if (mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            BetterChatLine betterChatLine = (BetterChatLine) ProModule.getProModule().getModuleManager().getModuleByName("BetterChatLine");
            if(betterChatLine!=null&&betterChatLine.isEnabled()){
                drawChatWithAnimation(p_drawChat_1_);
            }else{
                drawChat1(p_drawChat_1_);
            }
            return;


        }
    }

    public void drawChat1(int p_drawChat_1_) {
        if (mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            int lvt_2_1_ = this.getLineCount();
            boolean lvt_3_1_ = false;
            int lvt_4_1_ = 0;
            int lvt_5_1_ = this.field_146253_i.size();
            float lvt_6_1_ = mc.gameSettings.chatOpacity * 0.9F + 0.1F;
            if (lvt_5_1_ > 0) {
                if (this.getChatOpen()) {
                    lvt_3_1_ = true;
                }

                float lvt_7_1_ = this.getChatScale();
                int lvt_8_1_ = MathHelper.ceiling_float_int((float)this.getChatWidth() / lvt_7_1_);
                GlStateManager.pushMatrix();
                GlStateManager.translate(2.0F, 20.0F, 0.0F);
                GlStateManager.scale(lvt_7_1_, lvt_7_1_, 1.0F);

                int lvt_9_1_;
                int lvt_11_1_;
                int lvt_14_1_;
                for(lvt_9_1_ = 0; lvt_9_1_ + this.scrollPos < this.field_146253_i.size() && lvt_9_1_ < lvt_2_1_; ++lvt_9_1_) {
                    ChatLine lvt_10_1_ = (ChatLine)this.field_146253_i.get(lvt_9_1_ + this.scrollPos);
                    if (lvt_10_1_ != null) {
                        lvt_11_1_ = p_drawChat_1_ - lvt_10_1_.getUpdatedCounter();
                        if (lvt_11_1_ < 200 || lvt_3_1_) {
                            double lvt_12_1_ = (double)lvt_11_1_ / 200.0D;
                            lvt_12_1_ = 1.0D - lvt_12_1_;
                            lvt_12_1_ *= 10.0D;
                            lvt_12_1_ = MathHelper.clamp_double(lvt_12_1_, 0.0D, 1.0D);
                            lvt_12_1_ *= lvt_12_1_;
                            lvt_14_1_ = (int)(255.0D * lvt_12_1_);
                            if (lvt_3_1_) {
                                lvt_14_1_ = 255;
                            }

                            lvt_14_1_ = (int)((float)lvt_14_1_ * lvt_6_1_);
                            ++lvt_4_1_;
                            if (lvt_14_1_ > 3) {
                                int lvt_15_1_ = 0;
                                int lvt_16_1_ = -lvt_9_1_ * 9;
                                drawRect(lvt_15_1_, lvt_16_1_ - 9, lvt_15_1_ + lvt_8_1_ + 4, lvt_16_1_, lvt_14_1_ / 2 << 24);
                                String lvt_17_1_ = lvt_10_1_.getChatComponent().getFormattedText();
                                GlStateManager.enableBlend();

                                BetterChatLine betterChatLine = (BetterChatLine) ProModule.getProModule().getModuleManager().getModuleByName("BetterChatLine");
                                if(betterChatLine!=null&&betterChatLine.isEnabled()&&betterChatLine.font){
                                    FontLoaders.msFont14.drawStringWithShadow(lvt_17_1_, (float)lvt_15_1_, (float)(lvt_16_1_ - 8), 16777215 + (lvt_14_1_ << 24));
                                }else {
                                    mc.fontRendererObj.drawStringWithShadow(lvt_17_1_, (float)lvt_15_1_, (float)(lvt_16_1_ - 8), 16777215 + (lvt_14_1_ << 24));

                                }




                                GlStateManager.disableAlpha();
                                GlStateManager.disableBlend();
                            }
                        }
                    }
                }

                if (lvt_3_1_) {
                    lvt_9_1_ = mc.fontRendererObj.FONT_HEIGHT;
                    GlStateManager.translate(-3.0F, 0.0F, 0.0F);
                    int lvt_10_2_ = lvt_5_1_ * lvt_9_1_ + lvt_5_1_;
                    lvt_11_1_ = lvt_4_1_ * lvt_9_1_ + lvt_4_1_;
                    int lvt_12_2_ = this.scrollPos * lvt_11_1_ / lvt_5_1_;
                    int lvt_13_1_ = lvt_11_1_ * lvt_11_1_ / lvt_10_2_;
                    if (lvt_10_2_ != lvt_11_1_) {
                        lvt_14_1_ = lvt_12_2_ > 0 ? 170 : 96;
                        int lvt_15_2_ = this.isScrolled ? 13382451 : 3355562;
                        drawRect(0, -lvt_12_2_, 2, -lvt_12_2_ - lvt_13_1_, lvt_15_2_ + (lvt_14_1_ << 24));
                        drawRect(2, -lvt_12_2_, 1, -lvt_12_2_ - lvt_13_1_, 13421772 + (lvt_14_1_ << 24));
                    }
                }

                GlStateManager.popMatrix();
            }
        }
    }


    public void drawChatWithAnimation(int p_drawChat_1_){

        if (mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {

            int lvt_2_1_ = this.getLineCount();
            boolean lvt_3_1_ = false;
            int lvt_4_1_ = 0;
            int lvt_5_1_ = this.field_146253_i.size();
            float lvt_6_1_ = mc.gameSettings.chatOpacity * 0.9F + 0.1F;
            if (lvt_5_1_ > 0) {
                if (this.getChatOpen()) {
                    lvt_3_1_ = true;
                }

                float lvt_7_1_ = this.getChatScale();
                int lvt_8_1_ = MathHelper.ceiling_float_int((float)this.getChatWidth() / lvt_7_1_);
                GlStateManager.pushMatrix();
                GlStateManager.translate(2.0F, 20.0F, 0.0F);
                GlStateManager.scale(lvt_7_1_, lvt_7_1_, 1.0F);

                int lvt_9_1_;
                int lvt_11_1_;
                int shadow;
                for(lvt_9_1_ = 0; lvt_9_1_ + this.scrollPos < this.field_146253_i.size() && lvt_9_1_ < lvt_2_1_; ++lvt_9_1_) {

                    ChatLine chatLine = this.field_146253_i.get(lvt_9_1_ + this.scrollPos);
//                    ChatLineCopy chatLine = new ChatLineCopy(chatLinea);
                    ChatLineInfo chatLineInfo = chatLineManager.getChatInfo(chatLine);


                    if (chatLine != null) {
                        lvt_11_1_ = p_drawChat_1_ - chatLine.getUpdatedCounter();
                        if (lvt_11_1_ < 200 || lvt_3_1_) {
                            double lvt_12_1_ = (double)lvt_11_1_ / 200.0D;
                            lvt_12_1_ = 1.0D - lvt_12_1_;
                            lvt_12_1_ *= 10.0D;
                            lvt_12_1_ = MathHelper.clamp_double(lvt_12_1_, 0.0D, 1.0D);
                            lvt_12_1_ *= lvt_12_1_;
                            shadow = (int)(255.0D * lvt_12_1_);
                            if (lvt_3_1_) {
                                shadow = 255;
                            }

                            shadow = (int)((float)shadow * lvt_6_1_);
                            ++lvt_4_1_;
                            if (shadow > 3) {
                                int lvt_15_1_ = 0;
                                int lvt_16_1_ = -lvt_9_1_ * 9;


                                BetterChatLine betterChatLine = (BetterChatLine) ProModule.getProModule().getModuleManager().getModuleByName("BetterChatLine");
                                if(betterChatLine!=null&&betterChatLine.isEnabled()&&betterChatLine.getNoBackground()){
                                    if(getChatOpen()) {
                                        drawRect(lvt_15_1_, lvt_16_1_ - 9, lvt_15_1_ + lvt_8_1_ + 4, lvt_16_1_, shadow / 2 << 24);
                                    }
                                }else {
                                    drawRect(lvt_15_1_, lvt_16_1_ - 9, lvt_15_1_ + lvt_8_1_ + 4, lvt_16_1_, shadow / 2 << 24);
                                }




                                String textString = chatLine.getChatComponent().getFormattedText();
                                GlStateManager.enableBlend();



                                float width = mc.fontRendererObj.getStringWidth(textString);

                                if(shadow==255){
                                    if(textString.contains("join")) {
                                        chatLineInfo.animation.setMin(0).setMax(width).setFunction(animationFunction2).setTotalTime(500).setLock(true);
                                    }else {
                                        chatLineInfo.animation.setMin(0).setMax(width).setFunction(animationFunction).setTotalTime(500).setLock(true);
                                    }

//                                    System.out.println(chatLine.animation.get);
                                    chatLineInfo.x = chatLineInfo.animation.update();
                                }



                                if(betterChatLine!=null&&betterChatLine.isEnabled()&&betterChatLine.font) {
                                    FontLoaders.msFont14.drawStringWithShadow(textString, (float)lvt_15_1_ + chatLineInfo.x-width, (float)(lvt_16_1_ - 8), 16777215 + (shadow << 24));

                                }else {
                                    mc.fontRendererObj.drawStringWithShadow(textString, (float)lvt_15_1_ + chatLineInfo.x-width, (float)(lvt_16_1_ - 8), 16777215 + (shadow << 24));

                                }

                                GlStateManager.disableAlpha();
                                GlStateManager.disableBlend();
                            }
                        }
                    }
                }

                if (lvt_3_1_) {
                    lvt_9_1_ = mc.fontRendererObj.FONT_HEIGHT;
                    GlStateManager.translate(-3.0F, 0.0F, 0.0F);
                    int lvt_10_2_ = lvt_5_1_ * lvt_9_1_ + lvt_5_1_;
                    lvt_11_1_ = lvt_4_1_ * lvt_9_1_ + lvt_4_1_;
                    int lvt_12_2_ = this.scrollPos * lvt_11_1_ / lvt_5_1_;
                    int lvt_13_1_ = lvt_11_1_ * lvt_11_1_ / lvt_10_2_;
                    if (lvt_10_2_ != lvt_11_1_) {
                        shadow = lvt_12_2_ > 0 ? 170 : 96;
                        int lvt_15_2_ = this.isScrolled ? 13382451 : 3355562;
                        drawRect(0, -lvt_12_2_, 2, -lvt_12_2_ - lvt_13_1_, lvt_15_2_ + (shadow << 24));
                        drawRect(2, -lvt_12_2_, 1, -lvt_12_2_ - lvt_13_1_, 13421772 + (shadow << 24));
                    }
                }

                GlStateManager.popMatrix();
            }
        }
    }

    /**
     * @author
     */
    @Overwrite
    public int getLineCount() {
        return this.getChatHeight() / 9;
    }

    /**
     * @author
     */
    @Overwrite
    public int getChatHeight() {
        return calculateChatboxHeight(this.getChatOpen() ? mc.gameSettings.chatHeightFocused : mc.gameSettings.chatHeightUnfocused);
    }

    /**
     * @author
     */
    @Overwrite
    public boolean getChatOpen() {
        return mc.currentScreen instanceof GuiChat;
    }

    /**
     * @author
     */
    @Overwrite
    public float getChatScale() {
        return mc.gameSettings.chatScale;
    }

    /**
     * @author
     */
    @Overwrite
    public int getChatWidth() {
        return calculateChatboxWidth(mc.gameSettings.chatWidth);
    }

//    @Shadow
//    private void setChatLine(IChatComponent p_setChatLine_1_, int p_setChatLine_2_, int p_setChatLine_3_, boolean p_setChatLine_4_) {
//        if (p_setChatLine_2_ != 0) {
//            this.deleteChatLine(p_setChatLine_2_);
//        }
//
//        int lvt_5_1_ = MathHelper.floor_float((float)this.getChatWidth() / this.getChatScale());
//        List<IChatComponent> lvt_6_1_ = GuiUtilRenderComponents.func_178908_a(p_setChatLine_1_, lvt_5_1_, mc.fontRendererObj, false, false);
//        boolean lvt_7_1_ = this.getChatOpen();
//
//        IChatComponent lvt_9_1_;
//        for(Iterator lvt_8_1_ = lvt_6_1_.iterator(); lvt_8_1_.hasNext(); this.field_146253_i.add(0, new ChatLine(p_setChatLine_3_, lvt_9_1_, p_setChatLine_2_))) {
//
//            lvt_9_1_ = (IChatComponent)lvt_8_1_.next();
//
//            ChatLine chatLine = new ChatLine(p_setChatLine_3_, lvt_9_1_, p_setChatLine_2_);
//            chatlineCopy.add((top.whitecola.kateclient.injection.wrappers.ChatLine) chatLine);
//
//            if (lvt_7_1_ && this.scrollPos > 0) {
//                this.isScrolled = true;
//                this.scroll(1);
//            }
//        }
//
//        while(this.field_146253_i.size() > 100) {
//            this.field_146253_i.remove(this.field_146253_i.size() - 1);
//        }
//
//        if (!p_setChatLine_4_) {
//            this.chatLines.add(0, new ChatLine(p_setChatLine_3_, p_setChatLine_1_, p_setChatLine_2_));
//
//            while(this.chatLines.size() > 100) {
//                this.chatLines.remove(this.chatLines.size() - 1);
//            }
//        }
//
//    }

    @Shadow
    public void deleteChatLine(int p_deleteChatLine_1_) {
        Iterator lvt_2_1_ = this.field_146253_i.iterator();

        ChatLine lvt_3_2_;
        while(lvt_2_1_.hasNext()) {
            lvt_3_2_ = (ChatLine)lvt_2_1_.next();
            if (lvt_3_2_.getChatLineID() == p_deleteChatLine_1_) {
                lvt_2_1_.remove();
            }
        }

        lvt_2_1_ = this.chatLines.iterator();

        while(lvt_2_1_.hasNext()) {
            lvt_3_2_ = (ChatLine)lvt_2_1_.next();
            if (lvt_3_2_.getChatLineID() == p_deleteChatLine_1_) {
                lvt_2_1_.remove();
                break;
            }
        }

    }

    @Shadow
    public void scroll(int p_scroll_1_) {
        this.scrollPos += p_scroll_1_;
        int lvt_2_1_ = this.field_146253_i.size();
        if (this.scrollPos > lvt_2_1_ - this.getLineCount()) {
            this.scrollPos = lvt_2_1_ - this.getLineCount();
        }

        if (this.scrollPos <= 0) {
            this.scrollPos = 0;
            this.isScrolled = false;
        }

    }

    /**
     * @author
     */
    @Overwrite
    private void setChatLine(IChatComponent p_setChatLine_1_, int p_setChatLine_2_, int p_setChatLine_3_, boolean p_setChatLine_4_) {
        if (p_setChatLine_2_ != 0) {
            this.deleteChatLine(p_setChatLine_2_);
        }

        int lvt_5_1_ = MathHelper.floor_float((float)this.getChatWidth() / this.getChatScale());
        List<IChatComponent> lvt_6_1_ = GuiUtilRenderComponents.func_178908_a(p_setChatLine_1_, lvt_5_1_, mc.fontRendererObj, false, false);
        boolean lvt_7_1_ = this.getChatOpen();

        IChatComponent lvt_9_1_;
        for(Iterator lvt_8_1_ = lvt_6_1_.iterator(); lvt_8_1_.hasNext(); this.field_146253_i.add(0, new ChatLine(p_setChatLine_3_, lvt_9_1_, p_setChatLine_2_))) {
            lvt_9_1_ = (IChatComponent)lvt_8_1_.next();
            if (lvt_7_1_ && this.scrollPos > 0) {
                this.isScrolled = true;
                this.scroll(1);
            }
        }

        while(this.field_146253_i.size() > 300) {
            ChatLine chatLine = this.field_146253_i.get(this.field_146253_i.size() - 1);
            this.chatLineManager.deleteChatLine(chatLine);
            this.field_146253_i.remove(chatLine);
        }

        if (!p_setChatLine_4_) {
            this.chatLines.add(0, new ChatLine(p_setChatLine_3_, p_setChatLine_1_, p_setChatLine_2_));

            while(this.chatLines.size() > 100) {
                this.chatLines.remove(this.chatLines.size() - 1);
            }
        }

    }
}
