package top.whitecola.promodule.gui.widgets;

import java.awt.*;

public class AbstractWidget {
    protected float width;
    protected float height;
    protected RelativePosition relativePosition = new RelativePosition();
    protected String text;
    protected Color color = new Color(237, 235, 233);
    protected Color backgroundColor = null;

    public AbstractWidget(float width,float height){
        this.width = width;
        this.height = height;
    }


    public void drawWidget(){

    }

    public RelativePosition getRelativePosition() {
        return relativePosition;
    }



    public AbstractWidget setHeight(float height) {
        this.height = height;
        return this;
    }


    public AbstractWidget setWidth(float width) {
        this.width = width;
        return this;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public String getWidgetName(){
        return this.getClass().getSimpleName();
    }


    public Color getColor() {
        return color;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }



    public boolean isHovered(int mouseX,int mouseY){
        return mouseX >= this.relativePosition.getRelativeX() && mouseY >= this.relativePosition.getRelativeY() && mouseX < this.relativePosition.getRelativeX() + this.width && mouseY < this.relativePosition.getRelativeY() + this.height;
    }


    public AbstractWidget setText(String text) {
        this.text = text;
        return this;
    }

    public String getText() {
        return text;
    }




}
