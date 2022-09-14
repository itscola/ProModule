package top.whitecola.promodule.modules.impls.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.injection.wrappers.IMixinEntityPlayerSP;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.PacketUtils;
import top.whitecola.promodule.utils.RandomUtils;

import static top.whitecola.promodule.utils.MCWrapper.*;

import java.awt.*;

public class Eagle extends AbstractModule {

//    @ModuleSetting(name = "PacketSneak" ,type = "select")
//    public Boolean packetSneak = true;

    @ModuleSetting(name = "MinDelay" ,type = "value")
    public Float minDelay =75f;

    @ModuleSetting(name = "MaxDelay" ,type = "value")
    public Float maxDelay =90f;

    @ModuleSetting(name = "MoreRandom" ,type = "select")
    public Boolean moreRandom = true;

    @ModuleSetting(name = "VanillaSneak" , type = "select")
    public Boolean vanillaSneak = true;



    private Robot robot;
    boolean lastShift;
    long time;



    public Eagle(){
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.MOVEMENT;
    }

    @Override
    public String getModuleName() {
        return "Eagle";

    }


    @Override
    public void onTick() {

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null || player.getEntityWorld() == null || !player.onGround) {
            return;
        }



        if (player.getEntityWorld().getBlockState(new BlockPos(player).add(0, -1, 0)).getBlock() == Blocks.air) {
            lastShift = true;
            time = System.currentTimeMillis();
//            if(packetSneak && !((IMixinEntityPlayerSP)mc.thePlayer).isServerSprintState()){
//                PacketUtils.sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
//            }
            if (vanillaSneak) {
                int keycode = mc.gameSettings.keyBindSneak.getKeyCode();
                KeyBinding.setKeyBindState(keycode,true);
                return;
            }

            robot.keyPress(16);
            return;
        }

        double delay =  RandomUtils.nextDouble(minDelay,maxDelay);
        if(moreRandom){
            delay -= RandomUtils.nextDouble(0,5);
        }

        if (lastShift && ((System.currentTimeMillis() - time) >= delay)) {
            lastShift = false;
//            if(packetSneak&&((IMixinEntityPlayerSP)mc.thePlayer).isServerSprintState()){
//                PacketUtils.sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
//                return;
//            }
            if(vanillaSneak){
                int keycode = mc.gameSettings.keyBindSneak.getKeyCode();
                KeyBinding.setKeyBindState(keycode,false);
                return;
            }
            robot.keyRelease(16);
            return;
        }

        super.onTick();
    }

    @Override
    public void onDisable() {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null || player.getEntityWorld() == null) {
            return;
        }

        if(player.isSneaking()){
            lastShift = false;
            robot.keyRelease(16);
        }
        super.onDisable();
    }
}
