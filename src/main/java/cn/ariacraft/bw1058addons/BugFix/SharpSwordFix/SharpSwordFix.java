package cn.ariacraft.bw1058addons.BugFix.SharpSwordFix;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SharpSwordFix implements Listener {

    /**
     * 修复: 丢弃剑后, 游戏会给予你一把木剑, 但是没有锋利
     * 应该是skid的@Chem
     * 25/5/30
     */
    @EventHandler
    public void onSwordDrop(PlayerDropItemEvent event) {
        Material it = event.getItemDrop().getItemStack().getType();
        if ((it == Material.STONE_SWORD || it == Material.IRON_SWORD || it == Material.DIAMOND_SWORD) && event.getItemDrop().getItemStack().getEnchantments().containsKey(Enchantment.DAMAGE_ALL)) {
            Inventory inv = event.getPlayer().getInventory();

            for (int i = 0; i < inv.getSize(); ++i) {
                ItemStack item = inv.getItem(i);
                if (item != null && item.getType() == Material.WOOD_SWORD) {
                    item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                }
            }
        }

    }
}
