package cn.ariacraft.bw1058addons.AfkKick;

import cn.ariacraft.bw1058addons.BedWars1058Addons;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AfkTask {

    public static final int kickTimeInSeconds = 180; // 设置踢出时间（单位：秒）
    public static final int afkTimeInSeconds = 170; // 设置提示时间（单位：秒）
    public static Map<World, BukkitRunnable> kickTasks = new HashMap<>();
    public static Map<UUID, Long> afkPlayers = new HashMap<>();

    public static void startKickTask(World world) {
        // 检查该世界是否已经在进行挂机检测
        if (kickTasks.containsKey(world)) {
            return;
        }

        // 创建异步任务来检测玩家是否挂机
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : world.getPlayers()) {
                    if (afkPlayers.get(player.getUniqueId()) == null) {
                        afkPlayers.replace(player.getUniqueId(), System.currentTimeMillis());
                    }
                    long afkTime = System.currentTimeMillis() - afkPlayers.get(player.getUniqueId());
                    if (afkTime >= kickTimeInSeconds * 1000) {
                        for (Player p : world.getPlayers()) {
                            p.sendMessage(Arena.getArenaByPlayer(player).getTeam(player).getColor().chat() + player.getDisplayName() + "§7因挂机离开了游戏。");
                        }
                        player.kickPlayer("§c§l你因挂机超过180秒而被移出。");
                    } else if (afkTime >= afkTimeInSeconds * 1000) {
                        player.sendMessage("§c你将因挂机而被移出游戏。");
                        for (int i = 0; i < 10; i++) {
                            final int seconds = i * 2;
                            Bukkit.getScheduler().runTaskLater(BedWars1058Addons.plugin,
                                    () -> player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F), seconds * 2L);
                        }
                    }
                }
            }
        };
        task.runTaskTimerAsynchronously(BedWars1058Addons.plugin, 0, 20); // 每秒运行一次任务

        kickTasks.put(world, task);
    }

    public static void cancelKickTask(World world) {
        BukkitRunnable task = kickTasks.get(world);
        if (task != null) {
            task.cancel();
            kickTasks.remove(world);
        }
    }
    public static void cancelAllTasks() {
        for (BukkitRunnable task : kickTasks.values()) {
            task.cancel();
        }
        kickTasks.clear();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (kickTasks.containsKey(event.getFrom().getWorld())) {
            afkPlayers.replace(playerId, System.currentTimeMillis());
        }
    }
}
