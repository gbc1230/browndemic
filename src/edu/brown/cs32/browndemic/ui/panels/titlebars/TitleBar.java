package edu.brown.cs32.browndemic.ui.panels.titlebars;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import edu.brown.cs32.browndemic.ui.UIConstants.UI;

public abstract class TitleBar extends JPanel implements MouseListener {
	private static final long serialVersionUID = -4180938726228510101L;
	
	public TitleBar() {
		super();
		setPreferredSize(new Dimension(UI.WIDTH, UI.TITLE_HEIGHT));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
