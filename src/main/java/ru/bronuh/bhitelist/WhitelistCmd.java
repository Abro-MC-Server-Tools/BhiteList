package ru.bronuh.bhitelist;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WhitelistCmd  implements CommandExecutor {

	private PluginContext context;
	private WhitelistController controller;


	public WhitelistCmd(PluginContext context, WhitelistController controller){
		this.context = context;
		this.controller = controller;
	}


	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		CommandSender sender = commandSender;
		String senderName = sender.getName();
		int argsCount = args.length;
		String action = argsCount > 0 ? args[0] : "";
		String targetName = argsCount > 1 ? args[1] : "";
		context.getLog().info("Executed 'wl' with args '"+action+"' '"+targetName+"'");
		if(command.getName().equalsIgnoreCase("wl")){
			if(action.equalsIgnoreCase("add")){
				if(argsCount>1){
					if(!controller.isWhitelisted(targetName)){
						controller.add(targetName);
						respondOk(sender,"Имя "+targetName+" успешно добавлено в список");
					}else{
						respondError(sender,"Этот игрок уже есть в списке");
					}
					return true;
				}else{
					respondError(sender, "Должен быть указан игрок, добавляемый в список");
					return false;
				}
			}
			if(action.equals("remove")){
				if(argsCount>1){
					if(!controller.isWhitelisted(targetName)){
						controller.remove(targetName);
						respondOk(sender,"Имя "+targetName+" успешно удалено из списка");
					}else{
						respondError(sender,"Этого игрока и не было в списке");
					}
					return true;
				}else{
					respondError(sender, "Должен быть указан игрок, удаляемый из списка");
					return false;
				}
			}

			// TODO: Добавить включение, отключение вайтлиста
		}

		return false;
	}

	private void respondOk(CommandSender player, String message){
		player.sendMessage(Component.text(message,context.getOkColor()));
	}
	private void respondError(CommandSender player, String message){
		player.sendMessage(Component.text("Ошибка: "+message,context.getWarnColor()));
	}
}
