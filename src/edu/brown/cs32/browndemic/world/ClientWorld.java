/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.world;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import edu.brown.cs32.browndemic.disease.Disease;
import edu.brown.cs32.browndemic.region.Region;
import edu.brown.cs32.browndemic.network.*;
import edu.brown.cs32.browndemic.ui.interfaces.*;

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
    private Queue<GameData> _data;
    
    public ClientWorld(){
        super();
        _data = new ArrayBlockingQueue<>(10);
    }
    
    public GameData getNextData(){
        return _data.poll();
    }
    
    public void setWorld(ServerWorld w){
        _world = w;
    }
    
    @Override
    public void addChatHandler(ChatHandler ch){
        _handler = ch;
    }
    
    @Override
    public void sendMessage(String msg){
        ChatMessage cm = new ChatMessage(msg);
        _data.add(cm);
    }
    
    public void acceptMessage(String msg){
        _handler.addMessage(msg);
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
    public List<Boolean> getCured(){
        return _world.getCured();
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
    public void addPerk(int dis, int perk, boolean buy){
        PerkInput pi = new PerkInput(dis, perk, buy);
        _data.add(pi);
    }
}
