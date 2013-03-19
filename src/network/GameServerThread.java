/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package network;
import java.net.*;
import java.io.*;

/**
 *
 * @author gcarling
 */
public class GameServerThread extends Thread{

    private GameServer _server;
    private Socket _socket;
    private int _ID;
    private DataInputStream _input;
    private DataOutputStream _output;

    public GameServerThread(GameServer server, Socket socket){
        super();
        _server = server;
        _socket = socket;
        _ID = _socket.getPort();
    }

    public void sendMessage(String msg) throws IOException{
        try{
            _output.writeUTF(msg);
        }
        catch(IOException e){
            System.out.println("ERROR: " + _ID + " couldn't send: " + e.getMessage());
            _server.remove(_ID);
        }
    }

    public int getID(){
        return _ID;
    }

    @Override
    public void run(){
        while (true){
            try{
                _server.handle(_ID, _input.readUTF());
            }
            catch(IOException e){
                System.out.println("ERROR: " + _ID + " can't read.");
                try{
                    _server.remove(_ID);
                }
                catch(IOException e2){
                    System.out.println("Oh come on.");
                }
            }
        }
    }

    public void open() throws IOException{

    }

    public void close() throws IOException{
        _socket.close();
        _input.close();
        _output.close();
    }

}
