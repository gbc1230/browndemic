package edu.brown.cs32.browndemic.ui.components;

import java.awt.event.MouseEvent;

import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;

public class BackButton extends HoverLabel {
	private static final long serialVersionUID = -3574185656494449120L;
	UIPanel _back;
	public BackButton(UIPanel back) {
		super(Resources.getImage(Images.BACK), Resources.getImage(Images.BACK_HOVER), UI.TITLE_HEIGHT, UI.TITLE_HEIGHT);
		_back = back;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1) return;
		if (!contains(e.getPoint())) return;
		Utils.getParentFrame(this).setPanel(_back);
	}
}
