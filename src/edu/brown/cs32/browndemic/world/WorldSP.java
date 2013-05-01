/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;

/**
 * Single player world
 * @author Graham
 */
public class WorldSP extends MainWorld{
    
    public WorldSP(){
        super();
    }
        
    @Override
    public void changeDiseasesPicked(int change){
        _numDiseasesPicked += change;
        if (_numDiseasesPicked == 1)
            _allDiseasesPicked = true;
    }
    
}
