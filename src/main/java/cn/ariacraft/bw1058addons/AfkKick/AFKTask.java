package cn.ariacraft.bw1058addons.AfkKick;

import cn.ariacraft.bw1058addons.BedWars1058Addons;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AFKTask implements Listener {
    private static final int AFK_TIME = 170; // s
    private static final int KICK_TIME = 180; // s
    private static final Map<Player, BukkitTask> afkTasks = new HashMap<>();
    private static final Plugin plugin = BedWars1058Addons.getInstance();
    private static final BukkitScheduler scheduler = plugin.getServer().getScheduler();

    public static void startAFKCheck(IArena arena) {

        HashSet<Player> playersWithAfkTask = new HashSet<>(afkTasks.keySet());
        for (Player player : arena.getPlayers()) {
            if (!playersWithAfkTask.contains(player)) {
                BukkitTask afkTask = scheduler.runTaskTimer(plugin, () -> {
                    if (player == null) {
                        return;
                    }
                    long lastMoveTime = getLastMoveTime(player);
                    if (lastMoveTime == 0) {
                        return; // 玩家第一次加入服务器时不计算挂机时间
                    }
                    long currentTime = System.currentTimeMillis();
                    long afkMs = TimeUnit.SECONDS.toMillis(AFK_TIME);
                    long kickMs = TimeUnit.SECONDS.toMillis(KICK_TIME);

                    if (currentTime - lastMoveTime >= afkMs) {
                        if (currentTime - lastMoveTime >= kickMs) {
                            Bukkit.getScheduler().runTask(plugin, () -> {
                                IArena arenaByPlayer = Arena.getArenaByPlayer(player);
                                ITeam team = arenaByPlayer.getTeam(player);
                                if (team != null) {
                                    ChatColor teamColor = team.getColor().chat();
                                    String displayName = player.getDisplayName();
                                    player.sendMessage(teamColor + displayName + "§7因挂机离开了游戏。");
                                    player.kickPlayer("§c§l你因挂机超过180秒而被移出。");
                                    cancelAfkTask(player);
                                }
                            });
                        } else if (currentTime - lastMoveTime >= TimeUnit.MINUTES.toMillis(2) && currentTime - lastMoveTime < TimeUnit.MINUTES.toMillis(3)) {
                            final String warningMessage = "§c你将因挂机而被移出游戏。";
                            player.sendMessage(warningMessage);
                            for (int i = 0; i < 10; i++) {
                                final int seconds = i * 2;
                                scheduler.runTaskLater(plugin,
                                        () -> player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F), seconds * 2L);
                            }
                        }
                    }
                }, 0L, 20L); // 每秒检查一次
                afkTasks.put(player, afkTask);
            }
        }
    }

    public static void cancelAfkTask(Player player) {
        if (afkTasks.containsKey(player)) {
            BukkitTask afkTask = afkTasks.get(player);
            afkTask.cancel();
            afkTasks.remove(player);
        }
    }

    public static boolean isAfkTaskRunning(Player player) {
        return afkTasks.containsKey(player);
    }

    private static long getLastMoveTime(Player player) {
        if (player.hasMetadata("lastMoveTime")) {
            for (MetadataValue meta : player.getMetadata("lastMoveTime")) {
                if (meta.getOwningPlugin().equals(BedWars1058Addons.getInstance())) {
                    return meta.asLong();
                }
            }
        }
        return 0;
    }
}
