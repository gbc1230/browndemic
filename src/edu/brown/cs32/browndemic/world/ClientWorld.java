/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import edu.brown.cs32.browndemic.network.WorldOutput;
//import edu.brown.cs32.browndemic.ui

/**
 *
 * @author Graham
 */
public class ClientWorld implements World{
    
    //the world that this world references
    private MainWorld _world;
    
    public ClientWorld(){
        super();
    }
    
    public void setWorld(MainWorld w){
        _world = w;
    }
    
    @Override
    public MainWorld getNextCommand(){
       // return _commands.poll();
    }
    
    @Override
    public void removeDisease(int id){
        //_diseases.get(id).die();
    }
    
}
