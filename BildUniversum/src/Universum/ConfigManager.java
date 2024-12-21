package Universum;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
	private static final String CONFIG_FILE = "config.properties";
	private static final String DB_KEY = "database.path";

	public static String ladeDatenbankPfad() {
		Properties properties = new Properties();
		try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
			properties.load(fis);
			return properties.getProperty(DB_KEY);
		} catch (IOException e) {
			return null; // Datei nicht vorhanden oder Fehler beim Laden
		}
	}

	public static void speichereDatenbankPfad(String datenbankPfad) {
		Properties properties = new Properties();
		properties.setProperty(DB_KEY, datenbankPfad);
		try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
			properties.store(fos, "Konfiguration des Programms");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
