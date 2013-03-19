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

    private Socket _socket;
    private GameClient _client;
    private DataInputStream _input;
    
    public GameClientThread(GameClient client, Socket socket) throws IOException{
        _client = client;
        _socket = socket;
        _input = new DataInputStream(socket.getInputStream());
        start();
    }

    public void close() throws IOException{
        _input.close();
    }

    @Override
    public void run(){
        while (true){
            try{
                _client.handle(_input.readUTF());
            }
            catch(IOException e){
                _client.stop();
            }
        }
    }

}
