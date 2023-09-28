package cn.ariacraft.bw1058addons.AfkKick;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerJoinArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static cn.ariacraft.bw1058addons.AfkKick.AfkTask.afkPlayers;
import static cn.ariacraft.bw1058addons.AfkKick.AfkTask.kickTasks;

public class AfkListener implements Listener {

    @EventHandler
    private void onGameStart(GameStateChangeEvent event) {
        if (event.getNewState() == GameState.playing) {
            AfkTask.startKickTask(event.getArena().getWorld());
        }
    }

    @EventHandler
    private void onGameEnd(GameEndEvent event) {
        if (kickTasks.containsKey(event.getArena().getWorld())) {
            AfkTask.cancelKickTask(event.getArena().getWorld());
        }
    }

    @EventHandler
    private void onPlayerLeave(PlayerLeaveArenaEvent event) {
        afkPlayers.remove(event.getPlayer().getUniqueId());
    }
}
