/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.region;

import java.util.ArrayList;
import java.util.Collection;

import edu.brown.cs32.browndemic.disease.Disease;


/**
 *
 * @author ckilfoyl
 */
public class Region {
    //ArrayList of all neighboring Regions by String name
    private ArrayList<String> _neighbors;

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
    private ArrayList<boolean> _hasCure;

    //Unique Region name
    //emphasis on the unique, some code in here runs on that assumption (hash, equals, etc.)
    private String _name;

    //number of seaports and airports open in this Region
    private int _sea;
    private int _air;
    
    //wealth of this Region (reflects infrastructure, productivity, actual wealth, etc.)
    private double _wealth
    

    /**
     * constructs a new Region with the given info
     * @param name The unique String name
     * @param population the initial population count
     * @param neighbors the names of all bordering Regions
     * @param sea if this Region has open seaports
     * @param air if this Region has open airports
     */
    public Region(String name, int population, Collection<String> neighbors,
            int sea, int air, double wealth){
        _name = name;
        _population = population;
        _healthy = population;
        _infected = new ArrayList<Integer>();
        _dead = new ArrayList<Integer>();
        _cured = new ArrayList<Integer>();
        _hasCure = new ArrayList<boolean>();
        _neighbors = new ArrayList<String>(neighbors.size());
        _neighbors.addAll(neighbors);
        _sea = sea;
        _air = air;
        _wealth = wealth;
    }

    /**
     * infect(Disease) updates the infected for the given disease, or adds the disease if it's new
     * @param disease the disease to update for
     **/
    public void infect(Disease disease){
      int index = disease.getID();
        if (_diseases.size() > index && null != _diseases.get(index)) {
          int number = 0;// TODO THIS NEEDS TO BE THE CALC FOR INFECTION
                    if(_healthy < number){
                        _infected.set(index, _infected.get(index) + _healthy);
                        _healthy = 0;
                    }else{
                    _infected.set(index, _infected.get(index) + number);
                    _healthy = _healthy - number;
                    }
                    break;
                }else{
            _diseases.add(index, disease);
            if(_healthy == 0){
                _infected.add(index, _healthy);
            }else{
                _infected.add(index, 1);
                _healthy = _healthy - 1;
            }
            _hasCure.add(index, false);
            _dead.add(index, 0);
            _cured.add(index, 0);
        }
    }

    /**
     * infect(Disease, int) updates the dead for a given disease
     * @param disease the disease to update dead for
     **/
    public void kill(Disease disease){
      int index = disease.getID();
        if (_diseases.size() > index && null != _diseases.get(index)) {
          int number = Math.floor(disease.getLethality() * _infected.get(index));
                    if(_healthy < number){
                        _dead.set(index, _dead.get(index) + _healthy);
                        _healthy = 0;
                    }else{
                    _dead.set(index, _dead.get(index) + number);
                    _healthy = _healthy - number;
                    }
                    break;
                }else{
                  throw new Error("ERROR: Disease " + index + " does not exist region " + _name + ". Cannot kill anyone.");
        }
    }
    
    /**
     * cure(Disease) updates the number of cured for this disease
     * @param d the disease to update cured for
     */
    public void cure(Disease d){
      int index = d.getID();
      if(_disease.size() > index && null != _diseases.get(index) && _hasCure.get(index) == true){
        int number = 0; // TODO NEED TO WRITE CALC FOR HOW MANY TO CURE
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
     * cure(int) sets the boolean cured for the disease at int to true
     * int d the index of the disease to cure
     */
    public void cure(int d){
      int index = d.getID();
      if(_disease.size() > index && null != _diseases.get(d)){
        _hasCude.set(d, true);
      }else{
          throw new Error("ERROR: Cannot cure disease " + d + " because it doesn't exist in Region " + _name + ".");
        }
    }
    
    public void update(){
      for(Disease d : _diseases){
        if(null != d){
          // TODO FILL IN THE UPDATE METHOD
        }
      }
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
    public ArrayList<String> getNeighbors(){
        return _neighbors;
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
    
    public void update(){
        
    }
}
