/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;

import edu.brown.cs32.browndemic.disease.Disease;
import edu.brown.cs32.browndemic.region.Region;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Graham
 */
public interface World {
    
    public abstract MainWorld getNextCommand();
    
    /**
     * Stop a disease that has left the game : used only for MP maps
     * @param id The id of the disease to remove
     */
    public abstract void removeDisease(int id);
    
    /**
     * gives a certain disease a perk
     * @param dis The disease
     * @param perk The perk
     * @param buy Whether we're buying or selling
     */
    public void addPerk(int dis, int perk, boolean buy);
    
    /**
     * addRegion() puts the Region into _regions and adds it to _regIndex
     * @param r the Region to add
     */
    public void addRegion(Region r);

    /**
     * addDisease() adds the given Disease to _diseases
     * @param d the Disease to add
     */
    public void addDisease(Disease d);

    /**
     * Get the population
     * @return integer population value
     */
    public long getPopulation();
    
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
    
    public List<Region> getRegions();
    
    public List<Disease> getDiseases();
    
    public List<Boolean> getCured();
    
    public List<String> getNews();
    
    public List<Integer> getWinners();
    
    public boolean isGameOver();
    
    /**
     * Once the world has been initialized, starts the world
     */
    public void start();
    
    /**
     * Updates the number of people killed by all diseases
     */
    public void updateKilled();
    
    
    /**
     * Updates the number of infected people
     */
    public void updateInfected();
    
    /**
     * Update the cure progress for every disease
     */
    public void updateCures();
    
    public void updateNews();
    
    /**
     * Tells each region to start curing a disease
     */
    public void sendCures(int d);
    
    public void checkCures();
    
    /**
     * Updates all the regions
     */
    public void updateRegions();

    /**
     * Updates which diseases have been cured
     */
    public void updateCured();
    
    /**
     * Lets me know if all diseases have been cured
     * @return boolean
     */
    public boolean allCured();
    
    /**
     * Lets me know if all the people in the world are dead
     * @return boolean
     */
    public boolean allKilled();
    
    /**
     * Return a list of winners, just in case there's a tie
     * @return 
     */
    public List<Integer> crownWinners();
    
    /**
     * Updates everything necessary from regions
     */
    public void update();
    
    /**
     * Runs the game
     */
    public void run();
    
}
