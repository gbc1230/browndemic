package edu.brown.cs32.browndemic.network;

public class NameChange implements GameData{
	private static final long serialVersionUID = -2813613818297163888L;
	
	private String _name;
	
	public NameChange(String name){
		_name = name;
	}
	
	@Override
	public String getID() {
		return "NC";
	}
	
	public String getName(){
		return _name;
	}

}
