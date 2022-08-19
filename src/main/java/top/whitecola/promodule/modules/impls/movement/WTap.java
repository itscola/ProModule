package top.whitecola.promodule.modules.impls.movement;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.modules.impls.combat.AimAssist;
import top.whitecola.promodule.utils.PlayerSPUtils;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class WTap extends AbstractModule {
    @ModuleSetting(name = "Delay_S")
    protected Float delay = 1f;

    @ModuleSetting(name = "RecoverDelay")
    protected Float recoverDelay = 0.3f;

    protected long last = 0;

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.MOVEMENT;
    }

    @Override
    public String getModuleName() {
        return "WTap";

    }

    @Override
    public void onAttackEntity(AttackEntityEvent e) {
        if(e.target==null){
            return;
        }

        if(!(e.target instanceof EntityLivingBase)){
            return;
        }

        if(!(e.target instanceof EntityPlayer)){
            return;
        }

//        if(!mc.thePlayer.isSprinting()&&System.currentTimeMillis()-last>=delay*1000){
//            mc.thePlayer.setSprinting(true);
//            return;
//        }

//        AimAssist aimAssist = (AimAssist) ProModule.getProModule().getModuleManager().getModuleByName("AimAssist");
//        if(aimAssist!=null&aimAssist.isEnabled()&&aimAssist.getTheTarget()!=null){
//            if(aimAssist.getTheTarget().equals(e.target)){
                if(System.currentTimeMillis()-last<=delay){
                    last = System.currentTimeMillis();
                    KeyBinding.onTick(mc.gameSettings.keyBindBack.getKeyCode());
                }
//            }
//        }


        super.onAttackEntity(e);
    }

    @Override
    public String getDisplayName() {
        return getModuleName()+" (G)";
    }
}
