/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.network;

/**
 *
 * @author Graham
 */
public class DiseasePicked implements GameData {
	private static final long serialVersionUID = 743021975576218385L;
	
	private int _change;
    
    public DiseasePicked(int c){
        _change = c;
    }
    
    @Override
    public String getID(){
        return "DP";
    }
    
    public int getChange(){
        return _change;
    }
    
}
