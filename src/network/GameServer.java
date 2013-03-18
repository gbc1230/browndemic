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
public class GameServer {

    //the port to run the server on, the maximum number of clients
    private final int _PORT, _MAX;
    //the clients that connect
    private List<GameClient> _clients;

    public GameServer(int p, int m){
        _PORT = p;
        _MAX = m;
        _clients = new ArrayList<GameClient>();
    }

    public void run() throws Exception{
        ServerSocket server = new ServerSocket(_PORT, _MAX);
        while (true){
            Socket client = server.accept();
            GameClient c = new GameClient(this, client);
            _clients.add(c);
        }
    }

    public void broadcast(String message){
        for (GameClient c : _clients){
            c.getMessage(message);
        }
    }

    public void removeClient(GameClient c){
        _clients.remove(c);
    }

    public static void main() throws Exception{
        GameServer server = new GameServer(6000, 5);
        server.run();
    }

}
