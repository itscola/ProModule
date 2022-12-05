package top.whitecola.promodule.modules.impls.other;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.ServerInfoUtils;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class HypixelPlus extends AbstractModule {
    @ModuleSetting(name = "LevelHead" ,type = "select")
    public Boolean levelHead = true;

    @ModuleSetting(name = "Pings" ,type = "select")
    public Boolean pings = true;

    @ModuleSetting(name = "AutoGG" ,type = "select")
    public Boolean autoGG = true;

    @ModuleSetting(name = "AutoTip" ,type = "select")
    public Boolean autoTip = true;

    protected String[] trriger = {"胜者","1st","2st","3st","第一名","第二名","第三名","击杀数","winner"};
    boolean speaked;
    protected long lastTip;



    public void onChatReceive1(ClientChatReceivedEvent e) {
        if(!autoTip){
            return;
        }
        if(needTip()&&ServerInfoUtils.checkHypixel() ){
            tip();
        }

        super.onTick();
    }

    @Override
    public void onChatReceive(ClientChatReceivedEvent e) {
        onChatReceive1(e);

        if(!autoGG){
            return;
        }

        if(speaked){
            return;
        }

        if(e.message.getFormattedText().contains(":")){
            return;
        }

        for(String str : trriger){
            if(e.message.getFormattedText().contains(str)){
                if(e.message.getFormattedText().contains("任务") || e.message.getFormattedText().contains("加入")){
                    continue;
                }
                speaked = true;
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/achat GG");
                return;
            }
        }

        super.onChatReceive(e);
    }

    @Override
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        if(!autoGG){
            return;
        }
        if(!(e.entity instanceof EntityPlayerSP)){
            return;
        }

        if(speaked){
            speaked = false;
        }

        super.onEntityJoinWorld(e);
    }
    public void setLastTip(long lastTip) {
        this.lastTip = lastTip;
    }

    public long getLastTip() {
        return lastTip;
    }

    public void tip(){
        if(mc.theWorld==null || mc.thePlayer==null){
            return;
        }

        mc.thePlayer.sendChatMessage("/tipall");
        this.setLastTip(System.currentTimeMillis());
    }

    public boolean needTip(){
        if(System.currentTimeMillis()-getLastTip()>=300000 || getLastTip()==0){
            return true;
        }

        return false;
    }


    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.OTHER;
    }

    @Override
    public String getModuleName() {
        return "HypixelPlus";

    }
}
