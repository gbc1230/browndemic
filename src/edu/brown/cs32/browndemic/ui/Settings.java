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
	public static final String NAME = "name";
	public static final String PORT = "port";
	
	private static final int DEFAULT_PORT = 6000;

	private static Map<String, String> _settings = new HashMap<>();
	private static Map<String, Boolean> _booleans = new HashMap<>();
	private static Map<String, Integer> _ints = new HashMap<>();
	
	public static void init() throws IOException {
		if (new File(PATH).exists())
			readSettings();
		defaultSettings();
	}
	
	private static void defaultSettings() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(PATH), true));

		if (!_booleans.containsKey(CACHING)) {
			bw.write(String.format("%s=%s", CACHING, "true"));
			bw.newLine();
		}
		if (!_settings.containsKey(NAME)) {
			bw.write(String.format("%s=%s", NAME, "Player"));
			bw.newLine();
		}
		if (!_ints.containsKey(PORT)) {
			bw.write(String.format("%s=%s", PORT, DEFAULT_PORT));
			bw.newLine();
		}
		bw.close();
		readSettings();
	}
	
	private static void readSettings() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(PATH)));
		
		String line;
		while ((line = br.readLine()) != null) {
			String[] split = line.split("=");
			if (split.length != 2) continue;
			if (split[0].equals(CACHING) && (split[1].toLowerCase().equals("true") || split[1].toLowerCase().equals("false"))) {
				_booleans.put(split[0], Boolean.parseBoolean(split[1]));
			} else if (split[0].equals(PORT)) {
				int val;
				try {
					val = Integer.parseInt(split[1]);
				} catch (NumberFormatException e) {
					val = DEFAULT_PORT;
				}
				_ints.put(split[0], val);
			} else {
				_settings.put(split[0], split[1]);
			}
		}
		
		br.close();
	}
	
	private static void writeSettings() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(PATH)));
		for (Map.Entry<String, String> e : _settings.entrySet()) {
			bw.write(String.format("%s=%s", e.getKey(), e.getValue()));
			bw.newLine();
		}
		for (Map.Entry<String, Boolean> e : _booleans.entrySet()) {
			bw.write(String.format("%s=%b", e.getKey(), e.getValue()));
			bw.newLine();
		}
		for (Map.Entry<String, Integer> e : _ints.entrySet()) {
			bw.write(String.format("%s=%d", e.getKey(), e.getValue()));
			bw.newLine();
		}
		bw.close();
	}
	
	public static String get(String key) {
		return _settings.get(key);
	}
	
	public static boolean getBoolean(String key) {
		return _booleans.get(key);
	}
	
	public static int getInt(String key) {
		Integer result = _ints.get(key);
		if (result == null) result = 0;
		return result;
	}
	
	public static void set(String key, String value) {
		_settings.put(key, value);
		try {
			writeSettings();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void set(String key, boolean value) {
		_booleans.put(key, value);
		try {
			writeSettings();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void set(String key, int value) {
		_ints.put(key, value);
		try {
			writeSettings();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
