/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.disease;

/**
 *
 * @author ckilfoyl
 */
public abstract class Disease {
    
    // TODO ensure in multiplayer that no two Diseases can have the same name
    //a unique String Identifier for this disease
    //emphasis on the unique, code in here relies on the uniqueness of the name
    private String _name;

    //A floating pt number reflecting how infectious this disease is
    // TODO decide how to apply infectivity, what scale it should be etc.
    private float _infectivity;

    //A floating pt number reflecting how deadly this disease is
    // TODO decide how to apply mortality, what scale it should be etc.
    private float _mortality;

    /**
     * getName() returns the unique String name for this disease
     * @return _name
     */
    public String getName(){
        return _name;
    }


    //IMPORTANT PLEASE READ
    //The following code relies on the uniqueness of the String name
    //TODO: BEN - the World object will refer to each disease by a unique
    //ID, will be one of 0, 1, 2, etc. depending on how many players.
    //Look at the method headers of World to see how this is used

    /**
     * toString gets a String of the unique name, the infectivity, the mortality
     * @return
     */
    @Override
    public String toString(){
        return _name + ", infectivity: " + _infectivity + ", mortality: " +
                _mortality;
    }

    /**
     * equals(Object) compares this disease to an object for equality
     * @param o the object to compare to
     * @return true if o is a disease of the same subclass and has the same name
     */
    @Override
    public abstract boolean equals(Object o);

    /**
     *
     * @return _name.hashCode() **assumes _name is unique**
     */
    @Override
    public int hashCode(){
        return _name.hashCode();
    }
}
