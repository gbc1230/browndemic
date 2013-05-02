/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;
import edu.brown.cs32.browndemic.network.LobbyMember;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Graham
 */
public class ServerWorld extends MainWorld{
    
    //for sending copies of this world out
    private Queue<ServerWorld> _output;
    //the lobby I have
    private List<LobbyMember> _lobby;
    //the queue for sending out lobbies
    private Queue<List<LobbyMember>> _lobbies;
    
    public ServerWorld(){
        super();
        _output = new ArrayBlockingQueue<>(10);
        _lobby = new ArrayList<>();
        _lobbies = new ArrayBlockingQueue<>(10);
    }
    
    @Override
    public void changeDiseasesPicked(int picked){
        if (!_lobby.get(picked).isReady()){
            _lobby.get(picked).changeReady(true);
            _lobbies.add(_lobby);
        }
    }
    
    public boolean allReady(){
        boolean ready = true;
        for (LobbyMember lm : _lobby){
            if (!lm.isReady()){
                ready = false;
                break;
            }
        }
        return ready;
    }

    /**
     * Gets the next world to send out: used only for MP maps
     * @return The next world to send out
     */
    public ServerWorld getNextCommand(){
        return _output.poll();
    }
    
    public List<LobbyMember> getNextLobby(){
        return _lobbies.poll();
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
    
    public void addLobbyMember(LobbyMember lm){
        _lobby.add(lm);
        _lobbies.add(_lobby);
    }
    
    public void removeLobbyMember(int r){
        _lobby.remove(r);
        _lobbies.add(_lobby);
    }
    
    public List<LobbyMember> getLobby(){
        return _lobby;
    }
    
}
