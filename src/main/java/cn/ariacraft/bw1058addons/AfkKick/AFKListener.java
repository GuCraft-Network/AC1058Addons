package cn.ariacraft.bw1058addons.AfkKick;

import cn.ariacraft.bw1058addons.BedWars1058Addons;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class AFKListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (AFKTask.isAfkTaskRunning()) {
            player.setMetadata("lastMoveTime", new FixedMetadataValue(BedWars1058Addons.plugin, System.currentTimeMillis()));
        }
    }

    @EventHandler
    public void onGameStart(GameStateChangeEvent event) {
        if (event.getNewState() == GameState.playing) {
            for (Player p : event.getArena().getPlayers()) {
                p.setMetadata("lastMoveTime", new FixedMetadataValue(BedWars1058Addons.plugin, System.currentTimeMillis()));
            }
            AFKTask.startAFKCheck(event.getArena());
        }
    }

    @EventHandler
    public void onRejoin(PlayerReJoinEvent event) {
        Player player = event.getPlayer();
        if (AFKTask.isAfkTaskRunning()) {
            player.setMetadata("lastMoveTime", new FixedMetadataValue(BedWars1058Addons.plugin, System.currentTimeMillis()));
        }
    }

    @EventHandler
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent event) {
        Player player = event.getPlayer();
        if (AFKTask.isAfkTaskRunning()) {
            player.removeMetadata("lastMoveTime", BedWars1058Addons.plugin);
        }
    }

    @EventHandler
    public void onEnd(GameEndEvent event) {
        if (AFKTask.isAfkTaskRunning()) {
            for (Player p : event.getArena().getPlayers()) {
                p.removeMetadata("lastMoveTime", BedWars1058Addons.plugin);
            }
            for (Player p : event.getArena().getSpectators()) {
                p.removeMetadata("lastMoveTime", BedWars1058Addons.plugin);
            }
            AFKTask.cancelAfkTask();
        }
    }
}