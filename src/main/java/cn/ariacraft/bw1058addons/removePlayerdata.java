package cn.ariacraft.bw1058addons;

import org.bukkit.Bukkit;

import java.io.File;

public class removePlayerdata {

    /**
     * 建议使用: https://github.com/kamcio96/DisablePlayerData
     * 25/5/30
     */

    public static void remove() {
        System.out.println("Deleteing PlayerData Files. Please Wait...");
        File[] worlds = Bukkit.getWorldContainer().listFiles(File::isDirectory);

        if (worlds != null) {
            for (File world : worlds) {
                //playerData
                File playerDataFolder = new File(world, "playerdata");

                if (playerDataFolder.exists() && playerDataFolder.isDirectory()) {
                    File[] playerDataFiles = playerDataFolder.listFiles();

                    if (playerDataFiles != null) {
                        for (File file : playerDataFiles) {
                            file.delete();
                        }
                    }
                }
                //Data
                System.out.println("Deleteing Data Files. Please Wait...");
                File dataFolder = new File(world, "data");

                if (dataFolder.exists() && dataFolder.isDirectory()) {
                    File[] dataFolderFiles = dataFolder.listFiles();

                    if (dataFolderFiles != null) {
                        for (File file : dataFolderFiles) {
                            file.delete();
                        }
                    }
                }
            }
        }

    }
}