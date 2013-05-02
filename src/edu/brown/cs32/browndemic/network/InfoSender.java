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
            GameData data = _world.getNextLobby();
            if (data == null)
                continue;
            GameData out;
            if (data.getID().equals("LS")){
                out = (LobbySender)data;
            }
            else{
                out = (CollectDiseases)data;
                startGame();
                _server.stopAccepting();
                System.out.println("Sent off the CD call");
            }
            for (GameServerThread thread : _clients){
                thread.sendMessage(out);
            }
        }
        while (!_gameOver){
            ServerWorld w = _world.getNextCommand();
            if (w == null)
                continue;
            WorldOutput wo = new WorldOutput(w);
            for (GameServerThread thread : _clients){
                System.out.println("Sending a world...");
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
