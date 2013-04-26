/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import edu.brown.cs32.browndemic.disease.Disease;


/**
 *
 * @author ckilfoyl
 */
public class Region {
    //ArrayList of all land neighboring Regions by String name
    private ArrayList<String> _landNeighbors;

    //ArrayList of all sea neighboring Regions by String name
    private ArrayList<String> _waterNeighbors;

    private HashMap<String, Region> _regions;

    //ArrayList of diseases in this Region
    private ArrayList<Disease> _diseases;

    //Total Region Population
    private int _population;

    //Healthy population count
    private int _healthy;

    //ArrayList of infected, order corresponds to the diseases in _disease
    private ArrayList<Integer> _infected;

    //ArrayList of dead, order corresponds to the diseases in _disease
    private ArrayList<Integer> _dead;
    
    //ArrrayList of cured, order corresponds to diseases in _disease
    private ArrayList<Integer> _cured;
    
    //ArrayList of boolean isCure, order corresponds to diseases in _disease
    private ArrayList<Boolean> _hasCure;

    //Unique Region name
    //emphasis on the unique, some code in here runs on that assumption (hash, equals, etc.)
    private String _name;

    //number of seaports and airports open in this Region
    private int _sea;
    private int _air;
    
    //wealth of this Region (reflects infrastructure, productivity, actual wealth, etc.)
    private double _wealth;

    private ArrayList<RegionTransmission> _transmissions;
    private ArrayList<String> _news;
    

    /**
     * constructs a new Region with the given info
     * @param name The unique String name
     * @param population the initial population count
     * @param neighbors the names of all bordering Regions
     * @param sea if this Region has open seaports
     * @param air if this Region has open airports
     */
    public Region(String name, int population, Collection<String> landNeighbors,
            Collection<String> waterNeighbors, HashMap<String, Region> hash,
            int sea, int air, double wealth){
        _name = name;
        _population = population;
        _healthy = population;
        _infected = new ArrayList<Integer>();
        _dead = new ArrayList<Integer>();
        _cured = new ArrayList<Integer>();
        _hasCure = new ArrayList<Boolean>();
        _landNeighbors = new ArrayList<String>(landNeighbors.size());
        _landNeighbors.addAll(landNeighbors);
        _waterNeighbors = new ArrayList<String>(waterNeighbors.size());
        _waterNeighbors.addAll(waterNeighbors);
        _regions = hash;
        _sea = sea;
        _air = air;
        _wealth = wealth;
        _transmissions = new ArrayList<RegionTransmission>();
        _news = new ArrayList<String>();
    }

