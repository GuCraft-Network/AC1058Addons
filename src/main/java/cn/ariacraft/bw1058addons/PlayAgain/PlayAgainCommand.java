package cn.ariacraft.bw1058addons.PlayAgain;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayAgainCommand extends Command {
    public static String Group = "";

    public PlayAgainCommand() {
        super("playagain");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        switch (Arena.getArenaByPlayer(p).getGroup()) {
            case "solo":
                Group = "hyp1v";
                break;
            case "doubles":
                Group = "hyp2v";
                break;
            case "4v4v4v4":
                Group = "hyp4v";
                break;
            case "rush_doubles":
                Group = "rhyp2v";
                break;
            case "rush_4v4v4v4":
                Group = "rhyp4v";
                break;
            case "ultimate_4v4v4v4":
                Group = "uhyp4v";
                break;
            case "swap_4v4v4v4":
                Group = "shyp4v";
        }
        Bukkit.dispatchCommand(p.getPlayer(), "sj fastjoin " + Group);
        return true;
    }
}
