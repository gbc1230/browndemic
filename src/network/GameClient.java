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

    //the socket this client is at
    private Socket _socket;
    //the thread this client will run on
    private Thread _thread;
    //the reader for this file
    private BufferedReader _input;
    //writer for this file
    private DataOutputStream _output;
    //Thread for this file's communications
    private GameClientThread _client;

    //constructor
    public GameClient(InetAddress serverName, int serverPort) throws Exception{
        System.out.println("Connecting...");
        try{
            _socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + _socket);
            _input = new BufferedReader(new InputStreamReader(System.in));
            _output = new DataOutputStream(_socket.getOutputStream());
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

    @Override
    public void run(){
        System.out.println("running");
        while(_thread != null){
            try{
                String out = _input.readLine();
                //System.out.println("got " + out);
                _output.writeUTF(out);
                _output.flush();
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
    public void handle(String msg){
        System.out.println(msg);
    }

    /**
     * Close down this client
     */
    public void stop(){
        try{
            _input.close();
            _output.close();
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
        InetAddress local = InetAddress.getByName(args[0]);
        GameClient client = new GameClient(local, Integer.parseInt(args[1]));
    }
}