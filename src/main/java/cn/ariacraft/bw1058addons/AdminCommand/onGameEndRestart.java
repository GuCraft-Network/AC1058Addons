package cn.ariacraft.bw1058addons.AdminCommand;

import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class onGameEndRestart extends Command implements Listener {

    /**
     * https://www.spigotmc.org/resources/bedwars1058-adminaddon.110145/
     * 我们是10场游戏后重启一次子服, 有些时候更新比较急就需要这个
     * 25/5/30
     */

    public static boolean NextGameRestart = false;

    public onGameEndRestart() {
        super("gameendrestart");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!sender.hasPermission("bw.admin")) {
            sender.sendMessage("§c你没有使用此命令的权限！");
            return true;
        }
        NextGameRestart = true;
        sender.sendMessage("§a成功！");
        return true;
    }


    @EventHandler
    public void onEnd(GameEndEvent e) {
        if (NextGameRestart) {
            Bukkit.shutdown();
        }
    }
}
