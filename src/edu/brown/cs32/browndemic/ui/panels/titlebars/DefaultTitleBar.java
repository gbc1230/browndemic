package edu.brown.cs32.browndemic.ui.panels.titlebars;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
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
		new DragWindow(this);
		
		add(Box.createHorizontalGlue());
		
		title = new JLabel(Strings.TITLE);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.revalidate();
		title.setForeground(Colors.RED_TEXT);
		title.setFont(Fonts.TITLE_BAR);
		new DragWindow(title);
		add(title);
		
		add(Box.createRigidArea(new Dimension(UI.WIDTH/2 - UI.TITLE_HEIGHT * 2 - 40,0)));
		
		minimize = new HoverLabel(Resources.getImage(Images.MINIMIZE_BUTTON), Resources.getImage(Images.MINIMIZE_BUTTON_HOVER));
		minimize.addMouseListener(this);
		minimize.setToolTipText(Strings.MINIMIZE);
		add(minimize);
		
		close = new HoverLabel(Resources.getImage(Images.CLOSE_BUTTON), Resources.getImage(Images.CLOSE_BUTTON_HOVER));
		close.addMouseListener(this);
		close.setToolTipText(Strings.EXIT);
		add(close);
	}

	@Override
	public void mouseReleasedInside(MouseEvent e) {
		if (e.getSource() == close) {
			System.exit(0);
		} else if (e.getSource() == minimize) {
			Utils.getParentFrame(this).setState(JFrame.ICONIFIED);
		}
	}
}
