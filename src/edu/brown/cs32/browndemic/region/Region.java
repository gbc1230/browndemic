/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.region;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import edu.brown.cs32.browndemic.disease.Disease;

/**
 *
 * @author ckilfoyl
 */
public class Region {
    //ArrayList of all land neighboring Regions by String name
    private ArrayList<Integer> _landNeighbors;

    //ArrayList of all sea neighboring Regions by String name
    private ArrayList<Integer> _waterNeighbors;

    //Hashmap of all Regions by ID
    private HashMap<Integer, Region> _regions;

    //ArrayList of diseases in this Region
    private ArrayList<Disease> _diseases;

    //Total Region Population
    private long _population;

    //Healthy population count
    private long _healthy;

    //ArrayList of infected, order corresponds to the diseases in _disease
    private ArrayList<Long> _infected;

    //ArrayList of dead, order corresponds to the diseases in _disease
    private ArrayList<Long> _dead;

    //ArrrayList of cured, order corresponds to diseases in _disease
    private ArrayList<Long> _cured;

    //ArrayList of boolean isCure, order corresponds to diseases in _disease
    private ArrayList<Boolean> _hasCure;

    //ArrayList of double awaresness for each disease
    private ArrayList<Double> _awareness;

    private double _CLOSEPORTS;
    
    //ArrayList of double cure progress for each disease
    private ArrayList<Double> _cureProgress;

    //Unique Region name
    //emphasis on the unique, some code in here runs on that assumption (hash, equals, etc.)
    private String _name;

    //Unique Int ID of this region
    private int _ID;

    //number of seaports and airports open in this Region
    private int _sea;
    private int _air;
    //wealth of this Region (reflects infrastructure, productivity, actual wealth, etc.)
    private double _wealth,_wet,_dry,_heat,_cold;
    
    private ArrayList<RegionTransmission> _transmissions;
    private ArrayList<String> _news;

    /**
     * constructs a new Region with the given info
     * @param name The unique String name
     * @param population the initial population count
     * @param neighbors the names of all bordering Regions
     * @param seaports if this Region has open seaports
     * @param airports if this Region has open airports
     */
    public Region(int ID, String name, long population, List<Integer> landNeighbors,
            List<Integer> waterNeighbors, HashMap<Integer, Region> hash,
            int seaports, int airports, double wealth, double wet, double dry,
            double heat, double cold) {
        _name = name;
        _ID = ID;
        _population = population;
        _healthy = population;
        _infected = new ArrayList<Long>();
        _dead = new ArrayList<Long>();
        _cured = new ArrayList<Long>();
        _hasCure = new ArrayList<Boolean>();
        _diseases = new ArrayList<Disease>();
        _awareness = new ArrayList<Double>();
        _cureProgress = new ArrayList<Double>();
        _landNeighbors = new ArrayList<Integer>(landNeighbors.size());
        _landNeighbors.addAll(landNeighbors);
        _waterNeighbors = new ArrayList<Integer>(waterNeighbors.size());
        _waterNeighbors.addAll(waterNeighbors);
        _regions = hash;
        _sea = seaports;
        _air = airports;
        _wealth = wealth;
        _wet = wet;
        _dry = dry;
        _heat = heat;
        _cold = cold;
        _transmissions = new ArrayList<RegionTransmission>();
        _news = new ArrayList<String>();
    }

    /**
     * The update method for this region
     */
    public void update() {
        for (Disease d : _diseases) {
            if (null != d) {
                awarenessCheck();
                updateAwareness(d);
                updateWealth(d);
                cure(d);
                kill(d);
                infect(d);
                transmitSeaAndAir(d);
                transmitToLandNeighbors(d);
                transmitToWaterNeighbors(d);
            }
        }
    }

    /**
     * calculates the number of pop to be infected
     * @param d the index of the disease
     * @param pop the population to infect
     * @return how many to infect
     */
    public int getInfected(int d, long pop) {
        int number = 0;
        //TODO calculate number of pop infected.
        return number;
    }

    /**
     * Infects a portion of the healthy population
     * @param d the index of the disease
     */
    public void infectHealthy(int d) {
        long infect = getInfected(d, _healthy);
        if (infect > _healthy) {
            _infected.set(d, _infected.get(d) + _healthy);
            _healthy = 0;
        } else {
            _infected.set(d, _infected.get(d) + infect);
            _healthy -= infect;
        }
    }

