package top.whitecola.promodule.modules.impls.world;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.*;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static top.whitecola.promodule.utils.MCWrapper.*;


public class Scaffold extends AbstractModule {


    EnumFacing enumFacing;
    float curYaw, curPitch;
    Vec3i rotate = null;
    int slot;
    List<Block> blackList;
    long last;
    long turnspeed = 6;
    boolean hypixel;

    public Scaffold(){
        blackList = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava,
                Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane,
                Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice,
                Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.trapped_chest,
                Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt,
                Blocks.gold_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore,
                Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate,
                Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button,
                Blocks.wooden_button, Blocks.lever, Blocks.tallgrass, Blocks.tripwire, Blocks.tripwire_hook,
                Blocks.rail, Blocks.waterlily, Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom,
                Blocks.vine, Blocks.trapdoor, Blocks.yellow_flower, Blocks.ladder, Blocks.furnace, Blocks.sand, Blocks.gravel,
                Blocks.ender_chest,
                Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.crafting_table, Blocks.web,
                Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence, Blocks.redstone_torch);
    }


    //temp skid
    @Override
    public void onTick() {
        if (mc.theWorld == null || mc.thePlayer==null) {
            return;
        }

        BlockPos blockPos = getBlockPosToPlaceOn(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ));
        if (blockPos != null) rotate = translate(blockPos, enumFacing);
        ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(slot);

        if (!(itemStack != null && (itemStack.getItem() instanceof ItemBlock))) {
            if (slot != getBlockSlot()) {
                if (getBlockSlot() == -1) return;
                mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(getBlockSlot()));
            }
        }


        if (blockPos != null && itemStack != null && itemStack.getItem() instanceof ItemBlock) {
            float[] rotation = hypixel ? getRotation(rotate, curYaw, curPitch, 6 * 30)
                    : faceBlock(blockPos, (float) (mc.theWorld.getBlockState(blockPos).getBlock().getBlockBoundsMaxY() - mc.theWorld.getBlockState(blockPos).getBlock().getBlockBoundsMinY()) + 0.5F, curYaw, curPitch, turnspeed * 30);

            curYaw = rotation[0];
            curPitch = rotation[1];


            MovingObjectPosition ray = rayCastedBlock(curYaw, curPitch);

            long n = System.currentTimeMillis();
            if (n - this.last >= 500L&& ray != null && ray.getBlockPos().equals(blockPos) ) {
                Vec3 hitVec = hypixel ? new Vec3(rotate.getX(), rotate.getY(), rotate.getZ()) : ray != null ? ray.hitVec : new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());

                if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, itemStack, blockPos, enumFacing, hitVec)) {
                    mc.thePlayer.swingItem();
                }
            }
        }



        super.onTick();
    }


    @Override
    public void onEnable() {
        hypixel = false;

        if (mc.theWorld == null || mc.thePlayer==null) {
            return;
        }
        curYaw = mc.thePlayer.rotationYaw;
        curPitch = mc.thePlayer.rotationPitch;
        slot = mc.thePlayer.inventory.currentItem;
        super.onEnable();
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.WORLD;
    }

    @Override
    public String getModuleName() {
        return "Scaffold";

    }

    private BlockPos getBlockPosToPlaceOn(BlockPos pos) {
        BlockPos blockPos1 = pos.add(-1, 0, 0);
        BlockPos blockPos2 = pos.add(1, 0, 0);
        BlockPos blockPos3 = pos.add(0, 0, -1);
        BlockPos blockPos4 = pos.add(0, 0, 1);
        float down = 0;
        if (mc.theWorld.getBlockState(pos.add(0, -1 - down, 0)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.UP;
            return (pos.add(0, -1, 0));
        } else if (mc.theWorld.getBlockState(pos.add(-1, 0 - down, 0)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.EAST;
            return (pos.add(-1, 0 - down, 0));
        } else if (mc.theWorld.getBlockState(pos.add(1, 0 - down, 0)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.WEST;
            return (pos.add(1, 0 - down, 0));
        } else if (mc.theWorld.getBlockState(pos.add(0, 0 - down, -1)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.SOUTH;
            return (pos.add(0, 0 - down, -1));
        } else if (mc.theWorld.getBlockState(pos.add(0, 0 - down, 1)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.NORTH;
            return (pos.add(0, 0 - down, 1));
        } else if (mc.theWorld.getBlockState(blockPos1.add(0, -1 - down, 0)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.UP;
            return (blockPos1.add(0, -1 - down, 0));
        } else if (mc.theWorld.getBlockState(blockPos1.add(-1, 0 - down, 0)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.EAST;
            return (blockPos1.add(-1, 0 - down, 0));
        } else if (mc.theWorld.getBlockState(blockPos1.add(1, 0 - down, 0)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.WEST;
            return (blockPos1.add(1, 0 - down, 0));
        } else if (mc.theWorld.getBlockState(blockPos1.add(0, 0 - down, -1)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.SOUTH;
            return (blockPos1.add(0, 0 - down, -1));
        } else if (mc.theWorld.getBlockState(blockPos1.add(0, 0 - down, 1)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.NORTH;
            return (blockPos1.add(0, 0 - down, 1));
        } else if (mc.theWorld.getBlockState(blockPos2.add(0, -1 - down, 0)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.UP;
            return (blockPos2.add(0, -1 - down, 0));
        } else if (mc.theWorld.getBlockState(blockPos2.add(-1, 0 - down, 0)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.EAST;
            return (blockPos2.add(-1, 0 - down, 0));
        } else if (mc.theWorld.getBlockState(blockPos2.add(1, 0 - down, 0)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.WEST;
            return (blockPos2.add(1, 0 - down, 0));
        } else if (mc.theWorld.getBlockState(blockPos2.add(0, 0 - down, -1)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.SOUTH;
            return (blockPos2.add(0, 0 - down, -1));
        } else if (mc.theWorld.getBlockState(blockPos2.add(0, 0 - down, 1)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.NORTH;
            return (blockPos2.add(0, 0 - down, 1));
        } else if (mc.theWorld.getBlockState(blockPos3.add(0, -1 - down, 0)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.UP;
            return (blockPos3.add(0, -1 - down, 0));
        } else if (mc.theWorld.getBlockState(blockPos3.add(-1, 0 - down, 0)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.EAST;
            return (blockPos3.add(-1, 0 - down, 0));
        } else if (mc.theWorld.getBlockState(blockPos3.add(1, 0 - down, 0)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.WEST;
            return (blockPos3.add(1, 0 - down, 0));
        } else if (mc.theWorld.getBlockState(blockPos3.add(0, 0 - down, -1)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.SOUTH;
            return (blockPos3.add(0, 0 - down, -1));
        } else if (mc.theWorld.getBlockState(blockPos3.add(0, 0 - down, 1)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.NORTH;
            return (blockPos3.add(0, 0 - down, 1));
        } else if (mc.theWorld.getBlockState(blockPos4.add(0, -1 - down, 0)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.UP;
            return (blockPos4.add(0, -1 - down, 0));
        } else if (mc.theWorld.getBlockState(blockPos4.add(-1, 0 - down, 0)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.EAST;
            return (blockPos4.add(-1, 0 - down, 0));
        } else if (mc.theWorld.getBlockState(blockPos4.add(1, 0 - down, 0)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.WEST;
            return (blockPos4.add(1, 0 - down, 0));
        } else if (mc.theWorld.getBlockState(blockPos4.add(0, 0 - down, -1)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.SOUTH;
            return (blockPos4.add(0, 0 - down, -1));
        } else if (mc.theWorld.getBlockState(blockPos4.add(0, 0 - down, 1)).getBlock() != Blocks.air) {
            enumFacing = EnumFacing.NORTH;
            return (blockPos4.add(0, 0 - down, 1));
        }
        return null;
    }

    public static Vec3i translate(BlockPos blockPos, EnumFacing enumFacing) {
        double x = blockPos.getX();
        double y = blockPos.getY();
        double z = blockPos.getZ();
        double r1 = ThreadLocalRandom.current().nextDouble(0.3, 0.5);
        double r2 = ThreadLocalRandom.current().nextDouble(0.9, 1.0);
        if (enumFacing.equals(EnumFacing.UP)) {
            x += r1;
            z += r1;
            y += 1.0;
        } else if (enumFacing.equals(EnumFacing.DOWN)) {
            x += r1;
            z += r1;
        } else if (enumFacing.equals(EnumFacing.WEST)) {
            y += r2;
            z += r1;
        } else if (enumFacing.equals(EnumFacing.EAST)) {
            y += r2;
            z += r1;
            x += 1.0;
        } else if (enumFacing.equals(EnumFacing.SOUTH)) {
            y += r2;
            x += r1;
            z += 1.0;
        } else if (enumFacing.equals(EnumFacing.NORTH)) {
            y += r2;
            x += r1;
        }
        return new Vec3i(x, y, z);
    }


    private int getBlockSlot() {

        int slot = -1;

        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];

            if (itemStack == null || !(itemStack.getItem() instanceof ItemBlock) || itemStack.stackSize < 1)
                continue;

            final ItemBlock block = (ItemBlock) itemStack.getItem();

            if (blackList.contains(block.getBlock()))
                continue;

            slot = i;
            break;
        }

        return slot;
    }


    private float[] getRotation(Vec3i vec3, float currentYaw, float currentPitch, float speed) {
        double xdiff = vec3.getX() - mc.thePlayer.posX;
        double zdiff = vec3.getZ() - mc.thePlayer.posZ;
        double y = vec3.getY();
        double posy = mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight() - y;
        double lastdis = MathHelper.sqrt_double(xdiff * xdiff + zdiff * zdiff);
        float calcYaw = (float) (Math.atan2(zdiff, xdiff) * 180.0 / Math.PI) - 90.0f;
        float calcPitch = (float) (Math.atan2(posy, lastdis) * 180.0 / Math.PI);
        if (Float.compare(calcYaw, 0.0f) < 0)
            calcPitch += 360.0f;

        float yaw = updateRotation(currentYaw, calcYaw, speed);
        float pitch = updateRotation(currentPitch, calcPitch, speed);

        return new float[]{yaw, pitch >= 90 ? 90 : pitch <= -90 ? -90 : pitch};
    }

    float updateRotation(float curRot, float destination, float speed) {
//        System.out.println(11111);
        float f = MathHelper.wrapAngleTo180_float(destination - curRot);

        if (f > speed) {
            f = speed;
        }

        if (f < -speed) {
            f = -speed;
        }

        return curRot + f;
    }

    public float[] faceBlock(BlockPos pos, float yTranslation, float currentYaw, float currentPitch, float speed) {
        double x = (pos.getX() + 0.5F) - mc.thePlayer.posX - mc.thePlayer.motionX;
        double y = (pos.getY() - yTranslation) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double z = (pos.getZ() + 0.5F) - mc.thePlayer.posZ - mc.thePlayer.motionZ;

        double calculate = MathHelper.sqrt_double(x * x + z * z);
        float calcYaw = (float) (MathHelper.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float calcPitch = (float) -(MathHelper.atan2(y, calculate) * 180.0D / Math.PI);

        float yaw = updateRotation(currentYaw, calcYaw, speed);
        float pitch = updateRotation(currentPitch, calcPitch, speed);

        final float sense = mc.gameSettings.mouseSensitivity * 0.8f + 0.2f;
        final float fix = (float) (Math.pow(sense, 3.0) * 1.5);
        yaw -= yaw % fix;
        pitch -= pitch % fix;

        return new float[]{yaw, pitch >= 90 ? 90 : pitch <= -90 ? -90 : pitch};
    }


    public static MovingObjectPosition rayCastedBlock(float yaw, float pitch) {
        float range = mc.playerController.getBlockReachDistance();

        Vec3 vec31 = getVectorForRotation(pitch, yaw);

        Vec3 vec3 = mc.thePlayer.getPositionEyes(1.0F);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);

        MovingObjectPosition ray = mc.theWorld.rayTraceBlocks(vec3, vec32, false, false, false);

        if (ray != null && ray.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            return ray;
        return null;
    }

    protected static final Vec3 getVectorForRotation(float pitch, float yaw)
    {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3(f1 * f2, f3, f * f2);
    }

}
