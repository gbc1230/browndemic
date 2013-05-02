/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.network;

/**
 *
 * @author Graham
 */
public class DiseaseIntroducer implements GameData{
    
    private int _region;
    private int _disease;
    
    public DiseaseIntroducer(int r, int d){
        _region = r;
        _disease = d;
    }
    
    @Override
    public String getID(){
        return "DI";
    }
    
    public int getRegion(){
        return _region;
    }
    
    public int getDisease(){
        return _disease;
    }
    
}
