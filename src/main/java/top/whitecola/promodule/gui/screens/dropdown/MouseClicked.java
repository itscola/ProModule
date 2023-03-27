package top.whitecola.promodule.gui.screens.dropdown;

public class MouseClicked {
    private int mouseX;
    private int mouseY;
    private boolean inPanel;

    public MouseClicked(int mouseX,int mouseY,boolean inPanel){
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.inPanel = inPanel;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public boolean isInPanel() {
        return inPanel;
    }

    public void setInPanel(boolean inPanel) {
        this.inPanel = inPanel;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }
}
