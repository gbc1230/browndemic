package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.brown.cs32.browndemic.disease.Perk;
import edu.brown.cs32.browndemic.ui.Resources;
import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Images;

public class PerkList extends JPanel implements MouseListener {
	private static final long serialVersionUID = -4135384928071263238L;
	
	private Map<PerkPanel, Perk> _buttons = new HashMap<>();
	private UpgradePanel _parent;
	private PerkPanel _selected;
	
	public PerkList(List<Perk> perks, UpgradePanel parent) {
		setBackground(Colors.TRANSPARENT);
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		_parent = parent;
		
		setList(perks);
	}
	
	public void setList(List<Perk> perks) {
		removeAll();
		_buttons.clear();
		for (Perk p : perks) {
			Image img = Resources.getImage(Images.PERKS_PATH + p.getImage());
			System.out.println(p.getImage());
			PerkPanel pp = new PerkPanel().setImage(img)//Resources.getImage(Images.PERK_LUNGS))
					.setText(p.getName()).setTextFont(Fonts.TITLE_BAR).setTextColor(Colors.RED_TEXT);
			pp.setBackground(Colors.MENU_BACKGROUND);
			//pp.setOpaque(false);
			pp.addMouseListener(this);
			pp.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
			_buttons.put(pp, p);
			add(pp);
		}
		add(Box.createGlue());
		revalidate();
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e)) return;
		if (e.getSource() instanceof PerkPanel) {
			PerkPanel pp = (PerkPanel) e.getSource();
			if (pp.contains(e.getPoint())) {
				if (_selected != null) {
					_selected.setTextColor(Colors.RED_TEXT).setBackground(Colors.MENU_BACKGROUND);
				}
				pp.setTextColor(Colors.HOVER_TEXT).setBackground(new Color(150, 0, 0));
				_selected = pp;
				_parent.setPerk(_buttons.get(pp));
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() instanceof PerkPanel) {
			PerkPanel pp = (PerkPanel) e.getSource();
			if (pp != _selected) {
				pp.setTextColor(Colors.HOVER_TEXT).setBackground(new Color(100, 0, 0));
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() instanceof PerkPanel) {
			PerkPanel pp = (PerkPanel) e.getSource();
			if (pp != _selected) {
				pp.setTextColor(Colors.RED_TEXT).setBackground(Colors.MENU_BACKGROUND);
			}
		}
	}
}
