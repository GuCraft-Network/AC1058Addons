package cn.ariacraft.bw1058addons.GameAnnouncements;

import cn.ariacraft.bw1058addons.BedWars1058Addons;
import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class GameAnnouncements {

    public static final String[] announcements = {
            "§c§l如果你断开连接，可以在起床大厅中使用/rejoin重新加入游戏。",
            "§c§l禁止队伍联合！使用/report举报违规队伍联合玩家。",
    };

    private static BukkitTask task;
    private static int currentAnnouncement = 0;

    public static void startAnnouncements(IArena arena) {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(BedWars1058Addons.getInstance(), () -> {
            if (currentAnnouncement >= announcements.length) {
                currentAnnouncement = 0;
            }

            for (Player p : arena.getPlayers()) {
                if (arena.getPlayers() == null) task.cancel();
                if (p != null && arena.isPlayer(p)) {
                    p.sendMessage(announcements[currentAnnouncement]);
                }
            }

            currentAnnouncement++;
        }, 25L * 60, 25L * 60 * 2);
    }

    public static boolean isAnnouncementsRunning() {
        return task != null;
    }

    public static void cancelAnnouncements() {
        if (task != null) {
            task.cancel();
        }
    }
}
