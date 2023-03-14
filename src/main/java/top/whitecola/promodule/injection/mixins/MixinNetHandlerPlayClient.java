package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.DataWatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S47PacketPlayerListHeaderFooter;
import net.minecraft.scoreboard.*;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StringUtils;
import org.apache.logging.log4j.core.lookup.JndiLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient {
    @Shadow private Minecraft gameController;


    @Shadow private WorldClient clientWorldController;

//    /**
//     * @author White_cola
//     * @reason fix the nullPointer bug.
//     */
//    @Overwrite
//    public void handlePlayerListHeaderFooter(S47PacketPlayerListHeaderFooter p_handlePlayerListHeaderFooter_1_) {
//        if(p_handlePlayerListHeaderFooter_1_.getHeader()!=null & p_handlePlayerListHeaderFooter_1_.getHeader().getFormattedText().length()!=0) {
//            this.gameController.ingameGUI.getTabList().setHeader(p_handlePlayerListHeaderFooter_1_.getHeader());
//        }
//
//        if(p_handlePlayerListHeaderFooter_1_.getFooter()!=null & p_handlePlayerListHeaderFooter_1_.getFooter().getFormattedText().length()!=0) {
//            this.gameController.ingameGUI.getTabList().setFooter(p_handlePlayerListHeaderFooter_1_.getFooter());
//        }
//    }


    @Shadow public abstract NetworkPlayerInfo getPlayerInfo(UUID p_getPlayerInfo_1_);

    /**
     * @author White_cola
     * @reason fix the nullPointer bug.
     */
    @Overwrite
    public void handleTeams(S3EPacketTeams p_handleTeams_1_) {
        if(p_handleTeams_1_==null )
            return;


        PacketThreadUtil.checkThreadAndEnqueue(p_handleTeams_1_, Minecraft.getMinecraft().getNetHandler(), this.gameController);
        Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        ScorePlayerTeam scoreplayerteam;
        if (p_handleTeams_1_.func_149307_h() == 0) {
            scoreplayerteam = scoreboard.createTeam(p_handleTeams_1_.func_149312_c());
        } else {
            scoreplayerteam = scoreboard.getTeam(p_handleTeams_1_.func_149312_c());
        }


        if(scoreplayerteam==null)
            return;

        if (p_handleTeams_1_.func_149307_h() == 0 || p_handleTeams_1_.func_149307_h() == 2) {
            scoreplayerteam.setTeamName(p_handleTeams_1_.func_149306_d());
            scoreplayerteam.setNamePrefix(p_handleTeams_1_.func_149311_e());
            scoreplayerteam.setNameSuffix(p_handleTeams_1_.func_149309_f());
            scoreplayerteam.setChatFormat(EnumChatFormatting.func_175744_a(p_handleTeams_1_.func_179813_h()));
            scoreplayerteam.func_98298_a(p_handleTeams_1_.func_149308_i());
            Team.EnumVisible team$enumvisible = Team.EnumVisible.func_178824_a(p_handleTeams_1_.func_179814_i());
            if (team$enumvisible != null) {
                scoreplayerteam.setNameTagVisibility(team$enumvisible);
            }
        }

        String s1;
        Iterator var6;
        if (p_handleTeams_1_.func_149307_h() == 0 || p_handleTeams_1_.func_149307_h() == 3) {
            var6 = p_handleTeams_1_.func_149310_g().iterator();

            while(var6.hasNext()) {
                s1 = (String)var6.next();
                scoreboard.addPlayerToTeam(s1, p_handleTeams_1_.func_149312_c());
            }
        }



        if (p_handleTeams_1_.func_149307_h() == 4) {
            var6 = p_handleTeams_1_.func_149310_g().iterator();

            while(var6.hasNext()) {
                s1 = (String)var6.next();
                scoreboard.removePlayerFromTeam(s1, scoreplayerteam);
            }
        }



        if (p_handleTeams_1_.func_149307_h() == 1) {
            scoreboard.removeTeam(scoreplayerteam);
        }

    }

    /**
     * @author White_cola
     * @reason fix the NPE bug.
     */
    @Overwrite
    public void handleSpawnPlayer(S0CPacketSpawnPlayer p_handleSpawnPlayer_1_) {
        if(p_handleSpawnPlayer_1_==null){
            return;
        }

        PacketThreadUtil.checkThreadAndEnqueue(p_handleSpawnPlayer_1_, Minecraft.getMinecraft().getNetHandler(), this.gameController);
        double d0 = (double)p_handleSpawnPlayer_1_.getX() / 32.0D;
        double d1 = (double)p_handleSpawnPlayer_1_.getY() / 32.0D;
        double d2 = (double)p_handleSpawnPlayer_1_.getZ() / 32.0D;
        float f = (float)(p_handleSpawnPlayer_1_.getYaw() * 360) / 256.0F;
        float f1 = (float)(p_handleSpawnPlayer_1_.getPitch() * 360) / 256.0F;
        EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP(this.gameController.theWorld, this.getPlayerInfo(p_handleSpawnPlayer_1_.getPlayer()).getGameProfile());
        if(entityotherplayermp==null){
            return;
        }
        entityotherplayermp.prevPosX = entityotherplayermp.lastTickPosX = (double)(entityotherplayermp.serverPosX = p_handleSpawnPlayer_1_.getX());
        entityotherplayermp.prevPosY = entityotherplayermp.lastTickPosY = (double)(entityotherplayermp.serverPosY = p_handleSpawnPlayer_1_.getY());
        entityotherplayermp.prevPosZ = entityotherplayermp.lastTickPosZ = (double)(entityotherplayermp.serverPosZ = p_handleSpawnPlayer_1_.getZ());
        int i = p_handleSpawnPlayer_1_.getCurrentItemID();
        if (i == 0) {
            entityotherplayermp.inventory.mainInventory[entityotherplayermp.inventory.currentItem] = null;
        } else {
            entityotherplayermp.inventory.mainInventory[entityotherplayermp.inventory.currentItem] = new ItemStack(Item.getItemById(i), 1, 0);
        }

        entityotherplayermp.setPositionAndRotation(d0, d1, d2, f, f1);
        this.clientWorldController.addEntityToWorld(p_handleSpawnPlayer_1_.getEntityID(), entityotherplayermp);
        List<DataWatcher.WatchableObject> list = p_handleSpawnPlayer_1_.func_148944_c();
        if (list != null) {
            entityotherplayermp.getDataWatcher().updateWatchedObjectsFromList(list);
        }
    }


    /**
     * @author white_cola
     * @reason fix NPE for some sever.
     */
    @Overwrite
    public void handleUpdateScore(S3CPacketUpdateScore p_handleUpdateScore_1_) {
        if(p_handleUpdateScore_1_==null){
            return;
        }
        PacketThreadUtil.checkThreadAndEnqueue(p_handleUpdateScore_1_, Minecraft.getMinecraft().getNetHandler(), this.gameController);
        Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        ScoreObjective scoreobjective = scoreboard.getObjective(p_handleUpdateScore_1_.getObjectiveName());
        if (p_handleUpdateScore_1_.getScoreAction() == S3CPacketUpdateScore.Action.CHANGE) {
            Score score = scoreboard.getValueFromObjective(p_handleUpdateScore_1_.getPlayerName(), scoreobjective);
            score.setScorePoints(p_handleUpdateScore_1_.getScoreValue());
        } else if (p_handleUpdateScore_1_.getScoreAction() == S3CPacketUpdateScore.Action.REMOVE) {
            if (StringUtils.isNullOrEmpty(p_handleUpdateScore_1_.getObjectiveName())) {
                scoreboard.removeObjectiveFromEntity(p_handleUpdateScore_1_.getPlayerName(), (ScoreObjective)null);
            } else if (scoreobjective != null) {
                scoreboard.removeObjectiveFromEntity(p_handleUpdateScore_1_.getPlayerName(), scoreobjective);
            }
        }

    }

    @Inject(method = "handleSpawnPlayer", at = @At("HEAD"), cancellable = true)
    public void handleSpawnPlayer(S0CPacketSpawnPlayer p_handleSpawnPlayer_1_, CallbackInfo ci){
        if(p_handleSpawnPlayer_1_==null){
            ci.cancel();
            return;
        }
    }


}
