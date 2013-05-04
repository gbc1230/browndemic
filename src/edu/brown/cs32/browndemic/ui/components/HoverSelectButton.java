package edu.brown.cs32.browndemic.ui.components;

import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class HoverSelectButton extends SelectButton {
	
	private ImageIcon _hover;

	public HoverSelectButton(Image normal, Image selected, Image hover) {
		super(normal, selected);
		_hover = new ImageIcon(hover);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (!_isSelected) {
			setIcon(_hover);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if (!_isSelected) {
			setIcon(_normal);
		}
	}
}
