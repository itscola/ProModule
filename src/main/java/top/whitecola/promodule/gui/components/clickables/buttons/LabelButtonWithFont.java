package top.whitecola.promodule.gui.components.clickables.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import top.whitecola.promodule.fonts.font2.FontLoaders;

import java.awt.*;

public class LabelButtonWithFont extends GuiButton {

    public Color color;

    private LabelButtonWithFont(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, Color color) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.color = color;
    }

    public LabelButtonWithFont(int buttonId, int x, int y, String buttonText, Color textColor){
        this(buttonId, x, y,FontLoaders.msFont18.getStringWidth(buttonText), 8, buttonText,textColor);
//        this(buttonId, x, y,Minecraft.getMinecraft().fontRendererObj.getStringWidth(buttonText), 8, buttonText,textColor);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        FontLoaders.msFont18.drawString(displayString,xPosition, yPosition,color.getRGB(),false);

    }


    @Override
    public void playPressSound(SoundHandler p_playPressSound_1_) {
        super.playPressSound(p_playPressSound_1_);
    }

}
