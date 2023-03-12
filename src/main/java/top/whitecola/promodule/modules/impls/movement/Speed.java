package top.whitecola.promodule.modules.impls.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.JumpEvent;
import top.whitecola.promodule.events.impls.event.MoveEvent;
import top.whitecola.promodule.events.impls.event.PacketReceivedEvent;
import top.whitecola.promodule.events.impls.event.PreMotionEvent;
import top.whitecola.promodule.injection.wrappers.IMixinEntityPlayerSP;
import top.whitecola.promodule.injection.wrappers.IMixinMinecraft;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.modules.impls.combat.Killaura;
import top.whitecola.promodule.utils.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class Speed extends AbstractModule {


    private final TimerUtils timerUtil = new TimerUtils();

    @ModuleSetting(name = "LagBack",type = "select")
    public Boolean lagback = true;

    @ModuleSetting(name = "TimerBoost",type = "select")
    public Boolean timerBoost = true;

    @ModuleSetting(name = "DamageBoost",type = "select")
    public Boolean damageBoost = true;


    private boolean shouldBoost, expBoost;
    private double moveSpeed, lastDist, baseSpeed, boostSpeed;


    double flightMagicCon1 = 2.1449999809265137;
    double bunnyDivFriction = 50;

    @Override
    public void onTick() {
        if(mc.theWorld==null||mc.thePlayer==null){
            return;
        }
        lastDist = PlayerSPUtils.getLastTickDistance();
        baseSpeed = PlayerSPUtils.getBaseMoveSpeed(0.16,moveSpeed) * (PlayerSPUtils.isInLiquid() ? 0.5 : mc.thePlayer.movementInput.sneak  ? 0.8 : 1.0);
        super.onTick();
    }


    @Override
    public void onJump(JumpEvent e) {
        if(PlayerSPUtils.isMoving()){
            e.setCancel();
        }

        super.onJump(e);
    }

    @Override
    public void onMove(MoveEvent e) {
        if (mc.thePlayer.onGround) {
            setTimer(1.0F);

            e.setY(mc.thePlayer.motionY = PlayerSPUtils.getBaseMotionY(/*isEnabled(Scaffold.class) ? 0.41999998688698 :*/ 0.39999998688698));
            moveSpeed = baseSpeed * flightMagicCon1;

        } else if (shouldBoost) {
            setTimer(1);
            moveSpeed = lastDist - 0.819999 * (lastDist - baseSpeed);

        } else {
            setTimer(1.2f);
            moveSpeed = lastDist - lastDist / bunnyDivFriction;
        }

        if (damageBoost && expBoost && !PlayerSPUtils.isPotionActive(Potion.poison) && !mc.thePlayer.isBurning()) {
            moveSpeed += boostSpeed;
            expBoost = false;
        }

        PlayerSPUtils.setSpeed(Math.max(moveSpeed, baseSpeed));
        shouldBoost = mc.thePlayer.onGround;
        super.onMove(e);
    }

    @Override
    public void onPreMotion(PreMotionEvent e) {
        if(e.isPre()){
            if (PlayerSPUtils.getLastTickDistance() > 0) {

                e.setY(e.getY() + 0.015625);

            }
        }

        super.onPreMotion(e);
    }

    @Override
    public void packetReceivedEvent(PacketReceivedEvent e) {
            if (e.getPacket() instanceof S27PacketExplosion) {
                S27PacketExplosion explosion = (S27PacketExplosion) e.getPacket();

                if (explosion.getAffectedBlockPositions().isEmpty()) {
                    boostSpeed = Math.hypot(mc.thePlayer.motionX + explosion.getX() / 8500, mc.thePlayer.motionZ + explosion.getZ() / 8500);
                    expBoost = true;
                }
            }
        super.packetReceivedEvent(e);
    }


    @Override
    public void onDisable() {
        ((IMixinMinecraft)mc).getTimer().timerSpeed = 1;
        super.onDisable();
    }

    private void setTimer(float speed) {
        ((IMixinMinecraft)mc).getTimer().timerSpeed = mc.thePlayer.fallDistance > 2 ? 1.0F : speed;
    }


    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.MOVEMENT;
    }

    @Override
    public String getModuleName() {
        return "Speed";

    }



}
