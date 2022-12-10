package top.whitecola.promodule.utils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class EnumFacingOffset {
    public EnumFacing enumFacing;
    private final Vec3 offset;

    public EnumFacingOffset(final EnumFacing enumFacing, final Vec3 offset) {
        this.enumFacing = enumFacing;
        this.offset = offset;
    }

    public EnumFacing getEnumFacing() {
        return enumFacing;
    }

    public Vec3 getOffset() {
        return offset;
    }
}
