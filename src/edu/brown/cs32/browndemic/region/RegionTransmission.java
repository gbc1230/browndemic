/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.region;

import java.io.Serializable;

/**
 *
 * @author ckilfoyl
 * This class represents a transmission of a disease from one region to another
 * by ship or by plane
 */
public class RegionTransmission implements Serializable{
    private String _start;
    private String _end;
    private int _disease;
    private boolean _air;

    public RegionTransmission(String start, String end, int disease, boolean air){
        _start = start;
        _end = end;
        _disease = disease;
        _air = air;
    }

    public String getStart(){return _start;}
    public String getEnd(){return _end;}
    public int getDisesase(){return _disease;}
    public boolean byAir(){return _air;}
}