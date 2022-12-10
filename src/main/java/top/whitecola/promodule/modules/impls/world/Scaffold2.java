package top.whitecola.promodule.modules.impls.world;

import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;
import top.whitecola.promodule.events.impls.event.PreMotionEvent;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.RotationUtils;
import top.whitecola.promodule.utils.ScaffoldUtils;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class Scaffold2 extends AbstractModule {
    private ScaffoldUtils.BlockCache blockCache, lastBlockCache;
    private float rotations[];

    @Override
    public void onPreMotion(PreMotionEvent e) {

        // Rotations
        if(lastBlockCache != null) {
            rotations = RotationUtils.getFacingRotations2(lastBlockCache.getPosition().getX(), lastBlockCache.getPosition().getY(), lastBlockCache.getPosition().getZ());
            mc.thePlayer.renderYawOffset = rotations[0];
            mc.thePlayer.rotationYawHead = rotations[0];
            e.setYaw(rotations[0]);
            e.setPitch(81);
//            mc.thePlayer.rotationPitchHead = 81;
        } else {
            e.setPitch(81);
            e.setYaw(mc.thePlayer.rotationYaw + 180);
//            mc.thePlayer.rotationPitchHead = 81;
            mc.thePlayer.renderYawOffset = mc.thePlayer.rotationYaw + 180;
            mc.thePlayer.rotationYawHead = mc.thePlayer.rotationYaw + 180;
        }

        // Speed 2 Slowdown
        if(mc.thePlayer.isPotionActive(Potion.moveSpeed.id)){
            mc.thePlayer.motionX *= 0.66;
            mc.thePlayer.motionZ *= 0.66;
        }

        // Setting Block Cache
        blockCache = ScaffoldUtils.grab();
        if (blockCache != null) {
            lastBlockCache = ScaffoldUtils.grab();
        }else{
            return;
        }

        // Setting Item Slot (Pre)
        int slot = ScaffoldUtils.grabBlockSlot();
        if(slot == -1) return;

        // Setting Slot
        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));

        // Placing Blocks (Pre)
//        if(placetype.getMode().equalsIgnoreCase("Pre")){
//            if (blockCache == null) return;
//            mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(slot), lastBlockCache.position, lastBlockCache.facing, ScaffoldUtils.getHypixelVec3(lastBlockCache));
//            if(swing.isEnabled()){
//                mc.thePlayer.swingItem();
//            }
//            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
//            blockCache = null;
//        }

        // Tower
//        if(tower.isEnabled()) {
//            if(mc.gameSettings.keyBindJump.isKeyDown()) {
//                mc.timer.timerSpeed = towerTimer.getValue().floatValue();
//                if(mc.thePlayer.motionY < 0) {
//                    mc.thePlayer.jump();
//                }
//            }else{
//                mc.timer.timerSpeed = 1;
//            }
//        }

        // Setting Item Slot (Post)
        slot = ScaffoldUtils.grabBlockSlot();
        if(slot == -1) return;

        // Placing Blocks (Post)
//        if(placetype.getMode().equalsIgnoreCase("Post")){
            if (blockCache == null) return;
            mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(slot), lastBlockCache.position, lastBlockCache.facing, ScaffoldUtils.getHypixelVec3(lastBlockCache));
//            if(swing.isEnabled()){
                mc.thePlayer.swingItem();
//            }
            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
            blockCache = null;
//        }




        super.onPreMotion(e);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.WORLD;
    }

    @Override
    public String getModuleName() {
        return "Scaffold";

    }
}
