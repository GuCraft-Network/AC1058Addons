package cn.ariacraft.bw1058addons.GameAnnouncements;

import cn.ariacraft.bw1058addons.BedWars1058Addons;
import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class GameAnnouncements {

    public static final String[] announcements = {
            "§c§l如果你断开连接，可在起床大厅中输入/rejoin重新加入游戏。",
            "§c§l禁止队伍联合！使用/report举报违规玩家。",
    };

    public static BukkitTask task;
    public static int currentAnnouncement = 0;

    public static void startAnnouncements() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(BedWars1058Addons.getInstance(), () -> {
            currentAnnouncement++;

            if (currentAnnouncement >= announcements.length) {
                currentAnnouncement = 0;
            }

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(announcements[currentAnnouncement]);
            }
        }, 25L * 60, 25L * 60 * 2);
    }
}
