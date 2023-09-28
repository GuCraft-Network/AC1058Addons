package cn.ariacraft.bw1058addons.GameAnnouncements;

import cn.ariacraft.bw1058addons.BedWars1058Addons;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class GameAnnouncements {

    public static final String[] announcements = {
            "§c§l如果你断开连接，可以在起床大厅中使用/rejoin重新加入游戏。",
            "§c§l禁止队伍联合！使用/report举报违规队伍联合玩家。",
    };
    private static final Map<World, BukkitTask> tasks = new HashMap<>();

    public static void startAnnouncements(World world) {
        if (tasks.containsKey(world)) {
            return; // 如果该世界上已经存在公告任务，则不再启动
        }

        final int[] index = {0}; // 将索引声明为 final

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                if (announcements != null) {
                    String announcement = announcements[index[0]];
                    for (Player player : world.getPlayers()) {
                        if (player.isOnline()) {
                            sendAnnouncement(player, announcement); // 发送公告
                        }
                    }
                    index[0] = (index[0] + 1) % announcements.length;
                    if (index[0] == 0) {
                        index[0] = 1; // 如果已经发送完所有公告，则重新开始索引
                    }
                }
            }
        }.runTaskTimerAsynchronously(BedWars1058Addons.plugin, 25L * 60, 25L * 60 * 2);

        tasks.put(world, task); // 将任务添加到映射中，以备取消
    }

    private static void sendAnnouncement(Player player, String announcement) {
        player.sendMessage(announcement);
    }

    public static void cancelAnnouncements(World world) {
        BukkitTask task = tasks.get(world);
        if (task != null) {
            task.cancel();
            tasks.remove(world); // 从映射中删除已取消的任务
        }
    }

    public static void cancelAllAnnouncements() {
        for (BukkitTask task : tasks.values()) {
            task.cancel();
        }
        tasks.clear(); // 清除所有任务
    }

}
