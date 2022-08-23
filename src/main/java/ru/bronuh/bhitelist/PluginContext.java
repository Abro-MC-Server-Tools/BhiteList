package ru.bronuh.bhitelist;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PluginContext {
	public final TextColor
			warnColor = TextColor.color(255,100,100),
			okColor = TextColor.color(100,255,100);


	private Logger log;
	private String pluginDir;
	private JavaPlugin pluginInstance;

	public PluginContext(Logger log, String pluginDir, JavaPlugin pluginInstance){
		this.log = log;
		this.pluginDir = pluginDir;
		this.pluginInstance = pluginInstance;
	}

	public String getPluginDir() {
		return pluginDir;
	}

	public Logger getLog() {
		return log;
	}

	public JavaPlugin getPluginInstance(){return pluginInstance;}
}
