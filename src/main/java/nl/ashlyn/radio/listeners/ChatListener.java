package nl.ashlyn.radio.listeners;

import nl.ashlyn.core.api.CoreConfig;
import nl.ashlyn.core.utils.TextScrambler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * Created by Ashlyn on 05/12/2025
 */
public class ChatListener implements Listener {

    private final JavaPlugin plugin;

    public ChatListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        boolean t1 = plugin.getConfig().getBoolean("tower-noord", true);
        boolean t2 = plugin.getConfig().getBoolean("tower-zuid", true);

        Player player = event.getPlayer();
        String message = event.getMessage();
        String prefix = "";

        if (!t1 && !t2 && CoreConfig.isLoreEnabled()) {
            event.setCancelled(true);
            return;
        }

        if (!t1 && CoreConfig.isLoreEnabled() || !t2 && CoreConfig.isLoreEnabled()) {
            event.setMessage(TextScrambler.scrambleLetters(event.getMessage()));
        }

        if (CoreConfig.isLoreEnabled()) {
            event.setFormat(ChatColor.GOLD + "[Radio] " + player.getName() + " >>> " + ChatColor.RESET + message);
        } else {

            Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
            for (Team team : sb.getTeams()) {
                if (team.hasEntry(player.getName())) {
                    prefix = team.getPrefix();
                    break;
                }
            }

            event.setFormat(prefix + player.getName() + ChatColor.RESET + ": " + message);
        }

    }
}
