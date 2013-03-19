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

    //one thread per client
    private List<GameServerThread> _clients;
    //the socket this server runs on
    private ServerSocket _server;
    //the thread this server runs on
    private Thread _thread;
    //whether or not this thread is running
    private boolean _threadOn;

    // constructor
    public GameServer(int port){
        try{
            System.out.println("Binding to port" + port);
            _server = new ServerSocket(port);
            _thread = new Thread(this);
            _clients = new ArrayList<GameServerThread>();
            _threadOn = true;
            _thread.start();
        }
        catch(IOException e){
            System.out.println("Cannot bind to port " + port);
        }
    }

    //run method: catches new threads as they come in
    @Override
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

    /**
     * Given a client's ID, find its location in the list
     * @param ID The ID to search
     * @return The client's position in the list
     */
    public int findClient(int ID){
        for (int i = 0; i < _clients.size(); i++){
            if (_clients.get(i).getID() == ID)
                return i;
        }
        return -1;
    }

    /**
     * Given a message from a client, handle it
     * @param ID The ID of the client
     * @param o The object being sent
     * @throws java.io.IOException
     */
    public synchronized void handle(int ID, Object o) throws IOException, ClassNotFoundException{
        TestWorld test = (TestWorld)o;
        //System.out.println("got here");
        for (int i = 0; i < _clients.size(); i++){
            if (_clients.get(i).getID() != ID)
                _clients.get(i).sendMessage(test);
        }
    }

    /**
     * Remove a client from our list of clients
     * @param ID The ID of the client to remove
     * @throws java.io.IOException
     */
    public synchronized void remove(int ID) throws IOException{
        int pos = findClient(ID);
        if (pos != -1){
            GameServerThread toKill = _clients.get(pos);
            _clients.remove(toKill);
            toKill.close();
            //toKill.stop();
        }
    }

    /**
     * Add a new thread to our list of threads
     * @param socket The socket of the thread
     * @throws java.io.IOException
     */
    private void addThread(Socket socket) throws IOException{
        GameServerThread temp = new GameServerThread(this, socket);
        temp.open();
        temp.start();
        System.out.println(temp);
        _clients.add(temp);
    }

    public static void main(String [] args) throws Exception{
        GameServer server = new GameServer(Integer.parseInt(args[0]));
    }

}
