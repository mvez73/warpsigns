package me.mvez73.warpsigns.events;

import me.mvez73.warpsigns.WarpSigns;
import me.mvez73.warpsigns.files.ConfigManager;
import me.mvez73.warpsigns.files.WarpLocations;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.List;
import java.util.Objects;

public class OnSignChange implements Listener {
    private final WarpSigns plugin;

    public OnSignChange(WarpSigns plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onSignChange(@org.jetbrains.annotations.NotNull SignChangeEvent e){
        Player p = e.getPlayer();

        // TODO Add inter world configuration
        String configLine = plugin.getConfig().getString("signTitle");
        String coloredLine = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(configLine));
        String stripedLine = ChatColor.stripColor(coloredLine);
        if(Objects.requireNonNull(e.getLine(0)).equalsIgnoreCase(stripedLine)){
            if (p.hasPermission("warpsigns.use") || p.hasPermission("warpsigns.admin") || p.hasPermission("warpsigns.*")) {
                String warpName = e.getLine(1);
                if (warpName != null && !warpName.equals("")) {
                    e.setLine(0, ChatColor.translateAlternateColorCodes('&', configLine));
                    // Get location and save it to data file
                    Location l = e.getBlock().getLocation();
                    List<String> configWorld = plugin.getConfig().getStringList("worlds");
                    String world = Objects.requireNonNull(l.getWorld()).getName();
                    if (!configWorld.contains(world)){
                        p.sendMessage(ConfigManager.getPrefix() + ChatColor.RED + "You can't put warp sign in this world " + ChatColor.GRAY + world + ChatColor.RED + ". Available world(s): " + ChatColor.GRAY + configWorld);
                        e.setCancelled(true);
                    }else{
                        double signX = l.getX();
                        double signY = l.getY();
                        double signZ = l.getZ();
                        double playerX = p.getLocation().getX();
                        double playerY = p.getLocation().getY();
                        double playerZ = p.getLocation().getZ();
                        float playerYaw = p.getLocation().getYaw();
                        int firstSecond;

                        if (WarpLocations.get().contains("warps." + p.getUniqueId() + "." + warpName + ".world1") && WarpLocations.get().contains("warps." + p.getUniqueId() + "." + warpName + ".world2")) {
                            p.sendMessage(ConfigManager.getPrefix() + ChatColor.RED + "You already have 2 signs placed for that warp. (" + ChatColor.RESET + warpName + ChatColor.RED + ") Please remove one sign to be able to place another one.");
                            e.setCancelled(true);
                        }else if (WarpLocations.get().contains("warps." + p.getUniqueId() + "." + warpName + ".world1")) {
                            firstSecond = 2;
                            CreateSignLocation(p, firstSecond, warpName, world, signX, signY, signZ, playerX, playerY, playerZ, playerYaw);
                            p.sendMessage(ConfigManager.getPrefix() + "Sign #1 exist already. Creating sign #" + firstSecond + "\nYou can now teleport yourself from one sign to the other.");
                        }else if (WarpLocations.get().contains("warps." + p.getUniqueId() + "." + warpName + ".world2")) {
                            firstSecond = 1;
                            CreateSignLocation(p, firstSecond, warpName, world, signX, signY, signZ, playerX, playerY, playerZ, playerYaw);
                            p.sendMessage(ConfigManager.getPrefix() + "Sign #1 exist already. Creating sign #" + firstSecond + "\nYou can now teleport yourself from one sign to the other.");
                        }else{
                            firstSecond = 1;
                            CreateSignLocation(p, firstSecond, warpName, world, signX, signY, signZ, playerX, playerY, playerZ, playerYaw);
                            p.sendMessage(ConfigManager.getPrefix() + "This is your first sign for warp " + ChatColor.RESET + ChatColor.GREEN + warpName + ChatColor.RESET + ".\nPlace another one with that name to teleport yourself from one sign to the other.");
                        }
                    }

                } else {
                    p.sendMessage(ConfigManager.getPrefix() + ChatColor.RED + "Second line must contain the name of the warp");
                }

            }else{
                e.setCancelled(true);
                p.sendMessage(ConfigManager.getPrefix() + ChatColor.RED + "You don't have the permission required (" +  ChatColor.GREEN + "warpsigns.use)");
            }

        }

    }
    public void CreateSignLocation(Player p, int sign, String wName, String world, double signX, double signY, double signZ, double playerX, double playerY, double playerZ, float playerYaw){
        p.sendMessage(ConfigManager.getPrefix() + "Sign location saved to: " + ChatColor.GREEN + signX + ChatColor.RESET + ", " + ChatColor.GREEN + signY + ChatColor.RESET + ", " + ChatColor.GREEN + signZ + ChatColor.RESET + " in world " + ChatColor.GREEN + world);
        WarpLocations.get().set("warps." + p.getUniqueId() + "." + wName + ".world" + sign, world);
        WarpLocations.get().set("warps." + p.getUniqueId() + "." + wName + ".signX" + sign, signX);
        WarpLocations.get().set("warps." + p.getUniqueId() + "." + wName + ".signY" + sign, signY);
        WarpLocations.get().set("warps." + p.getUniqueId() + "." + wName + ".signZ" + sign, signZ);

        WarpLocations.get().set("warps." + p.getUniqueId() + "." + wName + ".playerX" + sign, playerX);
        WarpLocations.get().set("warps." + p.getUniqueId() + "." + wName + ".playerY" + sign, playerY);
        WarpLocations.get().set("warps." + p.getUniqueId() + "." + wName + ".playerZ" + sign, playerZ);
        WarpLocations.get().set("warps." + p.getUniqueId() + "." + wName + ".playerYaw" + sign, playerYaw);

        WarpLocations.save();
    }
}

