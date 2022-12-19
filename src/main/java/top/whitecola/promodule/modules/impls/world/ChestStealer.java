package top.whitecola.promodule.modules.impls.world;

import com.google.common.collect.Lists;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.PreMotionEvent;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.TimerUtils;

import java.util.List;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class ChestStealer extends AbstractModule {
    @ModuleSetting(name = "Delay",addValue = 1)
    public Float delay = 80f;

    @ModuleSetting(name = "Title",type = "select")
    public Boolean title = true;


    private final TimerUtils timer = new TimerUtils();



    @Override
    public void reset() {
        timer.reset();
        super.reset();
    }


    @Override
    public void onPreMotion(PreMotionEvent e) {
        if(e.isPre()&& mc.thePlayer.openContainer instanceof ContainerChest){
            ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
            String chestName = chest.getLowerChestInventory().getName();
            if (title && !((chestName.contains("Chest") && !chestName.equals("Ender Chest")) || chestName.equals("LOW")))
                return;

            List<Integer> slots = Lists.newArrayList();
            for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                if (chest.getLowerChestInventory().getStackInSlot(i) != null) {
                    slots.add(i);
                }
            }

//            if (reverse.isEnabled()) Collections.reverse(slots);

            for (int slot : slots) {
                if (delay == 0 || timer.hasTimeElapsed( delay.longValue(), true)) {
                    mc.playerController.windowClick(chest.windowId, slot, 0, 1, mc.thePlayer);
                }
            }

            if (slots.isEmpty() || this.isInventoryFull()) {
                mc.thePlayer.closeScreen();
            }
        }
        super.onPreMotion(e);
    }

    private boolean isInventoryFull() {
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getStack() == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.WORLD;
    }

    @Override
    public String getModuleName() {
        return "ChestStealer";

    }

}
