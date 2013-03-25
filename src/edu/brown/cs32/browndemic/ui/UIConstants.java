package edu.brown.cs32.browndemic.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UIConstants {
	public static final String RESOURCE_PATH = "resources/";
	
	public static void init() throws FileNotFoundException, FontFormatException, IOException {
		Fonts.init();
	}
	
	public class UI {
		public static final int WIDTH = 800;
		public static final int HEIGHT = 600;
		public static final int TITLE_HEIGHT = 25;
		public static final int CONTENT_HEIGHT = HEIGHT - TITLE_HEIGHT;
	}
	
	public class Strings {
		public static final String TITLE = "Browndemic";
		public static final String SINGLE_PLAYER = "Single Player";
		public static final String MULTI_PLAYER = "Multiplayer";
		public static final String EXIT = "Exit";
		public static final String MINIMIZE = "Minimize";
	}
	
	public static class Images {
		public static final String IMAGE_PATH = RESOURCE_PATH + "images/";
		
		public static final String DEFAULT = IMAGE_PATH + "placeholder.jpg";
		public static final String SINGLE_PLAYER = IMAGE_PATH + "singleplayer.png";
		public static final String MULTI_PLAYER = IMAGE_PATH + "multiplayer.png";
		public static final String CLOSE_BUTTON = IMAGE_PATH + "closebutton.png";
		public static final String MINIMIZE_BUTTON = IMAGE_PATH + "minimizebutton.png";
		public static final String TITLE = IMAGE_PATH + "title.png";
		
		public static final String[] MENU_IMAGES = {
			DEFAULT,
			SINGLE_PLAYER,
			MULTI_PLAYER,
			CLOSE_BUTTON,
			MINIMIZE_BUTTON,
			TITLE,
		};
		
		public static final String[] GAME_IMAGES = {
		};
		
	}
	
	public static class Colors {
		public static final Color RED_TEXT = new Color(197, 14, 14);
		public static final Color MENU_BACKGROUND = Color.BLACK;
	}
	
	public static class Fonts {
		public static Font TITLE_BAR;
		
		public static void init() throws FileNotFoundException, FontFormatException, IOException {
			TITLE_BAR = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(RESOURCE_PATH + "fonts/GhostWriter.ttf"))).deriveFont(22f);
		}
	}
}
