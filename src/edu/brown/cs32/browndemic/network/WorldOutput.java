/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.browndemic.network;
import edu.brown.cs32.browndemic.region.AirTransmission;
import edu.brown.cs32.browndemic.world.ServerWorld;

/**
 *
 * @author Graham
 */
public class WorldOutput implements GameData{
	private static final long serialVersionUID = -7655203119076387109L;
	
	//the world that this class stores
    private ServerWorld _world;
    //most recent air transmission
    private AirTransmission _trans;
    
    public WorldOutput(ServerWorld w, AirTransmission a){
        _world = w;
        _trans = a;
    }
    
    public ServerWorld getWorld(){
        return _world;
    }
    
    public AirTransmission getTrans(){
    	return _trans;
    }
    
    @Override
    public String getID(){
        return "W";
    }
    
}
