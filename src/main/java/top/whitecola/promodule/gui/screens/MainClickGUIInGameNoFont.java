package top.whitecola.promodule.gui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import top.whitecola.animationlib.Animation;
import top.whitecola.animationlib.functions.type.CubicInFunction;
import top.whitecola.animationlib.functions.type.CubicOutFunction;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.fonts.font2.FontLoaders;
import top.whitecola.promodule.gui.UICache;
import top.whitecola.promodule.gui.components.clickables.ClickGUIEntry;
import top.whitecola.promodule.gui.components.clickables.SubEntryCategory;
import top.whitecola.promodule.gui.components.clickables.buttons.ClickGUISubEntry;
import top.whitecola.promodule.gui.components.clickables.buttons.LabelButton;
import top.whitecola.promodule.gui.components.clickables.buttons.LabelButtonWithFont;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.modules.impls.other.GUIBlur;
import top.whitecola.promodule.modules.impls.other.NoClickGUISound;
import top.whitecola.promodule.utils.*;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Vector;

public class MainClickGUIInGameNoFont extends GuiScreen implements IMainClickGUIIngame{
    protected float width = 300;
    protected float height = 200;
    protected float xPosition = 90;
    protected float yPosition = 16;

    protected float dragX;
    protected float dragY;

    protected boolean draged;

    private boolean needClose;
    private boolean closed;

    protected Color backgroundColor = new Color(224, 224, 224);
    protected Color mainColor = new Color(255, 255, 255);
    protected Color titleColor = new Color(0, 0, 0);
    protected Color subColor = new Color(4, 115, 130);

    protected Color barColor = new Color(189, 189, 189);
    protected Color githubColor = new Color(50, 50, 50);
    protected Color textColor = new Color(64, 45, 45);

    protected Color selectedEntryColor = new Color(51, 51, 51);
    protected Color selectedEntryTextColor = new Color(255, 255, 255);

    protected Color unSelectedSubEntryColor = new Color(224, 224, 224);
    protected Color unSelectedSubEntryTextColor = new Color(84, 84, 84);

    protected Color selectedSubEntryColor = new Color(51,51,51);
    protected Color selectedSubEntryTextColor = new Color(255, 255, 255);


    protected LabelButton combatLabel;
    protected LabelButton movementLabel;
    protected LabelButton renderLabel;
    protected LabelButton worldLabel;
    protected LabelButton miscLabel;

    protected Vector<ClickGUIEntry> entries = new Vector<ClickGUIEntry>();
    protected Vector<ClickGUISubEntry> subEntries = new Vector<ClickGUISubEntry>();
    protected int rollingValue = 0;

    protected ModuleUtils moduleUtils = new ModuleUtils();

    protected int subEntriesRollingValue = 0;

    protected Animation enableAnimation = new Animation();
    protected Animation closeAnimation = new Animation();
    protected Animation startScaleAnimation = new Animation();





    @Override
    public void initGui() {

        ProModule.getProModule().getModuleConfig().config.loadConfigForModules();

        combatLabel = new LabelButton(91,0,0,"Combat",titleColor);
        movementLabel = new LabelButton(92,0,0,"Movement",titleColor);
        renderLabel = new LabelButton(93,0,0,"Render",titleColor);
        worldLabel = new LabelButton(94,0,0,"World",titleColor);
        miscLabel = new LabelButton(95,0,0,"Other",titleColor);

        buttonList.add(combatLabel);
        buttonList.add(movementLabel);
        buttonList.add(renderLabel);
        buttonList.add(worldLabel);
        buttonList.add(miscLabel);

        loadDefaultEntries();

        highlightSubtitle(UICache.selectedSubtitle);


//        enableAnimation.setMin(0).setMax(8).setTotalTime(200).setFunction(new CubicOutFunction());
        startScaleAnimation.setMin(0.5f).setMax(1).setTotalTime(340).setFunction(new CubicOutFunction()).setLock(true);
//        System.out.println(startScaleAnimation.getMin()+"   "+startScaleAnimation.getMax());

        closeAnimation.setMin(0.5f).setMax(1).setTotalTime(200).setFunction(new CubicOutFunction()).setReverse(true).setLock(true);

        this.xPosition = ProModule.getProModule().getModuleConfig().getConfig().uix;
        this.yPosition = ProModule.getProModule().getModuleConfig().getConfig().uiy;
        super.initGui();
    }




    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        BlurUtils.doBlur(6);



