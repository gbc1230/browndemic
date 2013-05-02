/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.network;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import edu.brown.cs32.browndemic.world.ServerWorld;

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
    //whether or not this thread is accepting new clients
    private boolean _accepting;
    //the world i'm operating on
    private ServerWorld _world;
    //sending out lobbies and then worlds
    private InfoSender _sender;

    // constructor
    public GameServer(ServerWorld w, int port) throws IOException{
        _server = new ServerSocket(port);
        _server.setSoTimeout(5000);
        _thread = new Thread(this);
        _clients = new ArrayList<GameServerThread>();
        _accepting = true;
        _world = w;
        _sender = new InfoSender(_clients, _world, this);
        _sender.start();
        _thread.start();
    }

    //run method: catches new threads as they come in
    @Override
    public void run(){
        while (!_world.isGameOver()){
            try{
                if (_accepting){
                    Socket temp = _server.accept();
                    if (temp != null){
                        addThread(temp);
                        System.out.println("Got new client.");
                    }
                }
            }
            catch(SocketTimeoutException e){
                continue;
            }
            catch(IOException e){
            	System.out.println("IOException at GameServer");
                continue;
            }
        }
    }
    
    //for once the game starts
    public void stopAccepting(){
        System.out.println("done accepting");
        _accepting = false;
        _thread.interrupt();
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
        int client = findClient(ID);
        if (id.equals("P")){
            PerkInput pi = (PerkInput)gd;
            _world.addPerk(pi.getDiseaseID(), pi.getPerkID(), pi.isBuying());
        }
        else if(id.equals("M")){
            for (GameServerThread c : _clients){
                c.sendMessage(gd);
            }
        }
        else if (id.equals("DA")){
            DiseaseAdder da = (DiseaseAdder)gd;
            _world.addDisease(da.getDisease(), client);
        }
        else if (id.equals("DI")){
            DiseaseIntroducer di = (DiseaseIntroducer)gd;
            _world.introduceDisease(di.getRegion(), di.getDisease());
        }
        else if (id.equals("DP")){
            _world.changeDiseasesPicked(client);
        }
        else if (id.equals("LM")){
            LobbyMember lm = (LobbyMember)gd;
            _world.addLobbyMember(lm);
        }
        else if (id.equals("LR")){
            _world.removeLobbyMember(client);
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
            DCMessage msg = new DCMessage(pos);
            for (GameServerThread gst : _clients){
                gst.sendMessage(msg);
            }
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
        _clients.add(temp);
    }
    
    public void stop(){
    	try{
    		_server.close();
    		for (GameServerThread kill : _clients){
    			kill.close();
    		}
    		_clients.clear();
    		_thread = null;
    	}
    	catch(IOException e){
    		//Error closing
    	}
    }

    /*public static void main(String [] args) throws Exception{
        String s = InetAddress.getLocalHost().getHostName();
        System.out.println(s);
        GameServer server = new GameServer();
    }*/

}
