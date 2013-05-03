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
        
    //when the user picks a disease
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
    

    /**
     * Runs the game
     */
    @Override
    public void run(){
        System.out.println("begin the loop");
        int i = 0;
        while(_numRegionsPicked < _diseases.size()){
            try{
                Thread.sleep(1);
            }
            catch(Exception e){
                
            }
        }
        System.out.println("starting game");
        while (!_gameOver){
            if (!_paused){
                long start = System.currentTimeMillis();
                update();
                if (allCured()){
                    _gameOver = true;
                    break;
                }
                else if (allKilled()){
                    crownWinners();
                    _gameOver = true;
                    break;
                }
                long end = System.currentTimeMillis();
                long offset = end - start;
                try{
                    Thread.sleep(_waitTime - offset);
                }
                catch(InterruptedException e){
                    System.out.println("Couldn't sleep...");
                }
            }
        }
    }
}