        if(isClosed()){
            return;
        }

        if(isNeedClose()&&startScaleAnimation.isFinish()){
            float scale = closeAnimation.update();
            ScaledResolution sr = new ScaledResolution(mc);
            GLUtils.scaleStart(sr.getScaledWidth()/2f,-1,scale);
            if(closeAnimation.isFinish()){
                super.onGuiClosed();
                setClosed(true);
                Minecraft.getMinecraft().displayGuiScreen(null);
                return;
            }
        }else{
            float scale = startScaleAnimation.update();

            if(!startScaleAnimation.isFinish()) {
//            GlStateManager.scale(scale, scale, scale);
//            System.out.println(scale);
                ScaledResolution sr = new ScaledResolution(mc);

                GLUtils.scaleStart(sr.getScaledWidth()/2f,-1,scale);
            }
        }

//        GlStateManager.scale(1, 1, 1);


//        float value = enableAnimation.update();
//        if(!enableAnimation.isFinish()){
//            yPosition = 16 + value;
//        }
//        Minecraft
//        WorldClient


        GUIBlur guiBlur = (GUIBlur) ProModule.getProModule().getModuleManager().getModuleByName("GUIBlur");
        if(guiBlur!=null&& guiBlur.isEnabled()){
            BlurUtils.doBlur(7);
        }


        FontRenderer fontRenderer = mc.fontRendererObj;

//        GlStateManager.scale(1,1,1);
//
//        fontRenderer.drawStringWithShadow("Copyright by White_cola.",0 ,0,mainColor.getRGB());
//        GlStateManager.scale(scale, scale, scale);

        if(GUIUtils.isHovered(this.xPosition + width/3.5f, this.yPosition+3, this.xPosition + (this.width)/1.5f+3, this.yPosition + 20,mouseX,mouseY) && Mouse.isButtonDown(0)){

            if (dragX == 0 && dragY == 0) {
                dragX = mouseX - xPosition;
                dragY = mouseY - yPosition;
            } else {
                xPosition = mouseX - dragX;
                yPosition = mouseY - dragY;
            }
            draged = true;

        } else if (dragX != 0 || dragY != 0) {
            dragX = 0;
            dragY = 0;
            if(draged){
                UICache.mainUIPosX = xPosition;
                UICache.mainUIPosY = yPosition;
                draged = false;
            }
        }


        if(GUIUtils.isHovered(this.xPosition + (this.width)/4f, this.yPosition, this.xPosition+(width/1.4f), this.yPosition +this.height,mouseX,mouseY)){
            int dwheel = Mouse.getDWheel();

            if(dwheel<0 && Math.abs(rollingValue) +152 < (entries.size() * 26)+4){

                rollingValue -=10;

                //down

            }else if(dwheel>0&& rollingValue<0){
                rollingValue +=10;
                //up
            }
        }


        int round = 2;

        //background
        Render2DUtils.drawRoundedRect2(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, round,this.backgroundColor.getRGB());
        //left
        Render2DUtils.drawRoundedRect2(this.xPosition, this.yPosition, this.xPosition + (this.width)/4f, this.yPosition + this.height, round,this.mainColor.getRGB());



        //title
//        FontRenderer fontRenderer = mc.fontRendererObj;
//        fontRenderer.drawString("ProModule",(int)this.xPosition+8 ,(int)this.yPosition+7,titleColor.getRGB());
//        CustomFont.getCustomFont().fontRenderer.drawString("ProModule",(int)this.xPosition+8 ,(int)this.yPosition+6,titleColor.getRGB(),false);
        fontRenderer.drawString("ProModule",(int)this.xPosition+8 ,(int)this.yPosition+8,titleColor.getRGB());


