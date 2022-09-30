package top.whitecola.promodule.modules.impls.render;

import net.minecraft.util.BlockPos;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

import java.util.Vector;

import static top.whitecola.promodule.utils.MCWrapper.mc;

public class Cave extends AbstractModule {
    private Vector<BlockPos> airs = new Vector<>();


    @Override
    public void onRender3D(int pass, float partialTicks, long finishTimeNano) {



        super.onRender3D(pass, partialTicks, finishTimeNano);
    }

    @Override
    public void onEnable() {
        mc.renderGlobal.loadRenderers();
        airs.clear();
        super.onEnable();
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.RENDERS;
    }

    @Override
    public String getModuleName() {
        return "Cave";
    }
}
