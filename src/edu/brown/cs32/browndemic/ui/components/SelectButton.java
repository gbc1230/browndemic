package edu.brown.cs32.browndemic.ui.components;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import edu.brown.cs32.browndemic.ui.actions.Action;

public class SelectButton extends JLabel implements MouseListener {
	private static final long serialVersionUID = -413064734900079598L;
	private ImageIcon _normal, _selected;
	private boolean _isSelected;
	
	private ArrayList<Action> _selectActions = new ArrayList<>();
	
	public SelectButton(BufferedImage normal, BufferedImage selected) {
		super();
		_normal = new ImageIcon(normal);
		_selected = new ImageIcon(selected);
		_isSelected = false;
		setIcon(_normal);
		setPreferredSize(new Dimension(normal.getWidth(), normal.getHeight()));
		addMouseListener(this);
	}
	
	public void addOnSelectAction(Action a) {
		_selectActions.add(a);
	}
	
	public void setSelected(boolean b) {
		_isSelected = b;
		if (_isSelected) {
			setIcon(_selected);
			for (Action a : _selectActions) {
				a.doAction();
			}
		} else {
			setIcon(_normal);
		}
	}
	
	public boolean isSelected() {
		return _isSelected;
	}
	
	public void select() {
		setSelected(true);
	}
	
	public void deSelect() {
		setSelected(false);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
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
		if (!contains(e.getPoint())) return;
		setSelected(true);
	}
}
