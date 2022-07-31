package top.whitecola.promodule.gui.widgets;

public class RelativePosition {
    protected float dragX;
    protected float dragY;
    protected float defaultX;
    protected float defaultY;
    protected float relativeX;
    protected float relativeY;

    public RelativePosition(){


    }

    public float getDragX() {
        return dragX;
    }

    public float getDragY() {
        return dragY;
    }

    public void setDragX(float dragX) {
        this.dragX = dragX;
    }

    public void setDragY(float dragY) {
        this.dragY = dragY;
    }

    public float getRelativeX() {
        return defaultX+relativeX;
    }

    public float getRelativeY() {
        return defaultY+relativeY;
    }


    public void addDragedX(float draggedX){
        this.dragX += draggedX;
    }

    public void addDragedY(float draggedY){
        this.dragY += draggedY;
    }


    public float getDefaultX() {
        return defaultX;
    }


    public float getDefaultY() {
        return defaultY;
    }

    public void setDefaultX(float defaultX) {
        this.defaultX = defaultX;
    }

    public void setDefaultY(float defaultY) {
        this.defaultY = defaultY;
    }
}
