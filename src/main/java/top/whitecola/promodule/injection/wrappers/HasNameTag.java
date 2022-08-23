package top.whitecola.promodule.injection.wrappers;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Score;

import javax.annotation.Nullable;

public class HasNameTag implements Predicate {

    @Override
    public boolean apply(@Nullable Object input) {
        Score score = (Score)input;
        return score.getPlayerName() != null && !score.getPlayerName().startsWith("#");
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return false;
    }
}
