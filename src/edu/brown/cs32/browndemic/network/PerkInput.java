/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.network;

/**
 *
 * @author Graham
 */
public class PerkInput implements GameData{
	private static final long serialVersionUID = -419869967897788650L;
	
	//which disease this perk is for and the perk id to deal with
    private int _diseaseID, _perkID;
    //whether this is a buy or sell
    private boolean _buy;
    
    public PerkInput(int d, int p, boolean b){
        _diseaseID = d;
        _perkID = p;
        _buy = b;
    }
    
    @Override
    public String getID(){
        return "P";
    }
    
    //basic accessors
    public int getDiseaseID(){
        return _diseaseID;
    }
    
    public int getPerkID(){
        return _perkID;
    }
    
    public boolean isBuying(){
        return _buy;
    }
    
    
}
