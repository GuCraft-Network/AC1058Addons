package cn.ariacraft.bw1058addons.SpongeAnimation;

import cn.ariacraft.bw1058addons.BedWars1058Addons;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SpongePlaceListener implements Listener {

    /**
     * 海绵动画
     * 应该是skid的@Mher, 或公开组件
     * 25/5/30
     */
    @EventHandler
    public void onSpongePlace(BlockPlaceEvent e) {
        if (!e.isCancelled()) {
            if (e.getBlock().getType().equals(Material.SPONGE)) {
                (new SpongeAnimationTask(e.getBlock())).runTaskTimerAsynchronously(BedWars1058Addons.getInstance(), 0L, 8L);
            }
        }
    }
}