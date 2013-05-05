/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.network;

import edu.brown.cs32.browndemic.world.ServerWorld;
import java.util.List;

/**
 *
 * @author Graham
 */
public class InfoSender extends Thread{
    
    //the clients I will send information to
    private List<GameServerThread> _clients;
    //the world I'm working off of
    private ServerWorld _world;
    //whether the game has started and I should send worlds or just info about
    //the lobby, whether the game is over
    private boolean _gameStarted, _gameOver;
    private GameServer _server;
    
    public InfoSender(List<GameServerThread> clients, ServerWorld w, GameServer s){
        _clients = clients;
        _world = w;
        _gameStarted = false;
        _server = s;
    }
    
    @Override
    public void run(){
        while (!_gameStarted){
            GameData data = _world.getNextData();
            if (data == null)
                continue;
            if (data.getID().equals("LS")){
                LobbySender out = (LobbySender)data;
                _server.remove(out.getRemove());
                for (GameServerThread thread : _clients){
                    thread.sendMessage(out);
                }
            }
            else if (data.getID().equals("H")){
            	for (GameServerThread thread : _clients){
            		thread.sendMessage(data);
            	}
            	_server.stop();
            }
            else{
                CollectDiseases out = (CollectDiseases)data;
                ServerWorld world = out.getWorld();
                startGame();
                _server.stopAccepting();
                for (int i = 0; i < _clients.size(); i++){
                	CollectDiseases temp = new CollectDiseases(i, world);
                	_clients.get(i).sendMessage(temp);
                }
            }
        }
        while (!_gameOver){
            ServerWorld w = _world.getNextCommand();
            if (w == null)
                continue;
            WorldOutput wo = new WorldOutput(w);
            for (GameServerThread thread : _clients){
//                System.out.println("Sending a world...");
                thread.sendMessage(wo);
            }
        }
    }
    
    public void startGame(){
        _gameStarted = true;
    }
    
    public void stopGame(){
        _gameOver = true;
    }
    
}
