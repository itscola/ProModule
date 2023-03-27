package top.whitecola.promodule.modules.impls.render;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.input.Keyboard;
import top.whitecola.animationlib.Animation;
import top.whitecola.animationlib.functions.type.CubicOutFunction;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.fonts.font4.CustomFont;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.*;
import top.whitecola.promodule.utils.RoundedUtils;
import static org.lwjgl.opengl.GL11.*;

import java.awt.*;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class Keystroke extends AbstractModule {
    private Color color = new Color(234, 234, 234);
    private Color color2 = new Color(56, 56, 56);
    private Color color3 = new Color(31, 31, 31);


    private Button keyBindForward;
    private Button keyBindLeft;
    private Button keyBindBack;
    private Button keyBindRight;
    private Button keyBindJump;

    @ModuleSetting(name = "X",max = 0,min = 255,addValue = 5)
    protected Float x = 568f;

    @ModuleSetting(name = "Y",max = 0,min = 255,addValue = 5)
    protected Float y = 260f;

//
//    @Override
//    public void onRenderOverLay(RenderGameOverlayEvent event) {
////        ScaledResolution scaledResolution = new ScaledResolution(mc);
//        FontRenderer fontRenderer = mc.fontRendererObj;
////        int x = scaledResolution.getScaledWidth() - scaledResolution.getScaledWidth()+5;
////        int y = scaledResolution.getScaledHeight() - scaledResolution.getScaledWidth()+5;
//        float x=this.x;
//        float y=this.y;
//        float height = 20;
//        float weight = 20;
//
//        //up
//        Render2DUtils.drawRoundedRect2(x,y,x+weight,y+height,2,color.getRGB());
//        fontRenderer.drawString("W",(int)x+6,(int)y+6,color3.getRGB());
//
//        float x1=x;
//        float y1=y+21.8f;
//
//        //down
//        Render2DUtils.drawRoundedRect2(x1,y1,x1+weight,y1+height,2,color.getRGB());
//        fontRenderer.drawString("S",(int)x+7,(int)y1+6,color3.getRGB());
//
//        float x2=x1-21.8f;
//        float y2=y1;
//
//        //left
//
//        Render2DUtils.drawRoundedRect2(x2,y2,x2+weight,y2+height,2,color.getRGB());
//        fontRenderer.drawString("A",(int)x2+7,(int)y1+6,color3.getRGB());
//
//        float x3=x1+21.8f;
//        float y3=y1;
//
//        //right
//
//        Render2DUtils.drawRoundedRect2(x3,y3,x3+weight,y3+height,2,color.getRGB());
//        fontRenderer.drawString("D",(int)x3+8,(int)y1+6,color3.getRGB());
//
//
//        //space
//        Render2DUtils.drawRoundedRect2(x2,y3+12+height-10,x3+weight,y3+12+height,2,color.getRGB());
//
////        float x4 =
//
//        if(mc.gameSettings.keyBindForward.isKeyDown()){
//            if(mc.thePlayer.isSprinting()){
//                Render2DUtils.drawRoundedRect2(x,y,x+weight,y+height,1,color3.getRGB());
//                fontRenderer.drawString("W",(int)x+6,(int)y+6,color.getRGB());
//
//            }else {
//                Render2DUtils.drawRoundedRect2(x,y,x+weight,y+height,1,color2.getRGB());
//
//            }
//
//        }
//
//        if(mc.gameSettings.keyBindBack.isKeyDown()){
//            Render2DUtils.drawRoundedRect2(x1,y1,x1+weight,y1+height,1,color2.getRGB());
//            fontRenderer.drawString("S",(int)x+7,(int)y1+6,color.getRGB());
//
//
//        }
//
//        if(mc.gameSettings.keyBindLeft.isKeyDown()){
//            Render2DUtils.drawRoundedRect2(x2,y2,x2+weight,y2+height,1,color2.getRGB());
//            fontRenderer.drawString("A",(int)x2+7,(int)y1+6,color.getRGB());
//
//        }
//
//        if(mc.gameSettings.keyBindRight.isKeyDown()){
//            Render2DUtils.drawRoundedRect2(x3,y3,x3+weight,y3+height,1,color2.getRGB());
//            fontRenderer.drawString("D",(int)x3+8,(int)y1+6,color.getRGB());
//
//        }
//
//        if(mc.gameSettings.keyBindJump.isKeyDown()) {
//            Render2DUtils.drawRoundedRect2(x2,y3+12+height-10,x3+weight,y3+12+height,1,color2.getRGB());
//
//        }
//
//
//        super.onRenderOverLay(event);
//    }


    @Override
    public void onRender2D(float partialTicks) {
        float offset = 3;
        float size = 20;
//        dragging.setHeight((float) ((sizeValue.getValue() + offset) * 3) - offset);
//        dragging.setWidth((float) ((sizeValue.getValue() + offset) * 3) - offset);


        float width =((20 + offset) * 3) - offset;
        float height = ((20 + offset) * 3) - offset;

        if (keyBindForward == null) {
            keyBindForward = new Button(mc.gameSettings.keyBindForward);
            keyBindLeft = new Button(mc.gameSettings.keyBindLeft);
            keyBindBack = new Button(mc.gameSettings.keyBindBack);
            keyBindRight = new Button(mc.gameSettings.keyBindRight);
            keyBindJump = new Button(mc.gameSettings.keyBindJump);
        }

        float x = this.x, y = this.y;

//        Button.font = mc.fontRendererObj;

        float increment = size + offset;
        keyBindForward.render(x + width / 2f - size / 2f, y, size);
        keyBindLeft.render(x, y + increment, size);
        keyBindBack.render(x + increment, y + increment, size);
        keyBindRight.render(x + (increment * 2), y + increment, size);
        keyBindJump.render(x, y + increment * 2, width, size);
        super.onRender2D(partialTicks);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.RENDERS;
    }

    @Override
    public String getModuleName() {
        return "Keystroke";
    }


    public static class Button {
        protected float radius = 3;

        private static CustomFont font = ProModule.getProModule().fonts.tenacityFont20;
        private final KeyBinding binding;
        private final Animation clickAnimation = new Animation().setMin(0).setMax(1).setTotalTime(250).setFunction(new CubicOutFunction());

        public Button(KeyBinding binding) {
            this.binding = binding;
        }

        public void renderForEffects(float x, float y, float size) {
            renderForEffects(x, y, size, size);
        }

        public void renderForEffects(float x, float y, float width, float height) {
            Color color = Color.BLACK;
//            if (event.getBloomOptions().getSetting("Keystrokes").isEnabled()) {
            color = ColorUtils.interpolateColorC(Color.BLACK, Color.WHITE, (float) clickAnimation.update());
//            }
            RoundedUtils.drawRound(x, y, width, height, radius, color);
        }

        public void render(float x, float y, float size) {
            render(x, y, size, size);
        }

        public void render(float x, float y, float width, float height) {
            Color color = ColorUtils.applyOpacity(Color.BLACK, 0.4f);
//            clickAnimation.setDirection(binding.isKeyDown() ? Direction.FORWARDS : Direction.BACKWARDS);
            clickAnimation.setReverse(!binding.isKeyDown());
            RoundedUtils.drawRound(x, y, width, height, this.radius, color);
            float offsetX =  .2f;
            int offsetY =  1;

            FontUtils.drawCenteredString("", x + width / 2 + offsetX, y + height / 2 - font.getHeight() / 2f + offsetY, Color.WHITE.getRGB());

//            font.drawCenteredString(Keyboard.getKeyName(binding.getKeyCode()), x + width / 2 + offsetX, y + height / 2 - font.getHeight() / 2f + offsetY, Color.WHITE.getRGB());

            if (!clickAnimation.isFinish(false)) {
                float animation = (float) clickAnimation.update();
                Color color2 = ColorUtils.applyOpacity(Color.WHITE, (0.5f * animation));

                glPushMatrix();
                GLUtils.scaleStart(x + width / 2f, y + height / 2f, animation);
                float diff = (height / 2f) - this.radius;
                RoundedUtils.drawRound(x, y, width, height, ((height / 2f) - (diff * animation)), color2);
                GLUtils.scaleEnd();

            }else {
                clickAnimation.reset();
            }


        }


    }
}
