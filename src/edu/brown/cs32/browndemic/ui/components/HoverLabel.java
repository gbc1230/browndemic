package edu.brown.cs32.browndemic.ui.components;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class HoverLabel extends JLabel implements MouseListener {
	private static final long serialVersionUID = 7752465083785508082L;
	
	ImageIcon _normal, _hover;
	public HoverLabel(BufferedImage normal, BufferedImage hover) {
		_normal = new ImageIcon(normal);
		_hover = new ImageIcon(hover);
		setIcon(_normal);
		addMouseListener(this);
	}
	
	public HoverLabel(BufferedImage normal, BufferedImage hover, int width, int height) {
		_normal = new ImageIcon(normal);
		_hover = new ImageIcon(hover);
		setPreferredSize(new Dimension(width, height));
		setIcon(_normal);
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		setIcon(_hover);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		setIcon(_normal);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
