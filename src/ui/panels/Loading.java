package ui.panels;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import ui.MainFrame;
import ui.Resources;
import ui.UIConstants.Images;

public class Loading extends UIPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 5754745059440665566L;
	
	private MainFrame parent_;
	private UIPanel next_;
	private JProgressBar progress_;
	
	public Loading(MainFrame parent, UIPanel next) {
		parent_ = parent;
		next_ = next;
	}
	
	@Override
	public void makeUI() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setPreferredSize(new Dimension(400, 600));
		JLabel l = new JLabel();
		l.setText("Loading...");
		add(l);
		
		progress_ = new JProgressBar(0, 100);
		progress_.setValue(0);
		add(progress_);
		
		Task t = new Task();
		t.addPropertyChangeListener(this);
		t.execute();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("progress"))
			progress_.setValue((int)evt.getNewValue());
	}
	
	private class Task extends SwingWorker<Void, Void> {
		@Override
		protected Void doInBackground() throws Exception {
			int total = Images.MENU_IMAGES.length;
			for (int i = 0; i < total; i++) {
				if (!Resources.loadImage(Images.MENU_IMAGES[i])) {
					System.err.println("Could not load file: " + Images.MENU_IMAGES[i]);
					System.exit(1);
				}
				setProgress((i+1) * 100 / total);
			}
			return null;
		}
		
		@Override
		public void done() {
			parent_.setPanel(next_);
		}
	}

}
