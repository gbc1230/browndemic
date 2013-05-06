package edu.brown.cs32.browndemic.network;

public class EndGame implements GameData{
	private static final long serialVersionUID = 7601210403607605381L;
	
	private boolean _win;
	
	public EndGame(boolean w){
		_win = w;
	}
	
	@Override
	public String getID(){
		return "EG";
	}
	
	public boolean isWinner(){
		return _win;
	}

}
