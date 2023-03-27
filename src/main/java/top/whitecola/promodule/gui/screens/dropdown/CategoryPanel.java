package top.whitecola.promodule.gui.screens.dropdown;

import top.whitecola.promodule.fonts.font4.CustomFont;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

import java.util.List;
import java.util.Vector;

public class CategoryPanel implements IPanel{
    protected float x = 6;
    protected float y = 6;

    protected float titleHeight;
    protected float titleWeight;


    protected float height;
    protected float width;

    protected float entryHeight;
    protected float entryWidth;



    public Vector<AbstractModule> getModules() {
        return modules;
    }

    public void setModules(Vector<AbstractModule> modules) {
        this.modules = modules;
    }

    protected CustomFont font;
    protected float type;

    protected ModuleCategory category;
    protected Vector<AbstractModule> modules;

    public CategoryPanel(ModuleCategory category){
        this.category = category;
    }

    public void fromModules(List<AbstractModule> modules){
        for(AbstractModule module : modules){
            if(this.category==module.getModuleType()){
                this.modules.add(module);
            }
        }

        this.height = titleHeight + entryHeight*this.modules.size();

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    public void mouseClickPanel(MouseClicked clicked, int mouseButton) {

    }




    public float getTitleHeight() {
        return titleHeight;
    }

    public void setTitleHeight(float titleHeight) {
        this.titleHeight = titleHeight;
    }

    public float getTitleWeight() {
        return titleWeight;
    }

    public void setTitleWeight(float titleWeight) {
        this.titleWeight = titleWeight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getEntryHeight() {
        return entryHeight;
    }

    public void setEntryHeight(float entryHeight) {
        this.entryHeight = entryHeight;
    }

    public float getEntryWidth() {
        return entryWidth;
    }

    public void setEntryWidth(float entryWidth) {
        this.entryWidth = entryWidth;
    }

    public float getType() {
        return type;
    }

    public void setType(float type) {
        this.type = type;
    }



    public CategoryPanel setFont(CustomFont font) {
        this.font = font;
        return this;
    }

    public CustomFont getFont() {
        return font;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setCategory(ModuleCategory category) {
        this.category = category;
    }

    public ModuleCategory getCategory() {
        return category;
    }
}
