package cn.ariacraft.bw1058addons.GameAnnouncements;

import cn.ariacraft.bw1058addons.BedWars1058Addons;
import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameAnnouncements {
    public static final String[] announcements = {
            "§c§l如果你断开连接，可以在起床大厅中使用/rejoin重新加入游戏。",
            "§c§l禁止队伍联合！使用/report举报违规队伍联合玩家。",
    };

    private static int currentAnnouncement = 0;

    private static Map<Player, BukkitTask> tasks = new HashMap<>();

    public static void startAnnouncements(IArena arena) {
        List<Player> players = new ArrayList<>(arena.getPlayers());

        for (Player player : players) {
            BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(BedWars1058Addons.getInstance(), () -> {
                if (player == null) {
                    cancelAnnouncement(player);
                    return;
                }

                if (currentAnnouncement >= announcements.length) {
                    currentAnnouncement = 0;
                }

                player.sendMessage(announcements[currentAnnouncement]);

                currentAnnouncement++;
            }, 25L * 60, 25L * 60 * 2);

            tasks.put(player, task);
        }
    }

    public static void cancelAnnouncement(Player player) {
        if (tasks.containsKey(player)) {
            BukkitTask task = tasks.get(player);
            task.cancel();
            tasks.remove(player);
        }
    }

    public static void cancelAnnouncements() {
        for (BukkitTask task : tasks.values()) {
            task.cancel();
        }
        tasks.clear();
    }
}