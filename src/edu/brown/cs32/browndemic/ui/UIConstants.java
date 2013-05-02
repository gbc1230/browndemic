package edu.brown.cs32.browndemic.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Contains various constants for the UI.  The init()
 * method should be called prior to using this class.
 * 
 * @author Ben
 *
 */
public class UIConstants {
	public static final String RESOURCE_PATH = "resources/";
	
	/**
	 * Initializes constants that require nontrivial initialization.
	 * @throws FileNotFoundException
	 * @throws FontFormatException
	 * @throws IOException
	 */
	public static void init() throws FileNotFoundException, FontFormatException, IOException {
		Fonts.init();
	}
	
	/**
	 * Contains UI constants (mostly dimensions).
	 * 
	 * @author Ben
	 *
	 */
	public class UI {
		public static final int WIDTH = 1000;
		public static final int HEIGHT = 700;
		public static final int TITLE_HEIGHT = 25;
		public static final int CONTENT_HEIGHT = HEIGHT - TITLE_HEIGHT;
	}
	
	/**
	 * Contains String constants for the UI.
	 * 
	 * @author Ben
	 *
	 */
	public class Strings {
		public static final String TITLE = "Browndemic";
		public static final String SINGLE_PLAYER = "SINGLE PLAYER";
		public static final String MULTI_PLAYER = "MULTIPLAYER";
		public static final String EXIT = "Exit";
		public static final String MINIMIZE = "Minimize";
		public static final String ENTER_DISEASE_NAME = "Disease Name: ";
		public static final String LOADING = "Loading";
		public static final String SELECT_DISEASE = "Select a Disease: ";
		public static final String GO_BACK = "Go back to: ";
		public static final String MAIN_MENU = "Main Menu";
		public static final String LOADING_MENU = "Loading Menu";
		public static final String MULTIPLAYER_MENU = "Multiplayer Menu";
		public static final String SINGLEPLAYER_MENU = "Singleplayer Menu";
		public static final String SINGLEPLAYER_GAME = "Singleplayer Game";
		public static final String MULTIPLAYER_JOIN_MENU = "Join a Multiplayer Game";
		public static final String MULTIPLAYER_CREATE_MENU = "Create a Multiplayer Game";
		public static final String JOIN_GAME = "JOIN GAME";
		public static final String CREATE_GAME = "CREATE GAME";
		public static final String START_GAME = "START GAME";
		public static final String LOAD_GAME = "LOAD GAME";
		public static final String ENTER_HOST = "Host: ";
		public static final String ENTER_HOST_TOOLTIP = "Enter host IP address or URL";
		public static final String ENTER_PORT = "Port: ";
		public static final String ENTER_PORT_TOOLTIP = "Enter port number of host";
		public static final String CREATE_PORT = ENTER_PORT;
		public static final String CREATE_PORT_TOOLTIP = "Enter port number to host on";
		public static final String CONNECT_TO_SERVER = "Connect to a server: ";
		public static final String HOST_SERVER = "Host a server: ";
		public static final String KICK_PLAYER = "KICK";
		public static final String CHAT_TOOLTIP = "Press enter to send chat message";
		public static final String SETTINGS = "Settings";
		public static final String SETTINGS_CACHING = "Caching Enabled";
		public static final String INFO_INFECTED = "Infected: ";
		public static final String INFO_DEAD = "Dead: ";
		public static final String INFO_POPULATION = "Population: ";
		public static final String MULTIPLAYER_LOBBY = "Multiplayer Lobby";
		public static final String SETTINGS_MULTIPLAYER_NAME = "Multiplayer Name: ";
	}
	
