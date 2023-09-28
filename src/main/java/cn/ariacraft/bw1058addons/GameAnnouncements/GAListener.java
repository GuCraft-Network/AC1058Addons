package cn.ariacraft.bw1058addons.GameAnnouncements;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GAListener implements Listener {

    @EventHandler
    private void onGameStart(GameStateChangeEvent event) {
        if (event.getNewState() == GameState.playing) {
            GameAnnouncements.startAnnouncements(event.getArena().getWorld());
        }
    }

    @EventHandler
    private void onGameEnd(GameEndEvent event) {
        GameAnnouncements.cancelAnnouncements(event.getArena().getWorld());
    }
}
