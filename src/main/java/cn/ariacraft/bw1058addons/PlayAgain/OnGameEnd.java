package cn.ariacraft.bw1058addons.PlayAgain;

import cn.ariacraft.bw1058addons.BedWars1058Addons;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnGameEnd implements Listener {

    @EventHandler
    public void onGameEnd(GameEndEvent e) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(BedWars1058Addons.getInstance(), () -> {
            TextComponent tc = new TextComponent("§b要再来一局吗？ " + "§6§l点击这里");
            tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/playagain"));
            for (Player p : e.getArena().getPlayers()) {
                p.spigot().sendMessage(tc);
            }
            for (Player p : e.getArena().getSpectators()) {
                p.spigot().sendMessage(tc);
            }
        }, 40L);
    }
}
