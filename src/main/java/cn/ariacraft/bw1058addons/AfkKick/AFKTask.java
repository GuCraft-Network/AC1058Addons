package cn.ariacraft.bw1058addons.AfkKick;

import cn.ariacraft.bw1058addons.BedWars1058Addons;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitTask;

public class AFKTask implements Listener {

    private static final int afkTime = 170; // s
    private static final int kickTime = 180; // s

    public static BukkitTask afkTask;

    public static void startAFKCheck(IArena arena) {
        afkTask = Bukkit.getScheduler().runTaskTimerAsynchronously(BedWars1058Addons.getInstance(), () -> {
            for (Player player : arena.getPlayers()) {
                if (player != null && arena.isPlayer(player)) {
                    long lastMoveTime = getLastMoveTime(player);
                    if (lastMoveTime == 0) {
                        continue; // 玩家第一次加入服务器时不计算挂机时间
                    }
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastMoveTime >= afkTime * 1000L) {
                        if (currentTime - lastMoveTime >= kickTime * 1000L) {
                            Bukkit.getScheduler().runTaskLater(BedWars1058Addons.getInstance(), () -> {
                                player.sendMessage(ChatColor.valueOf(Arena.getArenaByPlayer(player).getTeam(player).getColor().toString()) + player.getDisplayName() + "§7因挂机离开了游戏。");
                                player.kickPlayer("§c§l你因挂机超过180秒而被移出。");
                            }, 20L);
                        } else if (currentTime - lastMoveTime >= 2 * 60 * 1000 && currentTime - lastMoveTime < 3 * 60 * 1000) {
                            final String warningMessage = "§c你将因挂机而被移出游戏。";
                            player.sendMessage(warningMessage);
                            for (int i = 0; i < 10; i++) {
                                final int seconds = i * 2;
                                Bukkit.getScheduler().runTaskLaterAsynchronously(BedWars1058Addons.getInstance(),
                                        () -> player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F), seconds * 2L);
                            }
                        }
                    }
                }
            }
        }, 0L, 20L); // 每秒检查一次
    }

    private static long getLastMoveTime(Player player) {
        long lastMoveTime = 0;
        for (MetadataValue meta : player.getMetadata("lastMoveTime")) {
            if (meta.getOwningPlugin().equals(BedWars1058Addons.plugin)) {
                lastMoveTime = meta.asLong();
                break;
            }
        }
        return lastMoveTime;
    }

}