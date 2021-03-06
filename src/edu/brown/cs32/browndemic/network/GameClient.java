/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.network;
import edu.brown.cs32.browndemic.world.*;
import java.io.*;
import java.net.*;
import java.util.List;

/**
 *
 * @author gcarling
 */
public class GameClient implements Runnable{

    //the socket this client is at
    private Socket _socket;
    //the thread this client will run on
    private Thread _thread;
    //writer for this file
    private ObjectOutputStream _output;
    //Thread for this file's communications
    private GameClientThread _client;
    //the world that this client references
    private ClientWorld _world;

    //constructor
    public GameClient(String host, int port, ClientWorld w) throws IOException {
        _socket = new Socket(host, port);
        _output = new ObjectOutputStream(_socket.getOutputStream());
        _output.flush();
        _client = new GameClientThread(this, _socket);
        _world = w;
        _thread = new Thread(this, "Client");
        String name = _world.getName();
        String IP = InetAddress.getLocalHost().getHostAddress();
        LobbyMember lm = new LobbyMember(name, IP);
        _output.writeObject(lm);
        _output.flush();
        _thread.start();
    }

    //run method for runnable: runs immediately, loops reading input and sending
    //it out
    @Override
    public void run(){
        while(_thread != null){
            try{
                GameData temp = _world.getNextData();
                if (temp != null){
                    _output.writeObject(temp);
                    _output.flush();
                }
            }
            catch(IOException e){
                stop();
                break;
            }
        }
    }

    /**
     * Handle and print an incoming message
     * @param msg The message
     */
    public void handle(GameData msg){
        String id = msg.getID();
        //received a new world
        if (id.equals("W")){
            WorldOutput wo = (WorldOutput)msg;
            _world.setWorld(wo);
        }
        //new chat message
        else if (id.equals("M")){
            ChatMessage m = (ChatMessage)msg;
            _world.acceptMessage(m.getMessage());
        }
        //another player has disconnected
        else if (id.equals("DC")){
        	DCMessage dc = (DCMessage)msg;
        	_world.getDisconnect(dc.getName(), dc.getPlayerID());
        }
        //new lobby update
        else if (id.equals("LS")){
            LobbySender ls = (LobbySender)msg;
            List<LobbyMember> lobby = ls.getLobby();
            _world.setLobby(lobby);
        }
        //message from server to add diseases
        else if (id.equals("CD")){
        	CollectDiseases cd = (CollectDiseases)msg;
            _world.sendDisease(cd.getDiseaseID(), cd.getWorld());
        }
        //host disconnect
        else if (id.equals("H")){
        	stop();
        }
        //got kicked
        else if (id.equals("LK")){
        	_world.getKicked();
//        	stop();
        }
    }

    /**
     * Close down this client
     */
    public void stop(){
        _world.disconnectHost();
        try{
            _output.close();
            _socket.close();
            _thread = null;
            _client.close();
        }
        catch(Exception e){
        	
        }
    }

}