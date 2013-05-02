/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.network;

/**
 *
 * @author Graham
 */
public class LobbyRemoval implements GameData{
    
    //the index of this removal
    private int _index;
    
    public LobbyRemoval(int ind){
        _index = ind;
    }
    
    @Override
    public String getID(){
        return "LR";
    }
    
    public int getIndex(){
        return _index;
    }
    
}
