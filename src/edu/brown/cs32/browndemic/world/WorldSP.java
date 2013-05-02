/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;

import edu.brown.cs32.browndemic.region.Region;

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
    
    @Override
    public void introduceDisease(int d, int r){
        start();
        System.out.println("Introducing " + d + " to " + r);
        _regions.get(r).introduceDisease(_diseases.get(d));
        _numRegionsPicked++;
    }
    
    public void start(){
        for (int i = 0; i < _diseases.size(); i++){
            _cures.add(0L);
            _kills.add(0L);
            _infects.add(0L);
            _sent.add(false);
            _cured.add(false);
        }
        for (Region r : _regions){
            _population += r.getPopulation();
            r.setNumDiseases(_diseases.size());
            _cureTotal += r.getWealth() * r.getPopulation() * _MINCURETICKS;
        }
        _paused = false;
        _started = true;
        new Thread(this).start();
    }
}
