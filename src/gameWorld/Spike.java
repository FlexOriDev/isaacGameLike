package gameWorld;


import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;
import resources.RoomInfos;

/**
 * Représente un pique qui est un obstacle dans le jeu.
 */
public class Spike{
	private Vector2 position;
	private Vector2 size;
	private int degat;
	
	public Spike(Vector2 position) {
		
		this.position = position;
		this.size = RoomInfos.SIZE_SPIKE;
		this.degat = 1;
	}
	
	
	
	public void drawGameObject() {
		StdDraw.picture(position.getX(), position.getY(), ImagePaths.SPIKES, size.getX(), size.getY(),
				0);
	}
	
	public Vector2 getPosition()
	{
		return position;
	}

	public void setPosition(Vector2 position)
	{
		this.position = position;
	}

	public Vector2 getSize()
	{
		return size;
	}

	public void setSize(Vector2 size)
	{
		this.size = size;
	}
	
	public int getDegat()
	{
		return degat;
	}

	public void setDegat(int degat)
	{
		this.degat = degat;
	}
}
