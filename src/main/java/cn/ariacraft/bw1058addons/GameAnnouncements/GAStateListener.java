package cn.ariacraft.bw1058addons.GameAnnouncements;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GAStateListener implements Listener {

    @EventHandler
    private void onStart(GameStateChangeEvent e) {
        if (e.getNewState() == GameState.playing) {
            GameAnnouncements.startAnnouncements();
        }
    }

    @EventHandler
    private void onEnd(GameEndEvent e) {
        GameAnnouncements.task.cancel();
    }
}