    /**
     * infects a portion of the population infected by other diseases
     * @param d
     */
    public void infectInfected(int d) {
        //TODO DECIDE HOW TO KEEP TRACK OF POP W/ MULTIPLE INFECTIONS
    }

    /**
     * infect(Disease) updates the infected for the given disease
     * @param disease the disease to update for
     **/
    public void infect(Disease disease) {
        int index = disease.getID();
        infectHealthy(index);
        infectInfected(index);
    }

    /**
     * infect(Disease, int) updates the dead for a given disease
     * @param disease the disease to update dead for
     **/
    public void kill(Disease disease) {
        int index = disease.getID();
        int number = (int) (disease.getLethality() * _infected.get(index));
        if (_infected.get(index) < number) {
            _dead.set(index, _dead.get(index) + _healthy);
            _infected.set(index, 0L);
        } else {
            _dead.set(index, _dead.get(index) + number);
            _infected.set(index, _infected.get(index) - number);
        }
    }

    /**
     * calculates the number of people to cure in a given population
     * @param d
     * @param pop
     * @return
     */
    public int getNumCured(int d, long pop) {
        //TODO right now cured just cures 5% of total pop per tick
        int number = (int) (0.05*_population);
        return number;
    }

    /**
     * cure(Disease) updates the number of cured for this disease
     * @param d the disease to update cured for
     */
    public void cure(Disease d) {
        int index = d.getID();
        if (_hasCure.get(index) == true) {
            long number = getNumCured(index, _infected.get(index));
            if (_infected.get(index) < number) {
                _cured.set(index, _cured.get(index) + _infected.get(index));
                _infected.set(index, 0L);
            } else {
                _cured.set(index, _cured.get(index) + number);
                _infected.set(index, _infected.get(index) - number);
            }
        }
    }

    /**
     * checks if ports should be closed
     */
    public void awarenessCheck(){
        for(double aware : _awareness){
            if(aware > _CLOSEPORTS && !(_air == 0 && _sea == 0)){
                _air = 0;
                _sea = 0;
                _news.add(_name + " has closed it's air and seaports.");
            }
        }
    }
    
    public void updateAwareness(Disease d){
        int index = d.getID();
        //awareness += vis*(infected + dead)
        //TODO update this if infected can overlap
        _awareness.set(index, _awareness.get(index) + d.getVisibility()
                * (_infected.get(index) + _dead.get(index)));
    }

    public void updateWealth(Disease d){
        int index = d.getID();
        //TODO update wealth calculation here
    }

    /**
     * setCure(int) sets the boolean cured for the disease at int to true
     * int d the index of the disease to cure
     */
    public void setCure(int d) {
        if (_diseases.size() > d && null != _diseases.get(d)) {
            _hasCure.set(d, true);
        } else {
            _hasCure.add(d, true);
        }
    }

    /**
     * introduces a disease to this region, initializes necessary lists
     * @param d hte disease to introduce
     */
    public void introduceDisease(Disease d) {
        int index = d.getID();
        _infected.add(index, 1L);
        _dead.add(index, 0L);
        _cured.add(index, 0L);
        _hasCure.add(index, false);
        _awareness.add(index, 0.0);
        _cureProgress.add(index, 0.0);
    }

    /**
     * Transmits the disease to all regions with open sea/airports if the transmission
     * conditions are met (probability)
     * @param d the disease to transmit
     */
    public void transmitSeaAndAir(Disease d) {
        for (Region region : _regions.values()) {
            if (region.hasDisease(d)) {
                continue;
            }
            int air = region.getAir();
            int sea = region.getAir();
            if (air > 0 && _air > 0) {
                boolean transmit = false;
                //TODO conditions for plane/sea transmit
                if (transmit) {
                    RegionTransmission rt = new RegionTransmission(_name, region.getName(), d.getID(), true);
                    _transmissions.add(rt);
                    region.introduceDisease(d);
                    continue;
                }
            }
            if (sea > 0 && _sea > 0) {
                boolean transmit = false;
                //TODO fill in conditions for ship transmission
                if (transmit) {
                    RegionTransmission rt = new RegionTransmission(_name, region.getName(), d.getID(), true);
                    _transmissions.add(rt);
                    region.introduceDisease(d);
                }
            }
        }
    }

