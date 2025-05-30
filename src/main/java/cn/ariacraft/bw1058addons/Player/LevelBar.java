package cn.ariacraft.bw1058addons.Player;

import cn.ariacraft.bw1058addons.BedWars1058Addons;
import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerJoinArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReSpawnEvent;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class LevelBar implements Listener {

    /**
     * 比较生草的方式 毕竟自用
     * !e.getArena().getGroup().startsWith("ultimate_") 为了防止超能力插件的冷却会顶掉
     * 25/5/30
     */
    @EventHandler
    public void onGameStart(GameStateChangeEvent e) {
        if (e.getNewState().equals(GameState.playing) && !e.getArena().getGroup().startsWith("ultimate_")) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(BedWars1058Addons.getInstance(), () -> e.getArena().getPlayers().forEach((player) -> {
                player.setLevel(Integer.parseInt(String.valueOf(BedWars.getLevelSupport().getPlayerLevel(player))));
                player.setExp((float) ((double) BedWars.getLevelSupport().getCurrentXp(player) / (double) BedWars.getLevelSupport().getRequiredXp(player)));
            }), 1L);
        }

    }

    @EventHandler
    public void onRespawn(PlayerReSpawnEvent e) {
        Player player = e.getPlayer();
        if (!Arena.getArenaByPlayer(player).getGroup().startsWith("ultimate_")) {
            player.setLevel(Integer.parseInt(String.valueOf(BedWars.getLevelSupport().getPlayerLevel(player))));
            player.setExp((float) ((double) BedWars.getLevelSupport().getCurrentXp(player) / (double) BedWars.getLevelSupport().getRequiredXp(player)));
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (Arena.isInArena(p)) {
            p.setLevel(0);
            p.setExp(0.0F);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinArenaEvent e) {
        Player p = e.getPlayer();
        p.setLevel(0);
        p.setExp(0.0F);
    }

    @EventHandler
    public void onLeave(PlayerLeaveArenaEvent e) {
        Player p = e.getPlayer();
        p.setLevel(0);
        p.setExp(0.0F);
    }
}
