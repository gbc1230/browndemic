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
import edu.brown.cs32.browndemic.network.LobbyKick;
import edu.brown.cs32.browndemic.network.LobbyMember;
import edu.brown.cs32.browndemic.network.LobbySender;
import edu.brown.cs32.browndemic.region.Region;

/**
 *
 * @author Graham
 */
public class ServerWorld extends MainWorld{
	private static final long serialVersionUID = -5771180367626627782L;
	
	//for sending copies of this world out
    transient private Queue<ServerWorld> _outWorlds;
    //the lobby I have
    private List<LobbyMember> _lobby;
    //the queue for sending out lobbies
    transient private Queue<GameData> _outData;
    //which users have picked their starting region
    transient private List<Boolean> _regionsPicked;
    
    /**
     * Basic constructor
     */
    public ServerWorld(){
        super();
        _outWorlds = new ConcurrentLinkedQueue<>();
        _lobby = new ArrayList<>();
        _outData = new ConcurrentLinkedQueue<>();
        _regionsPicked = new ArrayList<>();
        _waitTime = _SPEED2;
    }
    
    /**
     * One of the lobby members changes which disease they picked
     */
    @Override
    public void changeDiseasesPicked(int picked){
        if (!_lobby.get(picked).isReady()){
            _lobby.get(picked).changeReady(true);
            _outData.add(new LobbySender(_lobby));
        }
    }
    
    /**
     * Tells me if everyone in the lobby has picked their disease
     * @return boolean, ready or not
     */
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
    
    /**
     * Gives off data to send to clients
     * @return GameData
     */
    public GameData getNextData(){
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
        if (_started && !_gameOver){
            _diseases.get(id).die();
//            _diseases.remove(id);
            _regionsPicked.remove(id);
        }
    }
    
    public void addLobbyMember(LobbyMember lm){
        _lobby.add(lm);
        _outData.add(new LobbySender(_lobby));
    }
    
    public void removePlayer(int r){
    	if (_started){
    		removeDisease(r);
    	}
    	else{
	        _lobby.remove(r);
	        _outData.add(new LobbySender(_lobby, r));
    	}
    }
    
    public void updateName(String name, int client){
    	LobbyMember lm = _lobby.get(client);
    	lm.setName(name);
    	_outData.add(new LobbySender(_lobby));
    }
    
    public void collectDiseases(){
    	for (int i = 0; i < _lobby.size(); i++){
    		_diseases.add(null);
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
    
    /**
     * Used for kicking someone out of the lobby
     * @param client The ID of the client to kick
     */
    public void kickLobbyMember(int client){
    	LobbyKick lk = new LobbyKick(client);
    	_outData.add(lk);
    	removePlayer(client);
    }
    
    @Override
    public void introduceDisease(int d, int r){
        _regions.get(r).introduceDisease(_diseases.get(d));
        _regionsPicked.set(d, true);
    }
    
    public void start(){
    	setupDiseases();
    	for (int i = 0; i < _diseases.size(); i++){
    		_regionsPicked.add(false);
    	}
        for (Region r : _regions){
            r.setNumDiseases(_diseases.size());
            _cureTotal += r.getWealth() * r.getPopulation() * _MINCURETICKS;
        }
        _paused = false;
        _started = true;
        addCommand();
        new Thread(this, "ServerWorld").start();
    }
    
    public boolean allRegionsPicked(){
    	for (boolean b : _regionsPicked){
    		if (!b)
    			return false;
    	}
    	return true;
    }
    
    @Override
    public void run(){
        while(!allRegionsPicked()){
            try{
                Thread.sleep(1);
            }
            catch(Exception e){
                continue;
            }
        }
        while (!_gameOver){
            long start = System.currentTimeMillis();
            update();
            if (allKilled() || allCured()){
                crownWinners();
                _gameOver = true;
                break;
            }
            addCommand();
            long end = System.currentTimeMillis();
            long offset = end - start;
            try{
                if (offset < _waitTime)
                    Thread.sleep(_waitTime - offset);
            }
            catch(InterruptedException e){
            	continue;
            }
        }
        addCommand();
    }
    
    @Override
    public void endGame(boolean b){
    	//not used
    }
    
    public void endGame(int winner){
    	for (int i = 0; i < _diseases.size(); i++){
    		Disease d = _diseases.get(i);
    		if (d.getName().equals("Chet")){
    				winner = i;
    				break;
    		}
    	}
    	if (winner > -1)
    		_winners.add(winner);
    	_gameOver = true;
    	addCommand();
    }

}
