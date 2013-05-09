/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.region;

import java.io.Serializable;
/**
 *
 * @author ckilfoyl
 */
public class NaturalDisaster implements Serializable{
	private static final long serialVersionUID = 3893867629247581860L;
	
	private String _name;
    private int _id;
    private double _wealth, _wet, _dry, _heat, _cold;

    public NaturalDisaster(int id, String name, double wealth, double wet, 
    		double dry, double heat, double cold){
    	_id = id;
        _name = name;
        _wealth = wealth;
        _wet = wet;
        _dry = dry;
        _heat = heat;
        _cold = cold;
        _wet = wet;
    }

    public int getID(){return _id;}
    public String getName(){return _name;}
    public double getWealthFactor(){return _wealth;}
    public double getWetChange(){return _wet;}
    public double getDryChange(){return _dry;}
    public double getHeatChange(){return _heat;}
    public double getColdChange(){return _cold;}
    
    @Override
    public String toString(){
    	return _name + "," + _wealth + "," + _wet + "," + _dry + "," + 
    			_heat + "," + _cold;
    }
}
