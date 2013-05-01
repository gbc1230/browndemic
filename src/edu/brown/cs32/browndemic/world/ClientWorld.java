/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import edu.brown.cs32.browndemic.disease.Disease;
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
    
    public ClientWorld(){
        super();
        _output = new ArrayBlockingQueue<>(10);
    }
    
    public GameData getNextData(){
        return _output.poll();
    }
    
    public void setWorld(ServerWorld w){
        System.out.println("Setting world to:\n" + w.toString());
        _world = w;
        System.out.println("Set world.");
    }
    
    @Override
    public void addChatHandler(ChatHandler ch){
        _handler = ch;
    }
    
    @Override
    public void sendMessage(String msg){
        System.out.println("Sending: " + msg);
        ChatMessage cm = new ChatMessage(msg);
        _output.add(cm);
    }
    
    public void acceptMessage(String msg){
        System.out.println("Accepting message: " + msg);
        //_handler.addMessage(msg);
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
    public long getHealthy(){
        return _world.getHealthy();
    }
    
    @Override
    public long getInfected(){
        return _world.getInfected();
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
    
    @Override
    public void addPerk(int dis, int perk, boolean buy){
        System.out.println("Adding perk: " + dis + ", " + perk + ", " + buy);
        PerkInput pi = new PerkInput(dis, perk, buy);
        _output.add(pi);
    }
}
