package top.whitecola.promodule.gui.screens.dropdown;

public interface IPanel {
    void drawScreen(int mouseX, int mouseY, float partialTicks);
    void mouseClickPanel(MouseClicked clicked, int mouseButton);
}
