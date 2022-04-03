package me.mvez73.warpsigns.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WarpSignsTabCompletion implements TabCompleter {

    List<String> arguments = new ArrayList<>();

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (arguments.isEmpty()) {
            arguments.add("reload"); arguments.add("listworlds");
        }

        List<String> result = new ArrayList<>();
        if (args.length == 1) {
            for (String a : arguments) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(a);
                }
            }
            return result;
        }
        return null;
    }
}
