/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import edu.brown.cs32.browndemic.disease.Disease;
import edu.brown.cs32.browndemic.network.CollectDiseases;
import edu.brown.cs32.browndemic.network.GameData;
import edu.brown.cs32.browndemic.network.HostDisconnect;
import edu.brown.cs32.browndemic.network.LobbyMember;
import edu.brown.cs32.browndemic.network.LobbySender;
import edu.brown.cs32.browndemic.region.Region;

/**
 *
 * @author Graham
 */
public class ServerWorld extends MainWorld{
	private static final long serialVersionUID = 1774845179387841713L;

	//for sending copies of this world out
    transient private Queue<ServerWorld> _outWorlds;
    //the lobby I have
    private List<LobbyMember> _lobby;
    //the queue for sending out lobbies
    transient private Queue<GameData> _outData;
    
    public ServerWorld(){
        super();
        _outWorlds = new ConcurrentLinkedQueue<>();
        _lobby = new ArrayList<>();
        _outData = new ConcurrentLinkedQueue<>();
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
        _outWorlds.add(this);
    }
    
    //stops the server from running
    public void killServer(){
    	_outData.add(new HostDisconnect());
    }
    
    public boolean hasStarted(){
    	return _started;
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
        _outData.add(new LobbySender(_lobby, r));
        System.out.println("Lobby ready? " + allReady());
    }
    
    public void collectDiseases(){
    	for (int i = 0; i < _lobby.size(); i++){
    		_diseases.add(null);
    	}
    	for (Region r : _regions){
            _population += r.getPopulation();
    	}
        _outData.add(new CollectDiseases(-1, this));
    }
    
    //special version for server
    public void addDisease(Disease d, int ind){
    	d.setID(ind);
    	_diseases.set(ind, d);
    	if (diseasesFull())
    		start();
    }
    
    @Override
    public void addDisease(Disease d){
        //not used here
    }
    
    private boolean diseasesFull(){
    	for (Disease d : _diseases){
    		if (d == null)
    			return false;
    	}
    	return true;
    }
    
    public List<LobbyMember> getLobby(){
        return _lobby;
    }
    
    @Override
    public void introduceDisease(int d, int r){
        System.out.println("Introducing " + d + " to " + r);
        _regions.get(r).introduceDisease(_diseases.get(d));
        _numRegionsPicked++;
    }
    
    public void start(){
    	setupDiseases();
        for (Region r : _regions){
            r.setNumDiseases(_diseases.size());
            _cureTotal += r.getWealth() * r.getPopulation() * _MINCURETICKS;
        }
        _paused = false;
        _started = true;
        addCommand();
        new Thread(this).start();
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
