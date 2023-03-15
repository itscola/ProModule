package top.whitecola.promodule.modules.impls.combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.*;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import org.lwjgl.Sys;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.PacketSendEvent;
import top.whitecola.promodule.events.impls.event.PreMotionEvent;
import top.whitecola.promodule.events.impls.event.WorldRenderEvent;
import top.whitecola.promodule.injection.wrappers.IMixinEntity;
import top.whitecola.promodule.injection.wrappers.IMixinEntityPlayer;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.modules.impls.other.MiddleClick;
import top.whitecola.promodule.utils.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class Killaura extends AbstractModule {
    private Color color = new Color(246, 246, 246);

    protected long lastAttack;
    protected long lastSwitch;
    protected int delay;

    public List<EntityLivingBase> targets = new ArrayList<>();
    public EntityLivingBase target;
    public boolean isBlocking;
    public boolean attacking;

    public boolean noAttack;

    private double lastTargetYaw;
    private double lasttargetPitch;

    public TimerUtils timer = new TimerUtils(), swtichTimer = new TimerUtils();

    @ModuleSetting(name = "MinCPS",addValue = 1f)
    public Float minCPS = 8f;

    @ModuleSetting(name = "MaxCPS",addValue = 1f)
    public Float maxCPS = 10f;

    @ModuleSetting(name = "Reach",addValue = 1f)
    public Float reach = 4f;

    @ModuleSetting(name = "mistake",addValue = 1f)
    public Float mistake = 15f;

    @ModuleSetting(name = "Switch",type = "select")
    public Boolean iswitch = true;



    @ModuleSetting(name = "Smooth",type = "select")
    public Boolean smooth = true;

    @ModuleSetting(name = "SwitchDelay",addValue = 10f)
    public Float switchDelay = 100f;

    @ModuleSetting(name = "Color4Team",type = "select")
    public Boolean color4Team = false;

    @ModuleSetting(name = "NoInv",type = "select")
    public Boolean noInv = true;



    @ModuleSetting(name = "NameTags",type = "select")
    public Boolean nameTags = true;

    @ModuleSetting(name = "ESP",type = "select")
    public Boolean esp = true;

    @ModuleSetting(name = "Safer",type = "select")
    public Boolean safer = true;


    @Override
    public void reset() {
        if(isBlocking){
            unBlock(true);
        }

        if(mc.thePlayer!=null){
            targets.clear();
            isBlocking = false;
            attacking = false;
            target = null;
            timer.reset();
            swtichTimer.reset();
        }
        super.reset();
    }


    @Override
    public void onPreMotion(PreMotionEvent e) {
        if(ProModule.getProModule().getModuleManager().getModuleByName("Scaffold").isEnabled()){
            return;
        }



        sortTargets();

        if(mc.thePlayer.isDead||mc.thePlayer.isSpectator()){
            return;
        }

        if(noInv&&mc.thePlayer.isInvisible()){
            return;
        }


        if (!targets.isEmpty()) {
            if (iswitch && swtichTimer.hasTimeElapsed(switchDelay.longValue())) {

                swtichTimer.reset();
            }
            if (e.isPre()) {
                target = targets.get(0);
                float[] rotations = getRotationsToEnt(target);
//                if (rotationsSetting.getSetting("Dynamic").isEnabled()) {
//                    rotations[0] += MathUtils.getRandomInRange(1, 5);
//                    rotations[1] += MathUtils.getRandomInRange(1, 5);
//                }
//                if (rotationsSetting.getSetting("Prediction").isEnabled()) {
                if(safer) {
                    if(!RotationUtils.isMouseOver(e.getYaw(),e.getPitch(),target,reach)){
                        rotations[0] = (float) (rotations[0] + ((Math.abs(target.posX - target.lastTickPosX) - Math.abs(target.posZ - target.lastTickPosZ)) * (2 / 3)) * 2);
                    }
                }else {
                    rotations[0] = (float) (rotations[0] + ((Math.abs(target.posX - target.lastTickPosX) - Math.abs(target.posZ - target.lastTickPosZ)) * (2 / 3)) * 2);
                }
                rotations[1] = (float) (rotations[1] + ((Math.abs(target.posY - target.lastTickPosY) - Math.abs(target.getEntityBoundingBox().minY - target.lastTickPosY)) * (2 / 3)) * 2);
//                }

//                if (rotationsSetting.getSetting("Resolver").isEnabled()) {
//                    if (target.posY < 0) {
//                        rotations[1] = 1;
//                    } else if (target.posY > 255) {
//                        rotations[1] = 90;
//                    }
//
//                    if (Math.abs(target.posX - target.lastTickPosX) > 0.50 || Math.abs(target.posZ - target.lastTickPosZ) > 0.50) {
//                        target.setEntityBoundingBox(new AxisAlignedBB(target.posX, target.posY, target.posZ, target.lastTickPosX, target.lastTickPosY, target.lastTickPosZ));
//                        if (debug.isEnabled()) {
//                            mc.thePlayer.addChatComponentMessage(new ChatComponentText("Tenacity: resloved target hitbox at " + target.posX + "," + target.posY + "," + target.posZ));
//                        }
//                    }
//                }

                if (smooth) {
                    float sens = RotationUtils.getSensitivityMultiplier();

                    rotations[0] = RotationUtils.smoothRotation(mc.thePlayer.rotationYaw, rotations[0], 360);
                    rotations[1] = RotationUtils.smoothRotation(mc.thePlayer.rotationPitch, rotations[1], 90);

                    rotations[0] = Math.round(rotations[0] / sens) * sens;
                    rotations[1] = Math.round(rotations[1] / sens) * sens;

                }

//                if (matrix.isEnabled()) {
//                    rotations[0] = rotations[0] + MathUtils.getRandomFloat(1.98f, -1.98f);
//                }
                e.setYaw(rotations[0]);
                e.setPitch(rotations[1]);
                this.lastTargetYaw = rotations[0];
                this.lasttargetPitch = rotations[1];


                RotationUtils.setRotations(rotations);
//            }

//            if (autoblock.isEnabled() && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
//                mc.playerController.syncCurrentPlayItem();
//                blocking = true;
//
//                switch (autoBlockMode.getMode()) {
//                    case "Watchdog":
//                        if (e.isPost()) {
//                            if (mc.thePlayer.swingProgressInt == -1) {
//                                PacketUtils.sendPacket(new C07PacketPlayerDigging(
//                                        C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.DOWN));
//                            } else if (mc.thePlayer.swingProgressInt == 0) {
//                                PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(
//                                        new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
//                            }
//                        }
//                        break;
//                    case "Interaction":
//                        if (e.isPost()) {
//                            if (blocking) {
//                                for (Entity current : targets) {
//                                    mc.playerController.interactWithEntitySendPacket(mc.thePlayer, current);
//                                }
//                                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
//                            } else {
//                                PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(
//                                        new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
//                                blocking = false;
//                            }
//                        }
//                        break;
//                    case "AAC":
//                        if (e.isPost()) {
//                            if (blocking) {
//                                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
//                            } else {
//                                PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(
//                                        new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
//                                blocking = false;
//                            }
//                        }
//                        break;
//                }
            }


            if (e.isPre()) {
                doBlock(true);
                isBlocking = true;
                attacking = true;



                if(safer&&RotationUtils.isMouseOver(e.getYaw(),e.getPitch(),target,reach) || !safer) {
                    if (timer.hasTimeElapsed((1000 / (long) MathUtils.getRandomInRange(minCPS.floatValue(), maxCPS.floatValue())), true)) {
                        if(RandomUtils.nextDouble(0,100)<mistake){
                            mc.getNetHandler().getNetworkManager().sendPacket(new C0APacketAnimation());
                        }else {
                            mc.thePlayer.swingItem();
                            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
//                            if(RandomUtils.nextDouble(0,100)<50) {
//                                ParticleUtils.spawnBloodParticle(target);
//                            }

                        }

                    }
                }
            }


        }


        if (targets.isEmpty()) {


            unBlock(true);
            attacking = false;
            isBlocking = false;

        }

//        System.out.println(lasttargetPitch+" "+mc.thePlayer.renderArmPitch);



        super.onPreMotion(e);
    }



    public void sortTargets() {
        targets.clear();
        for (Entity entity : mc.theWorld.getLoadedEntityList()) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entLiving = (EntityLivingBase) entity;
                if (mc.thePlayer.getDistanceToEntity(entLiving) < reach && entLiving != mc.thePlayer && !entLiving.isDead && shouldAttack(entLiving)) {
//                    System.out.println(entLiving.getDisplayName().getFormattedText());
                    targets.add(entLiving);
                }
            }
        }
        targets.sort(Comparator.comparingDouble(mc.thePlayer::getDistanceToEntity));
    }



    private boolean shouldAttack(EntityLivingBase entity){

        MiddleClick middleClick = (MiddleClick) ProModule.getProModule().getModuleManager().getModuleByName("MiddleClick");
        if(middleClick!=null&&middleClick.isEnabled()){
            if(middleClick.getFriends().contains(entity)){
                return false;
            }
        }

        if(entity.isInvisible()){
            return false;
        }

        if(color4Team){
            if(entity.getEquipmentInSlot(4)==null||entity.getEquipmentInSlot(4).getTagCompound()==null||entity.getEquipmentInSlot(4).getTagCompound().getCompoundTag("display")==null||entity.getEquipmentInSlot(4).getTagCompound().getCompoundTag("display").getInteger("color")==0){
                return false;
            }

            if(entity.getEquipmentInSlot(4)!=null&&mc.thePlayer.getEquipmentInSlot(4)!=null) {
                Integer targetLeatherHatColor = entity.getEquipmentInSlot(4).getTagCompound().getCompoundTag("display").getInteger("color");
                Integer playerLeatherHatColor = mc.thePlayer.getEquipmentInSlot(4).getTagCompound().getCompoundTag("display").getInteger("color");


                if (targetLeatherHatColor != null&&playerLeatherHatColor!=null) {
                    if (targetLeatherHatColor.equals(playerLeatherHatColor)) {
                        return false;
                    }
                }
            }
        }


        if(isBot(entity)){
            return false;
        }



        return true;
    }

    //skid : temp
    public boolean isBot(EntityLivingBase entity){
        if (entity == null) {
            return true;
        }

        if (entity.isPlayerSleeping()) {
            return true;
        }

//        if (entity.getEntityId() > 1000000) {
//            return true;
//        }

        if (!ServerUtils.isInTabList(entity)) {
            return true;
        }

        if (((EntityPlayer) entity).ticksExisted <= 80) {
            return true;
        }



        if(entity.getDisplayName().getFormattedText().contains("[NPC]")){
            return true;
        }


        if (entity.getDisplayName() != null && entity instanceof EntityPlayer) {
            if (nameTags && (entity.getDisplayName().getFormattedText().contains("§c") || entity.getDisplayName().getFormattedText().contains("§4"))) {
                return true;
            }
        }

        AntiBot antiBot = (AntiBot) ProModule.getProModule().getModuleManager().getModuleByName("AntiBot");
        if(antiBot!=null&&antiBot.isEnabled()){
            if(antiBot.isBot(entity)){
                return true;
            };
        }

        return false;
    }






