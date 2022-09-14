package top.whitecola.promodule.gui.components.clickables;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class ConfigEntry {
    private String fileName;
    private boolean selected;

    public ConfigEntry(String fileName,boolean selected){
        this.fileName = fileName;
        this.selected = selected;
    }

//    public void

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isSelected() {
        return selected;
    }
}
