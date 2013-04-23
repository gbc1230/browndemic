/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.network;
import edu.brown.cs32.browndemic.world.MainWorld;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author gcarling
 */
public class GameServer implements Runnable{

    private final int PORT = 6000;
    //one thread per client
    private List<GameServerThread> _clients;
    //the socket this server runs on
    private ServerSocket _server;
    //the thread this server runs on
    private Thread _thread;
    //whether or not this thread is accepting new clients
    private boolean _accepting;
    //the world i'm operating on
    private MainWorld _world;

    // constructor
    public GameServer(MainWorld w) throws IOException{
        System.out.println("Binding to port" + PORT);
        _server = new ServerSocket(PORT);
        _thread = new Thread(this);
        _clients = new ArrayList<GameServerThread>();
        _accepting = true;
        _world = w;
        _thread.start();
    }

    //run method: catches new threads as they come in
    @Override
    public void run(){
        while (_accepting){
            try{
                addThread(_server.accept());
            }
            catch(IOException e){
                System.out.println("Server accept error");
                if (_thread != null){
                    _accepting = false;
                    _thread = null;
                }
            }
        }
        while (true){
            MainWorld w = _world.getNextCommand();
            if (w == null)
                continue;
            WorldOutput wo = new WorldOutput(w);
            for (GameServerThread thread : _clients){
                thread.sendMessage(wo);
            }
        }
    }
    
    //for once the game starts
    public void stopAccepting(){
        _accepting = false;
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
    public synchronized void handle(int ID, GameData gd) throws IOException, ClassNotFoundException{
        String id = gd.getID();
        if (id.equals("P")){
            PerkInput pi = (PerkInput)gd;
            _world.addPerk(pi.getDiseaseID(), pi.getPerkID(), pi.isBuying());
        }
        else if(id.equals("M")){
            
        }
    }

    /**
     * Remove a client from our list of clients
     * @param ID The ID of the client to remove
     * @throws java.io.IOException
     */
    public synchronized void remove(int ID){
        int pos = findClient(ID);
        if (pos != -1){
            GameServerThread toKill = _clients.get(pos);
            _clients.remove(toKill);
            _world.removeDisease(pos);
            toKill.close();
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

    /*public static void main(String [] args) throws Exception{
        String s = InetAddress.getLocalHost().getHostName();
        System.out.println(s);
        GameServer server = new GameServer();
    }*/

}
