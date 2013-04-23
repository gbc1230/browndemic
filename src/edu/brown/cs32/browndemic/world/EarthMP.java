/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import edu.brown.cs32.browndemic.network.WorldOutput;

/**
 *
 * @author Graham
 */
public class EarthMP extends Earth{
    
    //queue for sending commands
    protected Queue<EarthMP> _commands;
    
    public EarthMP(){
        super();
        _commands = new ArrayBlockingQueue<>(10);
    }
    
    @Override
    public World getNextCommand(){
        return _commands.poll();
    }
    
    @Override
    public void removeDisease(int id){
        //_diseases.get(id).die();
    }
    
}
