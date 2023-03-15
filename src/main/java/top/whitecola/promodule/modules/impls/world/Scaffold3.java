package top.whitecola.promodule.modules.impls.world;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MouseFilter;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.PacketSendEvent;
import top.whitecola.promodule.events.impls.event.PreMotionEvent;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.*;

import static top.whitecola.promodule.utils.MCWrapper.*;


public class Scaffold3 extends AbstractModule {

    private ScaffoldUtils.BlockCache blockCache, lastBlockCache;
    private float y;
//    private float speed;
    private final MouseFilter pitchMouseFilter = new MouseFilter();
    private final TimerUtils delayTimer = new TimerUtils();
    private final TimerUtils timerUtil = new TimerUtils();
    public static double keepYCoord;
    private boolean shouldSendPacket;
    private boolean shouldTower;
    private boolean firstJump;
    private boolean pre;
    private int jumpTimer;
    private int slot;
    private int prevSlot;
    private float[] cachedRots = new float[2];





    @ModuleSetting(name = "sprint",type = "select")
    public Boolean sprint = false;

    @ModuleSetting(name = "itemSpoof",type = "select")
    public Boolean itemSpoof = true;

    @ModuleSetting(name = "delay",addValue = 0.1f)
    public Float delay = 0.1f;

    @ModuleSetting(name = "timer",addValue = 0.01f)
    public Float timer = 1f;

    @ModuleSetting(name = "speed",addValue = 0.01f)
    public Float speed = 1f;

    @ModuleSetting(name = "swing",type = "select")
    public Boolean swing = true;

    @ModuleSetting(name = "tower",type = "select")
    public Boolean tower = false;

    @ModuleSetting(name = "towerTimer",addValue = 0.1f)
    public Float towerTimer = 1.2f;

    @ModuleSetting(name = "keepY",type = "select")
    public Boolean keepY = false;

    @ModuleSetting(name = "speedSlowdown",type = "select")
    public Boolean speedSlowdown = true;



    @Override
    public void onPreMotion(PreMotionEvent e) {

        if(sprint){
            mc.thePlayer.setSprinting(false);
        }

        if (!mc.gameSettings.keyBindJump.isKeyDown()) {
            Wrapper.getTimer().timerSpeed = timer;
        } else {
            Wrapper.getTimer().timerSpeed = tower ? towerTimer : 1;
        }


        if(e.isPre()){


            mc.thePlayer.motionX *= speed;
            mc.thePlayer.motionZ *= speed;

            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));


            float[] rotations;
//            switch (rotationMode.getMode()) {
//                case "Watchdog":
            rotations = new float[]{PlayerSPUtils.getMoveYaw(e.getYaw()) - 180, y};
            e.setYaw(rotations[0]);
            e.setPitch(rotations[1]);
//                    break;
//                case "NCP":


//
//            float prevYaw = cachedRots[0];
//            if ((blockCache = ScaffoldUtils.getBlockInfo()) == null) {
//                blockCache = lastBlockCache;
//            }
//            if (blockCache != null && (mc.thePlayer.ticksExisted % 3 == 0
//                    || mc.theWorld.getBlockState(new BlockPos(e.getX(), ScaffoldUtils.getYLevel(), e.getZ())).getBlock() == Blocks.air)) {
//                cachedRots = RotationUtils.getRotations(blockCache.getPosition(), blockCache.getFacing());
//            }
//            if ((mc.thePlayer.onGround || (PlayerSPUtils.isMoving() && tower && mc.gameSettings.keyBindJump.isKeyDown())) && Math.abs(cachedRots[0] - prevYaw) >= 90) {
//                cachedRots[0] = PlayerSPUtils.getMoveYaw(e.getYaw()) - 180;
//            }
//            rotations = cachedRots;
////            e.setRotations(rotations[0], rotations[1]);
//            e.setYaw(rotations[0]);
//            e.setPitch(rotations[1]);
////            break;

//            }
            /////////////////////
            RotationUtils.setRotations(rotations);




            if (speedSlowdown && mc.thePlayer.isPotionActive(Potion.moveSpeed) && !mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.onGround) {
                PlayerSPUtils.setSpeed(0.1);
            }

            if (mc.thePlayer.onGround) {
                keepYCoord = Math.floor(mc.thePlayer.posY - 1.0);
            }

            if (tower && mc.gameSettings.keyBindJump.isKeyDown()) {
                double centerX = Math.floor(e.getX()) + 0.5, centerZ = Math.floor(e.getZ()) + 0.5;
//                switch (towerMode.getMode()) {
//                    case "Vanilla":
//                        mc.thePlayer.motionY = 0.42f;
//                        break;
//                    case "Verus":
//                        if (mc.thePlayer.ticksExisted % 2 == 0)
//                            mc.thePlayer.motionY = 0.42f;
//                        break;
//                    case "Watchdog":
                if (!PlayerSPUtils.isMoving() && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).down()).getBlock() != Blocks.air && lastBlockCache != null) {
                    if (mc.thePlayer.ticksExisted % 6 == 0) {
                        e.setX(centerX + 0.1);
                        e.setZ(centerZ + 0.1);
                    } else {
                        e.setX(centerX - 0.1);
                        e.setZ(centerZ - 0.1);
                    }
                    PlayerSPUtils.setSpeed(0);
                }

                mc.thePlayer.motionY = 0.3;
                e.setGround(true);
