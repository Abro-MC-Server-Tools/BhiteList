package ru.bronuh.bhitelist;

import net.kyori.adventure.text.format.TextColor;

import java.util.logging.Logger;

public class PluginContext {
	public final TextColor
			warnColor = TextColor.color(255,100,100),
			okColor = TextColor.color(100,255,100);


	private Logger log;
	private String pluginDir;
	private BhiteList pluginInstance;

	public PluginContext(Logger log, String pluginDir, BhiteList pluginInstance){
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

	public BhiteList getPluginInstance(){return pluginInstance;}
}
