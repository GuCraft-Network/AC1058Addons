package cn.ariacraft.bw1058addons.AdminCommand;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetBed extends Command {


    public SetBed() {
        super("setbed");
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
            p.sendMessage("§c用法：/setbed <玩家> <true/false>。");
            return true;
        }
        Player joinP = Bukkit.getPlayer(args[0]);
        if (joinP == null) {
            p.sendMessage("§c玩家不存在！");
            return true;
        }
        if (Arena.getArenaByPlayer(joinP) == null) {
            p.sendMessage("§c该玩家没有加入游戏！");
            return true;
        }
        IArena arena;
        ITeam mplayerteam;
        arena = Arena.getArenaByPlayer(joinP);
        switch (args[1].toLowerCase()) {
            case "true":
                mplayerteam = arena.getPlayerTeam(joinP.getName());
                mplayerteam.setBedDestroyed(false);
                p.sendMessage("§a成功！");
                break;
            case "false":
                mplayerteam = arena.getPlayerTeam(joinP.getName());
                mplayerteam.setBedDestroyed(true);
                p.sendMessage("§a成功！");
        }
        return true;
    }
}