//    public boolean isValid(EntityLivingBase entLiving) {
//        if (entLiving instanceof EntityPlayer  && !entLiving.isInvisible()) {
//            return true;
//        }
//        if (entLiving instanceof EntityPlayer && entLiving.isInvisible()) {
//            return true;
//        }
//
//
//
//        if (entLiving instanceof EntityMob && mob) {
//            return true;
//        }
//        if (ticks && entLiving.ticksExisted < 100) {
//            return false;
//        }
//        if (entLiving.getDisplayName() != null && entLiving instanceof EntityPlayer) {
//            if (nameTags && (entLiving.getDisplayName().getFormattedText().contains("§c") || entLiving.getDisplayName().getFormattedText().contains("§4"))) {
//                return false;
//            }
//        }
//
//        if (!entLiving.canEntityBeSeen(mc.thePlayer) && invisiable) {
//            return false;
//        }
//        return entLiving instanceof EntityAnimal && animals;
//    }


    @Override
    public void onSendPacket(PacketSendEvent e) {
        if (e.getPacket() instanceof C16PacketClientStatus ) {
            noAttack = true;
        }

        if (e.getPacket() instanceof C0DPacketCloseWindow ) {
            noAttack = false;
        }
        super.onSendPacket(e);
    }

    private float[] getRotationsToEnt(Entity ent) {
        final double differenceX = ent.posX - mc.thePlayer.posX;
        final double differenceY = (ent.posY + ent.height) - (mc.thePlayer.posY + mc.thePlayer.height) - 0.5;
        final double differenceZ = ent.posZ - mc.thePlayer.posZ;
        final float rotationYaw = (float) (Math.atan2(differenceZ, differenceX) * 180.0D / Math.PI) - 90.0f;
        final float rotationPitch = (float) (Math.atan2(differenceY, mc.thePlayer.getDistanceToEntity(ent)) * 180.0D
                / Math.PI);
        final float finishedYaw = mc.thePlayer.rotationYaw
                + MathHelper.wrapAngleTo180_float(rotationYaw - mc.thePlayer.rotationYaw);
        final float finishedPitch = mc.thePlayer.rotationPitch
                + MathHelper.wrapAngleTo180_float(rotationPitch - mc.thePlayer.rotationPitch);
        return new float[]{finishedYaw, -MathHelper.clamp_float(finishedPitch, -90, 90)};
    }


    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "Killaura";
    }
    @Override
    public String getDisplayName() {
        return this.getModuleName()+" (NG)";
    }

    @Override
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        if(e.entity instanceof EntityPlayerSP){
            this.targets.clear();
        }


        super.onEntityJoinWorld(e);
    }



    private void sendLeftClick(boolean state){
        int keycode = mc.gameSettings.keyBindAttack.getKeyCode();
//        System.out.println(keycode);
        if (state) {
            KeyBinding.onTick(keycode);
        }
    }

    @Override
    public void onEnable() {
//        mc.thePlayer.timer
        if(noAttack){
            noAttack = false;
        }
        super.onEnable();
    }



    private void doBlock(boolean setItemUseInCount) {
        if (setItemUseInCount&&mc.thePlayer!=null&&mc.thePlayer.getHeldItem()!=null)
            ((IMixinEntityPlayer) mc.thePlayer).setItemInUseCount(mc.thePlayer.getHeldItem().getMaxItemUseDuration());

//        mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));

        isBlocking = true;
    }

    private void unBlock(boolean setItemUseInCount) {
        if (setItemUseInCount&&mc.thePlayer!=null&&mc.thePlayer.getHeldItem()!=null)
            ((IMixinEntityPlayer) mc.thePlayer).setItemInUseCount(0);

//        double blockvalue = -1;
//
//        if (!PlayerUtil.isMoving2() && dynamic.getValue())
//            blockvalue = ThreadLocalRandom.current().nextDouble(-1.0, -0.2);
//
//        if (!blockMode.isCurrentMode("Always")) {
//
//            if (!blockMode.isCurrentMode("Exploit"))
//                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(blockvalue, blockvalue, blockvalue), EnumFacing.DOWN));

        isBlocking = false;
    }
}

//    @Override
//    public void onRender3D(int pass, float partialTicks, long finishTimeNano) {
////        Render3DUtils.drawCircle(target, partialTicks, .75f, color.getRGB(), auraESPAnim.getOutput().floatValue());
//
//        super.onRender3D(pass, partialTicks, finishTimeNano);
//    }