//                        break;
//                    case "NCP":
//                        if (!MovementUtils.isMoving() || MovementUtils.getSpeed() < 0.16) {
//                            if (mc.thePlayer.onGround) {
//                                mc.thePlayer.motionY = 0.42;
//                            } else if (mc.thePlayer.motionY < 0.23) {
//                                mc.thePlayer.setPosition(mc.thePlayer.posX, (int) mc.thePlayer.posY, mc.thePlayer.posZ);
//                                mc.thePlayer.motionY = 0.42;
//                            }
//                        }
//                        break;
            }



            // Setting Block Cache
            blockCache = ScaffoldUtils.getBlockInfo();
            if (blockCache != null) {
                lastBlockCache = ScaffoldUtils.getBlockInfo();
            } else {
                return;
            }

            if (mc.thePlayer.ticksExisted % 4 == 0) {
                pre = true;
            }

            // Placing Blocks (Pre)
            if (/*placeType.is("Pre") || (placeType.is("Dynamic") &&*/ pre) {
                if (place()) {
                    pre = false;
                }
            }



        }else {
            if (!itemSpoof) {
                mc.thePlayer.inventory.currentItem = slot;
            }

            // Placing Blocks (Post)
            if (/*placeType.is("Post") || (placeType.is("Dynamic")*/ !pre) {
                place();
            }

            pre = false;

        }







        super.onPreMotion(e);

    }

    @Override
    public void BlockPlaceableEvent() {
        place();
        super.BlockPlaceableEvent();
    }

    private boolean place() {
        int slot = ScaffoldUtils.getBlockSlot();
        if (blockCache == null || lastBlockCache == null || slot == -1) return false;

        if (this.slot != slot) {
            this.slot = slot;
            PacketUtils.sendPacketWithoutEvent(new C09PacketHeldItemChange(this.slot));
        }

        boolean placed = false;
        if (delayTimer.hasTimeElapsed((long) (delay * 1000))) {
            firstJump = false;
            if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,
                    mc.thePlayer.inventory.getStackInSlot(this.slot),
                    lastBlockCache.getPosition(), lastBlockCache.getFacing(),
                    ScaffoldUtils.getHypixelVec3(lastBlockCache))) {
                placed = true;
                y = MathUtils.getRandomInRange(79.5f, 83.5f);
                if (swing) {
//                    if (swingMode.is("Client")) {
                    mc.thePlayer.swingItem();
//                    } else {
//                        PacketUtils.sendPacket(new C0APacketAnimation());
//                    }
                }
            }
            delayTimer.reset();
            blockCache = null;
        }
        return placed;
    }



    @Override
    public void onTick() {
        if (mc.thePlayer == null) return;
//        if (hideJump.isEnabled() && !mc.gameSettings.keyBindJump.isKeyDown() && MovementUtils.isMoving() && !mc.thePlayer.onGround && autoJump.isEnabled()) {
//            mc.thePlayer.posY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
//            mc.thePlayer.lastTickPosY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
//            mc.thePlayer.cameraYaw = mc.thePlayer.cameraPitch = 0.1F;
//        }
//        if (downwards.isEnabled()) {
//            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
//            mc.thePlayer.movementInput.sneak = false;
//        }
        super.onTick();
    }





    @Override
    public void onSendPacket(PacketSendEvent e) {
//        if (e.getPacket() instanceof C0BPacketEntityAction
//                && ((C0BPacketEntityAction) e.getPacket()).getAction() == C0BPacketEntityAction.Action.START_SPRINTING
//                && sprint) {
//            e.setCancel();
//        }
        if (e.getPacket() instanceof C09PacketHeldItemChange && itemSpoof) {
            e.setCancel();
        }
        super.onSendPacket(e);
    }

    @Override
    public void onEnable() {
        if(mc.thePlayer!=null){
            prevSlot = mc.thePlayer.inventory.currentItem;
            slot = mc.thePlayer.inventory.currentItem;

//            if(mc.thePlayer.isSprinting()&&sprint){
//                PacketUtils.sendPacketWithoutEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
//            }
        }
        lastBlockCache = null;
        firstJump = true;
        speed = 1.1f;
        timerUtil.reset();
        jumpTimer = 0;
        y = 80;
        super.onEnable();
    }


    @Override
    public void onDisable() {
        if (mc.thePlayer != null) {
            if (!itemSpoof) mc.thePlayer.inventory.currentItem = prevSlot;
//            if (slot != mc.thePlayer.inventory.currentItem && itemSpoof)
//                PacketUtils.sendPacketWithoutEvent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));

//            if (auto3rdPerson.isEnabled()) {
//                mc.gameSettings.thirdPersonView = 0;
//            }
//            if (mc.thePlayer.isSneaking() && sneak.isEnabled())
//                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), GameSettings.isKeyDown(mc.gameSettings.keyBindSneak));
        }
        Wrapper.getTimer().timerSpeed = 1;
        super.onDisable();
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.WORLD;
    }

    @Override
    public String getModuleName() {
        return "Scaffold3";

    }

    public static boolean isDownwards() {
        return GameSettings.isKeyDown(mc.gameSettings.keyBindSneak);
    }
}
