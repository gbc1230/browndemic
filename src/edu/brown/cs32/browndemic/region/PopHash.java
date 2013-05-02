/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.region;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author Chet
 * A custom hashmap to keep track of infected, cured, healthy populations
 * Keys are 0/1/2 strings where each digits corresponds to a disease
 * 0----uninfected
 * 1----infected
 * 2----cured
 */
public class PopHash implements Serializable{

    private HashMap<String, InfWrapper> _hash;
    private int _len;

    public PopHash(int len) {
        _hash = new HashMap<String, InfWrapper>();
        _len = len;
        ArrayList<String> zeroOnes = buildIDStrings("", len);
        for (String str : zeroOnes) {
            _hash.put(str, new InfWrapper(str, 0L));
        }
    }

    public ArrayList<String> buildIDStrings(String str, int len) {
        ArrayList<String> strs = new ArrayList<String>();
        if (str.length() == len) {
            strs.add(str);
        } else {
            strs.addAll(buildIDStrings("0" + str, len));
            strs.addAll(buildIDStrings("1" + str, len));
            strs.addAll(buildIDStrings("2" + str, len));
        }
        return strs;
    }

    /**
     * Gets all populations of type type correesponding to the disease at index ind
     * @param ind the index of the disease
     * @param type the type of population to get (0-uninfected, 1-infected, 2-cured)
     * @return ArrayList of requested InfWrappers
     */
    public ArrayList<InfWrapper> getAllOfType(int ind, int type) {
        ArrayList<InfWrapper> list = new ArrayList<InfWrapper>();
        ArrayList<String> strs = buildIDStrings("", _len - 1);
        for (String str : strs) {
            String zeroOne = str.substring(0, ind) + Integer.toString(type) + str.substring(ind);
            list.add(_hash.get(zeroOne));
        }
        return list;
    }

    public InfWrapper get(String id) {
        return _hash.get(id);
    }

    public void put(InfWrapper inf) {
        _hash.put(inf.getID(), inf);
    }

    public InfWrapper getZero() {
        String id = "";
        for (int i = 0; i < _len; i++) {
            id += "0";
        }
        return _hash.get(id);
    }

    public void addZero(long num) {
        String id = "";
        for (int i = 0; i < _len; i++) {
            id += "0";
        }
        _hash.put(id, new InfWrapper(id, num));
    }
}