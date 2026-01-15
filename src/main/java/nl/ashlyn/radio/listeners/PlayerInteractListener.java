package nl.ashlyn.radio.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *  Created by Ashlyn on 24/11/2025
 */
public class PlayerInteractListener implements Listener {

    private final JavaPlugin plugin;

    public PlayerInteractListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static String radioTower = ChatColor.GOLD + "[Radio Toren]";

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action.equals(Action.LEFT_CLICK_BLOCK) && event.getClickedBlock() != null) {

            Block signBlock = event.getClickedBlock();
            Material type = signBlock.getType();

            if (type == Material.BIRCH_SIGN || type == Material.BIRCH_WALL_SIGN) {

                Sign s = (Sign) signBlock.getState();

                if (s.getLine(0).equals(radioTower)) {
                    // Block powerBlock = signBlock.getRelative(BlockFace.DOWN, 2);
                    String towerName = ChatColor.stripColor(s.getLine(1)).toLowerCase().trim();

                    boolean online = plugin.getConfig().getBoolean("tower-" + towerName, true);
                    online = !online;
                    plugin.getConfig().set("tower-" + towerName, online);
                    plugin.saveConfig();

                    if (online) {
                        // powerBlock.setType(Material.AIR);
                        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.0f, 1.0f);
                        s.setLine(3, ChatColor.GREEN + "Online");
                        player.sendMessage(ChatColor.GREEN + "Radio toren " + towerName + " is nu online.");
                    } else {
                        // powerBlock.setType(Material.REDSTONE_BLOCK);
                        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.0f, 1.0f);
                        s.setLine(3, ChatColor.RED + "Offline");
                        player.sendMessage(ChatColor.RED + "Radio toren " + towerName + " is nu offline.");
                    }
                    s.update(true);

                    boolean t1 = plugin.getConfig().getBoolean("tower-noord", true);
                    boolean t2 = plugin.getConfig().getBoolean("tower-zuid", true);

                    if (!t1 && !t2) {
                        plugin.getServer().broadcastMessage(ChatColor.RED + "Allebei de radio torens zijn offline! Je kunt nu de radio niet meer gebruiken!");
                        for (Player p : plugin.getServer().getOnlinePlayers()) {
                            // Shadow color not in sendTitle
                            plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"浮\uDAC0\uDC00\uDAC0\uDC00\uDAC0\uDC00\uDAFF\uDFFF漈\",\"shadow_color\":false}");
                            p.playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.0f, 1.0f);;
                        }
                    }
                }
            }
        }
    }
}
