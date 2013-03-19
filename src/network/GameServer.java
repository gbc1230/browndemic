/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package network;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author gcarling
 */
public class GameServer implements Runnable{

    private List<GameServerThread> _clients;
    private ServerSocket _server;
    private Thread _thread;
    private boolean _threadOn;

    public GameServer(int port){
        try{
            System.out.println("Binding to " + port);
            _server = new ServerSocket(port);
            _thread = new Thread(this);
            _threadOn = true;
            _thread.start();
        }
        catch(IOException e){
            System.out.println("Cannot bind to port " + port);
        }
    }

    public void run(){
        while (_threadOn){
            try{
                addThread(_server.accept());
            }
            catch(IOException e){
                System.out.println("Server accept error");
                if (_thread != null){
                    _threadOn = false;
                    _thread = null;
                }
            }
        }
    }

    public int findClient(int ID){
        for (int i = 0; i < _clients.size(); i++){
            if (_clients.get(i).getID() == ID)
                return i;
        }
        return -1;
    }

    public synchronized void handle(int ID, String input) throws IOException{
        if (input.equals("EXIT")){
            _clients.get(findClient(ID)).sendMessage("EXIT");
            remove(ID);
        }
        else{
            for (int i = 0; i < _clients.size(); i++){
                _clients.get(i).sendMessage(input);
            }
        }
    }

    public synchronized void remove(int ID) throws IOException{
        int pos = findClient(ID);
        if (pos != -1){
            GameServerThread toKill = _clients.get(pos);
            _clients.remove(toKill);
            toKill.close();
            //toKill.stop();
        }
    }

    private void addThread(Socket socket) throws IOException{
        GameServerThread temp = new GameServerThread(this, socket);
        temp.open();
        temp.start();
        _clients.add(temp);
    }

    public static void main(String [] args) throws Exception{
        GameServer server = new GameServer(Integer.parseInt(args[0]));
    }

}
