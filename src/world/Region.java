/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package world;

import java.util.ArrayList;
import java.util.Collection;

import disease.Disease;


/**
 *
 * @author ckilfoyl
 */
public class Region {
    //ArrayList of all neighboring Regions by String name
    private ArrayList<String> _neighbors;

    //ArrayList of diseases in this Region
    private ArrayList<Disease> _diseases;

    //Healthy population count
    private int _healthy;

    //ArrayList of infected, order corresponds to the diseases in _disease
    private ArrayList<Integer> _infected;

    //ArrayList of dead, order corresponds to the diseases in _disease
    private ArrayList<Integer> _dead;

    //Unique Region name
    //emphasis on the unique, some code in here runs on that assumption (hash, equals, etc.)
    private String _name;

    //booleans for if this Region has open transportation facilities
    private boolean _sea;
    private boolean _air;


    /**
     * constructs a new Region with the given info
     * @param name The unique String name
     * @param population the initial population count
     * @param neighbors the names of all bordering Regions
     * @param sea if this Region has open seaports
     * @param air if this Region has open airports
     */
    public Region(String name, int population, Collection<String> neighbors,
            boolean sea, boolean air){
        _name = name;
        _healthy = population;
        _infected = new ArrayList<Integer>();
        _dead = new ArrayList<Integer>();
        _neighbors = new ArrayList<String>(neighbors.size());
        _neighbors.addAll(neighbors);
        _sea = sea;
        _air = air;

    }

    /**
     * hasAir() gets a boolean indicating if this Region has open airports
     * @return _air;
     */
    public boolean hasAir(){
        return _air;
    }

    /**
     * hasSea() gets a boolean indicating if this Region has open seaports
     * @return _sea
     */
    boolean hasSea(){
        return _sea;
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
            if(_diseases.get(i).getName().equals(disease))
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
