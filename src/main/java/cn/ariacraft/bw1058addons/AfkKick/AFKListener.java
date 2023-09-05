package cn.ariacraft.bw1058addons.AfkKick;

import cn.ariacraft.bw1058addons.BedWars1058Addons;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class AFKListener implements org.bukkit.event.Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (AFKTask.afkTask != null) {
            player.setMetadata("lastMoveTime", new FixedMetadataValue(BedWars1058Addons.plugin, System.currentTimeMillis()));
        }
    }

    @EventHandler
    public void onGameStart(GameStateChangeEvent event) {
        if (event.getNewState() == GameState.playing) {
            for (Player p : event.getArena().getPlayers()) {
                p.setMetadata("lastMoveTime", new FixedMetadataValue(BedWars1058Addons.plugin, System.currentTimeMillis()));
            }
            AFKTask.startAFKCheck();
        }
    }

    @EventHandler
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent event) {
        Player player = event.getPlayer();
        player.removeMetadata("lastMoveTime", BedWars1058Addons.plugin);
    }

    @EventHandler
    public void onEnd(GameEndEvent event) {
        AFKTask.afkTask.cancel();
    }
}
