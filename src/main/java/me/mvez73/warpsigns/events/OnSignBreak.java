package me.mvez73.warpsigns.events;

import me.mvez73.warpsigns.WarpSigns;
import me.mvez73.warpsigns.files.ConfigManager;
import me.mvez73.warpsigns.files.WarpLocations;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Objects;

public class OnSignBreak implements Listener {
    private final WarpSigns plugin;

    public OnSignBreak(WarpSigns plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnSignbreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

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
                        double signX1 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".signX1");
                        double signY1 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".signY1");
                        double signZ1 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".signZ1");
                        double signX2 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".signX2");
                        double signY2 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".signY2");
                        double signZ2 = WarpLocations.get().getDouble("warps." + uuidKey + "." + wName + ".signZ2");
                        boolean containsUUID = uuidKey.contains(p.getUniqueId().toString());
                        if (locX == signX1 && locY == signY1 && locZ == signZ1){
                            if (containsUUID){
                                if (WarpLocations.get().contains("warps." + uuidKey + "." + warpName + "." + ".world2")){
                                    p.sendMessage(ConfigManager.getPrefix() + "Sign #1 deleted...");
                                    int signNumber = 1;
                                    DeleteSignLocation(p, uuidKey, signNumber, warpName);
                                }else{
                                    p.sendMessage(ConfigManager.getPrefix() + "Removing last sign... " + ChatColor.GREEN + warpName + ChatColor.RESET + " has now 0 sign left.");
                                    WarpLocations.get().set("warps." + uuidKey + "." + warpName, null);
                                    WarpLocations.save();
                                }
                            }else{
                                if (p.hasPermission("warpsigns.admin") || p.hasPermission("warpsigns.*")){
                                    if (WarpLocations.get().contains("warps." + uuidKey + "." + warpName + "." + ".world2")){
                                        p.sendMessage(ChatColor.RED + "[Admin]" + ChatColor.RESET + "Sign #1 deleted...");
                                        int signNumber = 1;
                                        DeleteSignLocation(p, uuidKey, signNumber, warpName);
                                    }else{
                                        p.sendMessage(ConfigManager.getPrefix() + ChatColor.RED + "[Admin]" + ChatColor.RESET + "Removing last sign... " + ChatColor.GREEN + warpName + ChatColor.RESET + " has now 0 sign left.");
                                        WarpLocations.get().set("warps." + uuidKey + "." + warpName, null);
                                        WarpLocations.save();
                                    }
                                }else{
                                    p.sendMessage(ConfigManager.getPrefix() + ChatColor.RED + "You can't delete a warpsign that is not yours.");
                                    e.setCancelled(true);
                                }
                            }
                        }else if (locX == signX2 && locY == signY2 && locZ == signZ2){
                            if (containsUUID){
                                if (WarpLocations.get().contains("warps." + uuidKey + "." + warpName + "." + ".world1")){
                                    p.sendMessage(ConfigManager.getPrefix() + ChatColor.GRAY + "So it's my sign2 and i can delete it.");
                                    int signNumber = 2;
                                    DeleteSignLocation(p, uuidKey, signNumber, warpName);
                                }else{
                                    p.sendMessage(ConfigManager.getPrefix() + "Removing last sign... " + ChatColor.GREEN + warpName + ChatColor.RESET + " has now 0 sign left.");
                                    WarpLocations.get().set("warps." + uuidKey + "." + warpName, null);
                                    WarpLocations.save();
                                }
                            }else{
                                if (p.hasPermission("warpsigns.admin") || p.hasPermission("warpsigns.*")){
                                    if (WarpLocations.get().contains("warps." + uuidKey + "." + warpName + "." + ".world1")){
                                        p.sendMessage(ChatColor.RED + "[Admin]" + ChatColor.RESET + "Sign #2 deleted...");
                                        int signNumber = 2;
                                        DeleteSignLocation(p, uuidKey, signNumber, warpName);
                                    }else{
                                        p.sendMessage(ConfigManager.getPrefix() + ChatColor.RED + "[Admin]" + ChatColor.RESET + "Removing last sign... " + ChatColor.GREEN + warpName + ChatColor.RESET + " has now 0 sign left.");
                                        WarpLocations.get().set("warps." + uuidKey + "." + warpName, null);
                                        WarpLocations.save();
                                    }
                                }else{
                                    p.sendMessage(ConfigManager.getPrefix() + ChatColor.RED + "You can't delete a warpsign that is not yours.");
                                    e.setCancelled(true);
                                }
                            }
                        }
                    }
                }

            } else {
                e.setCancelled(true);
                p.sendMessage(ConfigManager.getPrefix() + ChatColor.RED + "You don't have the permission to do that");
            }
        }
    }
    private void DeleteSignLocation(Player p, String uuID, int sign, String wName){
        p.sendMessage(ConfigManager.getPrefix() + "Sign" + ChatColor.GREEN + sign + ChatColor.RESET + " of warp " + ChatColor.GREEN + wName + ChatColor.GREEN + " removed. 1 sign left for this warp.");
        WarpLocations.get().set("warps." + uuID + "." + wName + ".world" + sign, null);
        WarpLocations.get().set("warps." + uuID + "." + wName + ".signX" + sign, null);
        WarpLocations.get().set("warps." + uuID + "." + wName + ".signY" + sign, null);
        WarpLocations.get().set("warps." + uuID + "." + wName + ".signZ" + sign, null);

        WarpLocations.get().set("warps." + uuID + "." + wName + ".playerX" + sign, null);
        WarpLocations.get().set("warps." + uuID + "." + wName + ".playerY" + sign, null);
        WarpLocations.get().set("warps." + uuID + "." + wName + ".playerZ" + sign, null);
        WarpLocations.get().set("warps." + uuID + "." + wName + ".playerYaw" + sign, null);

        WarpLocations.save();
    }



}
