package cn.ariacraft.bw1058addons.AdminCommand;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.NextEvent;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkipEvent extends Command {

    /**
     * https://www.spigotmc.org/resources/bedwars1058-adminaddon.110145/
     * 顾名思义 跳过事件
     * 25/5/30
     */


    public SkipEvent() {
        super("skipevent");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!p.hasPermission("bw.admin")) {
            p.sendMessage("§c你没有使用此命令的权限！");
            return true;
        }
        if (args.length == 0) {
            p.sendMessage("§c用法：/skipevent <地图具体名称>。");
            return true;
        }
        String arenaWorld = args[0];
        if (Arena.getArenaByName(arenaWorld) == null) {
            p.sendMessage("§c地图不存在！");
            return true;
        }

        IArena arena;
        arena = Arena.getArenaByName(args[0]);
        String event = arena.getNextEvent().toString();
        p.sendMessage(event);
        switch (event) {
            case "NextEvent.DIAMOND_GENERATOR_TIER_II":
                arena.setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_III);
                break;
            case "NextEvent.DIAMOND_GENERATOR_TIER_III":
                arena.setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_II);
                break;
            case "EMERALD_GENERATOR_TIER_II":
                arena.setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_III);
                break;
            case "EMERALD_GENERATOR_TIER_III":
                arena.setNextEvent(NextEvent.BEDS_DESTROY);
                break;
            case "NextEvent.BEDS_DESTROY":
                arena.setNextEvent(NextEvent.ENDER_DRAGON);
                break;
            case "NextEvent.ENDER_DRAGON":
                arena.setNextEvent(NextEvent.GAME_END);
                break;
            case "NextEvent.GAME_END":
                p.sendMessage("§a你已经到最后一个事件了！");
                return true;
        }
        return true;
    }

}