    /**
     * transmits the disease to all Land Neighbors if the conditions are met
     * @param d the disease to transmit
     */
    public void transmitToLandNeighbors(Disease d) {
        for (Integer id : _landNeighbors) {
            Region region = _regions.get(id);
            if (region.hasDisease(d)) {
                continue;
            }
            boolean transmit = false;
            //TODO fill in conditions for land transmission
            if (transmit) {
                region.introduceDisease(d);
            }
        }
    }

    /**
     * transmits the disease to all Water Neighbors if the conditions are met
     * @param d the disease to transmit
     */
    public void transmitToWaterNeighbors(Disease d) {
        for (Integer id : _landNeighbors) {
            Region region = _regions.get(id);
            if (region.hasDisease(d)) {
                continue;
            }
            boolean transmit = false;
            //TODO fill in conditions for water transmission
            if (transmit) {
                region.introduceDisease(d);
            }
        }
    }

    /**
     * prompts a natural disaster with the given intensity in this region
     * @param intensity on a scale of 1-10
     */
    public void naturalDisaster(int intensity) {
        String news = "";
        //TODO generate disaster and impact wealth, maybe population?
        _news.add(news);
    }

    /**
     * gets the ArrayList of all air/sea transmissions
     * @return _transmissions
     */
    public ArrayList<RegionTransmission> getTransmissions() {
        return _transmissions;
    }

    /**
     * clears the transmissions list
     */
    public void clearTransmissions() {
        _transmissions.clear();
    }

    /**
     * gets the news
     * @return
     */
    public ArrayList<String> getNews() {
        return _news;
    }

    /**
     * clears the news
     */
    public void clearNews() {
        _news.clear();
    }

    /**
     * hasDisease(Disease) returns true if this region has been infected by this disease
     * @param d
     * @return
     */
    public boolean hasDisease(Disease d) {
        return (_diseases.size() < d.getID() || _diseases.get(d.getID()) == null);
    }

    /**
     * getAir() gets a the number of open airports in this Region
     * @return _air;
     */
    public int getAir() {
        return _air;
    }

    /**
     * getSea() gets the number of open seaports in this Region
     * @return _sea
     */
    public int getSea() {
        return _sea;
    }

    /**
     * getWealth() returns the wealth of this region
     * @return _wealth
     */
    public double getWealth() {
        return _wealth;
    }

    /**
     * getNeighbors() gets the ids of all bordering Regions
     * @return _neighbors
     */
    public ArrayList<Integer> getLandNeighbors() {
        return _landNeighbors;
    }

    /**
     * getNeighbors() gets the ids of all bordering Regions (by Water)
     * @return _neighbors
     */
    public ArrayList<Integer> getWaterNeighbors() {
        return _waterNeighbors;
    }

    /**
     * getInfected() gets the ArrayList of infected people in this Region
     * @return _infected
     */
    public ArrayList<Long> getInfected() {
        return _infected;
    }

    /**
     * getKilled() gets the ArrayList of dead people in this Region
     * @return _dead;
     */
    public ArrayList<Long> getKilled() {
        return _dead;
    }

    /**
     * getCured() gets the ArrayList of cured people in this Region
     * @return _cured;
     **/
    public ArrayList<Long> getCured() {
        return _cured;
    }

    /**
     * getAlive() gets the number of healthy people in this Region
     * @return _healthy
     */
    public long getHealthy() {
        return _healthy;
    }

    /**
     * getName() gets the unique String name for this Region
     * @return _name
     */
    public String getName() {
        return _name;
    }

    /**
     * getID() gets the unique int ID for this region
     * @return _ID
     */
    public int getID(){
        return _ID;
    }
    
    //accessor for getting population
    public long getPopulation(){
        return _population;
    }

    /**
     * toString() returns a String with the name and population counts for this Region
     * @return
     */
    @Override
    public String toString() {
        return _name + ", healthy: " + _healthy + ", infected: " + _infected +
                ", dead: " + _dead;
    }

    /**
     * equals(Object o) returns true if o is a Region with the same name,
     * false otherwise
     * @param o the object to compare to
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) {
            return false;
        } else {
            Region r = (Region) o;
            return _ID == r.getID();
        }
    }

    /**
     * hashCode() returns the hashCode for this Region's String name
     * @return _name.hashCode()
     */
    @Override
    public int hashCode() {
        return new Integer(_ID).hashCode();
    }
}
