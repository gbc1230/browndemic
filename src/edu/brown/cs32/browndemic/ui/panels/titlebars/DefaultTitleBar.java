package edu.brown.cs32.browndemic.ui.panels.titlebars;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.components.HoverLabel;
import edu.brown.cs32.browndemic.ui.panels.DragWindow;

public class DefaultTitleBar extends TitleBar implements MouseListener {
	private static final long serialVersionUID = -8406477447630443105L;
	
	JLabel close, minimize, title;
	
	public DefaultTitleBar() {
		super();
		makeUI();
	}
	
	protected void makeUI() {
		setBackground(Colors.MENU_BACKGROUND);
		setPreferredSize(new Dimension(UI.WIDTH, UI.TITLE_HEIGHT));
		
		add(Box.createHorizontalGlue());
		
		title = new JLabel(Strings.TITLE);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.revalidate();
		title.setForeground(Colors.RED_TEXT);
		title.setFont(Fonts.TITLE_BAR);
		new DragWindow(title);
		add(title);
		
		add(Box.createRigidArea(new Dimension(300,0)));
		
		minimize = new HoverLabel(Resources.getImage(Images.MINIMIZE_BUTTON), Resources.getImage(Images.MINIMIZE_BUTTON_HOVER), 
				UI.TITLE_HEIGHT, UI.TITLE_HEIGHT);
		minimize.addMouseListener(this);
		minimize.setToolTipText(Strings.MINIMIZE);
		add(minimize);
		
		close = new HoverLabel(Resources.getImage(Images.CLOSE_BUTTON), Resources.getImage(Images.CLOSE_BUTTON_HOVER),
				UI.TITLE_HEIGHT, UI.TITLE_HEIGHT);
		close.addMouseListener(this);
		close.setToolTipText(Strings.EXIT);
		add(close);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1) return;
		if (!(e.getSource() instanceof Container)) return;
		if (!((Container)e.getSource()).contains(e.getPoint())) return;
		if (e.getSource() == close) {
			System.exit(0);
		} else if (e.getSource() == minimize) {
			Utils.getParentFrame(this).setState(JFrame.ICONIFIED);
		}
	}
}
