package gameobjects;


import java.util.ArrayList;

import gameWorld.Room;
import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;

/**
 * Représente le projectile " Tear", qui peut venir du heros, d'un mob ou du toucan.
 */
public abstract class Tear  {

	private String imagePath;
	private boolean isAlive;
	
	public Tear(){

		this.imagePath = ImagePaths.TEAR;
		this.isAlive = true;
		
	}
	
	public abstract void drawTear();
	
	public abstract void tirUpdate();
		
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public abstract Vector2 getPosition();

	public abstract void setPosition(Vector2 position);
	
	public abstract Vector2 getSize();

	public abstract void setSize(Vector2 position);
	
	public abstract int getDegat();

	public abstract void setDegat(int degat);
	

	

}