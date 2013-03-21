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
    private ArrayList<String> _diseases;

    //Total Region Population
    private int _population;

    //Healthy population count
    private int _healthy;

    //ArrayList of infected, order corresponds to the diseases in _disease
    private ArrayList<Integer> _infected;

    //ArrayList of dead, order corresponds to the diseases in _disease
    private ArrayList<Integer> _dead;

    //Unique Region name
    //emphasis on the unique, some code in here runs on that assumption (hash, equals, etc.)
    private String _name;

    //number of seaports and airports open in this Region
    private int _sea;
    private int _air;
    

    /**
     * constructs a new Region with the given info
     * @param name The unique String name
     * @param population the initial population count
     * @param neighbors the names of all bordering Regions
     * @param sea if this Region has open seaports
     * @param air if this Region has open airports
     */
    public Region(String name, int population, Collection<String> neighbors,
            int sea, int air){
        _name = name;
        _population = population;
        _healthy = population;
        _infected = new ArrayList<Integer>();
        _dead = new ArrayList<Integer>();
        _neighbors = new ArrayList<String>(neighbors.size());
        _neighbors.addAll(neighbors);
        _sea = sea;
        _air = air;

    }

    public void infect(String disease, int number){
        if (_diseases.contains(disease)) {
            for (int i = 0; i < _diseases.size(); i++) {
                if (_diseases.get(i).equals(disease)) {
                    if(_healthy < number){
                        _infected.set(i, _infected.get(i) + _healthy);
                        _healthy = 0;
                    }else{
                    _infected.set(i, _infected.get(i) + number);
                    _healthy = _healthy - number;
                    }
                    break;
                }
            }
        }else{
            _diseases.add(disease);
            if(_healthy < number){
                _infected.add(_healthy);
                _healthy = 0;
            }else{
                _infected.add(number);
                _healthy = _healthy - number;
            }
        }
    }

    public void kill(String disease, int number){
        if (_diseases.contains(disease)) {
            for (int i = 0; i < _diseases.size(); i++) {
                if (_diseases.get(i).equals(disease)) {
                    if(_infected.get(i) < number){
                        _dead.set(i, _dead.get(i) + _infected.get(i));
                        _infected.set(i, 0);
                    }else{
                    _dead.set(i, _dead.get(i) + number);
                    _infected.set(i, _infected.get(i) - number);
                    }
                    break;
                }
            }
        }else{
            throw new Error ("ERROR: Cannot kill " + number + " people because \'"
                    + disease + "\' has not infected Region " + getName());
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
     * getNeighbors() gets the names of all bordering Regions
     * @return _neighbors
     */
    public ArrayList<String> getNeighbors(){
        return _neighbors;
    }

    /**
     * getInfected() gets the number of infected people in this Region
     * @return _infected
     */
    public int getInfected(){
        int count = 0;
        for(int i : _infected)
            count = count + i;
        return count;
    }

    /**
     * getInfected(String) gets the infected count for a particular disease
     * @param disease the String disease name to find
     * @return the number infected by this disease
     */
    public int getInfected(String disease){
        for(int i = 0; i < _diseases.size(); i++){
            if(_diseases.get(i).equals(disease))
                return _infected.get(i);
        }
        return 0;
    }

    /**
     * getDead() gets the number of dead people in this Region
     * @return _dead;
     */
    public int getDead(){
        int count = 0;
        for(int i : _dead)
            count = count + i;
        return count;
    }

    /**
     * getAlive() gets the number of healthy people in String str =this Region
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
