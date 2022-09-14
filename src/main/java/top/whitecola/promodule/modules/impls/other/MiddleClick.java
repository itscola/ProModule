package top.whitecola.promodule.modules.impls.other;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import org.lwjgl.input.Mouse;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.PlayerSPUtils;

import java.util.Vector;

import static top.whitecola.promodule.utils.MCWrapper.mc;

public class MiddleClick extends AbstractModule {

    @ModuleSetting(name = "AddFriend",type = "select")
    protected Boolean addFriend = true;

    @ModuleSetting(name = "Only1World",type = "select")
    protected Boolean only1World = true;

    @ModuleSetting(name = "Only1",type = "select")
    protected Boolean only1 = false;

    @ModuleSetting(name = "OnlyFriend",type = "select")
    protected Boolean onlyFriend = false;

    protected Vector<EntityLivingBase> entities = new Vector<EntityLivingBase>();
    protected boolean wasClick;

    @Override
    public void onTick() {
        if(Minecraft.getMinecraft()==null||Minecraft.getMinecraft().theWorld==null || mc.thePlayer==null){
            return;
        }

        if(!addFriend){
            return;
        }

        if(!wasClick&&Mouse.isButtonDown(2)){
            Entity entity = mc.objectMouseOver.entityHit;

            if(entity==null){
                return;
            }

            if(!(entity instanceof EntityLivingBase)){
                return;
            }

            if(!(entity instanceof EntityPlayer)){
                return;
            }
            if(entities.contains((EntityLivingBase)entity)){
                this.entities.remove((EntityLivingBase) entity);
                PlayerSPUtils.sendMsgToSelf("remove friend: "+entity.getDisplayName().getFormattedText());

            }else{
                this.entities.add((EntityLivingBase) entity);
                PlayerSPUtils.sendMsgToSelf("Added new friend: "+entity.getDisplayName().getFormattedText());

            }

        }

        wasClick = Mouse.isButtonDown(2);

        super.onTick();
    }

    @Override
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        if(only1World && e.entity instanceof EntityPlayerSP){
            entities.clear();
        }

        super.onEntityJoinWorld(e);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.OTHER;
    }

    @Override
    public String getModuleName() {
        return "MiddleClick";

    }

    public Vector<EntityLivingBase> getFriends() {
        return entities;
    }
}
