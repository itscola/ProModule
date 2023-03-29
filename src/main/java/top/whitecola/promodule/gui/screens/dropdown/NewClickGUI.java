package top.whitecola.promodule.gui.screens.dropdown;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import top.whitecola.animationlib.Animation;
import top.whitecola.animationlib.functions.type.CubicOutFunction;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.fonts.font4.CustomFont;
import top.whitecola.promodule.gui.screens.simple.IMainClickGUIIngame;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.GLUtils;
import top.whitecola.promodule.utils.GUIUtils;

import java.io.IOException;
import java.util.Vector;

public class NewClickGUI extends GuiScreen implements IMainClickGUIIngame {
    protected Vector<CategoryPanel> panels = new Vector<>();
    protected Animation enableAnim = new Animation();
    protected Animation closeAnim = new Animation();
    protected CustomFont font;
    protected boolean closed;
    protected boolean close;


    @Override
    public void initGui() {
        enableAnim.setMin(0.6f).setMax(1f).setTotalTime(340).setFunction(new CubicOutFunction());
        closeAnim.setMin(0.6f).setMax(1f).setTotalTime(250).setFunction(new CubicOutFunction()).setReverse(true);
        font = ProModule.getProModule().fonts.tenacityFont24;

        CategoryPanel combatPanel = new CategoryPanel(ModuleCategory.COMBAT).setFont(getFont());
        CategoryPanel movementPanel = new CategoryPanel(ModuleCategory.MOVEMENT).setFont(getFont());
        CategoryPanel rendererPanel = new CategoryPanel(ModuleCategory.RENDERS).setFont(getFont());
        CategoryPanel worldPanel = new CategoryPanel(ModuleCategory.WORLD).setFont(getFont());
        CategoryPanel otherPanel = new CategoryPanel(ModuleCategory.OTHER).setFont(getFont());

        panels.add(combatPanel);
        panels.add(movementPanel);
        panels.add(rendererPanel);
        panels.add(worldPanel);
        panels.add(otherPanel);


        Vector<AbstractModule> modules = ProModule.getProModule().getModuleManager().getModules();

        for(CategoryPanel panel : panels){
            panel.fromModules(modules);
        }

        super.initGui();
    }



    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (!enableAnim.isFinish(false)) {
            ScaledResolution sr = new ScaledResolution(mc);
            enableAnim.setReverse(!close);
            GLUtils.scaleStart(sr.getScaledWidth()/2,sr.getScaledHeight()/2,enableAnim.update());
        }

        panels.forEach(i->{
            i.drawScreen(mouseX,mouseY,partialTicks);
        });

        super.drawScreen(mouseX,mouseY,partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        panels.forEach(i->{
            i.mouseClickPanel(new MouseClicked(mouseX,mouseY, GUIUtils.isHovered(i.getX(),i.getY(),i.getX()+i.getWidth(),i.getY()+i.getHeight(),mouseX,mouseY)),mouseButton);
        });
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public CustomFont getFont() {
        return font;
    }

    @Override
    public void drawString(String text, float x, int y, int color) {

    }

    public void setClose(boolean close) {
        this.close = close;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean isClose() {
        return close;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
