package me.mvez73.warpsigns.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class WarpLocations {

    private static File file;
    private static FileConfiguration locationFile;

    public static void setup(){
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("WarpSigns")).getDataFolder(), "location.yml");

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        locationFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return locationFile;
    }

    public static void save(){
        try{
            locationFile.save(file);
        }catch (IOException e){
            System.out.println("Couldn't save file");
        }
    }

    public static void reload(){
        locationFile = YamlConfiguration.loadConfiguration(file);
    }

}
