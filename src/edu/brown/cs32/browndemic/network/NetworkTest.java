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
            for (long i = 0; i < 100000000L; i++){
                
            }
            System.out.println("done");
            _server.stopAccepting();
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
                if (line.startsWith("M")){
                    String[] s = line.split(":");
                    _clientWorld1.sendMessage(s[1]);
                }
                else if (line.startsWith("P")){
                    String[] s = line.split(" ");
                    int a = Integer.parseInt(s[1]);
                    int b = Integer.parseInt(s[2]);
                    boolean c = s[3].equals("t");
                    _clientWorld2.addPerk(a, b, c);
                }
                else if (line.startsWith("W")){
                    _serverWorld.addCommand();
                }
            }
            catch(IOException e){
                System.out.println("Couldn't read line.");
                continue;
            }
        }
    }
    
    
    
    public static void main (String[] args){
        NetworkTest n = new NetworkTest();
        n.run();
    }

}