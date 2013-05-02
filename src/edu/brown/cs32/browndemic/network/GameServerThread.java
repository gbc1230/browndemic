/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.network;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author gcarling
 */
public class GameServerThread extends Thread{

    //the 
    private GameServer _server;
    private Socket _socket;
    private int _ID;
    private ObjectInputStream _input;
    private ObjectOutputStream _output;

    public GameServerThread(GameServer server, Socket socket){
        super();
        _server = server;
        _socket = socket;
        _ID = _socket.getPort();
    }

    /**
     * Send a message to the the client
     * @param msg The message to send
     */
    public void sendMessage(GameData msg){
        try{
            _output.reset();
            _output.writeObject(msg);
            _output.flush();
        }
        catch(IOException e){
            _server.remove(_ID);
        }
    }

    public int getID(){
        return _ID;
    }

    //this runs: waits for a new message to come in, and has the server handle it
    @Override
    public void run(){
        while (true){
            try{
                _server.handle(_ID, (GameData)_input.readObject());
            }
            catch(IOException e){
            	int id = _server.findClient(_ID);
                System.out.println("Player " + id + " couldn't be read from.");
                e.printStackTrace();
                _server.remove(_ID);
                break;
            }
            catch(ClassNotFoundException e){
                _server.remove(_ID);
                break;
            }
        }
    }

    /**
     * Opens the streams
     * @throws java.io.IOException
     */
    
    public void open() throws IOException{
        _input = new ObjectInputStream(new BufferedInputStream(_socket.getInputStream()));
        _output = new ObjectOutputStream(new BufferedOutputStream(_socket.getOutputStream()));
    }

    /**
     * Closes the streams
     * @throws java.io.IOException
     */
    public void close(){
        try{
            _socket.close();
            _input.close();
            _output.close();
        }
        catch(IOException e){
            
        }
    }

}
