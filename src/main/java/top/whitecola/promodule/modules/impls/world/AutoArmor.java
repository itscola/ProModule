package top.whitecola.promodule.modules.impls.world;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.PreMotionEvent;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.utils.InventoryUtils;
import top.whitecola.promodule.utils.PlayerSPUtils;
import top.whitecola.promodule.utils.TimerUtils;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class AutoArmor extends AbstractModule {
    @ModuleSetting(name = "Delay",addValue = 50f)
    public Float delay = 150f;

    @ModuleSetting(name = "NoMoving",type = "select")
    public Boolean noMoving = true;

    @ModuleSetting(name = "InvOnly",type = "select")
    public Boolean invOnly = true;

    private final TimerUtils timer = new TimerUtils();




    @Override
    public void reset() {
        timer.reset();
        super.reset();
    }



    @Override
    public void onPreMotion(PreMotionEvent e) {
        if(e.isPre()){
            if ((invOnly && !(mc.currentScreen instanceof GuiInventory)) || (noMoving && PlayerSPUtils.isMoving())) {
                return;
            }
            if (mc.thePlayer.openContainer instanceof ContainerChest) {
                timer.reset();
                return;
            }
            if (timer.hasTimeElapsed(delay.longValue())) {
                for (int armorSlot = 5; armorSlot < 9; armorSlot++) {
                    if (equipBest(armorSlot)) {
                        timer.reset();
                        break;
                    }
                }
            }

        }

        super.onPreMotion(e);
    }

    private boolean equipBest(int armorSlot) {
        int equipSlot = -1, currProt = -1;
        ItemArmor currItem = null;
        ItemStack slotStack = mc.thePlayer.inventoryContainer.getSlot(armorSlot).getStack();
        if (slotStack != null && slotStack.getItem() instanceof ItemArmor) {
            currItem = (ItemArmor) slotStack.getItem();
            currProt = currItem.damageReduceAmount
                    + EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, mc.thePlayer.inventoryContainer.getSlot(armorSlot).getStack());
        }
        for (int i = 9; i < 45; i++) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is != null && is.getItem() instanceof ItemArmor) {
                int prot = ((ItemArmor) is.getItem()).damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, is);
                if ((currItem == null || currProt < prot) && isValidPiece(armorSlot, (ItemArmor) is.getItem())) {
                    currItem = (ItemArmor) is.getItem();
                    equipSlot = i;
                    currProt = prot;
                }
            }
        }
        if (equipSlot != -1) {
            if (slotStack != null) {
                InventoryUtils.drop(armorSlot);
            } else {
                InventoryUtils.click(equipSlot, 0, true);
            }
            return true;
        }
        return false;
    }

    private boolean isValidPiece(int armorSlot, ItemArmor item) {
        String unlocalizedName = item.getUnlocalizedName();
        return armorSlot == 5 && unlocalizedName.startsWith("item.helmet")
                || armorSlot == 6 && unlocalizedName.startsWith("item.chestplate")
                || armorSlot == 7 && unlocalizedName.startsWith("item.leggings")
                || armorSlot == 8 && unlocalizedName.startsWith("item.boots");
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.WORLD;
    }

    @Override
    public String getModuleName() {
        return "AutoArmor";

    }
}
