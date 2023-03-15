package top.whitecola.promodule.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.modules.impls.world.Scaffold3;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class ScaffoldUtils {
    public static class BlockCache {

        private final BlockPos position;
        private final EnumFacing facing;

        public BlockCache(final BlockPos position, final EnumFacing facing) {
            this.position = position;
            this.facing = facing;
        }

        public BlockPos getPosition() {
            return this.position;
        }

        public EnumFacing getFacing() {
            return this.facing;
        }
    }


    public static BlockCache getBlockInfo() {
        final BlockPos belowBlockPos = new BlockPos(mc.thePlayer.posX, getYLevel() - (Scaffold3.isDownwards() ? 1 : 0), mc.thePlayer.posZ);
        if (mc.theWorld.getBlockState(belowBlockPos).getBlock() instanceof BlockAir) {
            for (int x = 0; x < 4; x++) {
                for (int z = 0; z < 4; z++) {
                    for (int i = 1; i > -3; i -= 2) {
                        final BlockPos blockPos = belowBlockPos.add(x * i, 0, z * i);
                        if (mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockAir) {
                            for (EnumFacing direction : EnumFacing.values()) {
                                final BlockPos block = blockPos.offset(direction);
                                final Material material = mc.theWorld.getBlockState(block).getBlock().getMaterial();
                                if (material.isSolid() && !material.isLiquid()) {
                                    return new BlockCache(block, direction.getOpposite());
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


    public static double getYLevel() {
        Scaffold3 scaffold3 = ProModule.getProModule().getModuleManager().getModuleByClass(Scaffold3.class);

//        if (!scaffold3.keepY || scaffold3.keepYMode.is("Speed toggled") && !Tenacity.INSTANCE.isEnabled(Speed.class)) {
//            return mc.thePlayer.posY - 1.0;
//        }
        return mc.thePlayer.posY - 1.0 >= Scaffold3.keepYCoord && Math.max(mc.thePlayer.posY, Scaffold3.keepYCoord)
                - Math.min(mc.thePlayer.posY, Scaffold3.keepYCoord) <= 3.0 && !mc.gameSettings.keyBindJump.isKeyDown()
                ? Scaffold3.keepYCoord
                : mc.thePlayer.posY - 1.0;
    }

    public static int getBlockSlot() {
        for (int i = 0; i < 9; i++) {
            final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize > 0) {
                final ItemBlock itemBlock = (ItemBlock) itemStack.getItem();
                if (isBlockValid(itemBlock.getBlock())) {
                    return i;
                }
            }
        }
        return -1;
    }


    private static boolean isBlockValid(final Block block) {
        return (block.isFullBlock() || block == Blocks.glass) &&
                block != Blocks.sand &&
                block != Blocks.gravel &&
                block != Blocks.dispenser &&
                block != Blocks.command_block &&
                block != Blocks.noteblock &&
                block != Blocks.furnace &&
                block != Blocks.crafting_table &&
                block != Blocks.tnt &&
                block != Blocks.dropper &&
                block != Blocks.beacon;
    }

    public static Vec3 getHypixelVec3(BlockCache data) {
        BlockPos pos = data.position;
        EnumFacing face = data.facing;
        double x = (double) pos.getX() + 0.5, y = (double) pos.getY() + 0.5, z = (double) pos.getZ() + 0.5;
        if (face != EnumFacing.UP && face != EnumFacing.DOWN) {
            y += 0.5;
        } else {
            x += 0.3;
            z += 0.3;
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += 0.15;
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += 0.15;
        }
        return new Vec3(x, y, z);
    }

    public static BlockCache grab() {
        final EnumFacing[] invert = {EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH,
                EnumFacing.EAST, EnumFacing.WEST};
        BlockPos position = new BlockPos(0, 0, 0);
        if (PlayerSPUtils.isMoving() && !mc.gameSettings.keyBindJump.isKeyDown()) {
            for (double n2 = 0 + 0.0001, n3 = 0.0; n3 <= n2; n3 += n2 / (Math.floor(n2))) {
                final BlockPos blockPos2 = new BlockPos(
                        mc.thePlayer.posX - MathHelper.sin(RotationUtils.clampRotation()) * n3,
                        mc.thePlayer.posY - 1.0,
                        mc.thePlayer.posZ + MathHelper.cos(RotationUtils.clampRotation()) * n3);
                final IBlockState blockState = mc.theWorld.getBlockState(blockPos2);
                if (blockState != null && blockState.getBlock() == Blocks.air) {
                    position = blockPos2;
                    break;
                }
            }
        } else {
            position = new BlockPos(new BlockPos(mc.thePlayer.getPositionVector().xCoord,
                    mc.thePlayer.getPositionVector().yCoord, mc.thePlayer.getPositionVector().zCoord))
                    .offset(EnumFacing.DOWN);
        }

        if (!(mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir)
                && !(mc.theWorld.getBlockState(position).getBlock() instanceof BlockLiquid)) {
            return null;
        }
        EnumFacing[] values;
        for (int length = (values = EnumFacing.values()).length, i = 0; i < length; ++i) {
            final EnumFacing facing = values[i];
            final BlockPos offset = position.offset(facing);
            if (!(mc.theWorld.getBlockState(offset).getBlock() instanceof BlockAir)
                    && !(mc.theWorld.getBlockState(position).getBlock() instanceof BlockLiquid)) {
                return new BlockCache(offset, invert[facing.ordinal()]);
            }
        }
        final BlockPos[] offsets = {new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1),
                new BlockPos(0, 0, 1)};
        BlockPos[] array;
        for (int length2 = (array = offsets).length, j = 0; j < length2; ++j) {
            final BlockPos offset2 = array[j];
            final BlockPos offsetPos = position.add(offset2.getX(), 0, offset2.getZ());
            if (mc.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
                EnumFacing[] values2;
                for (int length3 = (values2 = EnumFacing.values()).length, k = 0; k < length3; ++k) {
                    final EnumFacing facing2 = values2[k];
                    final BlockPos offset3 = offsetPos.offset(facing2);
                    if (!(mc.theWorld.getBlockState(offset3).getBlock() instanceof BlockAir)) {
                        return new BlockCache(offset3, invert[facing2.ordinal()]);
                    }
                }
            }

        }
        return null;
    }


    public static int grabBlockSlot() {
        int slot = -1;
        int highestStack = -1;
        boolean didGetHotbar = false;
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && canItemBePlaced(itemStack) && itemStack.stackSize > 0) {
                if (Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize > highestStack && i < 9) {
                    highestStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize;
                    slot = i;
                    if (slot == getLastHotbarSlot()) {
                        didGetHotbar = true;
                    }
                }
                if (i > 8 && !didGetHotbar) {
                    int hotbarNum = getFreeHotbarSlot();
                    if (hotbarNum != -1 && Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize > highestStack) {
                        highestStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize;
                        slot = i;
                    }
                }
            }
        }
        if (slot > 8) {
            int hotbarNum = getFreeHotbarSlot();
            if (hotbarNum != -1) {
                Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, Minecraft.getMinecraft().thePlayer);
            } else {
                return -1;
            }
        }
        return slot;
    }



    public static int getLastHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; k++) {
            final ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[k];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && canItemBePlaced(itemStack) && itemStack.stackSize > 1) {
                hotbarNum = k;
                continue;
            }
        }
        return hotbarNum;
    }

    public static int getFreeHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; k++) {
            if (Minecraft.getMinecraft().thePlayer.inventory.mainInventory[k] == null) {
                hotbarNum = k;
                continue;
            } else {
                hotbarNum = 7;
            }
        }
        return hotbarNum;
    }


    public static boolean canItemBePlaced(ItemStack item) {
        if (item.getItem().getIdFromItem(item.getItem()) == 116)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 30)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 31)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 175)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 28)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 27)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 66)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 157)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 31)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 6)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 31)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 32)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 140)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 390)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 37)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 38)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 39)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 40)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 69)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 50)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 75)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 76)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 54)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 130)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 146)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 342)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 12)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 77)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 143)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 46)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 145)
            return false;

        return true;
    }
}
