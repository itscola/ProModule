package top.whitecola.promodule.gui.components.clickables;

import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

public abstract class AbstractClickGUIEntry{
    protected AbstractModule module;

    protected String entryName;
    protected String entryDisplayName;

    protected String entryDescription;

    protected float xPosition;
    protected float yPosition;
    protected float x2Position;
    protected float y2Position;

    protected float width;
    protected float height;
    protected boolean enabled;

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }


    public float getxPosition() {
        return xPosition;
    }


    public float getyPosition() {
        return yPosition;
    }



    public String getEntryDescription() {
        return entryDescription;
    }


    public String getEntryName() {
        return entryName;
    }

    public String getEntryDisplayName() {
        return entryDisplayName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggle(){
        if(this.isEnabled()){
            this.enabled = false;
            ProModule.getProModule().getModuleManager().getModuleByName(this.getEntryName()).disable();
        }else{
            this.enabled = true;
            ProModule.getProModule().getModuleManager().getModuleByName(this.getEntryName()).enable();
        }
    }

    public void setxPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setPos(float x,float y,float x2,float y2){
        setxPosition(x);
        setyPosition(y);
        setX2Position(x2);
        setY2Position(y2);
    }

    public void setX2Position(float x2Position) {
        this.x2Position = x2Position;
    }

    public void setY2Position(float y2Position) {
        this.y2Position = y2Position;
    }

    public float getX2Position() {
        return x2Position;
    }

    public float getY2Position() {
        return y2Position;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
        this.entryDisplayName = entryName;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setEntryDescription(String entryDescription) {
        this.entryDescription = entryDescription;
    }

    public void setEntryDisplayName(String entryDisplayName) {
        this.entryDisplayName = entryDisplayName;
    }

    public AbstractModule getModule() {
        return module;
    }

    public void setModule(AbstractModule module) {
        this.module = module;
    }
}
