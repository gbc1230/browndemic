package edu.brown.cs32.browndemic.ui;

import java.awt.Component;
import java.awt.Container;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Contains various static utilities for the UI.
 * @author Ben
 *
 */
public class Utils {
	
	private Utils() { }
	
	/**
	 * Gets the parent BrowndemicFrame of a Container.
	 * If there is no BrowndemicFrame as a parent then this
	 * method will cause a NullPointerException.
	 * 
	 * @param c The Container to get the parent of.
	 * @return The BrowndemicFrame that is the parent of c.
	 */
	public static BrowndemicFrame getParentFrame(Container c) {
		if (!(c instanceof BrowndemicFrame)) {
			return getParentFrame(c.getParent());
		}
		return (BrowndemicFrame) c;
	}
	
	public static void setDefaultLook(Component... components) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			for (Component c : components)
				SwingUtilities.updateComponentTreeUI(c);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
		}
	}
	
	public static void setOSLook(Component... components) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			for (Component c : components)
				SwingUtilities.updateComponentTreeUI(c);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
		}
	}
}
