package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import static top.whitecola.promodule.utils.MCWrapper.*;

import java.util.Vector;

public class AntiBot extends AbstractModule {
    public Vector<EntityLivingBase> entities = new Vector<EntityLivingBase>();

    @ModuleSetting(name = "IllegalName" ,type = "select")
    public Boolean illegalName = true;

    @ModuleSetting(name = "CheckTick" ,type = "select")
    public Boolean checkTick = true;


    @ModuleSetting(name = "PosCheck" ,type = "select")
    public Boolean posCheck = true;

    @Override
    public void onTick() {

        if(mc.theWorld==null||mc.thePlayer==null){
            return;
        }

        for (final EntityPlayer player : mc.theWorld.playerEntities) {
            if (player != mc.thePlayer) {

                if(checkTick){
                    if(player.ticksExisted<=0){
                        this.entities.add(player);
                        break;
                    }
                }



                if(illegalName){
                    final String valid = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_";
                    final String name = player.getName();

                    for (int i = 0; i < name.length(); i++) {
                        final String c = String.valueOf(name.charAt(i));
                        if (!valid.contains(c)) {
                            entities.add(player);
                            break;
                        }
                    }
                }

//                if(posCheck&& player.ticksExisted < 20 && player.posX == (int) mc.thePlayer.posX && (int) player.posZ == (int) mc.thePlayer.posZ && player.isInvisible()){
//                    entities.add(player);
//                    break;
//                }


            }
        }


        super.onTick();
    }

    @Override
    public void onEnable() {
        this.entities.clear();
        super.onEnable();
    }

    @Override
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        if(e.entity instanceof EntityPlayerSP){
            this.entities.clear();
        }

        super.onEntityJoinWorld(e);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "AntiBot";

    }
}
