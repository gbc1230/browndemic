package edu.brown.cs32.browndemic.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Resources {
	private static Map<String, BufferedImage> images_ = new HashMap<>();
	
	private Resources() {
		
	}
	
	public static BufferedImage getImage(String path) {
		return images_.get(path);
	}
	
	public static boolean loadImages(String... paths) {
		for (String path : paths) {
			if (!loadImage(path)) 
				return false;
		}
		return true;
	}
	
	public static boolean loadImage(String path) {
		if (images_.containsKey(path)) return true;
		try {
			images_.put(path, ImageIO.read(new File(path)));
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public static void clear() {
		images_.clear();
	}
}
