package ru.bronuh.bhitelist;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class BhiteList extends JavaPlugin {
	// TODO: Добавить вайтлист IP адресов

	private final Logger log = getLogger();
	private final String pluginDir = getDataFolder().getPath();

	private final BhiteList instance = this;
	private final PluginContext context = new PluginContext(log, pluginDir, instance);
	private final WhitelistController controller = new WhitelistController(context);
	private final CommandExecutor executor = new WhitelistCmd(context,controller);
	private final EventsListener listener = new EventsListener(context,controller);
	@Override
	public void onEnable() {
		// Plugin startup logic

		getServer().getPluginManager().registerEvents(listener,this);
		getServer().getPluginCommand("wl").setExecutor(executor);
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		controller.saveWhitelist();
	}
}
