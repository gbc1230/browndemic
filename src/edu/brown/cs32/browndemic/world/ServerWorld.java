/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;
import edu.brown.cs32.browndemic.disease.Disease;
import edu.brown.cs32.browndemic.network.CollectDiseases;
import edu.brown.cs32.browndemic.network.GameData;
import edu.brown.cs32.browndemic.network.LobbyMember;
import edu.brown.cs32.browndemic.network.LobbySender;
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
    private Queue<ServerWorld> _outWorlds;
    //the lobby I have
    private List<LobbyMember> _lobby;
    //the queue for sending out lobbies
    private Queue<GameData> _outData;
    
    public ServerWorld(){
        super();
        _outWorlds = new ArrayBlockingQueue<>(10);
        _lobby = new ArrayList<>();
        _outData = new ArrayBlockingQueue<>(10);
    }
    
    @Override
    public void changeDiseasesPicked(int picked){
        if (!_lobby.get(picked).isReady()){
            _lobby.get(picked).changeReady(true);
            _outData.add(new LobbySender(_lobby));
        }
        System.out.println("Ready now? " + allReady());
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
        return _outWorlds.poll();
    }
    
    public GameData getNextLobby(){
        return _outData.poll();
    }
    
    /**
     * For adding onto my command queue
     */
    public void addCommand(){
        System.out.println("Adding...");
        _outWorlds.add(this);
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
        _outData.add(new LobbySender(_lobby));
    }
    
    public void removeLobbyMember(int r){
        _lobby.remove(r);
        _outData.add(new LobbySender(_lobby));
        System.out.println("Lobby ready? " + allReady());
    }
    
    public void collectDiseases(){
        _outData.add(new CollectDiseases());
    }
    
    @Override
    public void addDisease(Disease d){
        System.out.println("Adding a disease: " + d.getName());
        int id = _diseases.size();
        _diseases.add(d);
        d.setID(id);
        if (_diseases.size() == _lobby.size())
            start();
    }
    
    public List<LobbyMember> getLobby(){
        return _lobby;
    }
    
    @Override
    public void run(){
        System.out.println("begin the loop");
        int i = 0;
        while(_numRegionsPicked < _diseases.size()){
            try{
                Thread.sleep(1);
            }
            catch(Exception e){
                
            }
        }
        System.out.println("starting game");
        while (!_gameOver){
            if (!_paused){
                long start = System.currentTimeMillis();
                update();
                if (allCured()){
                    _gameOver = true;
                    break;
                }
                else if (allKilled()){
                    crownWinners();
                    _gameOver = true;
                    break;
                }
                addCommand();
                long end = System.currentTimeMillis();
                long offset = end - start;
                try{
                    Thread.sleep(_waitTime - offset);
                }
                catch(InterruptedException e){
                    System.out.println("Couldn't sleep...");
                }
            }
        }
    }
    
}
