package tech.bedev.discordsrvutils.commands;


import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.JDA;
import github.scarsz.discordsrv.dependencies.jda.api.OnlineStatus;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.arim.dazzleconf.error.InvalidConfigException;
import tech.bedev.discordsrvutils.DiscordSRVUtils;
import tech.bedev.discordsrvutils.StatusUpdater;
import tech.bedev.discordsrvutils.UpdateChecker;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class DiscordSRVUtilsCommand implements CommandExecutor {
    public static JDA getJda() {
        return DiscordSRV.getPlugin().getJda();
    }
    Map<String, Long> map = new HashMap<>();

    public static JDA jda;

    int warnings = 0;
    int errors = 0;
    private final DiscordSRVUtils core;

    public DiscordSRVUtilsCommand(DiscordSRVUtils core) {
        this.core = core;
    }


    private FileConfiguration newConfig = null;


    private File configFile = null;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(args.length >= 1)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bRunning DiscordSRVUtils v.&a" + core.getDescription().getVersion() + " "));
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("discordsrvutils.reload")) {
                    if (!DiscordSRV.isReady) {
                        sender.sendMessage(ChatColor.RED + "It seems like DiscordSRV haven't logged in to discord yet.");
                        return true;
                    }
                    sender.sendMessage(ChatColor.GREEN + "Reloading...");
                    YamlConfiguration config = new YamlConfiguration();
                    try {
                        core.ModerationConfigManager.reloadConfig();
                        DiscordSRVUtils.Moderationconfig = core.ModerationConfigManager.reloadConfigData();
                        core.SQLConfigManager.reloadConfig();
                        DiscordSRVUtils.SQLconfig = core.SQLConfigManager.reloadConfigData();
                        core.LevelingConfigManager.reloadConfig();
                        DiscordSRVUtils.Levelingconfig = core.LevelingConfigManager.reloadConfigData();
                        core.BotSettingsConfigManager.reloadConfig();
                        DiscordSRVUtils.BotSettingsconfig = core.BotSettingsConfigManager.reloadConfigData();
                        core.BansIntegrationConfigManager.reloadConfig();
                        DiscordSRVUtils.BansIntegrationconfig = core.BansIntegrationConfigManager.reloadConfigData();
                        core.MainConfManager.reloadConfig();
                        DiscordSRVUtils.Config = core.MainConfManager.reloadConfigData();
                        core.SuggestionsConfManager.reloadConfig();
                        DiscordSRVUtils.SuggestionsConfig = core.SuggestionsConfManager.reloadConfigData();
                    } catch (IOException |InvalidConfigException exception) {
                        sender.sendMessage(ChatColor.RED + "Config Broken. Check the error on console.");
                        exception.printStackTrace();
                        return true;
                    }

                    if (DiscordSRVUtils.BotSettingsconfig.isStatusUpdates()) {
                        DiscordSRVUtils.timer = new Timer();
                            String l = DiscordSRVUtils.BotSettingsconfig.Status_Update_Interval() + "000";
                            DiscordSRVUtils.timer.schedule(new StatusUpdater(core), 0, Integer.parseInt(l));

                    }
                    String status = DiscordSRVUtils.BotSettingsconfig.status();
                    if (status != null) {
                        switch (status.toUpperCase()) {
                            case "DND":
                                getJda().getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
                                break;
                            case "IDLE":
                                getJda().getPresence().setStatus(OnlineStatus.IDLE);
                                break;
                            case "ONLINE":
                                getJda().getPresence().setStatus(OnlineStatus.ONLINE);
                                break;
                            default:
                                sender.sendMessage("Unknown bot status in BotSettings.yml");
                                errors++;
                        }
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bPlugin reloaded with &e" + errors + " &berrors and &e" + warnings + "&b warnings."));
                    warnings = 0;
                    errors = 0;
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have perms to use this command.");
                }

            } else if (args[0].equalsIgnoreCase("updatecheck")) {
                if (sender.hasPermission("discordsrvutils.updatecheck")) {
                    sender.sendMessage(ChatColor.GREEN + "Checking for updates...");

                    String newVersion = UpdateChecker.getLatestVersion();
                    if (newVersion.equalsIgnoreCase(core.getDescription().getVersion())) {
                        core.getLogger().info(net.md_5.bungee.api.ChatColor.GREEN + "No new version available. (" + newVersion + ")");
                        sender.sendMessage(ChatColor.YELLOW + "No new version available.");
                    } else {
                        core.getLogger().info(net.md_5.bungee.api.ChatColor.GREEN + "A new version is available. Please update ASAP!" + " Your version: " + net.md_5.bungee.api.ChatColor.YELLOW + core.getDescription().getVersion() + net.md_5.bungee.api.ChatColor.GREEN + " New version: " + net.md_5.bungee.api.ChatColor.YELLOW + newVersion);
                        TextComponent msg = new TextComponent(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "&aA newer version of DiscordSRVUtils is available.\n&9Your version: &5" + core.getDescription().getVersion() + "\n&9Newer version: &5" + newVersion + "\n&6Click to download."));
                        msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/discordsrvutils.85958/updates"));
                        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(net.md_5.bungee.api.ChatColor.GREEN + "" + net.md_5.bungee.api.ChatColor.BOLD + "Click to download").create()));
                        sender.spigot().sendMessage(msg);
                    }

                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have perms to use this command");
                }
            } else if (args[0].equalsIgnoreCase("clearmemory")) {
                if (sender.hasPermission("discordsrvutils.clearmemory")) {
                    sender.sendMessage(ChatColor.RED + "Are you sure you want to clear memory? This will cancel: \n\n" + ChatColor.DARK_BLUE +
                            "People creating tickets\nPeople making suggestions\nPeople making suggestion reply\nHelp commands\nAnti-spam for leveling");
                    sender.sendMessage(ChatColor.GREEN + "Type /" + label + " confirm to confirm in 10 seconds.");
                    map.remove(sender.getName());
                    map.putIfAbsent(sender.getName(), System.currentTimeMillis());
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                }
            } else if (args[0].equals("confirm")) {
                Long puttime = map.get(sender.getName());
                if (puttime == null) {
                    sender.sendMessage("You don't have anything to confirm");
                } else {
                    if ((System.currentTimeMillis() - puttime) <= 10000L) {
                        try (Connection conn = core.getMemoryConnection()) {
                            conn.prepareStatement("DELETE FROM suggestions_Awaiting").execute();
                            conn.prepareStatement("DELETE FROM tickets_creating").execute();
                            conn.prepareStatement("DELETE FROM discordsrvutils_ticket_allowed_roles").execute();
                            conn.prepareStatement("DELETE FROM discordsrvutils_Awaiting_Edits").execute();
                            conn.prepareStatement("DELETE FROM helpmsges").execute();
                            conn.prepareStatement("DELETE FROM srmsgesreply").execute();
                            core.lastchattime = new HashMap<>();
                            sender.sendMessage(ChatColor.GREEN + "Memory cleared!");
                        } catch (SQLException ex) {
                            sender.sendMessage(ChatColor.RED + "Could not clear memory.");
                            ex.printStackTrace();
                        }
                        map.remove(sender.getName());
                    } else {
                        sender.sendMessage(ChatColor.RED + "You are late! 10 seconds already passed");
                    }
                }

            }
            else {
                sender.sendMessage(ChatColor.RED + "Unknown arg \"" + args[0] + "\"");
            }
        }
        return true;

    }

    public InputStream getResource(@NotNull String filename) {
        try {
            URL url = getClass().getClassLoader().getResource(filename);

            if (url == null) {
                return null;
            }

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException ex) {
            return null;
        }
    }
}
