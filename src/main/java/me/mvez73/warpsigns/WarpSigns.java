package me.mvez73.warpsigns;

import me.mvez73.warpsigns.commands.WarpsignsCommands;
import me.mvez73.warpsigns.events.OnSignBreak;
import me.mvez73.warpsigns.events.OnSignChange;
import me.mvez73.warpsigns.events.OnSignClick;
import me.mvez73.warpsigns.files.WarpLocations;
import me.mvez73.warpsigns.utils.WarpSignsTabCompletion;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class WarpSigns extends JavaPlugin {

    public static WarpSigns plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new OnSignChange(this), this);
        getServer().getPluginManager().registerEvents(new OnSignClick(this), this);
        getServer().getPluginManager().registerEvents(new OnSignBreak(this), this);


        getConfig().options().copyDefaults();
        saveDefaultConfig();

        WarpLocations.setup();
        WarpLocations.get().options().copyDefaults(true);
        WarpLocations.save();

        System.out.println("[WarpSigns] loaded");
        getLogger().log(Level.INFO, "[WarpSigns] loaded");
        Objects.requireNonNull(getCommand("warpsigns")).setExecutor(new WarpsignsCommands(this));
        Objects.requireNonNull(getCommand("warpsigns")).setTabCompleter(new WarpSignsTabCompletion());
        MetricsLite metrics = new MetricsLite(this, 8610);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
