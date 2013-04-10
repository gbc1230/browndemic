package edu.brown.cs32.browndemic.ui.panels.subpanels;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;

public class ChatPanel extends BrowndemicPanel implements KeyListener {

	private static final long serialVersionUID = 587517148784490168L;
	
	JTextField _input;
	JTextArea _chat;
	
	public ChatPanel() {
		super();
		makeUI();
	}
	
	public void makeUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Colors.MENU_BACKGROUND);
		
		_input = new JTextField();
		_input.setFont(Fonts.NORMAL_TEXT);
		_input.setBackground(Colors.MENU_BACKGROUND);
		_input.setForeground(Colors.RED_TEXT);
		_input.addKeyListener(this);
		_input.setMaximumSize(new Dimension(UI.WIDTH, 100));
		_input.setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 2));
		
		_chat = new JTextArea();
		_chat.setBackground(Colors.MENU_BACKGROUND);
		_chat.setForeground(Colors.RED_TEXT);
		_chat.setFont(Fonts.NORMAL_TEXT);
		_chat.setEditable(false);
		_chat.setBorder(BorderFactory.createEmptyBorder());
		
		JScrollPane scrollPane = new JScrollPane(_chat, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 2));
		add(scrollPane);
		add(_input);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			_chat.setText((_chat.getText() + "\nTest: " + _input.getText()).trim());
			_input.setText("");
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
