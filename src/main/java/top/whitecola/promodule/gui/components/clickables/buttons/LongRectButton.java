package top.whitecola.promodule.gui.components.clickables.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import top.whitecola.animationlib.Animation;
import top.whitecola.animationlib.functions.type.CubicOutFunction;
import top.whitecola.promodule.utils.Render2DUtils;

import java.awt.*;

public class LongRectButton extends AbstractButton {
    protected Color mainColor = new Color(31, 31, 31);
    protected Color mainColor2 = new Color(27, 27, 27);
    protected Color presentColor = mainColor;
    protected Color textColor = new Color(239, 239, 239);
    protected Color textColor2 = new Color(255, 255, 255);
    protected Color presentTextColor = textColor;

    protected Animation upAnimation = new Animation()
            .setMin(0).setMax(-3).setTotalTime(400).setFunction(new CubicOutFunction());
    protected long animationTime;



    int animatedHeight = 0;

    public LongRectButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    public LongRectButton(int buttonId, int x, int y, String buttonText){
        super(buttonId, x, y, 200, 20, buttonText);
    }

    public void init(){

    }


    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(!this.visible){
            return;
        }

        Render2DUtils.drawRoundedRect2(this.xPosition, this.yPosition + animatedHeight, this.xPosition + this.width, this.yPosition + this.height + animatedHeight,3,this.presentColor.getRGB());
        FontRenderer fontRenderer = mc.fontRendererObj;
        GlStateManager.color(1.0F,1.0F,1.0F, 1.0F);



        if(this.isHovered(mouseX,mouseY)){
            if(animationTime==0){
                animationTime = System.currentTimeMillis();
            }

            presentColor = mainColor2;
            presentTextColor = textColor2;

            animatedHeight--;
            if(animatedHeight < -3) {
                animatedHeight = -3;
            }

            presentTextColor = textColor2;
        }else {
            animationTime = 0;
            presentColor = mainColor;
            presentTextColor = textColor;


            animatedHeight++;
            if(animatedHeight > 0) {
                animatedHeight = 0;
            }

            presentTextColor = textColor;

        }


        this.drawCenteredString(fontRenderer,this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2 + animatedHeight, this.presentTextColor.getRGB());


    }


    @Override
    public void playPressSound(SoundHandler p_playPressSound_1_) {
        super.playPressSound(p_playPressSound_1_);
    }




}
