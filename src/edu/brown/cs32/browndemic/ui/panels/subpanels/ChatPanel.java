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
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.interfaces.ChatHandler;
import edu.brown.cs32.browndemic.ui.interfaces.ChatServer;
import edu.brown.cs32.browndemic.ui.panels.BrowndemicPanel;

public class ChatPanel extends BrowndemicPanel implements KeyListener, ChatHandler {

	private static final long serialVersionUID = 587517148784490168L;
	
	private JTextField _input;
	private JTextArea _chat;
	private JScrollPane _scroll;
	private ChatServer _chatServer;
	
	public ChatPanel(ChatServer chatServer) {
		super();
		_chatServer = chatServer;
		_chatServer.addChatHandler(this);
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
		Utils.setDefaultLook(_input);
		
		_chat = new JTextArea();
		_chat.setBackground(Colors.MENU_BACKGROUND);
		_chat.setForeground(Colors.RED_TEXT);
		_chat.setFont(Fonts.NORMAL_TEXT);
		_chat.setEditable(false);
		_chat.setBorder(BorderFactory.createEmptyBorder());
		
		_scroll = new JScrollPane(_chat, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		_scroll.setBorder(BorderFactory.createLineBorder(Colors.RED_TEXT, 2));
		add(_scroll);
		add(_input);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			_chatServer.sendMessage(_input.getText());
			_input.setText("");
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void addMessage(String message) {
		_chat.setText((_chat.getText() + "\n" + message).trim());
		_chat.setCaretPosition(_chat.getDocument().getLength());
	}

}
