package edu.brown.cs32.browndemic.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * Contains Resources for the UI that can be accessed by their path.
 * All methods and data are static, the Class cannot be instantiated.
 * @author Ben
 *
 */
public class Resources {
	private static Map<String, BufferedImage> _images = new HashMap<>();
	
	private Resources() {
		
	}
	
	/**
	 * Gets an already loaded image by its path.
	 * @param path The path of the image.
	 * @return The image as a BufferedImage, or null if the image has not been loaded.
	 */
	public static BufferedImage getImage(String path) {
		return _images.get(path);
	}
	
	/**
	 * Loads the images specified one by one.  
	 * @param paths The paths of the images to load.
	 * @return true if loading was successful, false otherwise.
	 */
	public static boolean loadImages(String... paths) {
		boolean out = true;
		for (String path : paths) {
			if (!loadImage(path)) 
				out = false;
		}
		return out;
	}
	
	/**
	 * Loads the specified image.
	 * @param path The path of the image.
	 * @return true if loading was successful, false otherwise.
	 */
	public static boolean loadImage(String path) {
		if (_images.containsKey(path)) return true;
		try {
			_images.put(path, ImageIO.read(new File(path)));
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Clears all loaded images.
	 */
	public static void clear() {
		_images.clear();
	}
}
