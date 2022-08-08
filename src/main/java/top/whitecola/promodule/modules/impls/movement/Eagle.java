package top.whitecola.promodule.modules.impls.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

import java.awt.*;

public class Eagle extends AbstractModule {
    private Robot robot;
    boolean lastShift;
    long time;

    public Eagle(){
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.MOVEMENT;
    }

    @Override
    public String getModuleName() {
        return "Eagle";

    }


    @Override
    public void onTick() {

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null || player.getEntityWorld() == null || !player.onGround) {
            return;
        }



        if (player.getEntityWorld().getBlockState(new BlockPos(player).add(0, -1, 0)).getBlock() == Blocks.air) {
            lastShift = true;
            time = System.currentTimeMillis();
            robot.keyPress(16);
            return;
        }

        if (lastShift && ((System.currentTimeMillis() - time) >= 85)) {
            lastShift = false;
            robot.keyRelease(16);
            return;
        }

        super.onTick();
    }
}
