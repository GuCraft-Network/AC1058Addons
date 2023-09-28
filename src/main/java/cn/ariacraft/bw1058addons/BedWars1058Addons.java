package cn.ariacraft.bw1058addons;

import cn.ariacraft.bw1058addons.AdminCommand.*;
import cn.ariacraft.bw1058addons.AfkKick.AfkListener;
import cn.ariacraft.bw1058addons.AfkKick.AfkTask;
import cn.ariacraft.bw1058addons.BugFix.ExplosionFix.ExplosionFix;
import cn.ariacraft.bw1058addons.BugFix.SharpSwordFix.SharpSwordFix;
import cn.ariacraft.bw1058addons.GameAnnouncements.GAListener;
import cn.ariacraft.bw1058addons.GameAnnouncements.GameAnnouncements;
import cn.ariacraft.bw1058addons.Player.LevelBar;
import cn.ariacraft.bw1058addons.Player.WaterWorkerHelmet;
import cn.ariacraft.bw1058addons.SpectatorSettings.SpectatorSettings;
import cn.ariacraft.bw1058addons.SpongeAnimation.Particle.ParticleSupport;
import cn.ariacraft.bw1058addons.SpongeAnimation.Particle.versions.Older;
import cn.ariacraft.bw1058addons.SpongeAnimation.SpongePlaceListener;
import com.andrei1058.bedwars.BedWars;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class BedWars1058Addons extends JavaPlugin {
    public static BedWars1058Addons instance;
    public static BedWars1058Addons plugin;
    //海绵动画所需变量
    private static String splash;
    private static String woodClick;
    private static ParticleSupport ParticleSupport;

    public static BedWars1058Addons getInstance() {
        return instance;
    }

    public static String getSplash() {
        return splash;
    }

    public static String getWoodClick() {
        return woodClick;
    }

    public static ParticleSupport getParticleSupport() {
        return ParticleSupport;
    }

    @Override
    public void onLoad() {
        getLogger().info(ChatColor.GREEN + "AC1058Addon 加载中....");
        removePlayerdata.remove();
    }

    @Override
    public void onEnable() {
        instance = this;
        plugin = this;
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————AriaCraft————————");
        getLogger().info(ChatColor.GREEN + "插件已启用");
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————AriaCraft————————");
        getServer().getPluginManager().registerEvents(new ExplosionFix(), this); // 防爆末地石修复
        getServer().getPluginManager().registerEvents(new SharpSwordFix(), this); // 掉落剑附魔
        getServer().getPluginManager().registerEvents(new SpectatorSettings(), this); // 旁观者设置
        getServer().getPluginManager().registerEvents(new SpongePlaceListener(), this); // 海绵动画
        getServer().getPluginManager().registerEvents(new WaterWorkerHelmet(), this); // 水下呼吸头盔
        getServer().getPluginManager().registerEvents(new LevelBar(), this); // 等级条
        getServer().getPluginManager().registerEvents(new GAListener(), this); // 定时公告
        getServer().getPluginManager().registerEvents(new AfkListener(), this); // AFK检查
        getServer().getPluginManager().registerEvents(new onGameEndRestart(), this); // 在游戏结束时重启游戏
        registerCommand(); //注册指令


        //隐身脚印已移至起床战争插件本体。
        //海绵动画所需变量
        splash = BedWars.getForCurrentVersion("SPLASH", "ENTITY_PLAYER_SPLASH", "ENTITY_PLAYER_SPLASH");
        woodClick = BedWars.getForCurrentVersion("WOOD_CLICK", "BLOCK_WOOD_BUTTON_CLICK_ON", "BLOCK_WOODEN_BUTTON_CLICK_ON");
        ParticleSupport = new Older();
    }

    void registerCommand() {
        Arrays.asList(
                new SpectatorSettings(),
                new ForceJoin(),
                new NextEvent(),
                new SkipEvent(),
                new SetBed(),
                new onGameEndRestart()).forEach(this::registerCommand);
    }

    private void registerCommand(final Command cmd) {
        MinecraftServer.getServer().server.getCommandMap().register(cmd.getName(), this.getName(), cmd);
    }

    @Override
    public void onDisable() {
        AfkTask.cancelAllTasks();
        GameAnnouncements.cancelAllAnnouncements();
        removePlayerdata.remove();
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————AriaCraft————————");
        getLogger().info(ChatColor.GREEN + "插件已关闭");
        getLogger().info(ChatColor.LIGHT_PURPLE + "————————AriaCraft————————");
    }
}