        int range = 22;

        //clickableSubTitles
        combatLabel.xPosition = (int)this.xPosition+13;
        combatLabel.yPosition = (int)this.yPosition+45;
//        FontLoaders.msFont18.drawString("Combat",(int)this.xPosition+13 ,(int)this.yPosition+45,titleColor.getRGB());

        movementLabel.xPosition = (int)this.xPosition+13;
        movementLabel.yPosition = (int)this.yPosition+45 +range*1;
//        FontLoaders.msFont18.drawString("Movement",(int)this.xPosition+13 ,(int)this.yPosition+45 +range*1,titleColor.getRGB());

        renderLabel.xPosition = (int)this.xPosition+13;
        renderLabel.yPosition = (int)this.yPosition+45 +range*2;
//        FontLoaders.msFont18.drawString("Render",(int)this.xPosition+13 ,(int)this.yPosition+45+range*2,titleColor.getRGB());

        worldLabel.xPosition = (int)this.xPosition+13;
        worldLabel.yPosition = (int)this.yPosition+45 +range*3;
//        FontLoaders.msFont18.drawString("World",(int)this.xPosition+13 ,(int)this.yPosition+45+range*3,titleColor.getRGB());

        miscLabel.xPosition = (int)this.xPosition+13;
        miscLabel.yPosition = (int)this.yPosition+45+range*4;
//        FontLoaders.msFont18.drawString("Misc",(int)this.xPosition+13 ,(int)this.yPosition+45+range*4,titleColor.getRGB());



//        Render2DUtils.drawRoundedRect2(this.xPosition, this.yPosition+height/1.3f+18 -20, this.xPosition + (this.width)/4f +2- 40, this.yPosition + this.height -40, round,this.githubColor.getRGB());


        //github
        Render2DUtils.drawRoundedRect2(this.xPosition, this.yPosition+height/1.3f+18, this.xPosition + (this.width)/4f, this.yPosition + this.height, round,this.githubColor.getRGB());
        fontRenderer.drawString("Github",this.xPosition+23, this.yPosition+height/1.3f+28,mainColor.getRGB(),false);


        //right
        Render2DUtils.drawRoundedRect2(this.xPosition+(width/1.4f), this.yPosition, this.xPosition + this.width, this.yPosition + this.height, round,this.mainColor.getRGB());

        //entries
        for(int i=0;i<this.entries.size();i++) {
            float yRange = this.height/7 * i +26;
            float top = this.yPosition + 24;
            float bottom = this.yPosition + this.height;
            float y2 = this.yPosition + 24+yRange+rollingValue;

            if(y2>bottom){
                y2 = bottom-2;
            }

            float y1 = this.yPosition+yRange +rollingValue;

            if(y1 > bottom-8){
                continue;
            }

            if(y2<top){
                continue;
            }

            ClickGUIEntry entry = entries.get(i);

            entry.setxPosition(this.xPosition + width/3.8f);
            entry.setyPosition(y1);

            entry.setX2Position(this.xPosition + (this.width)/1.39f-6);
            entry.setY2Position(y2);

            if(entry.isEnabled()){
                Render2DUtils.drawRoundedRect2(this.xPosition + width/3.8f, y1, this.xPosition + (this.width)/1.39f-6, y2, 3,this.selectedEntryColor.getRGB());
                if(y2-y1<20){
                    continue;
                }
                fontRenderer.drawString(entry.getEntryDisplayName(),(this.xPosition + width/3.8f)+13, (this.yPosition+yRange)+8+rollingValue,selectedEntryTextColor.getRGB(),false);
            }else {
                Render2DUtils.drawRoundedRect2(this.xPosition + width/3.8f, y1, this.xPosition + (this.width)/1.39f-6, y2, 3,this.mainColor.getRGB());
                if(y2-y1<20){
                    continue;
                }
                fontRenderer.drawString(entry.getEntryDisplayName(),(this.xPosition + width/3.8f)+13, (this.yPosition+yRange)+8+rollingValue,textColor.getRGB(),false);
            }


        }


//        //items
//        for(int i=0;i<6;i++){
//            float yRange = this.height/7 * i +26;
//            Render2DUtils.drawRoundedRect2(this.xPosition + width/3.8f, this.yPosition+yRange, this.xPosition + (this.width)/1.39f-6, this.yPosition + 24+yRange, 3,this.mainColor.getRGB());
////            FontRenderer fontRenderer = mc.fontRendererObj;
////            fontRenderer.drawString("Reach",(int)(this.xPosition + width/3.8f)+19, (int)(this.yPosition+yRange)+8,textColor.getRGB());
////            FontLoaders.msFont18.drawString("Reach",(this.xPosition + width/3.8f)+9, (this.yPosition+yRange)+8,textColor.getRGB(),false);
//        }


