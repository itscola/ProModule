package top.whitecola.promodule.modules.impls.world;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.opengl.GL11;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.modules.impls.render.Chams;
import top.whitecola.promodule.utils.RenderWings;

public class Comestic extends AbstractModule {
    private RenderWings renderWings = new RenderWings();



    @ModuleSetting(name = "DragonWings" ,type = "select")
    public Boolean dragonWings = false;


    @Override
    public void onRenderPlayer(RenderPlayerEvent.Post e) {
        if(dragonWings){
            EntityPlayer player = e.entityPlayer;
            if(player.isInvisible()){
                return;
            }

            if(player instanceof EntityPlayerSP){
                Chams chams = (Chams) ProModule.getProModule().getModuleManager().getModuleByName("Chams");
                boolean useChams = chams!=null&& chams.isEnabled();
                if(useChams){
                    GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
                    GL11.glPolygonOffset(1.0F,-1000000F);
                }
                renderWings.renderWings(player, e.partialRenderTick);
                if (useChams) {
                    GL11.glPolygonOffset(1.0F, 1000000F);
                    GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
                }
            }
        }

        super.onRenderPlayer(e);
    }


    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.WORLD;
    }

    @Override
    public String getModuleName() {
        return "Comestic";
    }
}
