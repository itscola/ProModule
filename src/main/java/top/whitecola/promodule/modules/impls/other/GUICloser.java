package top.whitecola.promodule.modules.impls.other;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import static top.whitecola.promodule.utils.MCWrapper.*;

public class GUICloser extends AbstractModule {

    @Override
    public void onAttackEntity(AttackEntityEvent e) {
        if(Minecraft.getMinecraft()==null||Minecraft.getMinecraft().theWorld==null || mc.thePlayer==null){
            return;
        }

        if(e.target!=null&&e.target.equals(mc.thePlayer)){
            mc.thePlayer.displayGui(null);
        }
        super.onAttackEntity(e);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.OTHER;
    }

    @Override
    public String getModuleName() {
        return "GUICloser";

    }
}
