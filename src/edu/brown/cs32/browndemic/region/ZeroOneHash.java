/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.region;
import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author Chet
 */
public class ZeroOneHash {
    private HashMap<String, InfWrapper> _hash;
    private int _len;
    
    public ZeroOneHash(int len){
        _hash = new HashMap<String, InfWrapper>();
        _len = len;
        ArrayList<String> zeroOnes = buildZeroOnes("",len);
        for(String str : zeroOnes){
            _hash.put(str, new InfWrapper(str, 0));
        }
    }
    
    public ArrayList<String> buildZeroOnes(String str, int len){
        ArrayList<String> strs = new ArrayList<String>();
        if(str.length() == len)
            strs.add(str);
        else{
            strs.addAll(buildZeroOnes("0" + str, len));
            strs.addAll(buildZeroOnes("1" + str, len));
        }
        return strs;
    }
    
    public ArrayList<InfWrapper> getAllIndex(int ind){
        ArrayList<InfWrapper> infected = new ArrayList<InfWrapper>();
        ArrayList<String> strs = buildZeroOnes("",_len-1);
        for(String str : strs){
            String zeroOne = str.substring(0, ind) + "1" + str.substring(ind);
            infected.add(_hash.get(zeroOne));
        }
        return infected;
    }
    
    public void putAllInf(ArrayList<InfWrapper> infected){
        for(InfWrapper inf : infected)
            _hash.put(inf.getID(), inf);
    }
    
    public InfWrapper get(String id){
        return _hash.get(id);
    }
    
    public void put(InfWrapper inf){
        _hash.put(inf.getID(), inf);
    }
}