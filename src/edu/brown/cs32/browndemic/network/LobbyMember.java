/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.network;

/**
 * Class to represent a member of the lobby
 * @author Graham
 */
public class LobbyMember implements GameData{
	private static final long serialVersionUID = 6845578981856285152L;
	
	//this lobby member's name and IP they are coming from
    private String _name, _IP;
    //whether this lobby member is ready or not
    private boolean _ready;
    
    public LobbyMember(String name, String ip){
        _name = name;
        _IP = ip;
        _ready = false;
    }
    
    @Override
    public String getID(){
        return "LM";
    }
    
    public String getName(){
        return _name;
    }
    
    public String getIP(){
        return _IP;
    }
    
    public boolean isReady(){
        return _ready;
    }
    
    public void changeReady(boolean b){
        _ready = b;
    }
    
    public void setName(String n){
    	_name = n;
    }
    
    @Override
    public String toString(){
        return _name + ", " + _IP;
    }
    
}
