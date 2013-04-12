package edu.brown.cs32.browndemic.ui.panels;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import edu.brown.cs32.browndemic.ui.Utils;

/**
 * This class causes the JComponent specified in the constructor
 * to act as a location that can drag the entire BrowndemicFrame
 * that is its parent.
 * 
 * DragWindow should be used in the following way:
 * new DragWindow(Component);
 * This will cause the component to be able to drag the Frame.
 * 
 * @author Ben
 *
 */
public class DragWindow implements MouseListener, MouseMotionListener {
	private Point _startDrag, _startLoc;
	private JComponent _component;
	
	public DragWindow(JComponent c) {
		_component = c;
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
	}
	
	private Point getScreenLocation(MouseEvent e) {
		Point cursor = e.getPoint();
		Point target_location = _component.getLocationOnScreen();
	    return new Point((int) (target_location.getX() + cursor.getX()),
	    		(int) (target_location.getY() + cursor.getY()));

	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e)) return;
		
		Point p = getScreenLocation(e);
		Point offset = new Point((int) p.getX() - (int) _startDrag.getX(),
		        (int) p.getY() - (int) _startDrag.getY());
		Point location = new Point((int) (_startLoc.getX() + offset.getX()),
				(int) (_startLoc.getY() + offset.getY()));
		Utils.getParentFrame(_component).setLocation(location);
	}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e)) return;
		
		_startDrag = getScreenLocation(e);
		_startLoc = Utils.getParentFrame(_component).getLocation();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
