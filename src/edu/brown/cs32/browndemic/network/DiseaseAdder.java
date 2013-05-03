/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.network;
import edu.brown.cs32.browndemic.disease.Disease;

/**
 *
 * @author Graham
 */
public class DiseaseAdder implements GameData{
	private static final long serialVersionUID = 8688855284835580572L;
	
	private Disease _disease;
    
    public DiseaseAdder(Disease d){
        _disease = d;
    }
    
    @Override
    public String getID(){
        return "DA";
    }
    
    public Disease getDisease(){
        return _disease;
    }
    
}
