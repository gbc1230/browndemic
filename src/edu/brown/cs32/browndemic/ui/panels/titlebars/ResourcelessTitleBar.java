package edu.brown.cs32.browndemic.ui.panels.titlebars;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.panels.DragWindow;

public class ResourcelessTitleBar extends TitleBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1677371591163498885L;
	
	public ResourcelessTitleBar() {
		super();
		
		setBackground(Colors.MENU_BACKGROUND);
		add(Box.createGlue());
		
		JLabel l = new JLabel(Strings.TITLE);
		l.setForeground(Colors.RED_TEXT);
		l.setFont(Fonts.TITLE_BAR);
		l.setHorizontalAlignment(SwingConstants.CENTER);
		add(l);

		add(Box.createHorizontalGlue());
		
		new DragWindow(this);
	}
}
