package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.Image;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import edu.brown.cs32.browndemic.disease.Perk;
import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;
import edu.brown.cs32.browndemic.ui.WrapLayout;
import edu.brown.cs32.browndemic.ui.actions.Action;
import edu.brown.cs32.browndemic.ui.components.HoverSelectButton;

public class PerkList extends JPanel {
	private static final long serialVersionUID = -4135384928071263238L;
	
	private Map<HoverSelectButton, Perk> _buttons = new HashMap<>();
	private UpgradePanel _parent;
	
	public PerkList(List<Perk> perks, UpgradePanel parent) {
		setBackground(Colors.TRANSPARENT);
		setOpaque(false);
		setLayout(new WrapLayout());
		_parent = parent;
		
		setList(perks);
	}
	
	public void setList(List<Perk> perks) {
		removeAll();
		_buttons.clear();
		for (Perk p : perks) {
			HoverSelectButton h = new HoverSelectButton(Resources.getImage(Images.DISEASE).getScaledInstance(35, 35, Image.SCALE_FAST), 
					Resources.getImage(Images.DISEASE_SELECTED).getScaledInstance(35, 35, Image.SCALE_FAST),
					Resources.getImage(Images.DISEASE_SELECTED).getScaledInstance(35, 35, Image.SCALE_FAST));
			h.addOnSelectAction(new SelectAction(h));
			_buttons.put(h, p);
			add(h);
		}
		revalidate();
		repaint();
	}
	
	private class SelectAction implements Action {
		private HoverSelectButton b;
		public SelectAction(HoverSelectButton b) {
			this.b = b;
		}
		@Override
		public void doAction() {
			_parent.setPerk(_buttons.get(b));
			for (HoverSelectButton h : _buttons.keySet()) {
				if (h != b)
					h.setSelected(false);
			}
		}
	}
}
