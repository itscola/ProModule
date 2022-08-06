package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.scoreboard.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.*;

@Mixin(Scoreboard.class)
public abstract class MixinScoreBoard {

    @Shadow @Final private Map<String, ScorePlayerTeam> teams;

    @Shadow @Final private Map<String, ScorePlayerTeam> teamMemberships;

    @Shadow public abstract void func_96513_c(ScorePlayerTeam p_96513_1_);

    @Shadow @Final private Map<String, ScoreObjective> scoreObjectives;

    @Shadow public abstract ScoreObjective getObjectiveInDisplaySlot(int p_getObjectiveInDisplaySlot_1_);

    @Shadow public abstract void setObjectiveInDisplaySlot(int p_setObjectiveInDisplaySlot_1_, ScoreObjective p_setObjectiveInDisplaySlot_2_);

    @Shadow public abstract void onScoreObjectiveRemoved(ScoreObjective p_onScoreObjectiveRemoved_1_);

    @Shadow @Final private Map<String, Map<ScoreObjective, Score>> entitiesScoreObjectives;

    @Shadow @Final private Map<IScoreObjectiveCriteria, List<ScoreObjective>> scoreObjectiveCriterias;

    /**
     * @author White_cola
     * @reason fix the nullPointer
     */
    @Overwrite
    public void removeTeam(ScorePlayerTeam p_removeTeam_1_) {
        if(p_removeTeam_1_==null || p_removeTeam_1_.getRegisteredName()==null){
            return;
        }
        this.teams.remove(p_removeTeam_1_.getRegisteredName());
        Iterator lvt_2_1_ = p_removeTeam_1_.getMembershipCollection().iterator();

        while(lvt_2_1_.hasNext()) {
            String lvt_3_1_ = (String)lvt_2_1_.next();
            if(lvt_3_1_==null){
                continue;
            }
            this.teamMemberships.remove(lvt_3_1_);
        }

        this.func_96513_c(p_removeTeam_1_);

    }


    /**
     * @author White_cola
     * @reason fix the nullPointer
     */
    @Overwrite
    public void removeObjective(ScoreObjective p_removeObjective_1_) {
        if(p_removeObjective_1_==null || p_removeObjective_1_.getName()==null){
            return;
        }
        this.scoreObjectives.remove(p_removeObjective_1_.getName());


        for(int lvt_2_1_ = 0; lvt_2_1_ < 19; ++lvt_2_1_) {
            if (this.getObjectiveInDisplaySlot(lvt_2_1_) == p_removeObjective_1_) {
                this.setObjectiveInDisplaySlot(lvt_2_1_, (ScoreObjective)null);
            }
        }

        List<ScoreObjective> lvt_2_2_ = (List)this.scoreObjectiveCriterias.get(p_removeObjective_1_.getCriteria());
        if (lvt_2_2_ != null) {
            lvt_2_2_.remove(p_removeObjective_1_);


            Iterator lvt_3_1_ = this.entitiesScoreObjectives.values().iterator();

            while(lvt_3_1_.hasNext()) {
                Map<ScoreObjective, Score> lvt_4_1_ = (Map)lvt_3_1_.next();

                lvt_4_1_.remove(p_removeObjective_1_);
            }
        }


        this.onScoreObjectiveRemoved(p_removeObjective_1_);
    }


}
