/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package network;
import java.io.*;
import java.net.*;

/**
 *
 * @author gcarling
 */
public class GameClient implements Runnable{

    private Socket _socket;
    private Thread _thread;
    private BufferedReader _console;
    private DataOutputStream _streamOut;
    private GameClientThread _client;

    public GameClient(String serverName, int serverPort) throws Exception{
        System.out.println("Connecting...");
        try{
            _socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + _socket);
            _console = new BufferedReader(new InputStreamReader(System.in));
            _streamOut = new DataOutputStream(_socket.getOutputStream());
            _client = new GameClientThread(this, _socket);
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

    public void run(){
        while(_thread != null){
            try{
                _streamOut.writeUTF(_console.readLine());
            }
            catch(IOException e){
                System.out.println("ERROR");
                stop();
            }
        }
    }

    public void handle(String msg){
        System.out.println(msg);
    }

    public void stop(){
        try{
            _console.close();
            _streamOut.close();
            _socket.close();
            _thread = null;
            _client.close();
            //_client.stop();
        }
        catch(Exception e){
            System.out.println("Error closing...");
        }
    }


    public static void main(String [] args) throws Exception{
        GameClient client = new GameClient(null, Integer.parseInt(args[0]));
    }
}