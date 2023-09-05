package cn.ariacraft.bw1058addons.AdminCommand;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NextEvent extends Command {

    public NextEvent() {
        super("nextevent");
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
            p.sendMessage("§c用法：/nextevent <地图具体名称> <事件名>。");
            return true;
        }
        String arenaWorld = args[0];
        if (Arena.getArenaByName(arenaWorld) == null) {
            p.sendMessage("§c地图不存在！");
            return true;
        }

        IArena arena;
        arena = Arena.getArenaByName(args[0]);
        String event = args[1].toLowerCase();

        switch (event) {
            case "diamond-2":
                arena.setNextEvent(com.andrei1058.bedwars.api.arena.NextEvent.DIAMOND_GENERATOR_TIER_II);
                break;
            case "diamond-3":
                arena.setNextEvent(com.andrei1058.bedwars.api.arena.NextEvent.DIAMOND_GENERATOR_TIER_III);
                break;
            case "emerald-2":
                arena.setNextEvent(com.andrei1058.bedwars.api.arena.NextEvent.EMERALD_GENERATOR_TIER_II);
                break;
            case "emerald-3":
                arena.setNextEvent(com.andrei1058.bedwars.api.arena.NextEvent.EMERALD_GENERATOR_TIER_III);
                break;
            case "bed-destroy":
                arena.setNextEvent(com.andrei1058.bedwars.api.arena.NextEvent.BEDS_DESTROY);
                break;
            case "dragon":
                arena.setNextEvent(com.andrei1058.bedwars.api.arena.NextEvent.ENDER_DRAGON);
                break;
            case "end":
                arena.setNextEvent(com.andrei1058.bedwars.api.arena.NextEvent.GAME_END);
        }
        p.sendMessage("§a成功！");
        return true;
    }
}
