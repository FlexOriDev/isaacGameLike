package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;

public class TearToucan extends Tear {

	private Vector2 position;
	private Vector2 direction;
	private String imagePath;
	private Vector2 size;	
	private int degat;
	private double speed;
	
	public TearToucan(Hero hero, Vector2 direction) {
		
		double posX = hero.getPosition().getX()+0.05;
		double posY = hero.getPosition().getY() + 0.03;
		this.position = new Vector2 (posX,posY);
		Vector2 newDir = direction.subVector(getPosition());
		this.direction = newDir;
		this.size = new Vector2(0.025,0.025);
		this.speed = 0.01;
		this.imagePath = ImagePaths.TEARTOUCAN;
		this.degat = hero.getDegat();
		
	}

	
	public void tirUpdate() {
		
		Vector2 normalizedVector = new Vector2(direction);
		normalizedVector.euclidianNormalize(speed);
		Vector2 positionAfterMoving = getPosition().addVector(normalizedVector);
		this.setPosition(positionAfterMoving);	
	}
	
	public void drawTear() {
		StdDraw.picture(getPosition().getX(), getPosition().getY() , getImagePath(),size.getX() , size.getY());	
	}


	public Vector2 getPosition() {
		return position;
	}


	public void setPosition(Vector2 position) {
		this.position = position;
	}


	public Vector2 getDirection() {
		return direction;
	}


	public void setDirection(Vector2 direction) {
		this.direction = direction;
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


	public Vector2 getSize() {
		return size;
	}


	public void setSize(Vector2 size) {
		this.size = size;
	}


	public int getDegat() {
		return degat;
	}


	public void setDegat(int degat) {
		this.degat = degat;
	}


	public double getSpeed() {
		return speed;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}


	
}