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
    public GameClient(String host, int port, ClientWorld w) throws IOException{
        try{
            _socket = new Socket(host, port);
            _output = new ObjectOutputStream(_socket.getOutputStream());
            _client = new GameClientThread(this, _socket);
            _world = w;
            _thread = new Thread(this);
            String name = _world.getName();
            String IP = InetAddress.getLocalHost().getHostAddress();
            LobbyMember lm = new LobbyMember(name, IP);
            _output.writeObject(lm);
            _output.flush();
            _thread.start();
        }
        catch(UnknownHostException e){
            System.out.println("Host Unknown: " + e.getMessage());
        }
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
                System.out.println("ERROR");
                stop();
            }
        }
    }

    /**
     * Handle and print an incoming message
     * @param msg The message
     */
    public void handle(GameData msg){
        String id = msg.getID();
        if (id.equals("W")){
            WorldOutput wo = (WorldOutput)msg;
            _world.setWorld(wo.getWorld());
        }
        else if (id.equals("M")){
            ChatMessage m = (ChatMessage)msg;
            _world.acceptMessage(m.getMessage());
        }
        else if (id.equals("DC")){
            System.out.println("got the d");
        }
        else if (id.equals("LS")){
            LobbySender ls = (LobbySender)msg;
            List<LobbyMember> lobby = ls.getLobby();
            _world.setLobby(lobby);
        }
    }

    /**
     * Close down this client
     */
    public void stop(){
        try{
            _output.close();
            _socket.close();
            _thread = null;
            _client.close();
        }
        catch(Exception e){
            //System.out.println("Error closing...");
        }
    }


    /*public static void main(String [] args) throws Exception{
        InetAddress local = InetAddress.getByName(args[0]);
        GameClient client = new GameClient(new EarthSP(), local);
    }*/
}