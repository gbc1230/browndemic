package edu.brown.cs32.browndemic.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Settings {
	public static final String PATH = "settings.txt";
	
	public static final String CACHING = "caching";

	private static Map<String, String> _settings = new HashMap<>();
	private static Map<String, Boolean> _booleans = new HashMap<>();
	
	public static void init() throws IOException {
		if (new File(PATH).exists())
			readSettings();
		else
			defaultSettings();
	}
	
	private static void defaultSettings() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(PATH)));
		
		bw.write(String.format("%s=%s\n", CACHING, "false"));
		bw.close();
		readSettings();
	}
	
	private static void readSettings() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(PATH)));
		
		String line;
		while ((line = br.readLine()) != null) {
			String[] split = line.split("=");
			if (split.length != 2) continue;
			if (split[1].toLowerCase().equals("true") || split[1].toLowerCase().equals("false")) {
				_booleans.put(split[0], Boolean.parseBoolean(split[1]));
			} else {
				_settings.put(split[0], split[1]);
			}
		}
		
		br.close();
	}
	
	public static void writeSettings() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(PATH)));
		for (Map.Entry<String, String> e : _settings.entrySet())
			bw.write(String.format("%s=%s\n", e.getKey(), e.getValue()));
		for (Map.Entry<String, Boolean> e : _booleans.entrySet())
			bw.write(String.format("%s=%b\n", e.getKey(), e.getValue()));
		bw.close();
	}
	
	public static String get(String key) {
		return _settings.get(key);
	}
	
	public static boolean getBoolean(String key) {
		return _booleans.get(key);
	}
	
	public static void set(String key, String value) {
		_settings.put(key, value);
	}
	
	public static void set(String key, boolean value) {
		_booleans.put(key, value);
	}
}
