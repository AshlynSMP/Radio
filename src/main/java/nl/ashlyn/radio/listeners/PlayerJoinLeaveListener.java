package nl.ashlyn.radio.listeners;

import net.md_5.bungee.api.ChatColor;
import nl.ashlyn.core.api.CoreConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Ashlyn on 05/12/2025
 */
public class PlayerJoinLeaveListener implements Listener {

    private final JavaPlugin plugin;

    public PlayerJoinLeaveListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        boolean t1 = plugin.getConfig().getBoolean("tower-noord", true);
        boolean t2 = plugin.getConfig().getBoolean("tower-zuid", true);

        if (!t1 && !t2 && CoreConfig.isLoreEnabled()) {
            player.sendMessage(ChatColor.RED + "Allebei de radio torens zijn offline! Je kunt de radio nu niet gebruiken!");
        }
    }
}
