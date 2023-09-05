package cn.ariacraft.bw1058addons.Player;

import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class onEndAllowFlying implements Listener {

    @EventHandler
    public void onEnd(GameEndEvent e) {
        for (Player p : e.getArena().getPlayers()) {
            p.setFlying(true);
        }
    }
}