        //middle
        Render2DUtils.drawRect(this.xPosition + (this.width)/4f, this.yPosition, this.xPosition+(width/1.4f), this.yPosition + 24,this.backgroundColor.getRGB());
        Render2DUtils.drawRoundedRect2(this.xPosition + width/3.5f, this.yPosition+3, this.xPosition + (this.width)/1.5f+3, this.yPosition + 20, 8,this.barColor.getRGB());

        Render2DUtils.drawRoundedRect2(this.xPosition + width/3.5f +4 +33, this.yPosition+4, this.xPosition + (this.width)/1.5f+3-40, this.yPosition +10, 0.5f,this.githubColor.getRGB());

        //bottom
        Render2DUtils.drawRect(this.xPosition + (this.width)/4f, this.yPosition+this.height-4, this.xPosition+(width/1.4f), this.yPosition + height,this.backgroundColor.getRGB());


        //right sub-entry
        int subRange = 28;
        int bigRange = 10;


//        Render2DUtils.drawRoundedRect2(this.xPosition+(width/1.4f)+4, this.yPosition+4, this.xPosition + this.width-4, this.yPosition + 28, round,this.unSelectedSubEntryColor.getRGB());
//        FontLoaders.msFont18.drawString("Vertical",this.xPosition+(width/1.4f)+4+10, this.yPosition+4+8,unSelectedSubEntryTextColor.getRGB(),false);
//
//        Render2DUtils.drawRoundedRect2(this.xPosition+(width/1.4f)+4, this.yPosition+4 + subRange, this.xPosition + this.width-4, this.yPosition + 28 + subRange, round,this.unSelectedSubEntryColor.getRGB());
//        FontLoaders.msFont18.drawString("WhileAttack",this.xPosition+(width/1.4f)+4+10, this.yPosition+4+8+ subRange,unSelectedSubEntryTextColor.getRGB(),false);
//
//        Render2DUtils.drawRoundedRect2(this.xPosition+(width/1.4f)+4, this.yPosition+4 + subRange*2, this.xPosition + this.width-4, this.yPosition + 28 + subRange*2 +10, round,this.unSelectedSubEntryColor.getRGB());
//        FontLoaders.msFont18.drawString("Speed - 50",this.xPosition+(width/1.4f)+4+10, this.yPosition+2+8+ subRange*2,unSelectedSubEntryTextColor.getRGB(),false);
//
//        Render2DUtils.drawRoundedRect2(this.xPosition+(width/1.4f)+4, this.yPosition+4 + subRange*3 +10, this.xPosition + this.width-4, this.yPosition + 28 + subRange*3 + 10, round,this.unSelectedSubEntryColor.getRGB());
//        FontLoaders.msFont18.drawString("AttackFirst",this.xPosition+(width/1.4f)+4+10, this.yPosition+4+8+ subRange*3 +10,unSelectedSubEntryTextColor.getRGB(),false);
//
//        Render2DUtils.drawRoundedRect2(this.xPosition+(width/1.4f)+4, this.yPosition+4 + subRange*4 +10, this.xPosition + this.width-4, this.yPosition + 28 + subRange*4 + 10, round,this.unSelectedSubEntryColor.getRGB());
//        FontLoaders.msFont18.drawString("AntiBot",this.xPosition+(width/1.4f)+4+10, this.yPosition+4+8+ subRange*4 +10,unSelectedSubEntryTextColor.getRGB(),false);



