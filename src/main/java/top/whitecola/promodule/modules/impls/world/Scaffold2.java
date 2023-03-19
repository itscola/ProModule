package top.whitecola.promodule.modules.impls.world;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.PreMotionEvent;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.modules.impls.movement.Sprint;
import top.whitecola.promodule.utils.*;

import java.util.concurrent.ThreadLocalRandom;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class Scaffold2 extends AbstractModule {
    private ScaffoldUtils.BlockCache blockCache, lastBlockCache;
    private float rotations[];
    private long last = 0;
    private float y;

    private long towerTicks;
    private long speedTicks;
    private boolean isSprint;

    @ModuleSetting(name = "Speed" ,type = "value",addValue = 0.01f)
    public Float speed = 0.82f;

    @ModuleSetting(name = "speedWait",type = "select")
    public Boolean speedWait = false;

    @ModuleSetting(name = "speedWaitingTick" ,addValue = 1f)
    public Float speedWaitingTick = 1f;

    @ModuleSetting(name = "tower",type = "select")
    public Boolean tower = true;

    @ModuleSetting(name = "towerWaitingTick" ,type = "value",addValue = 1f)
    public Float towerWaitingTick = 10f;

    @Override
    public void onPreMotion(PreMotionEvent e) {
//        mc.thePlayer.setSprinting(false);
        Sprint sprint = ProModule.getProModule().getModuleManager().getModuleByClass(Sprint.class);
        if(sprint.isEnabled()){
            isSprint = true;
            sprint.disable();
        }
//        if(){
//
//        }
        // Rotations

        if(lastBlockCache != null) {

//            rotations = new float[]{PlayerSPUtils.getMoveYaw(e.getYaw()) - 180, y};

//            rotations = RotationUtils.getRotations(lastBlockCache.getPosition(),lastBlockCache.getFacing());
            rotations = RotationUtils.getFacingRotations2(lastBlockCache.getPosition().getX(), lastBlockCache.getPosition().getY(), lastBlockCache.getPosition().getZ());
            e.setYaw(rotations[0]);
            e.setPitch((float) RandomUtils.nextDouble(81,83));
//            mc.thePlayer.rotationPitchHead = 81;
        }
        else {
            e.setPitch((float) RandomUtils.nextDouble(81,83));
            e.setYaw(mc.thePlayer.rotationYaw + 180);
//            mc.thePlayer.rotationPitchHead = 81;
//            mc.thePlayer.renderYawOffset = mc.thePlayer.rotationYaw + 180;
//            mc.thePlayer.rotationYawHead = mc.thePlayer.rotationYaw + 180;
        }

        // Speed 2 Slowdown
//        if(mc.thePlayer.isPotionActive(Potion.moveSpeed.id)){



//        if(speedWait){
//            if(speedTicks<speedWaitingTick) {
//                mc.thePlayer.motionX *= speed;
//                mc.thePlayer.motionZ *= speed;
//                speedTicks++;
//            }else {
//                mc.thePlayer.motionX = 0;
//                mc.thePlayer.motionZ = 0;
//                speedTicks = 0;
//                System.out.println(speedTicks);
//            }
//        }else {
            mc.thePlayer.motionX *= speed;
            mc.thePlayer.motionZ *= speed;
//        }



//        }

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

//         Placing Blocks (Pre)
//        if(e.isPre()){
//            if (blockCache == null) return;
//            mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(slot), lastBlockCache.position, lastBlockCache.facing, ScaffoldUtils.getHypixelVec3(lastBlockCache));
//            mc.thePlayer.swingItem();
//            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
//            blockCache = null;
//        }

        // Tower
        if(tower) {
            if(mc.gameSettings.keyBindJump.isKeyDown() && !PlayerSPUtils.isMoving()) {
                if(towerTicks<towerWaitingTick) {
                    mc.thePlayer.motionY = 0.42;
                    mc.thePlayer.jump();
                    towerTicks++;
                }else{
//                    mc.thePlayer.motionY = 0.28;
                    towerTicks = 0;
//                    System.out.println(towerTicks);
                }
//                if(mc.thePlayer.motionY < 0) {
//                }
            }
        }

        // Setting Item Slot (Post)
        slot = ScaffoldUtils.grabBlockSlot();
        if(slot == -1) return;

        // Placing Blocks (Post)
//        if(e.is){
        if (blockCache == null) return;

//        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(slot), lastBlockCache.position, lastBlockCache.facing, ScaffoldUtils.getHypixelVec3(lastBlockCache));
////            if(swing.isEnabled()){
//        mc.thePlayer.swingItem();
////            }
//        mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());

        long n = System.currentTimeMillis();
        if (n - this.last >= 25L && RayCastUtils.overBlock(new Vector2f(e.getYaw(), e.getPitch()),lastBlockCache.getFacing(),lastBlockCache.getPosition(),false)) {
            this.last = n;
            if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(slot), lastBlockCache.getPosition(), lastBlockCache.getFacing(), ScaffoldUtils.getHypixelVec3(lastBlockCache))) {
                mc.thePlayer.swingItem();
                mc.getItemRenderer().resetEquippedProgress();
                blockCache = null;
            }

        }

//        }




        super.onPreMotion(e);
    }


    private void sendRightClick(boolean state){
        int keycode = mc.gameSettings.keyBindUseItem.getKeyCode();

        if (state) {
            KeyBinding.onTick(keycode);
        }
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.WORLD;
    }

    @Override
    public String getModuleName() {
        return "Scaffold";

    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName()+" (NG)";
    }

    @Override
    public void onEnable() {
        lastBlockCache = null;
        this.last = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if(mc.theWorld==null||mc.thePlayer==null){
            return;
        }

        this.towerTicks = 0;

        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));


        Sprint sprint = ProModule.getProModule().getModuleManager().getModuleByClass(Sprint.class);


        if(isSprint&&!sprint.isEnabled()){
            System.out.println(00000);
            sprint.enable();
            isSprint = false;
        }

        super.onDisable();
    }
}
