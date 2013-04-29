/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.region;

/**
 *
 * @author Chet
 * This class is just a container for holding infected populations and identifying them
 */
public class InfWrapper {
    private String _ID;
    private long _inf;
    
    public InfWrapper(String ID, long inf){
        _ID = ID;
        _inf = inf; 
    }
    
    public String getID(){return _ID;}
    public long getInf(){return _inf;}
    
    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        InfWrapper inf = (InfWrapper) o;
        return (inf.getID().equals(_ID) && _inf == inf.getInf());
    }
    
    @Override
    public int hashCode(){
        return (int)Math.pow(_ID.hashCode(), new Long(_inf).hashCode());
    }
}
