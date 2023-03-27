package top.whitecola.promodule.gui.screens.simple;

import net.minecraft.client.gui.GuiScreen;
import top.whitecola.promodule.fonts.font2.Config;

import java.util.Vector;

public class ConfigEditorGUI extends GuiScreen {
    protected float width = 300;
    protected float height = 200;
    protected float xPosition = 90;
    protected float yPosition = 16;

    protected float dragX;
    protected float dragY;

    protected boolean draged;

    protected Vector<Config> configs = new Vector<Config>();

    @Override
    public void initGui() {
        super.initGui();
    }


}
