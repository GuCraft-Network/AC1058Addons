package cn.ariacraft.bw1058addons.AdminCommand;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceJoin extends Command {

    /**
     * https://www.spigotmc.org/resources/bedwars1058-adminaddon.110145/
     * ForceJoin 好像没什么用
     * 25/5/30
     */

    public ForceJoin() {
        super("forcejoin");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!p.hasPermission("bw.admin")) {
            p.sendMessage("§c你没有使用此命令的权限！");
            return true;
        }
        if (args.length < 2) {
            p.sendMessage("§c用法：/forcejoin <玩家> <地图具体名称>。");
            return true;
        }
        Player joinP = Bukkit.getPlayer(args[0]);
        if (joinP == null) {
            p.sendMessage("§c玩家不存在！");
            return true;
        }
        String arenaWorld = args[1];
        if (Arena.getArenaByName(arenaWorld) == null) {
            p.sendMessage("§c地图不存在！");
            return true;
        }
        IArena arena;
        arena = Arena.getArenaByName(arenaWorld);
        arena.addPlayer(joinP, true);
        p.sendMessage("§a成功强制该玩家加入该游戏！");
        return true;
    }
}
