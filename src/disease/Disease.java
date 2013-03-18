/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package disease;

/**
 *
 * @author ckilfoyl
 */
public abstract class Disease {
    
    // TODO ensure in multiplayer that no two Diseases can have the same name
    //a unique String Identifier for this disease
    //emphasis on the unique, code in here relies on the uniqueness of the name
    private String _name;

    /**
     * getName() returns the unique String name for this disease
     * @return _name
     */
    public String getName(){
        return _name;
    }
}
