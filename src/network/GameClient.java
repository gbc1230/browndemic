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
public class GameClient{

    private Socket _socket;
    private DataInputStream _console;
    private DataOutputStream _streamOut;

    public GameClient(String serverName, int serverPort) throws Exception{
        System.out.println("Connecting...");
        try{
            _socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + _socket);
            _console = new DataInputStream(System.in);
            _streamOut = new DataOutputStream(_socket.getOutputStream());
        }
        catch(UnknownHostException e){
            System.out.println("Host Unknown: " + e.getMessage());
        }
        catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        String line = "";
        while (!line.equals("EXIT")){
            try{
                line = _console.readLine();
                _streamOut.writeUTF(line);
                _streamOut.flush();
            }
            catch(IOException e){
                System.out.println("ERORR: " + e.getMessage());
            }
        }
    }

    public void stop(){
        try{
            _console.close();
            _streamOut.close();
            _socket.close();
        }
        catch(Exception e){
            System.out.println("Error closing...");
        }
    }
    /*public void getMessage(String message){
        _output.println(message);
    }

    public void run(){
        String line;
        try{
            while(true){
                line = _input.readLine();
                if (line.equals("EXIT")){
                    _server.removeClient(this);
                    break;
                }
                _server.broadcast(line);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }*/

    public static void Main(String [] args) throws Exception{
        URL url = new URL("6000");
        URLConnection con = url.openConnection();
        con.connect();
    }
}