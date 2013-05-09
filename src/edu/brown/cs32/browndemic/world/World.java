/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;

import edu.brown.cs32.browndemic.disease.Disease;
import edu.brown.cs32.browndemic.region.Region;
import edu.brown.cs32.browndemic.region.AirTransmission;
import java.util.List;

/**
 *
 * @author Graham
 */
public interface World {
    
    /**
     * gives a certain disease a perk
     * @param dis The disease
     * @param perk The perk
     * @param buy Whether we're buying or selling
     */
    public void addPerk(int dis, int perk, boolean buy);

    /**
     * Get the population
     * @return population value
     */
    public long getPopulation();
    
    /**
     * Gets total population, including dead
     * @return total people
     */
    public long getTotalPopulation();
    
    /**
     * getHealthy() gets the number of healthy people in this world
     * @return integer healthy people value
     */
    public long getHealthy();

    /**
     * getInfected() gets the number of infected people in this world (not dead people)
     * @return integer infected value
     */
    public long getInfected();

    /**
     * getDead() gets the number of dead people in this world
     * @return integer dead value
     */
    public long getDead();
    
    //get infected for a specific disease
    public long getInfected(int d);
    
    //get dead for a specific disease
    public long getDead(int d);
    
    //get all airports
    public List<Airport> getAirports();
    
    //get all regions
    public List<Region> getRegions();
    
    //get a region by name
    public Region getRegion(String name);
    
    // get a region by id
    public Region getRegion(int id);
    
    //get all diseases
    public List<Disease> getDiseases();
    
    //tells me which diseases are cured
    public List<Boolean> getWhichCured();
    
    //tells me how many of each disease have been cured
    public List<Long> getCured();
    
    //percentage to the cure
    public double getCurePercentage(int d);
    
    //gets the news
    public List<String> getNews();
    
    //get airplane transmissions
    public AirTransmission getTransmission();
    
    //get the winner(s)
    public List<Integer> getWinners();
    
    //tells if the game is over
    public boolean isGameOver();
    
    //introduce a disease to a region
    public void introduceDisease(int r, int d);
    
    //add a new disease 
    public void addDisease(Disease d);
    
    //get a disease by id
    public Disease getDisease(int d);
    
    //tell this world that a user has picked or unpicked a disease when
    //choosing diseases 
    public void changeDiseasesPicked(int change);

    //tells me if all diseases have been picked
    public boolean allDiseasesPicked();
    /**
     * Lets me know if all diseases have been cured
     * @return boolean
     */
    public boolean allCured();
    
    /**
     * Leaves the game in MP, ends the game in SP or on the server side
     */
    public void leaveGame();
    
    /**
     * Used in ClientWorld, won't have anything extra to do in other versions
     */
    public boolean hostDisconnected();
    
    /**
     * Lets me know if all the people in the world are dead
     * @return boolean
     */
    public boolean allKilled();
    
    //pausing: only works in SP
    public void pause();
    
    //unpausing: only works in SP
    public void unpause();
    
    //change speed: only works in MP
    public void setSpeed(int t);
    
    //testing purposes ONLY: ends the game on command
    public void endGame(boolean win);
    
}