        int bigSubEntriesNum = 0;
        int top = 15;


        if(GUIUtils.isHovered(this.xPosition+(width/1.4f), this.yPosition, this.xPosition + this.width, this.yPosition + this.height,mouseX,mouseY)){
            int dwheel = Mouse.getDWheel();

            if(dwheel<0 && Math.abs(subEntriesRollingValue) +152 < (subEntries.size() * 28)){

                subEntriesRollingValue -=10;

                //down

            }else if(dwheel>0&& subEntriesRollingValue<0){
                subEntriesRollingValue +=10;
                //up
            }
        }




        for(int i=0;i<this.subEntries.size();i++){
            float theTop = this.yPosition + 14;
            float theBottom = this.yPosition+this.height-6;


            ClickGUISubEntry entry = subEntries.get(i);


            if(entry==null){
                continue;
            }

            float theFirstY = this.yPosition+4 + subRange*i + bigRange*bigSubEntriesNum + top+subEntriesRollingValue;

            if(entry.getCategory()== SubEntryCategory.Boolean){

                float theLastY =this.yPosition + 28 + subRange*i + bigRange*bigSubEntriesNum + top+subEntriesRollingValue ;

                if(theLastY<=theTop){
                    continue;
                }

                if(theFirstY>=theBottom){
                    continue;
                }

                if(theFirstY<=theTop){
                    theFirstY = theTop;
                }

                if(theLastY>=theBottom){
                    theLastY = theBottom;
                }

                float theTextFristY = this.yPosition+4+8+ subRange*i+ bigRange*bigSubEntriesNum +top+subEntriesRollingValue;
                float theTextLastY = theTextFristY + 8;


                if(theTextFristY<=theTop || theTextLastY>=theBottom){
                    theTextFristY = -20;
                }


                if(entry.isEnabled()){
                    Render2DUtils.drawRoundedRect2(this.xPosition+(width/1.4f)+4, theFirstY, this.xPosition + this.width-4, theLastY, round,this.selectedSubEntryColor.getRGB());
                    fontRenderer.drawString(entry.getEntryDisplayName(),this.xPosition+(width/1.4f)+4+10, theTextFristY,selectedSubEntryTextColor.getRGB(),false);
                }else{
                    Render2DUtils.drawRoundedRect2(this.xPosition+(width/1.4f)+4, theFirstY, this.xPosition + this.width-4, theLastY, round,this.unSelectedSubEntryColor.getRGB());
                    fontRenderer.drawString(entry.getEntryDisplayName(),this.xPosition+(width/1.4f)+4+10, theTextFristY,unSelectedSubEntryTextColor.getRGB(),false);
                }

                entry.setxPosition(this.xPosition+(width/1.4f)+4);
                entry.setX2Position(this.xPosition + this.width-4);


                entry.setyPosition(theFirstY);
                entry.setY2Position(theLastY);
            }else if(entry.getCategory()== SubEntryCategory.Value){

                float theLastY =this.yPosition + 28 + subRange*i + bigRange*bigSubEntriesNum + 10 + top+subEntriesRollingValue;

                if(theLastY<=theTop){
                    continue;
                }

                if(theFirstY>=theBottom){
                    continue;
                }

                if(theFirstY<=theTop){
                    theFirstY = theTop;
                }

                if(theLastY>=theBottom){
                    theLastY = theBottom;
                }

                float theTextFristY = this.yPosition+2+8+ subRange*i + bigRange*bigSubEntriesNum + top+subEntriesRollingValue;
                float theTextLastY = theTextFristY + 8;



                if(theTextFristY<=theTop || theTextLastY>=theBottom){
                    theTextFristY = -20;
                }



                Render2DUtils.drawRoundedRect2(this.xPosition+(width/1.4f)+4, theFirstY, this.xPosition + this.width-4, theLastY, round,this.unSelectedSubEntryColor.getRGB());
                fontRenderer.drawString(entry.getEntryDisplayName(),this.xPosition+(width/1.4f)+2+10, theTextFristY,unSelectedSubEntryTextColor.getRGB(),false);
                fontRenderer.drawString("- "+entry.getValue(),this.xPosition+(width/1.4f)+2+10, this.yPosition+2+8+10+ subRange*i + bigRange*bigSubEntriesNum + top+subEntriesRollingValue,unSelectedSubEntryTextColor.getRGB(),false);


                entry.setxPosition(this.xPosition+(width/1.4f)+4);
                entry.setX2Position(this.xPosition + this.width-4);

                entry.setyPosition(theFirstY);
                entry.setY2Position(theLastY);

                bigSubEntriesNum++;

            }

            Render2DUtils.drawRoundedRect2(this.xPosition+(width/1.4f), this.yPosition, this.xPosition + this.width, this.yPosition + 14,round,this.mainColor.getRGB());
            Render2DUtils.drawRoundedRect2(this.xPosition+(width/1.4f), this.yPosition+this.height-8, this.xPosition + this.width, this.yPosition + this.height,round,this.mainColor.getRGB());

            fontRenderer.drawString(subEntries.get(0).getModule().getModuleName(),this.xPosition+(width/1.4f)+4+1, this.yPosition+4+1 ,textColor.getRGB(),false);


        }


