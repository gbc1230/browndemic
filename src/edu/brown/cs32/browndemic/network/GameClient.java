/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.network;
import edu.brown.cs32.browndemic.world.*;
import java.io.*;
import java.net.*;

/**
 *
 * @author gcarling
 */
public class GameClient implements Runnable{

    private final int PORT = 6000;
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
    public GameClient(String host, ClientWorld w) throws Exception{
        try{
            _socket = new Socket(host, PORT);
            _output = new ObjectOutputStream(_socket.getOutputStream());
            _client = new GameClientThread(this, _socket);
            _world = w;
            _thread = new Thread(this);
            _thread.start();
        }
        catch(UnknownHostException e){
            System.out.println("Host Unknown: " + e.getMessage());
        }
        catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    //run method for runnable: runs immediately, loops reading input and sending
    //it out
    @Override
    public void run(){
        while(_thread != null){
            try{
                //GameData temp = _gui.getNextCommand();
                if (temp != null){
                    _output.writeObject(temp);
                    _output.flush();
                }
                //System.out.println("sent " + out);
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
            //world deal with this shizit
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