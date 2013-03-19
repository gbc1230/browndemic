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
public class GameClientThread extends Thread{

    //the socket this thread runs on
    private Socket _socket;
    //the client this thread is running for
    private GameClient _client;
    //the input to this thread
    private DataInputStream _input;

    //constructor
    public GameClientThread(GameClient client, Socket socket) throws IOException{
        _client = client;
        System.out.println(socket);
        _socket = socket;
        _input = new DataInputStream(_socket.getInputStream());
        start();
    }

    /**
     * Close down this thread
     * @throws java.io.IOException
     */
    public void close() throws IOException{
        _input.close();
    }

    @Override
    public void run(){
        while (true){
            try{
                //System.out.println("aww shit");
                String input = _input.readUTF();
                //System.out.println("got " + input);
                _client.handle(input);
                //System.out.println("sent " + input);
            }
            catch(IOException e){
                _client.stop();
            }
        }
    }

}
