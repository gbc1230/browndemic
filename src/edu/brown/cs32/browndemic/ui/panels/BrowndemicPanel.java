package edu.brown.cs32.browndemic.ui.panels;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public abstract class BrowndemicPanel extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = -8793848342821599004L;

	public BrowndemicPanel() {
		super();
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
		if (e.getButton() != MouseEvent.BUTTON1) return;
		if (!(e.getSource() instanceof Container)) return;
		if (!((Container)e.getSource()).contains(e.getPoint())) return;
		mouseReleasedInside(e);
	}
	
	public void mouseReleasedInside(MouseEvent e) {
	}
}
