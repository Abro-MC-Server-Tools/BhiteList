package ru.bronuh.bhitelist;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WhitelistCmd  implements CommandExecutor {


	@FunctionalInterface
	private interface CommandAction{
		/**
		 * Метод, описывающий действие команды
		 * @param sender отправитель команды
		 * @param argsCount количество параметров
		 * @param actionName название выполняемого действия
		 * @param targetName полное имя цели
		 * @param args аргументы команды
		 * @return
		 */
		boolean execute(CommandSender sender, @Deprecated int argsCount, @Deprecated String actionName, String targetName, String[] args);
	}

	private PluginContext context;
	private WhitelistController controller;

	/**
	 * Хранилище действий команды. Ключи должны быть в нижнемм регистре.
	 */
	private final Map<String, CommandAction> actions = new ConcurrentHashMap<>();


	private CommandAction
		/**
		 * Добавляет имя в список разрешенных
		 */
		addAction = new CommandAction() {
			@Override
			public boolean execute(CommandSender sender, int argsCount, String actionName, String targetName, String[] args) {
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
		},



		/**
		 * Удаляет имя из списка разрешенных
		 */
		removeAction = new CommandAction() {
			@Override
			public boolean execute(CommandSender sender, int argsCount, String actionName, String targetName, String[] args) {
				if(argsCount>1){
					if(controller.isWhitelisted(targetName)){
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
		};



	public WhitelistCmd(PluginContext context, WhitelistController controller){
		this.context = context;
		this.controller = controller;

		actions.put("add",addAction);
		actions.put("remove",removeAction);
	}


	/**
	 * Метод обработчик команд. Если возвращается false, сервер отправит вызвавшему команду её описание из plugin.yml
	 * @param commandSender Source of the command
	 * @param command Command which was executed
	 * @param label Alias of the command which was used
	 * @param args Passed command arguments
	 * @return
	 */
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		CommandSender sender = commandSender;
		String senderName = sender.getName();
		int argsCount = args.length;
		String action = (argsCount > 0 ? args[0] : "").toLowerCase();
		String targetName = argsCount > 1 ? args[1] : "";


		if(command.getName().equalsIgnoreCase("wl")){
			CommandAction executableAction = actions.get(action);
			if(executableAction != null)
				return executableAction.execute(commandSender, argsCount, action, targetName, args);

			// TODO: Добавить включение, отключение вайтлиста
			// TODO: Добавить вывод списка в чат
		}

		return false;
	}

	/**
	 * Отправляет ответ зеленго цвета
	 * @param player поучатель ответа
	 * @param message текст сообщения
	 */
	private void respondOk(CommandSender player, String message){
		player.sendMessage(Component.text(message,context.okColor));
	}

	/**
	 * Отправляет сообщение об ошибке красного цвета
	 * @param player получатель ответа
	 * @param message текст сообщения
	 */
	private void respondError(CommandSender player, String message){
		player.sendMessage(Component.text("Ошибка: "+message,context.warnColor));
	}
}
