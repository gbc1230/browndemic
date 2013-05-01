/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Graham
 */
public class ServerWorld extends MainWorld{
    
    //for sending copies of this world out
    private Queue<ServerWorld> _output;
    
    public ServerWorld(){
        super();
        _output = new ArrayBlockingQueue<>(10);
    }
    
    @Override
    public void changeDiseasesPicked(int change){
        System.out.println("Changing DP by " + change);
        _numDiseasesPicked += change;
    }
    
    public void updatePickedStatus(int total){
        if (_numDiseasesPicked == total)
            _allDiseasesPicked = true;
        else 
            _allDiseasesPicked = false;
        if (_numDiseasesPicked > total)
            System.out.println("You have more diseases than players!");
        System.out.println("All diseases picked? " + _allDiseasesPicked);
    }
    
    /**
     * Gets the next world to send out: used only for MP maps
     * @return The next world to send out
     */
    public ServerWorld getNextCommand(){
        return _output.poll();
    }
    
    /**
     * For adding onto my command queue
     */
    public void addCommand(){
        System.out.println("Adding...");
        _output.add(this);
        System.out.println("Added.");
    }
    
        
    /**
     * Stop a disease that has left the game : used only for MP maps
     * @param id The id of the disease to remove
     */
    public void removeDisease(int id){
        if (_started && !_gameOver)
            _diseases.get(id).die();
    }
    
}
