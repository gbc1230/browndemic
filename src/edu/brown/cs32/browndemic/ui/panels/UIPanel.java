package edu.brown.cs32.browndemic.ui.panels;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BoxLayout;

import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;


@SuppressWarnings("serial")
public abstract class UIPanel extends BrowndemicPanel {
	public UIPanel() {
		super();
	}
	
	public abstract void setupForDisplay();
        
        public void stopPanel() {
            
        }
	
	protected void makeUI() {
		setBackground(Colors.TRANSPARENT);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);
		setPreferredSize(new Dimension(UI.WIDTH, UI.CONTENT_HEIGHT));
	}
	
	@Override
	public abstract String toString();
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(Resources.getImage(Images.BACKGROUND), 0, 0, null);
		super.paintComponent(g);
	}



}
