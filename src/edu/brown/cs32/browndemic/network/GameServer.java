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
import java.util.Collections;
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
        _thread = new Thread(this, "Server");
        _clients = Collections.synchronizedList(new ArrayList<GameServerThread>());
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
                Socket temp = _server.accept();
                if (_accepting){
                    if (temp != null){
                        addThread(temp);
                    }
                }
                else{
                	temp.close();
                }
            }
            catch(SocketTimeoutException e){
                continue;
            }
            catch(IOException e){
                break;
            }
        }
    }
    
    //for once the game starts
    public void stopAccepting(){
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
        //chat message
        else if (id.equals("M")){
            for (int i = 0; i < _clients.size(); i++){
                if (i != client){
                    GameServerThread c = _clients.get(i);
                    c.sendMessage(gd);
                }
            }
        }
        //add a new disease
        else if (id.equals("DA")){
            DiseaseAdder da = (DiseaseAdder)gd;
            _world.addDisease(da.getDisease(), client);
        }
        //introduce a new disease to a region
        else if (id.equals("DI")){
            DiseaseIntroducer di = (DiseaseIntroducer)gd;
            _world.introduceDisease(di.getRegion(), di.getDisease());
        }
        //user  has picked a new disease at the start screen
        else if (id.equals("DP")){
            _world.changeDiseasesPicked(client);
        }
        //new lobby member
        else if (id.equals("LM")){
            LobbyMember lm = (LobbyMember)gd;
            _world.addLobbyMember(lm);
        }
        //someone has left the game
        else if (id.equals("GL")){
        	if (client == 0)
        		stop();
        	if (client != -1)
        		_world.removePlayer(client);
        }
        else if (id.equals("NC")){
        	NameChange nc = (NameChange)gd;
        	_world.updateName(nc.getName(), client);
        }
        else if (id.equals("EG")){
        	EndGame eg = (EndGame)gd;
        	boolean w = eg.isWinner();
        	if (w)
        		_world.endGame(client);
        	else
        		_world.endGame(-1);
        }
    }

    /**
     * Remove a client from our list of clients
     * @param ID The ID of the client to remove
     * @throws java.io.IOException
     */
    public synchronized void remove(int ID){
    	int pos;
    	if (ID > 1000)
    		pos = findClient(ID);
    	else 
    		pos = ID;
        if (pos != -1 && pos < _clients.size()){
            GameServerThread toKill = _clients.get(pos);
            _clients.remove(toKill);
            String name = "";
            if (_world.hasStarted()){
	            name = _world.getDiseases().get(pos).getName();
            }
            _world.removeDisease(pos);
            toKill.close();
            if (_world.hasStarted()){
	            DCMessage msg = new DCMessage(name, pos);            
	            for (GameServerThread gst : _clients){
	                gst.sendMessage(msg);
	            }
            }
        }
        if (_clients.size() == 0)
        	stop();
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
    
    public synchronized void stop(){
    	try{
    		_server.close();
    		for (GameServerThread toKill : _clients){
    			toKill.close();
    		}
    		_world.leaveGame();
    		_clients.clear();
    		_thread = null;
    	}
    	catch(IOException e){
    		//Error closing
    	}
    }

}
