package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraftforge.client.event.RenderWorldEvent;
import org.lwjgl.Sys;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.WorldRenderEvent;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.PlayerSPUtils;
import top.whitecola.promodule.utils.RandomUtils;
import top.whitecola.promodule.utils.ServerUtils;

import java.util.Vector;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class Killaura extends AbstractModule {

    private Vector<Integer> grounded = new Vector<Integer>();

    protected long lastAttack;
    protected int delay;

    @ModuleSetting(name = "MinCPS",addValue = 1f)
    public Float minCPS = 6f;

    @ModuleSetting(name = "MaxCPS",addValue = 1f)
    public Float maxCPS = 10f;


    @Override
    public void worldRenderEvent(WorldRenderEvent e) {
        if(System.currentTimeMillis() - this.lastAttack >= delay) {
            lastAttack = System.currentTimeMillis();


            Entity pointedEntity = mc.pointedEntity;
            if(pointedEntity==null){
                return;
            }


            if(pointedEntity.isEntityAlive()&&!isBot((EntityLivingBase) pointedEntity)){
                return;
            }



            delay = (int) RandomUtils.nextDouble(1000f/minCPS,1000f/maxCPS);

//            mc.thePlayer.swingItem();
//            mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(pointedEntity, C02PacketUseEntity.Action.ATTACK));
            sendLeftClick(true);
            sendLeftClick(false);
        }
        super.worldRenderEvent(e);
    }

    private void sendLeftClick(boolean state){
        int keycode = mc.gameSettings.keyBindAttack.getKeyCode();
//        System.out.println(keycode);
        if (state) {
            KeyBinding.onTick(keycode);
        }
    }

    private void sendRightClick(boolean state){
        int keycode = mc.gameSettings.keyBindUseItem.getKeyCode();

        if (state) {
            KeyBinding.onTick(keycode);
        }
    }

    public boolean isBot(EntityLivingBase entity){
        if (entity == null) {
            return true;
        }

        if (entity.isPlayerSleeping()) {
            return true;
        }

        if (entity.getEntityId() > 1000000) {
            return true;
        }

        if (!ServerUtils.isInTabList(entity)) {
            return true;
        }

        if (((EntityPlayer) entity).ticksExisted <= 80) {
            return true;
        }

        if (!this.grounded.contains(entity.getEntityId())) {
            return true;
        }

        if(entity.getDisplayName().getFormattedText().contains("[NPC]")){
            return true;
        }

        AntiBot antiBot = (AntiBot) ProModule.getProModule().getModuleManager().getModuleByName("AntiBot");
        if(antiBot!=null&&antiBot.isEnabled()){
            if(antiBot.entities.contains(entity)){
                return true;
            };
        }

        return false;
    }


    @Override
    public void onTick() {
        if(mc.theWorld==null){
            return;
        }

        for(EntityLivingBase entity : mc.theWorld.playerEntities){
            if(entity==null){
                continue;
            }
            if(entity.onGround){
                if(!grounded.contains(entity.getEntityId())){
                    grounded.add(entity.getEntityId());
                }
            }


        }
        super.onTick();
    }

    @Override
    public void onEnable() {
        this.grounded.clear();
        super.onEnable();
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "Killaura";
    }
    @Override
    public String getDisplayName() {
        return this.getModuleName();
    }
}
