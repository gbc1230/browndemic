package edu.brown.cs32.browndemic.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class HoverLabel extends JLabel implements MouseListener {
	private static final long serialVersionUID = 7752465083785508082L;
	
	ImageIcon _normal, _hover;
	Color _normalColor, _hoverColor;
	
	public HoverLabel(BufferedImage normal, BufferedImage hover) {
		super();
		_normal = new ImageIcon(normal);
		_hover = new ImageIcon(hover);
		setPreferredSize(new Dimension(normal.getWidth(), normal.getHeight()));
		setIcon(_normal);
		addMouseListener(this);
	}
	
	public HoverLabel(String text, Font font, Color normal, Color hover) {
		super(text);
		_normalColor = normal;
		_hoverColor = hover;
		setFont(font);
		setForeground(normal);
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (_hover != null)
			setIcon(_hover);
		if (_hoverColor != null)
			setForeground(_hoverColor);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if (_normal != null)
			setIcon(_normal);
		if (_normalColor != null)
			setForeground(_normalColor);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