    /**
     * The update method for this region
     */
    public void update(){
      for(Disease d : _diseases){
        if(null != d){
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
    public int getInfected(int d, int pop){
        int number = 0;
        //TODO calculate number of pop infected.
        return number;
    }

    /**
     * Infects a portion of the healthy population
     * @param d the index of the disease
     */
    public void infectHealthy(int d){
        int infect = getInfected(d, _healthy);
        if(infect > _healthy){
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
    public void infectInfected(int d){
        //TODO DECIDE HOW TO KEEP TRACK OF POP W/ MULTIPLE INFECTIONS
    }

    /**
     * infect(Disease) updates the infected for the given disease, or adds the disease if it's new
     * @param disease the disease to update for
     **/
    public void infect(Disease disease) {
        int index = disease.getID();
        if(_diseases.size() < index || null == _diseases.get(index)){
            _infected.add(index, 1);
            _dead.add(index,0);
            _cured.add(index,0);
            if(_hasCure.size() < index || _hasCure.get(index) == null)
                _hasCure.add(index, false);
        }
        infectHealthy(index);
        infectInfected(index);
    }

    /**
     * infect(Disease, int) updates the dead for a given disease
     * @param disease the disease to update dead for
     **/
    public void kill(Disease disease) {
        int index = disease.getID();
        if (_diseases.size() > index && null != _diseases.get(index)) {
            int number = (int) (disease.getLethality() * _infected.get(index));
            if (_infected.get(index) < number) {
                _dead.set(index, _dead.get(index) + _healthy);
                _infected.set(index, 0);
            } else {
                _dead.set(index, _dead.get(index) + number);
                _infected.set(index, _infected.get(index) - number);
            }
        }
    }

    /**
     * calculates the number of people to cure in a given population
     * @param d
     * @param pop
     * @return
     */
    public int getCured(int d, int pop){
        int number = 0;
        //TODO calculate cured given the population
        return number;
    }

    /**
     * cure(Disease) updates the number of cured for this disease
     * @param d the disease to update cured for
     */
    public void cure(Disease d){
      int index = d.getID();
      if(_diseases.size() > index && null != _diseases.get(index) && _hasCure.get(index) == true){
        int number = getCured(index, _infected.get(index));
        if(_infected.get(index) < number){
          _cured.set(index, _cured.get(index) + _infected.get(index));
          _infected.set(index, 0);
        }else{
          _cured.set(index, _cured.get(index) + number);
          _infected.set(index, _infected.get(index) - number);
        }
      }
    }
    
    /**
     * setCure(int) sets the boolean cured for the disease at int to true
     * int d the index of the disease to cure
     */
    public void setCure(int d){
      if(_diseases.size() > d && null != _diseases.get(d)){
        _hasCure.set(d, true);
      } else {
          _hasCure.add(d, true);
      }
    }

    /**
     * Transmits the disease to all regions with open sea/airports if the transmission
     * conditions are met (probability)
     * @param d the disease to transmit
     */
    public void transmitSeaAndAir(Disease d){
        for(Region region : _regions.values()){
            if(region.hasDisease(d))
                continue;
            int air = region.getAir();
            int sea = region.getAir();
            if(air > 0 && _air > 0){
                boolean transmit = false;
                //TODO fill in conditions for airplane transmission
                if(transmit){
                    RegionTransmission rt = new RegionTransmission(_name, region.getName(), d.getID(), true);
                    _transmissions.add(rt);
                    region.infect(d);
                    continue;
                }
            }
            if(sea > 0 && _sea > 0){
                boolean transmit = false;
                //TODO fill in conditions for ship transmission
                if(transmit){
                    RegionTransmission rt = new RegionTransmission(_name, region.getName(), d.getID(), true);
                    _transmissions.add(rt);
                    region.infect(d);
                }
            }
        }
    }

    /**
     * transmits the disease to all Land Neighbors if the conditions are met
     * @param d the disease to transmit
     */
    public void transmitToLandNeighbors(Disease d) {
        for (String name : _landNeighbors) {
            Region region = _regions.get(name);
            if (region.hasDisease(d)) {
                continue;
            }
            boolean transmit = false;
            //TODO fill in conditions for land transmission
            if (transmit)
                region.infect(d);
        }
    }

    /**
     * transmits the disease to all Water Neighbors if the conditions are met
     * @param d the disease to transmit
     */
    public void transmitToWaterNeighbors(Disease d){
        for(String name : _landNeighbors){
            Region region = _regions.get(name);
            if (region.hasDisease(d)) {
                continue;
            }
            boolean transmit = false;
            //TODO fill in conditions for water transmission
            if (transmit)
                region.infect(d);
        }
    }

    /**
     * prompts a natural disaster with the given intensity in this region
     * @param intensity on a scale of 1-10
     */
    public void naturalDisaster(int intensity){
        String news = "";
        //TODO generate disaster and impact wealthy, maybe population?
        _news.add(news);
    }

    /**
     * gets the ArrayList of all air/sea transmissions
     * @return _transmissions
     */
    public ArrayList<RegionTransmission> getTransmissions(){
        return _transmissions;
    }

    /**
     * clears the transmissions list
     */
    public void clearTransmissions(){
        _transmissions.clear();
    }

    /**
     * gets the news
     * @return
     */
    public ArrayList<String> getNews(){
        return _news;
    }

    /**
     * clears the news
     */
    public void clearNews(){
        _news.clear();
    }

    /**
     * hasDisease(Disease) returns true if this region has been infected by this disease
     * @param d
     * @return
     */
    public boolean hasDisease(Disease d){
        return (_diseases.size() < d.getID() || _diseases.get(d.getID()) == null);
    }

    /**
     * getAir() gets a the number of open airports in this Region
     * @return _air;
     */
    public int getAir(){
        return _air;
    }

    /**
     * getSea() gets the number of open seaports in this Region
     * @return _sea
     */
    public int getSea(){
        return _sea ;
    }
    
    /**
     * getWealth() returns the wealth of this region
     * @return _wealth
     */
    public double getWealth(){
      return _wealth;
    }

    /**
     * getNeighbors() gets the names of all bordering Regions
     * @return _neighbors
     */
    public ArrayList<String> getLandNeighbors(){
        return _landNeighbors;
    }


    /**
     * getNeighbors() gets the names of all bordering Regions (by Water)
     * @return _neighbors
     */
    public ArrayList<String> getWaterNeighbors(){
        return _waterNeighbors;
    }

    /**
     * getInfected() gets the ArrayList of infected people in this Region
     * @return _infected
     */
    public ArrayList<Integer> getInfected(){
        return _infected;
    }

    /**
     * getKilled() gets the ArrayList of dead people in this Region
     * @return _dead;
     */
    public ArrayList<Integer> getKilled(){
        return _dead;
    }
    
    /**
     * getCured() gets the ArrayList of cured people in this Region
     * @return _cured;
     **/
    public ArrayList<Integer> getCured(){
      return _cured;
    }

    /**
     * getAlive() gets the number of healthy people in this Region
     * @return _healthy
     */
    public int getHealthy(){
        return _healthy;
    }

    /**
     * getName() gets the unique String name for this Region
     * @return _name
     */
    public String getName(){
        return _name;
    }


    //IMPORTANT PLEASE READ
    //The following methods assume that no two Regions will share the same String name


    /**
     * toString() returns a String with the name and population counts for this Region
     * @return
     */
    @Override
    public String toString(){
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
    public boolean equals(Object o){
        if(this.getClass() != o.getClass())
            return false;
        else{
            Region r = (Region) o;
            return _name.equals(r.getName());
        }
    }

    /**
     * hashCode() returns the hashCode for this Region's String name
     * @return _name.hashCode()
     */
    @Override
    public int hashCode(){
        return _name.hashCode();
    }
}
