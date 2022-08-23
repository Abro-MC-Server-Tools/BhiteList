package ru.bronuh.bhitelist;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class EventsListener implements Listener {
	PluginContext context;
	WhitelistController controller;

	public EventsListener(PluginContext context, WhitelistController controller){
		this.context = context;
		this.controller = controller;
	}

	@EventHandler(ignoreCancelled = true ,priority = EventPriority.HIGHEST)
	public void onPlayerLogin(PlayerJoinEvent event) {
		context.getLog().info(event.getPlayer().getName()+" зашел на сервер.");
		Player player = event.getPlayer();
		String name = player.getName();
		if(!controller.isWhitelisted(name)){
			player.kick(Component.text("Вас нет в вайтлисте",context.getWarnColor()));
		}
	}
}
