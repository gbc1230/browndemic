/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.region;

import java.io.Serializable;

import edu.brown.cs32.browndemic.world.Airport;

/**
 *
 * @author ckilfoyl
 * This class represents a transmission of a disease from one region to another
 * by plane
 */
public class AirTransmission implements Serializable{
	private static final long serialVersionUID = -6843584036923113594L;
	
	private Airport _start, _end;
    private int _disease;

    public AirTransmission(Airport start, Airport end, int disease){
        _start = start;
        _end = end;
        _disease = disease;
    }

    public Airport getStart(){return _start;}
    public Airport getEnd(){return _end;}
    public int getDisesase(){return _disease;}
}