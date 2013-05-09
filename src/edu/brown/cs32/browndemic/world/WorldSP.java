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
	private static final long serialVersionUID = -8695489680338623284L;

	/**
	 * Basic constructor, only calls super()
	 */
    public WorldSP(){
        super();
    }
        
    /**
     * For use when the user picks a disease at the menu
     */
    @Override
    public void changeDiseasesPicked(int change){
        _numDiseasesPicked += change;
        if (_numDiseasesPicked == 1)
            _allDiseasesPicked = true;
    }
    
    /**
     * Introduce a disease to a region
     */
    @Override
    public void introduceDisease(int d, int r){
        start();
        _regions.get(r).introduceDisease(_diseases.get(d));
        _numRegionsPicked++;
    }
    
    /**
     * Starts the game and sets up important information
     */
    public void start(){
    	setupDiseases();
        for (Region r : _regions){
            r.setNumDiseases(_diseases.size());
            _cureTotal += r.getWealth() * r.getPopulation() * _MINCURETICKS;
        }
        _paused = false;
        _started = true;
        new Thread(this).start();
    }
    
    /**
     * For use after the game has been loaded 
     */
    public void startFromLoad(){
    	_waitTime = _SPEED1;
    	_paused = false;
    	new Thread(this).start();
    }
    
    /**
     * Runs the game
     */
    @Override
    public void run(){
        while(_numRegionsPicked < _diseases.size()){
            try{
                Thread.sleep(1);
            }
            catch(Exception e){
                continue;
            }
        }
        while (!_gameOver){
            try{
                Thread.sleep(1);
            }
            catch(Exception e){
                continue;
            }
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
                	if (offset < _waitTime)
                		Thread.sleep(_waitTime - offset);
                }
                catch(InterruptedException e){
                	continue;
                }
            }
        }
    }
}