	/**
	 * Contains paths to images for the UI.
	 * 
	 * @author Ben
	 *
	 */
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
		public static final String BACK_DOUBLE = IMAGE_PATH + "back_double.png";
		public static final String BACK_DOUBLE_HOVER = IMAGE_PATH + "back_double_hover.png";
		public static final String BACK_HOVER = IMAGE_PATH + "back_hover.png";
		public static final String BACKGROUND = IMAGE_PATH + "background.png";
		public static final String DISEASE1 = IMAGE_PATH + "bacteria.png";
		public static final String DISEASE1_SELECTED = IMAGE_PATH + "bacteria_selected.png";
		public static final String DISEASE2 = IMAGE_PATH + "virus.png";
		public static final String DISEASE2_SELECTED = IMAGE_PATH + "virus_selected.png";
		public static final String DISEASE3 = IMAGE_PATH + "disease_placeholder.png";
		public static final String DISEASE3_SELECTED = IMAGE_PATH + "disease_placeholder_selected.png";
		public static final String STARTGAME = IMAGE_PATH + "startgame.png";
		public static final String STARTGAME_HOVER = IMAGE_PATH + "startgame_hover.png";
		public static final String PLAY1 = IMAGE_PATH + "play.png";
		public static final String PLAY1_HOVER = IMAGE_PATH + "play_hover.png";
		public static final String PLAY2 = IMAGE_PATH + "play2.png";
		public static final String PLAY2_HOVER = IMAGE_PATH + "play2_hover.png";
		public static final String PLAY3 = IMAGE_PATH + "play3.png";
		public static final String PLAY3_HOVER = IMAGE_PATH + "play3_hover.png";
		public static final String PAUSE = IMAGE_PATH + "pause.png";
		public static final String PAUSE_HOVER = IMAGE_PATH + "pause_hover.png";
		
		public static final String MAP = IMAGE_PATH + "earth_large.png";
		public static final String REGIONS = IMAGE_PATH + "earth_large_regions.png";
		public static final String AIRPLANE = IMAGE_PATH + "airplane.png";
		public static final String AIRPORT_OPEN = IMAGE_PATH + "airport_open.png";
		public static final String AIRPORT_CLOSED = IMAGE_PATH + "airport_closed.png";
		public static final String AIRPORT_OPEN_BIG = IMAGE_PATH + "airport_open_big.png";
		public static final String AIRPORT_CLOSED_BIG = IMAGE_PATH + "airport_closed_big.png";
		public static final String SEAPORT_OPEN = IMAGE_PATH + "seaport_open.png";
		public static final String SEAPORT_CLOSED = IMAGE_PATH + "seaport_closed.png";
		public static final String SEAPORT_OPEN_BIG = IMAGE_PATH + "seaport_open_big.png";
		public static final String SEAPORT_CLOSED_BIG = IMAGE_PATH + "seaport_closed_big.png";
		
		/*
		 * Images that should be loaded when the program starts.
		 */
		public static final String[] MENU_IMAGES = {
			DEFAULT,
			CLOSE_BUTTON,
			CLOSE_BUTTON_HOVER,
			MINIMIZE_BUTTON,
			MINIMIZE_BUTTON_HOVER,
			TITLE,
			BACK,
			BACK_HOVER,
			BACKGROUND,
			DISEASE1,
			DISEASE1_SELECTED,
			DISEASE2,
			DISEASE2_SELECTED,
			DISEASE3,
			DISEASE3_SELECTED,
			PLAY1,
			PLAY1_HOVER,
			PLAY2,
			PLAY2_HOVER,
			PLAY3,
			PLAY3_HOVER,
			PAUSE,
			PAUSE_HOVER,
		};
		
		public static final String[] GAME_IMAGES = {
			MAP,
			REGIONS,
			AIRPLANE,
			AIRPORT_OPEN,
			AIRPORT_CLOSED,
			AIRPORT_OPEN_BIG,
			AIRPORT_CLOSED_BIG,
			SEAPORT_OPEN,
			SEAPORT_CLOSED,
			SEAPORT_OPEN_BIG,
			SEAPORT_CLOSED_BIG,
		};
		
	}
	
	/**
	 * Contains Color constants for the UI.
	 * 
	 * @author Ben
	 *
	 */
	public static class Colors {
		public static final Color RED_TEXT = new Color(197, 14, 14);
		public static final Color HOVER_TEXT = Color.RED;
		public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
		public static final Color MENU_BACKGROUND = Color.BLACK;
		public static final Color LIGHT_GRAY = Color.gray;
	}
	
	/**
	 * Contains Font constants for the UI.  The construction
	 * of these Fonts requires that the constants not be final.
	 * These constants should never be modified.
	 * 
	 * @author Ben
	 *
	 */
	public static class Fonts {
		public static Font TITLE_BAR;
		public static Font BIG_TEXT;
		public static Font NORMAL_TEXT;
		public static Font BUTTON_TEXT;
		
		public static void init() throws FileNotFoundException, FontFormatException, IOException {
			TITLE_BAR = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(RESOURCE_PATH + "fonts/GhostWriter.ttf"))).deriveFont(22f);
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(TITLE_BAR);
			BIG_TEXT = TITLE_BAR.deriveFont(38f);
			NORMAL_TEXT = TITLE_BAR.deriveFont(16f);
			BUTTON_TEXT = TITLE_BAR.deriveFont(46f);
		}
	}
}
