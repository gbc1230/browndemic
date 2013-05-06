package edu.brown.cs32.browndemic.network;

public class LobbyKick implements GameData {
	private static final long serialVersionUID = 6578440723176415536L;
	
	private int _client;
	
	public LobbyKick(int client){
		_client = client;
	}
	
	@Override
	public String getID() {
		return "LK";
	}
	
	public int getClient(){
		return _client;
	}

}