package top.whitecola.promodule.gui.components.clickables.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import top.whitecola.animationlib.Animation;
import top.whitecola.promodule.utils.Render2DUtils;

public class IconButton extends AbstractButton{
    protected ResourceLocation resourceLocation;
    int animatedHeight = 0;

    protected Animation animation = new Animation();
    protected long animationTime = 0;

    public IconButton(int buttonId, int x, int y, int width, int height, String buttonText) {
        super(buttonId, x, y, width, height, buttonText);
        initAnimation();
    }

    public IconButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
        initAnimation();
    }

    public void initAnimation(){
//        animation.setMin(0).setMax(3).setFunction(new CubeInOutFunction()).setTotalTime(200);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(!this.visible){
            return;
        }


        Render2DUtils.drawCustomImage(this.xPosition, this.yPosition,width ,height ,resourceLocation);

//        if(this.isHovered(mouseX,mouseY)){
//            animatedY -= 0.2;
//            if(animatedY - yPosition <= -3){
//                animatedY = yPosition -3;
//            }
//        }else{
//            animatedY += 0.2;
//            if(animatedY >= yPosition){
//                animatedY = yPosition;
//            }
//        }

//        if(this.isHovered(mouseX,mouseY)){
//
//            if(animationTime==0){
//                animationTime = System.currentTimeMillis();
//            }
//
////            animatedHeight--;
////            if(animatedHeight < -3) {
////                animatedHeight = -3;
////            }
//
//            float animationValue = animation.update(System.currentTimeMillis() - animationTime);
////            System.out.println(animationValue);
////            System.out.println(System.currentTimeMillis() - animationTime);
//            animatedHeight -= animationValue;
//            if(animatedHeight < -4) {
//                animatedHeight = -4;
//            }
////            presentTextColor = textColor2;
//        }else {
//            animationTime = 0;
//            animatedHeight++;
//            if(animatedHeight > 0) {
//                animatedHeight = 0;
//            }
//
////            presentTextColor = textColor;
//
//        }


    }


    @Override
    public void playPressSound(SoundHandler p_playPressSound_1_) {
//        super.playPressSound(p_playPressSound_1_);
    }

    public IconButton setIcon(ResourceLocation resourceLocation){
        this.resourceLocation = resourceLocation;
        return this;
    }

    @Override
    public void mouseReleased(int p_mouseReleased_1_, int p_mouseReleased_2_) {

        super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_2_);
    }
}
