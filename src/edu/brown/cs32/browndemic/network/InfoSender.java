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
    
    public InfoSender(List<GameServerThread> clients, ServerWorld w){
        _clients = clients;
        _world = w;
        _gameStarted = false;
    }
    
    @Override
    public void run(){
        while (!_gameStarted){
            List<LobbyMember> lobby = _world.getNextLobby();
            if (lobby == null)
                continue;
            LobbySender ls = new LobbySender(lobby);
            for (GameServerThread thread : _clients){
                thread.sendMessage(ls);
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
