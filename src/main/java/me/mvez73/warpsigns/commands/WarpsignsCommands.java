package me.mvez73.warpsigns.commands;

import me.mvez73.warpsigns.WarpSigns;
import me.mvez73.warpsigns.files.ConfigManager;
import me.mvez73.warpsigns.files.WarpLocations;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpsignsCommands implements CommandExecutor {

    private final WarpSigns plugin;
    public WarpsignsCommands(WarpSigns plugin){
         this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player p)){
            sender.sendMessage("Only player can send commands.");
            return true;
        }
        if (args.length == 0 && p.hasPermission("warpsigns.admin")) {
            p.sendMessage(ChatColor.RED + "Usage: /warpsigns [reload/listworlds]");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && p.hasPermission("warpsigns.admin")){
            plugin.reloadConfig();
            WarpLocations.reload();
            p.sendMessage(ConfigManager.getPrefix() + "WarpSigns config reloaded");
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("listworlds") && p.hasPermission("warpsigns.admin")){
            for (String configWorld : plugin.getConfig().getStringList("worlds")){
                p.sendMessage(ConfigManager.getPrefix() + "World available: " + configWorld);
            }

        }
        return true;
    }
}
