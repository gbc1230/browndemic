package edu.brown.cs32.browndemic.ui.panels;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;

public class DefaultTitleBar extends TitleBar implements MouseListener {
	private static final long serialVersionUID = -8406477447630443105L;
	
	JLabel close, minimize, title;
	
	public DefaultTitleBar() {
		super();
		int emptySpace = UI.WIDTH - UI.TITLE_HEIGHT;

		setBackground(Colors.MENU_BACKGROUND);
		setPreferredSize(new Dimension(UI.WIDTH, UI.TITLE_HEIGHT));
		
		add(Box.createHorizontalGlue());
		
		title = new JLabel(Strings.TITLE);
		//title.setMinimumSize(new Dimension(emptySpace, UI.TITLE_HEIGHT));
		title.setPreferredSize(new Dimension(emptySpace, UI.TITLE_HEIGHT));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.revalidate();
		title.setForeground(Colors.RED_TEXT);
		title.setFont(Fonts.TITLE_BAR);
		new DragWindow(title);
		add(title);
		
		minimize = new JLabel(new ImageIcon(Resources.getImage(Images.MINIMIZE_BUTTON)));
		minimize.setPreferredSize(new Dimension(UI.TITLE_HEIGHT, UI.TITLE_HEIGHT));
		minimize.addMouseListener(this);
		minimize.setToolTipText(Strings.MINIMIZE);
		add(minimize);
		
		close = new JLabel(new ImageIcon(Resources.getImage(Images.CLOSE_BUTTON)));
		close.setPreferredSize(new Dimension(UI.TITLE_HEIGHT, UI.TITLE_HEIGHT));
		close.addMouseListener(this);
		close.setToolTipText(Strings.EXIT);
		//close.setBorder(BorderFactory.createEmptyBorder());
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
		if (e.getSource() == close) {
			System.exit(0);
		} else if (e.getSource() == minimize) {
			getParentFrame().setState(JFrame.ICONIFIED);
		}
	}
}
