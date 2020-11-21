package com.bluetree.discordsrvutils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {

    private DiscordSRVUtils plugin;

    public UpdateChecker(DiscordSRVUtils plugin) {
        this.plugin = plugin;
    }


    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (Scanner scanner = new Scanner(new URL("http://bluecraftweb.000webhostapp.com/Plugins/DiscordSRVUtils.html").openStream())) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                this.plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
            }
        });
    }
}
