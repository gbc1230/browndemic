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
    private double _wealth, _wet, _dry, _heat, _cold, _med;

    public NaturalDisaster(double wealth, double wet, double dry, double heat,
            double cold, double med){
        _wealth = wealth;
        _wet = wet;
        _dry = dry;
        _heat = heat;
        _cold = cold;
        _wet = wet;
        _med = med;
    }

    public double getWealthFactor(){return _wealth;}
    public double getWetChange(){return _wet;}
    public double getDryChange(){return _dry;}
    public double getHeatChange(){return _heat;}
    public double getColdChange(){return _cold;}
    public double getMedChange(){return _med;}

    public static ArrayList<NaturalDisaster> buildDisasters(){
        ArrayList<NaturalDisaster> nattyD = new ArrayList<NaturalDisaster>();
        //TODO build Nat'l disasters for a region
        return nattyD;
    }
}
