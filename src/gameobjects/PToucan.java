package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;

public class PToucan extends Passif {

	private Vector2 position;
	private Vector2 direction;
	private Vector2 size;	
	private TearHero tear;
	private int degat;
	private Hero hero;
	
	public PToucan(Hero hero) {
		
		this.hero = hero;
		this.position = new Vector2 (0.5,0.5);
		this.size = new Vector2(0.06,0.06);
		this.direction = getPosition();

	}


	public void effetItem(Hero hero) {
			
	}

	@Override
	public void drawPassif() {
		StdDraw.picture(getPosition().getX(), getPosition().getY() , ImagePaths.TOUCAN ,size.getX() , size.getY());	
		
	}

	public void drawTear() {
		StdDraw.picture(getPosition().getX(), getPosition().getY(), ImagePaths.TEARTOUCAN, size.getX(), size.getY());	
	}
	
	


	public Vector2 getPosition() {
		return position;
	}

	public Vector2 getSize() {
		return size;
	}

	public Vector2 getDirection() {
		return direction;
	}

	public void setDirection(Vector2 direction) {
		this.direction = direction;
	}

	public TearHero getTear() {
		return tear;
	}

	public void setTear(TearHero tear) {
		this.tear = tear;
	}


	public int getDegat() {
		return degat;
	}

	public void setDegat(int degat) {
		this.degat = degat;
	}


	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	
}