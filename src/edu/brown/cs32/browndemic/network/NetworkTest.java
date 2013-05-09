/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs32.browndemic.network;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.brown.cs32.browndemic.disease.Bacteria;
import edu.brown.cs32.browndemic.disease.Virus;
import edu.brown.cs32.browndemic.world.ClientWorld;
import edu.brown.cs32.browndemic.world.ServerWorld;
import edu.brown.cs32.browndemic.world.WorldMaker;

/**
 * Test class for my object sending 
 * @author gcarling
 */
public class NetworkTest implements Runnable{

    private ClientWorld _clientWorld1, _clientWorld2;
    private ServerWorld _serverWorld;
    @SuppressWarnings("unused")
	private GameClient _client1, _client2;
    private GameServer _server;
    private BufferedReader _input;
    private final int PORT = 6000;
    
    public NetworkTest(){
    	try{
    		_serverWorld = WorldMaker.makeNewEarthServer();
    	}
    	catch(IOException e){
    		
    	}
        _clientWorld1 = new ClientWorld("Client 1");
        _clientWorld2 = new ClientWorld("Client 2");
        try{
            _server = new GameServer(_serverWorld, PORT);
            _client1 = new GameClient("localhost", PORT, _clientWorld1);
            for (long i = 0; i < 100000000L; i++){
                
            }
            _client2 = new GameClient("localhost", PORT, _clientWorld2);
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
                else if (line.startsWith(("DC"))){
                    _client1.stop();
                }
                else if (line.startsWith("DA1")){
                    _clientWorld1.addDisease(new Virus("Virus"));
                }
                else if (line.startsWith("DA2")){
                    _clientWorld2.addDisease(new Bacteria("Bacteria"));
                }
                else if (line.startsWith("DI1")){
                    _clientWorld1.introduceDisease(0, 0);
                }
                else if (line.startsWith("DI2")){
                    _clientWorld2.introduceDisease(1, 0);
                }
                else if (line.startsWith("DP1")){
                    _clientWorld1.changeDiseasesPicked(1);
                }
                else if (line.startsWith("DP2")){
                    _clientWorld2.changeDiseasesPicked(1);
                }
                else if (line.startsWith("LR")){
                    _clientWorld2.leaveGame();
                }
                else if (line.startsWith("S")){
                    _serverWorld.start();
                }
                else if (line.startsWith("CD")){
                    _serverWorld.collectDiseases();
                }
                else if (line.startsWith("H")){
                	_serverWorld.killServer();
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