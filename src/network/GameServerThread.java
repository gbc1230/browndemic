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

    public void sendMessage(TestWorld msg) throws IOException{
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

    public void open() throws IOException{
        _input = new ObjectInputStream(new BufferedInputStream(_socket.getInputStream()));
        _output = new ObjectOutputStream(new BufferedOutputStream(_socket.getOutputStream()));
    }

    public void close() throws IOException{
        _socket.close();
        _input.close();
        _output.close();
    }

}
