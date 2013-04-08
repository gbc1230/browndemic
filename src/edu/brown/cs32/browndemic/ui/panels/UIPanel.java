package edu.brown.cs32.browndemic.ui.panels;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.panels.menus.MultiplayerMenu;
import edu.brown.cs32.browndemic.ui.panels.menus.SinglePlayer;

@SuppressWarnings("serial")
public abstract class UIPanel extends JPanel implements MouseListener {
	public UIPanel() {
		super();
	}
	
	public abstract void setupForDisplay();
	
	@Override
	public abstract String toString();

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
		if (e.getButton() != MouseEvent.BUTTON1) return;
		if (!(e.getSource() instanceof Container)) return;
		if (!((Container)e.getSource()).contains(e.getPoint())) return;
		mouseReleasedInside(e);
	}
	
	public void mouseReleasedInside(MouseEvent e) {
	}
}
