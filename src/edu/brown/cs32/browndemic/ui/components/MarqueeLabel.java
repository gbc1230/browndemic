package edu.brown.cs32.browndemic.ui.components;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JComponent;

public class MarqueeLabel extends JComponent {
	private static final long serialVersionUID = -8551552406370774034L;

	private Queue<String> _queue = new LinkedList<>();
	
	private int _x, _y, _width, _currentX, _currentWidth;
	private String _current;
	
	private static final int SPEED = 2;
	
	public MarqueeLabel(int x, int y, int width) {
		_x = x; _y = y; _width = width;
	}

	public void addString(String s) {
		_queue.add(s);
	}
	
	public void update(FontMetrics fm) {
		if (_current == null) {
			_current = _queue.poll();
			if (_current == null) return;
			_currentWidth = fm.stringWidth(_current);
			_currentX = _x + _width;
		} else if (!_queue.isEmpty()) {
			int right = _width -  (_currentX + _currentWidth);
			String spaces = "        ";
			while (fm.stringWidth(spaces) < right) {
				spaces += " ";
			}
			_current += spaces + _queue.poll();
			_currentWidth = fm.stringWidth(_current);
		}
		_currentX -= SPEED;
		if (_currentX < _x - _currentWidth) {
			_current = null;
			update(fm);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.setFont(getFont());
		FontMetrics fm = g.getFontMetrics();
		
		update(fm);
		
		if (_current == null) return;
		
		g.fillRect(_x, _y, _width, fm.getHeight() + 4);
		
		g.setColor(getForeground());
		g.drawString(_current, _currentX, _y + fm.getHeight());
	}
}
