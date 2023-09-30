package cn.ariacraft.bw1058addons;

import org.bukkit.Bukkit;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class pluginsUpdater {

    public static void moveFiles() {
        System.out.println("Checking for updates. Please Wait...");
        String serverDirectoryPath = System.getProperty("user.dir");

        // 根据工作目录创建 File 对象
        File folder = new File(serverDirectoryPath + "/update");

        // 检查文件夹是否存在
        if (folder.exists() && folder.isDirectory()) {
            // 获取文件夹中的所有文件
            File[] files = folder.listFiles();

            // 确保 plugins 文件夹存在
            File pluginsFolder = new File(serverDirectoryPath + "/plugins");
            if (!pluginsFolder.exists()) {
                pluginsFolder.mkdirs();
            }

            boolean needRestart = false;
            // 将文件移动到 plugins 文件夹中
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        try {
                            Files.move(file.toPath(), (new File(pluginsFolder.getAbsolutePath() + "/" + file.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("Moved file: " + file.getName());
                            needRestart = true;
                        } catch (Exception e) {
                            System.out.println("Failed to move file: " + file.getName());
                            needRestart = false;
                        }
                    }
                }
                if (needRestart) {
                    System.out.println("Done! The server is restarting...");
                    Bukkit.getServer().spigot().restart();
                }
            }
        }
    }
}