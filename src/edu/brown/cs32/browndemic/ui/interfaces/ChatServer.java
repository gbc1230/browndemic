package edu.brown.cs32.browndemic.ui.interfaces;

public interface ChatServer {
	public void sendMessage(String message);
	
	public void addChatHandler(ChatHandler c);
}
