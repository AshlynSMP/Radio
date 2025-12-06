package nl.ashlyn.radio.commands;

import net.md_5.bungee.api.ChatColor;
import nl.ashlyn.core.api.CoreConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ashlyn on 05/12/2025
 */
public class BroadcastCommand implements CommandExecutor {

    private Set<String> leaders;

    public BroadcastCommand(JavaPlugin plugin) {
        FileConfiguration config = plugin.getConfig();
        leaders = new HashSet<>(config.getStringList("leaders"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {

            if (!CoreConfig.isLeader(player.getName()) && !player.isOp()) {
                player.sendMessage(ChatColor.RED + "Deze command kan alleen gebruikt worden door de leiders.");
                return true;
            }
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /bc <message>");
            return false;
        }

        String message = String.join(" ", args);

        Bukkit.broadcastMessage(ChatColor.GOLD + "[Radio] " + ChatColor.GOLD + message);
        return true;
    }
}