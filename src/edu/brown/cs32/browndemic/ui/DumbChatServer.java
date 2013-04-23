package edu.brown.cs32.browndemic.ui;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs32.browndemic.ui.interfaces.ChatHandler;
import edu.brown.cs32.browndemic.ui.interfaces.ChatServer;

public class DumbChatServer implements ChatServer {
	
	private List<ChatHandler> _ch = new ArrayList<>();

	@Override
	public void sendMessage(String message) {
		for (ChatHandler ch : _ch)
			ch.addMessage(message);
	}

	@Override
	public void addChatHandler(ChatHandler c) {
		_ch.add(c);
	}

}
