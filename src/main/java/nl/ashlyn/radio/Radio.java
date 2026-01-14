package nl.ashlyn.radio;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import nl.ashlyn.core.api.CoreAPI;
import nl.ashlyn.core.utils.Logger;
import nl.ashlyn.radio.commands.BroadcastCommand;
import nl.ashlyn.radio.listeners.ChatListener;
import nl.ashlyn.radio.listeners.PlayerInteractListener;
import nl.ashlyn.radio.listeners.PlayerJoinLeaveListener;
import nl.ashlyn.radio.listeners.SignChangeListener;
import nl.ashlyn.radio.voicechat.VoiceChatPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Radio extends JavaPlugin {

    public static final String PLUGIN_ID = "Radio";


    @Override
    public void onEnable() {

        Logger.logRadio("Using Core v" + CoreAPI.getVersion());
        saveDefaultConfig();
        registerCommands();
        registerListeners();

        BukkitVoicechatService service = getServer().getServicesManager().load(BukkitVoicechatService.class);
        if (service != null) {
            VoiceChatPlugin voicechatPlugin = new VoiceChatPlugin();
            service.registerPlugin(voicechatPlugin);
            Logger.logRadio("Successfully registered radio in voicechat");
        }

        Logger.logRadio("+=========================================+");
        Logger.logRadio("Radio Initiated! (Build: v" + getDescription().getVersion() + ")");
        Logger.logRadio("+=========================================+");
    }

    @Override
    public void onDisable() {
        Logger.logRadio("+=========================================+");
        Logger.logRadio("Radio Disabled! (Build: v" + getDescription().getVersion() + ")");
        Logger.logRadio("+=========================================+");
    }

    private void registerCommands()
    {
        registerCommand("broadcast", new BroadcastCommand(this));
    }

    private void registerListeners() {
        registerListener(new ChatListener(this), this);
        registerListener(new PlayerInteractListener(this), this);
        registerListener(new PlayerJoinLeaveListener(this), this);
        registerListener(new SignChangeListener(this), this);
    }

    private void registerCommand(String name, CommandExecutor executor) {
        this.getCommand(name).setExecutor(executor);
        Logger.logRadio("Registered command: " + ChatColor.GREEN + name);
    }

    private void registerListener(Listener listener, Plugin plugin) {
        getServer().getPluginManager().registerEvents(listener, plugin);
        Logger.logRadio("Registered listener: " + ChatColor.GREEN + listener.getClass().getSimpleName());
    }
}
