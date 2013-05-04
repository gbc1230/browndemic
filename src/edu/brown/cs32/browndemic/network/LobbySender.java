/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.network;

import java.util.List;

/**
 *
 * @author Graham
 */
public class LobbySender implements GameData{
	
	private static final long serialVersionUID = -2442268800083564695L;
	
    private List<LobbyMember> _lobby;
    //for when a user leaves the lobby
    private int _remove;
    
    public LobbySender(List<LobbyMember> lobby){
        _lobby = lobby;
        _remove = -1;
    }
    
    public LobbySender(List<LobbyMember> lobby, int remove){
    	_lobby = lobby;
    	_remove = remove;
    }
    
    @Override
    public String getID(){
        return "LS";
    }
    
    public List<LobbyMember> getLobby(){
        return _lobby;
    }
    
    public int getRemove(){
    	return _remove;
    }
    
}
