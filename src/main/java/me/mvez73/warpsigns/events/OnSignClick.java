package me.mvez73.warpsigns.events;

import me.mvez73.warpsigns.WarpSigns;
import me.mvez73.warpsigns.files.ConfigManager;
import me.mvez73.warpsigns.files.WarpLocations;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class OnSignClick implements Listener {
    private final WarpSigns plugin;

    public OnSignClick(WarpSigns plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnClickSign(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        String configLine = plugin.getConfig().getString("signTitle");
        String coloredLine = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(configLine));

        if ((Objects.requireNonNull(b).getState() instanceof Sign)) {
            Sign sign = (Sign) b.getState();
            if (!Objects.requireNonNull(sign.getLine(0)).equalsIgnoreCase(coloredLine)) {
                return;
            }

            if (p.hasPermission("warpsigns.use") || p.hasPermission("warpsigns.admin") || p.hasPermission("warpsigns.*")) {

                Location l = sign.getLocation();

                double locX = l.getX();
                double locY = l.getY();
                double locZ = l.getZ();
                String warpName = sign.getLine(1);
                for (String uuidKey : Objects.requireNonNull(WarpLocations.get().getConfigurationSection("warps")).getKeys(false)) {
                    for (String wName : Objects.requireNonNull(WarpLocations.get().getConfigurationSection("warps." + uuidKey)).getKeys(false)) {

                        String world1 = WarpLocations.get().getString("warps." + uuidKey + "." + wName + ".world1");
                        String world2 = WarpLocations.get().getString("warps." + uuidKey + "." + wName + ".world2");
                        double signX1 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".signX1");
                        double signX2 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".signX2");
                        double signY1 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".signY1");
                        double signY2 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".signY2");
                        double signZ1 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".signZ1");
                        double signZ2 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".signZ2");
                        double playerX1 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".playerX1");
                        double playerX2 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".playerX2");
                        double playerY1 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".playerY1");
                        double playerY2 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".playerY2");
                        double playerZ1 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".playerZ1");
                        double playerZ2 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".playerZ2");
                        float playerYaw1 = (float) WarpLocations.get().getDouble("warps." + p.getUniqueId() + "." + wName + ".playerYaw1");
                        float playerYaw2 = (float) WarpLocations.get().getDouble("warps." + p.getUniqueId() + "." + wName + ".playerYaw2");
                        float playerPitch1 = 0;
                        float playerPitch2 = 0;


                        boolean containsUUID = uuidKey.contains(p.getUniqueId().toString());
                        if (locX == signX1 && locY == signY1 && locZ == signZ1){
                            if (containsUUID){
                                if (WarpLocations.get().contains("warps." + uuidKey + "." + warpName + "." + ".world2")){
                                    TeleportPlayer(p, world2, playerX2 , playerY2, playerZ2, playerYaw2, playerPitch2);
                                }else{
                                    p.sendMessage(ConfigManager.getPrefix() + ChatColor.RED + "Cannot teleport. This sign is not paired...");
                                }
                            }else{
                                if (p.hasPermission("warpsigns.admin") || p.hasPermission("warpsigns.*")){
                                    if (WarpLocations.get().contains("warps." + uuidKey + "." + warpName + "." + ".world2")){
                                        TeleportPlayer(p, world2, playerX2 , playerY2, playerZ2, playerYaw2, playerPitch2);
                                    }else{
                                        p.sendMessage(ConfigManager.getPrefix() + ChatColor.GOLD + "Cannot teleport. This sign is not paired...");
                                    }
                                }else{
                                    p.sendMessage(ConfigManager.getPrefix() + ChatColor.RED + "You can't tp with a sign that is not yours.");
                                }
                            }
                        }else if (locX == signX2 && locY == signY2 && locZ == signZ2){
                            if (containsUUID){
                                if (WarpLocations.get().contains("warps." + uuidKey + "." + warpName + "." + ".world1")){
                                    TeleportPlayer(p, world1, playerX1 , playerY1, playerZ1, playerYaw1, playerPitch1);
                                }else{
                                    p.sendMessage(ConfigManager.getPrefix() + ChatColor.RED + "Cannot teleport. This sign is not paired...");
                                }
                            }else{
                                if (p.hasPermission("warpsigns.admin") || p.hasPermission("warpsigns.*")){
                                    if (WarpLocations.get().contains("warps." + uuidKey + "." + warpName + "." + ".world1")){
                                        TeleportPlayer(p, world1, playerX1 , playerY1, playerZ1, playerYaw1, playerPitch1);
                                    }else{
                                        p.sendMessage(ConfigManager.getPrefix() + ChatColor.GOLD + "Cannot teleport. This sign is not paired...");
                                    }
                                }else{
                                    p.sendMessage(ConfigManager.getPrefix() + ChatColor.RED + "You can't tp with a sign that is not yours.");
                                }
                            }
                        }
                    }
                }
            } else {
                p.sendMessage(ConfigManager.getPrefix() + ChatColor.RED + "You don't have the permission to do that");
            }
        }
    }
    private void TeleportPlayer(Player p, String world, double playerX, double playerY, double playerZ, float playerYaw, float playerPitch){
        Location tp = new Location(Bukkit.getWorld(Objects.requireNonNull(world)), playerX, playerY, playerZ, playerYaw, playerPitch);
        if (Objects.equals(plugin.getConfig().getString("teleportmsg"), "enabled")) {
            p.sendMessage("Teleport to: " + ChatColor.GREEN + Math.round(playerX) + ChatColor.RESET + ", " + ChatColor.GREEN + Math.round(playerY) + ChatColor.RESET + ", " + ChatColor.GREEN + Math.round(playerZ) + ChatColor.RESET + " in world: " + ChatColor.GREEN + world);
        }
        p.teleport(tp);
    }
}

