package top.whitecola.promodule.modules.impls.world;

import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.HiThread;

import static top.whitecola.promodule.utils.MCWrapper.*;

import java.util.TimerTask;

public class BedBreaker extends AbstractModule {

    @ModuleSetting(name = "Range",addValue = 1)
    public Float range = 5f;

    @ModuleSetting(name = "Delay",addValue = 50f)
    public Float delay = 600f;

    private BlockPos blockPos = null;

    private java.util.Timer timer;
    private TimerTask timerTask;
    private boolean enabled;


//    private HiThread hiThread = new HiThread("breakThread",delay.intValue());

    @Override
    public synchronized void onEnable() {
        enabled = true;
        timerTask = t();
        (this.timer = new java.util.Timer()).schedule(timerTask, 0L, 600L);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if(this.timer!=null){
            enabled = false;
//            this.timer.cancel();
//            this.timer.purge();
            this.timer = null;
//            timerTask.cancel();
            timerTask = null;
        }
        this.blockPos = null;
        super.onDisable();
    }


    //skid temp
    public TimerTask t() {
        return new TimerTask() {
            public void run() {
                int ra = range.intValue();

                for(int y = ra; y >= -ra; --y) {
                    for(int x = -ra; x <= ra; ++x) {
                        for(int z = -ra; z <= ra; ++z) {

                            if(!enabled){
                                this.cancel();
                                return;
                            }

                            if (mc.thePlayer!=null&mc.theWorld!=null) {
                                BlockPos p = new BlockPos(mc.thePlayer.posX + (double) x, mc.thePlayer.posY + (double) y, mc.thePlayer.posZ + (double) z);
                                boolean bed = mc.theWorld.getBlockState(p).getBlock() == Blocks.bed;
                                if (blockPos == p) {
                                    if (!bed) {
                                        blockPos = null;
                                    }
                                } else if (bed) {
                                    breakBlock(p);
                                    blockPos = p;
                                    break;
                                }
                            }
                        }
                    }
                }

            }
        };
    }

    private void breakBlock(BlockPos p) {
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, p, EnumFacing.NORTH));
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, p, EnumFacing.NORTH));
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.WORLD;
    }

    @Override
    public String getModuleName() {
        return "BedBreaker";

    }

}
