package edu.brown.cs32.browndemic.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
		Strings.init();
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
	public static class Strings {
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
		public static final String MAIN_MENU_CAPS = "MAIN MENU";
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
		public static final String SETTINGS_MENU = "SETTINGS";
		public static final String SETTINGS_CACHING = "Caching Enabled";
		public static final String SETTINGS_FPS = "Show FPS";
		public static final String INFO_INFECTED = "Infected: ";
		public static final String INFO_HEALTHY = "Healthy: ";
		public static final String INFO_DEAD = "Dead: ";
		public static final String INFO_POPULATION = "Population: ";
		public static final String INFO_CURED = "Cure Progress: ";
		public static final String MULTIPLAYER_LOBBY = "Multiplayer Lobby";
		public static final String SETTINGS_MULTIPLAYER_NAME = "Multiplayer Name: ";
		public static final String POST_GAME_MENU = "Postgame Menu";
		public static final String VICTORY = "VICTORY";
		public static final String DEFEAT = "DEFEAT";
		public static final String PEOPLE_KILLED = "People Killed:";
		public static final String PERKS_BOUGHT = "Perks Bought:";
		public static final String PERKS_SOLD = "Perks Sold:";
		public static final String RANDOM_PERKS = "Mutations Gained:";
		public static final String POINTS_EARNED = "Points Gained:";
		public static final String POINTS_SPENT = "Points Spent:";
		public static final String ABOUT = "About";
		public static final String ABOUT_MENU = "ABOUT";
		public static final String ABOUT_PATH = "About.txt";
		
		public static String ABOUT_HTML = "";
		
		public static void init() {
			try (BufferedReader br = new BufferedReader(new FileReader(new File(ABOUT_PATH)));) {
				String line;
				while ((line = br.readLine()) != null)
					ABOUT_HTML = ABOUT_HTML + line;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Contains paths to images for the UI.
	 * 
	 * @author Ben
	 *
	 */
	public static class Images {
		public static final String IMAGE_PATH = RESOURCE_PATH + "images/";
		public static final String PERKS_PATH = IMAGE_PATH + "perks/";
		
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
		public static final String DISEASE3 = IMAGE_PATH + "parasite.png";
		public static final String DISEASE3_SELECTED = IMAGE_PATH + "parasite_selected.png";
		public static final String STARTGAME = IMAGE_PATH + "startgame.png";
		public static final String STARTGAME_HOVER = IMAGE_PATH + "startgame_hover.png";
		public static final String SETTINGS = IMAGE_PATH + "settings.png";
		public static final String SETTINGS_HOVER = IMAGE_PATH + "settings_hover.png";
		
		public static final String MAP = IMAGE_PATH + "earth_large.png";
		public static final String REGIONS = IMAGE_PATH + "earth_large_regions.png";
		public static final String AIRPLANE = IMAGE_PATH + "airplane.png";
		public static final String AIRPLANE_INFECTED = IMAGE_PATH + "airplane_infected.png";
		public static final String AIRPORT_OPEN = IMAGE_PATH + "airport_open.png";
		public static final String AIRPORT_CLOSED = IMAGE_PATH + "airport_closed.png";
		public static final String AIRPORT_OPEN_BIG = IMAGE_PATH + "airport_open_big.png";
		public static final String AIRPORT_CLOSED_BIG = IMAGE_PATH + "airport_closed_big.png";
		public static final String SEAPORT_OPEN = IMAGE_PATH + "seaport_open.png";
		public static final String SEAPORT_CLOSED = IMAGE_PATH + "seaport_closed.png";
		public static final String SEAPORT_OPEN_BIG = IMAGE_PATH + "seaport_open_big.png";
		public static final String SEAPORT_CLOSED_BIG = IMAGE_PATH + "seaport_closed_big.png";
		public static final String DISEASE = IMAGE_PATH + "disease_placeholder.png";
		public static final String DISEASE_SELECTED = IMAGE_PATH + "disease_placeholder_selected.png";
		public static final String PLAY1 = IMAGE_PATH + "play.png";
		public static final String PLAY1_HOVER = IMAGE_PATH + "play_hover.png";
		public static final String PLAY2 = IMAGE_PATH + "play2.png";
		public static final String PLAY2_HOVER = IMAGE_PATH + "play2_hover.png";
		public static final String PLAY3 = IMAGE_PATH + "play3.png";
		public static final String PLAY3_HOVER = IMAGE_PATH + "play3_hover.png";
		public static final String PAUSE = IMAGE_PATH + "pause.png";
		public static final String PAUSE_HOVER = IMAGE_PATH + "pause_hover.png";
		public static final String CHECKED = IMAGE_PATH + "checked.png";
		public static final String UNCHECKED = IMAGE_PATH + "unchecked.png";
		public static final String SELECTED = IMAGE_PATH + "selected.png";
		public static final String UNSELECTED = IMAGE_PATH + "unselected.png";
		
		public static final String PERK_LUNGS = PERKS_PATH + "lung.png";
		public static final String PERK_COLD1 = PERKS_PATH + "cold1.png";
		public static final String PERK_COLD2 = PERKS_PATH + "cold2.png";
		public static final String PERK_COLD3 = PERKS_PATH + "cold3.png";
		public static final String PERK_CRACKED_HEAD = PERKS_PATH + "cracked_head.png";
		public static final String PERK_DRY1 = PERKS_PATH + "dry1.png";
		public static final String PERK_DRY2 = PERKS_PATH + "dry2.png";
		public static final String PERK_DRY3 = PERKS_PATH + "dry3.png";
		public static final String PERK_HEART = PERKS_PATH + "heart.png";
		public static final String PERK_HEAT1 = PERKS_PATH + "heat1.png";
		public static final String PERK_HEAT2 = PERKS_PATH + "heat2.png";
		public static final String PERK_HEAT3 = PERKS_PATH + "heat3.png";
		public static final String PERK_INTESTINES = PERKS_PATH + "intestines.png";
		public static final String PERK_MED1 = PERKS_PATH + "med1.png";
		public static final String PERK_MED2 = PERKS_PATH + "med2.png";
		public static final String PERK_MED3 = PERKS_PATH + "med3.png";
		public static final String PERK_SKELETON = PERKS_PATH + "skeleton.png";
		public static final String PERK_SKULL = PERKS_PATH + "skull.png";
		public static final String PERK_STOMACH = PERKS_PATH + "stomach.png";
		public static final String PERK_WET1 = PERKS_PATH + "wet1.png";
		public static final String PERK_WET2 = PERKS_PATH + "wet2.png";
		public static final String PERK_WET3 = PERKS_PATH + "wet3.png";
		public static final String PERK_WATER1 = PERKS_PATH + "water1.png";
		public static final String PERK_WATER2 = PERKS_PATH + "water2.png";
		public static final String PERK_WATER3 = PERKS_PATH + "water3.png";
		public static final String PERK_AIR1 = PERKS_PATH + "air1.png";
		public static final String PERK_AIR2 = PERKS_PATH + "air2.png";
		public static final String PERK_AIR3 = PERKS_PATH + "air3.png";
		public static final String PERK_AVIAN1 = PERKS_PATH + "avian1.png";
		public static final String PERK_AVIAN2 = PERKS_PATH + "avian2.png";
		public static final String PERK_AVIAN3 = PERKS_PATH + "avian3.png";
		public static final String PERK_BODY = PERKS_PATH + "body.png";
		public static final String PERK_SIMPLEBODY = PERKS_PATH + "simplebody.png";
		public static final String PERK_INSECT1 = PERKS_PATH + "insect1.png";
		public static final String PERK_INSECT2 = PERKS_PATH + "insect2.png";
		public static final String PERK_INSECT3 = PERKS_PATH + "insect3.png";
		
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
			SETTINGS,
			SETTINGS_HOVER,
		};
		
		public static final String[] GAME_IMAGES = {
			MAP,
			REGIONS,
			AIRPLANE,
			AIRPLANE_INFECTED,
			AIRPORT_OPEN,
			AIRPORT_CLOSED,
			AIRPORT_OPEN_BIG,
			AIRPORT_CLOSED_BIG,
			SEAPORT_OPEN,
			SEAPORT_CLOSED,
			SEAPORT_OPEN_BIG,
			SEAPORT_CLOSED_BIG,
			DISEASE,
			DISEASE_SELECTED,
			PLAY1,
			PLAY1_HOVER,
			PLAY2,
			PLAY2_HOVER,
			PLAY3,
			PLAY3_HOVER,
			PAUSE,
			PAUSE_HOVER,
			CHECKED,
			UNCHECKED,
			SELECTED,
			UNSELECTED,
			PERK_LUNGS,
			PERK_COLD1,
			PERK_COLD2,
			PERK_COLD3,
			PERK_CRACKED_HEAD,
			PERK_DRY1,
			PERK_DRY2,
			PERK_DRY3,
			PERK_HEART,
			PERK_HEAT1,
			PERK_HEAT2,
			PERK_HEAT3,
			PERK_INTESTINES,
			PERK_MED1,
			PERK_MED2,
			PERK_MED3,
			PERK_SKELETON,
			PERK_SKULL,
			PERK_STOMACH,
			PERK_WET1,
			PERK_WET2,
			PERK_WET3,
			PERK_WATER1,
			PERK_WATER2,
			PERK_WATER3,
			PERK_AIR1,
			PERK_AIR2,
			PERK_AIR3,
			PERK_AVIAN1,
			PERK_AVIAN2,
			PERK_AVIAN3,
			PERK_BODY,
			PERK_SIMPLEBODY,
			PERK_INSECT1,
			PERK_INSECT2,
			PERK_INSECT3,
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
		public static Font HUGE_TEXT;
		public static Font NUMBER_TEXT;
		
		public static void init() throws FileNotFoundException, FontFormatException, IOException {
			TITLE_BAR = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(RESOURCE_PATH + "fonts/GhostWriter.ttf"))).deriveFont(22f);
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(TITLE_BAR);
			BIG_TEXT = TITLE_BAR.deriveFont(38f);
			NORMAL_TEXT = TITLE_BAR.deriveFont(16f);
			BUTTON_TEXT = TITLE_BAR.deriveFont(46f);
			HUGE_TEXT = TITLE_BAR.deriveFont(68f);
			
			NUMBER_TEXT = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(RESOURCE_PATH + "fonts/kree.ttf"))).deriveFont(26f);
		}
	}
}
