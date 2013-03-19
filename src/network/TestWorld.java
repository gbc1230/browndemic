/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package network;
import java.io.Serializable;

/**
 *
 * @author gcarling
 */
public class TestWorld implements Serializable{

    private static final long serialVersionUID = 42L;
    private String _data;

    public TestWorld(){
    }

    public TestWorld(String s){
        _data = s;
    }

    public String getData(){
        return _data;
    }

    @Override
    public String toString(){
        return "TestWorld: " + _data;
    }

}