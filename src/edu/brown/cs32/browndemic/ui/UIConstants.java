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
		public static final String ENTER_DISEASE_NAME = "Disease Name: ";
		public static final String LOADING = "Loading";
	}
	
	public static class Images {
		public static final String IMAGE_PATH = RESOURCE_PATH + "images/";
		
		public static final String DEFAULT = IMAGE_PATH + "placeholder.jpg";
		public static final String SINGLE_PLAYER = IMAGE_PATH + "singleplayer.png";
		public static final String SINGLE_PLAYER_HOVER = IMAGE_PATH + "singleplayer_hover.png";
		public static final String MULTI_PLAYER = IMAGE_PATH + "multiplayer.png";
		public static final String MULTI_PLAYER_HOVER = IMAGE_PATH + "multiplayer_hover.png";
		public static final String CLOSE_BUTTON = IMAGE_PATH + "closebutton.png";
		public static final String CLOSE_BUTTON_HOVER = IMAGE_PATH + "closebutton_hover.png";
		public static final String MINIMIZE_BUTTON = IMAGE_PATH + "minimizebutton.png";
		public static final String MINIMIZE_BUTTON_HOVER = IMAGE_PATH + "minimizebutton_hover.png";
		public static final String TITLE = IMAGE_PATH + "title.png";
		public static final String BACK = IMAGE_PATH + "back.png";
		public static final String BACK_HOVER = IMAGE_PATH + "back_hover.png";
		public static final String BACKGROUND = IMAGE_PATH + "background.png";
		
		public static final String[] MENU_IMAGES = {
			DEFAULT,
			SINGLE_PLAYER,
			SINGLE_PLAYER_HOVER,
			MULTI_PLAYER,
			MULTI_PLAYER_HOVER,
			CLOSE_BUTTON,
			CLOSE_BUTTON_HOVER,
			MINIMIZE_BUTTON,
			MINIMIZE_BUTTON_HOVER,
			TITLE,
			BACK,
			BACK_HOVER,
			BACKGROUND,
		};
		
		public static final String[] GAME_IMAGES = {
		};
		
	}
	
	public static class Colors {
		public static final Color RED_TEXT = new Color(197, 14, 14);
		public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
		public static final Color MENU_BACKGROUND = Color.BLACK;
		public static final Color LIGHT_GRAY = Color.gray;
	}
	
	public static class Fonts {
		public static Font TITLE_BAR;
		public static Font BIG_TEXT;
		
		public static void init() throws FileNotFoundException, FontFormatException, IOException {
			TITLE_BAR = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(RESOURCE_PATH + "fonts/GhostWriter.ttf"))).deriveFont(22f);
			BIG_TEXT = TITLE_BAR.deriveFont(30f);
		}
	}
}
