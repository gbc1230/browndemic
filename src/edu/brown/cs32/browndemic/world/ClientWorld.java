/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;
import edu.brown.cs32.browndemic.disease.Bacteria;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import edu.brown.cs32.browndemic.disease.Disease;
import edu.brown.cs32.browndemic.disease.Parasite;
import edu.brown.cs32.browndemic.disease.Virus;
import edu.brown.cs32.browndemic.network.*;
import edu.brown.cs32.browndemic.region.Region;
import edu.brown.cs32.browndemic.region.RegionTransmission;
import edu.brown.cs32.browndemic.ui.interfaces.ChatHandler;
import edu.brown.cs32.browndemic.ui.interfaces.ChatServer;

/**
 *
 * @author Graham
 */
public class ClientWorld implements ChatServer, World{
    
    //the world that this world references
    private ServerWorld _world;
    //the chat handler I'm using
    private ChatHandler _handler;
    //for sending off data
    private Queue<GameData> _output;
    //the lobby
    private List<LobbyMember> _lobby;
    //name of this client
    private String _name;
    //which disease this world has picked currently, for use in lobby
    //the index/diseaseID of this disease
    private int _picked, _diseaseID;
    //is this world ready to start the game
    //has the host disconnected?
    private boolean _isGameReady, _hostDisconnected;
    
    public ClientWorld(String name){
        super();
        _output = new ArrayBlockingQueue<>(10);
        _name = name;
        _isGameReady = false;
        _diseaseID = -1;
        _hostDisconnected = false;
    }
    
    public GameData getNextData(){
        return _output.poll();
    }
    
    public void setWorld(ServerWorld w){
        _world = w;
    }
    
    public void setLobby(List<LobbyMember> lobby){
        System.out.println(_name + ": Got a new lobby: " + lobby);
        _lobby = lobby;
    }
    
    public List<LobbyMember> getLobby(){
        return _lobby;
    }
    
    public void leaveGame(){
        GameLeave lr = new GameLeave();
        _output.add(lr);
    }
    
    @Override
    public void addChatHandler(ChatHandler ch){
        _handler = ch;
    }
    
    @Override
    public void sendMessage(String msg){
        System.out.println("Sending: " + msg);
        msg = _name + ": " + msg;
        ChatMessage cm = new ChatMessage(msg);
        _output.add(cm);
        acceptMessage(msg);
    }
    
    public void acceptMessage(String msg){
        System.out.println(_name + ": Accepting message: " + msg);
        _handler.addMessage(msg);
    }
    
    public void getDisconnect(String name, int id){
    	String dc = name + " has disconnected.";
    	_handler.addMessage(dc);
    	if (id < _diseaseID)
    		_diseaseID--;
    }
    
    public void disconnectHost(){
    	System.out.println(_name + " disconnected because the host left.");
    	_hostDisconnected = true;
    }
    
    public boolean hostDisconnected(){
    	return _hostDisconnected;
    }
    
    @Override
    public boolean allKilled(){
    	return _world.allKilled();
    }
    
    @Override
    public boolean allCured(){
        return _world.allCured();
    }
    
    @Override
    public boolean isGameOver(){
        return _world.isGameOver();
    }
    
    @Override
    public List<Integer> getWinners(){
        return _world.getWinners();
    }
    
    @Override
    public List<String> getNews(){
        return _world.getNews();
    }
    
    @Override
    public List<RegionTransmission> getTransmissions(){
        return _world.getTransmissions();
    }
    
    @Override
    public List<Boolean> getCured(){
        return _world.getCured();
    }
    
    @Override
    public double getCurePercentage(int d){
    	return _world.getCurePercentage(d);
    }
    
    @Override
    public List<Disease> getDiseases(){
        return _world.getDiseases();
    }
    
    @Override
    public Disease getDisease(int d){
    	return _world.getDisease(d);
    }
    
    @Override
    public List<Region> getRegions(){
        return _world.getRegions();
    }
    
    @Override
    public Region getRegion(int id) {
    	return _world.getRegion(id);
    }
    
    @Override
    public Region getRegion(String name){
        return _world.getRegion(name);
    }
    
    @Override
    public long getDead(){
        return _world.getDead();
    }
    
    @Override
    public long getDead(int d){
        return _world.getDead(d);
    }
    
    @Override
    public long getHealthy(){
        return _world.getHealthy();
    }
    
    @Override
    public long getInfected(){
        return _world.getInfected();
    }
    
    @Override
    public long getInfected(int d){
        return _world.getInfected(d);
    }
    
    @Override
    public long getPopulation(){
        return _world.getPopulation();
    }
    
    @Override
    public void introduceDisease(int r, int d){
        DiseaseIntroducer di = new DiseaseIntroducer(r, d);
        _output.add(di);
    }
    
    @Override
    public void changeDiseasesPicked(int c){
        _picked = c;
        DiseasePicked dp = new DiseasePicked(c);
        _output.add(dp);
    }
    
    @Override
    public boolean allDiseasesPicked(){
        return _world.allDiseasesPicked();
    }
    
    @Override
    public void addDisease(Disease d){
        DiseaseAdder da = new DiseaseAdder(d);
        _output.add(da);
    }
    
    public void sendDisease(int id, ServerWorld world){
        if (_picked == 0)
            addDisease(new Bacteria(_name));
        else if (_picked == 1)
            addDisease(new Virus(_name));
        else if (_picked == 2)
            addDisease(new Parasite(_name));
        if (_picked >= 0 && _picked <= 2){
        	_diseaseID = id;
        	_isGameReady = true;
        	setWorld(world);
        	System.out.println("Is game ready is true");
        }
    }
    
    public boolean isGameReady(){
    	return _isGameReady;
    }
    
    public int getDiseaseID(){
    	return _diseaseID;
    }
    
    @Override
    public void addPerk(int dis, int perk, boolean buy){
        System.out.println(_name + " :Adding perk: " + dis + ", " + perk + ", " + buy);
        PerkInput pi = new PerkInput(dis, perk, buy);
        _output.add(pi);
    }
    
    public String getName(){
        return _name;
    }
    
    public void setName(String name){
    	_name = name;
    }
    
    //not used in multiplayer
    @Override
    public void pause(){
    }
    
    @Override
    public void unpause(){
    }
    
    @Override
    public void setSpeed(int t){
    }
}
