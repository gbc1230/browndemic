/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.region;

import java.util.ArrayList;
/**
 *
 * @author ckilfoyl
 */
public class NaturalDisaster {
    private String _name;
    private double _wealth, _wet, _dry, _heat, _cold;

    public NaturalDisaster(String name, double wealth, double wet, double dry, double heat,
            double cold){
        _name = name;
        _wealth = wealth;
        _wet = wet;
        _dry = dry;
        _heat = heat;
        _cold = cold;
        _wet = wet;
    }

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
