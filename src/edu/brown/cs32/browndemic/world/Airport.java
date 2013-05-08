package edu.brown.cs32.browndemic.world;

/**
 * Representation of an airport
 * @author gcarling
 *
 */
public class Airport {

	private int _id, _region, _x, _y;
	private String _descript;
	private boolean _open;
	
	public Airport(int id, int r, int x, int y, String d){
		_id = id;
		_region = r;
		_x = x;
		_y = y;
		_descript = d;
		_open = true;
	}
	
	public int getID(){
		return _id;
	}
	
	public int getRegion(){
		return _region;
	}
	
	public int getX(){
		return _x;
	}
	
	public int getY(){
		return _y;
	}
	
	public String getDescription(){
		return _descript;
	}
	
	public boolean isOpen(){
		return _open;
	}
	
	public void close(){
		_open = false;
	}
	
}
