package me.mvez73.warpsigns.files;

import me.mvez73.warpsigns.WarpSigns;
import org.bukkit.ChatColor;

import java.util.Objects;

public class ConfigManager {

    public static String getPrefix() {
        String configPrefix = WarpSigns.getPlugin(WarpSigns.class).getConfig().getString("prefix");
        configPrefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(configPrefix));

        return configPrefix;
    }

}