        super.drawScreen(mouseX,mouseY,partialTicks);
    }



    @Override
    public void onGuiClosed() {
        UICache.selectedSubtitle = 0;
        ProModule.getProModule().getModuleConfig().config.uix = this.xPosition;
        ProModule.getProModule().getModuleConfig().config.uiy = this.yPosition;

        ProModule.getProModule().getModuleConfig().config.modulesToConfig();
        ProModule.getProModule().getModuleConfig().saveConfig();
        super.onGuiClosed();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {


        if(GUIUtils.isHovered(this.xPosition, this.yPosition+height/1.3f+18, this.xPosition + (this.width)/4f, this.yPosition + this.height,mouseX,mouseY)){
            playButtonSound();
            if(mouseButton==0&&Math.abs(subEntriesRollingValue) +152 < (subEntries.size() * 28)) {
                subEntriesRollingValue -=20;
            }else if(mouseButton==1&& subEntriesRollingValue<0){
                subEntriesRollingValue +=20;
            }

        }

        if(GUIUtils.isHovered(this.xPosition + width/3.5f +4 +33, this.yPosition+4, this.xPosition + (this.width)/1.5f+3-40, this.yPosition +10,mouseX,mouseY)){
            playButtonSound();
            if(mouseButton==0&& Math.abs(rollingValue) +152 < (entries.size() * 26)+4) {
                rollingValue -=20;
            }else if(mouseButton==1&& rollingValue<0){
                rollingValue +=20;
            }

        }

        //left:0 right:1

        for(ClickGUIEntry entry : entries){
            if(GUIUtils.isHovered(entry.getxPosition(),entry.getyPosition(),entry.getX2Position(),entry.getY2Position(),mouseX,mouseY)){
                if(mouseY<yPosition +24|| mouseY>yPosition+height){
                    continue;
                }
                if(mouseButton==0) {
                    entry.toggle();
                    playButtonSound();
                    loadSubEntries(entry.getEntryName());
                }else if(mouseButton==1){
                    loadSubEntries(entry.getEntryName());
                    playButtonSound();
                }
            }
        }



        for(ClickGUISubEntry entry : subEntries){
            if(GUIUtils.isHovered(entry.getxPosition(),entry.getyPosition(),entry.getX2Position(),entry.getY2Position(),mouseX,mouseY)) {
                if(entry.getCategory()==SubEntryCategory.Boolean){
                    entry.getModule().toogleBooleanSetting(entry);
                    playButtonSound();
                }else if(entry.getCategory()==SubEntryCategory.Value) {
                    System.out.println(entry.getAddValue());
                    if (mouseButton == 0) {
                        float value = absoluteAdd(entry.getValue(),entry.getAddValue());
                        entry.getModule().setFloatSetting(entry.getEntryName(),value);
                        entry.setValue(value);
                    }else if(mouseButton == 1){
                        float value = absoluteAdd(entry.getValue(),-entry.getAddValue());
                        entry.getModule().setFloatSetting(entry.getEntryName(),value);
                        entry.setValue(value);
                    }
                    playButtonSound();
                }
            }
        }


        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int p_mouseReleased_1_, int p_mouseReleased_2_, int p_mouseReleased_3_) {
        super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_2_, p_mouseReleased_3_);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        highlightSubtitle(button.id);

        super.actionPerformed(button);
    }

    public void highlightSubtitle(int id){
        clearAllSubstitleHighlight();
        UICache.selectedSubtitle = id;

        if(combatLabel.id==UICache.selectedSubtitle){
            combatLabel.color = subColor;
            loadEntriesByCategory(ModuleCategory.COMBAT);
        }else if(movementLabel.id==UICache.selectedSubtitle){
            movementLabel.color = subColor;
            loadEntriesByCategory(ModuleCategory.MOVEMENT);
        }else if(renderLabel.id==UICache.selectedSubtitle){
            renderLabel.color = subColor;
            loadEntriesByCategory(ModuleCategory.RENDERS);
        }else if(worldLabel.id==UICache.selectedSubtitle){
            worldLabel.color = subColor;
            loadEntriesByCategory(ModuleCategory.WORLD);
        }else if(miscLabel.id==UICache.selectedSubtitle){
            miscLabel.color = subColor;
            loadEntriesByCategory(ModuleCategory.OTHER);

        }
    }

    public void clearAllSubstitleHighlight(){
        UICache.selectedSubtitle = 0;
        combatLabel.color = titleColor;
        movementLabel.color = titleColor;
        renderLabel.color = titleColor;
        worldLabel.color = titleColor;
        miscLabel.color = titleColor;


    }

    public void loadDefaultEntries(){
        clearEntries();
//        Vector<AbstractModule> modules = ProModule.getProModule().getModuleManager().getModules();
//        for(AbstractModule module: modules){
//            addEntrie(new ClickGUIEntry().fromModule(module));
//        }
        loadEntriesByCategory(ModuleCategory.COMBAT);
    }

    public void loadEntriesByCategory(ModuleCategory category){
        clearEntries();
        Vector<AbstractModule> modules = ProModule.getProModule().getModuleManager().getModules();
        for(AbstractModule module: modules){
            if(module==null) continue;
            if(module.getModuleType().equals(category)) {
                addEntrie(new ClickGUIEntry().fromModule(module));
            }
        }
    }


    public void addEntrie(ClickGUIEntry entry){
        this.entries.add(entry);
    }

    public void removeEntrie(ClickGUIEntry entry){
        this.entries.remove(entry);
    }

    public void clearEntries(){
        this.rollingValue = 0;
        this.entries.clear();
    }

    private void playButtonSound(){
        NoClickGUISound noClickGUISound = (NoClickGUISound) ProModule.getProModule().getModuleManager().getModuleByName("NoClickGUISound");
        if(noClickGUISound!=null&& noClickGUISound.isEnabled()){
            return;
        }
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    private void loadSubEntries(String moduleName){

        try {
            Vector<ClickGUISubEntry> subEntries = moduleUtils.subEntriesfromModuleName(moduleName);
            if(subEntries == null){
                return;
            }
            this.subEntriesRollingValue = 0;
            this.subEntries = subEntries;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void drawString(String text, float x, int y, int color) {

    }


    private float absoluteAdd(float a,float b){
        BigDecimal a1 = new BigDecimal(a+"");
        return a1.add(new BigDecimal(b+"")).floatValue();
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean isNeedClose() {
        return needClose;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void setNeedClose(boolean needClose) {
        this.needClose = needClose;
    }


}
