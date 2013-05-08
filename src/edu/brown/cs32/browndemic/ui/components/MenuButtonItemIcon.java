package edu.brown.cs32.browndemic.ui.components;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.plaf.UIResource;

public class MenuButtonItemIcon implements Icon, UIResource {
	
	private Image unselected, selected;
	
	public MenuButtonItemIcon(Image unselected, Image selected) {
		this.unselected = unselected;
		this.selected = selected;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		ButtonModel model = ((JMenuItem)c).getModel();
		
		if (model.isSelected()) {
			g.drawImage(selected, x, y, null);
		} else {
			g.drawImage(unselected, x, y, null);
		}
		
	}

	@Override
	public int getIconWidth() {
		return unselected.getWidth(null);
	}

	@Override
	public int getIconHeight() {
		return unselected.getHeight(null);
	}

}
