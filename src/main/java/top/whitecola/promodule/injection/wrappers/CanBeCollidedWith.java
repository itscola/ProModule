package top.whitecola.promodule.injection.wrappers;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;

import javax.annotation.Nullable;

public class CanBeCollidedWith implements Predicate {


    @Override
    public boolean apply(@Nullable Object input) {
        return ((Entity)input).canBeCollidedWith();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return false;
    }
}
