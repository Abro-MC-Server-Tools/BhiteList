package ru.bronuh.bhitelist;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Хранит вайтлист и предоставляет методы для работы с ним
 */
public class WhitelistController {
	PluginContext context;
	private List<String> whitelist = new ArrayList<>();
	private String dataDir;
	private Logger log;

	public WhitelistController(PluginContext context){
		this.context = context;
		this.dataDir = new File(context.getPluginDir()).getPath();
		log = context.getLog();

		File dir = new File(dataDir);
		try{
			dir.mkdir();
		}catch (Exception e){
			log.warning("Не удалось создать директорию "+dataDir+": "+e.getMessage());
			throw e;
		}
		loadWhitelist();
	}


	/**
	 * Проверяет наличие имени в списке
	 * @param name имя игрока
	 * @return
	 */
	public boolean isWhitelisted(String name){
		String lowercaseName = name.toLowerCase();
		return whitelist.contains(lowercaseName);
	}


	/**
	 * Добавляет имя в список
	 * @param name имя игрока
	 */
	public void add(String name){
		String lowercaseName = name.toLowerCase();
		whitelist.add(lowercaseName);
		saveWhitelist();
	}


	/**
	 * Удаляет имя из списка
	 * @param name имя игрока
	 */
	public void remove(String name){
		String lowercaseName = name.toLowerCase();
		whitelist.remove(lowercaseName);
		saveWhitelist();
	}


	/**
	 * Сохраняет вайтлист на диск
	 */
	public void saveWhitelist(){
		Gson gson = new Gson();
		File file = new File(dataDir,"whitelist.json");

		try{
			String content = gson.toJson(whitelist);
			if(file.exists()){
				Files.writeString(file.toPath(), content, StandardOpenOption.TRUNCATE_EXISTING);
			}else {
				Files.writeString(file.toPath(), content, StandardOpenOption.CREATE);
			}
		}catch (Exception e){
			log.warning("Ошибка при записи в файл: "+e.getMessage());
			e.printStackTrace();
		}
	}


	/**
	 * Читает вайтлист из файла
	 */
	private void loadWhitelist(){
		Gson gson = new Gson();
		File file = new File(dataDir,"whitelist.json");
		Type whitelistType = new TypeToken<ArrayList<String>>(){}.getType();
		try {
			whitelist = gson.fromJson(Files.readString(file.toPath()),whitelistType);
		} catch (IOException e) {
			log.warning("Не удалось прочитать файл "+file.getName()+": "+e.getMessage());
		}
	}
}
