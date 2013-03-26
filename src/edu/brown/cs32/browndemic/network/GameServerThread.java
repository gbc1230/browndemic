/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.network;
import edu.brown.cs32.browndemic.world.World;
import java.net.*;
import java.io.*;

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
        System.out.println(socket);
        _socket = socket;
        _ID = _socket.getPort();
    }

    /**
     * Send a message to the the client
     * @param msg The message to send
     * @throws java.io.IOException
     */
    public void sendMessage(World msg) throws IOException{
        try{
            //System.out.println("SUP!");
            _output.writeObject(msg);
            _output.flush();
            //System.out.println("sent " + msg);
        }
        catch(IOException e){
            System.out.println("ERROR: " + _ID + " couldn't send: " + e.getMessage());
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
                _server.handle(_ID, _input.readObject());
            }
            catch(IOException e){
                System.out.println("ERROR: " + _ID + " can't read.");
                try{
                    _server.remove(_ID);
                    break;
                }
                catch(IOException e2){
                    System.out.println("Oh come on.");
                }
            }
            catch(ClassNotFoundException e){
                try{
                    _server.remove(_ID);
                    break;
                }
                catch(IOException e2){
                    System.out.println("Oh come on.");
                }
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
    public void close() throws IOException{
        _socket.close();
        _input.close();
        _output.close();
    }

}
