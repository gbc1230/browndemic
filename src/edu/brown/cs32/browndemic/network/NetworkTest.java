/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.network;
import edu.brown.cs32.browndemic.world.ClientWorld;
import edu.brown.cs32.browndemic.world.ServerWorld;
import java.io.*;

/**
 * Test class for my object sending 
 * @author gcarling
 */
public class NetworkTest implements Runnable{

    private ClientWorld _clientWorld1, _clientWorld2;
    private ServerWorld _serverWorld;
    private GameClient _client1, _client2;
    private GameServer _server;
    private BufferedReader _input;
    
    public NetworkTest(){
        _serverWorld = new ServerWorld();
        _clientWorld1 = new ClientWorld();
        _clientWorld2 = new ClientWorld();
        try{
            _server = new GameServer(_serverWorld);
            _client1 = new GameClient("localhost", _clientWorld1);
            _client2 = new GameClient("localhost", _clientWorld2);
            _input = new BufferedReader(new InputStreamReader(System.in));
        }
        catch(IOException e){
            System.out.println("Error starting.");
        }
    }
    
    @Override
    public void run(){
        while (true){
            try{
                String line = _input.readLine();
                System.out.println("read in: " + line);
                if (line.equals("M"))
                    _clientWorld1.sendMessage("TEST MESSAGE");
                else if (line.equals("P"))
                    _clientWorld2.addPerk(0, 0, true);
            }
            catch(IOException e){
                System.out.println("Couldn't read line.");
                continue;
            }
        }
    }
    
    
    
    public static void main (String[] args){
        NetworkTest n = new NetworkTest();
    }

}