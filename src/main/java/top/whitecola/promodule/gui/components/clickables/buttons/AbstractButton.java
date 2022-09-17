package top.whitecola.promodule.gui.components.clickables.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

public class AbstractButton extends GuiButton {


    public AbstractButton(int buttonId, int x, int y, int width, int height, String buttonText) {
        super(buttonId, x, y, width, height, buttonText);
    }

    public AbstractButton(int buttonId, int x, int y, String buttonText){
        super(buttonId, x, y, 200, 20, buttonText);

    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {

    }

    @Override
    public void mouseReleased(int p_mouseReleased_1_, int p_mouseReleased_2_) {
        super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_2_);
    }

    @Override
    public void playPressSound(SoundHandler p_playPressSound_1_) {
        super.playPressSound(p_playPressSound_1_);
    }

    public void setHeight(int height){
        this.height = height;
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
    }

    public boolean isHovered(int mouseX,int mouseY){
        return mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

}
