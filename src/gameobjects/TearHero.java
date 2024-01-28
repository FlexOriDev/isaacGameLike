package gameobjects;

import java.util.ArrayList;

import gameWorld.Room;
import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;

public class TearHero extends Tear {

	private Vector2 position;
	private Vector2 direction;
	private String imagePath;
	private Vector2 size;	
	private int degat;
	private double speed;
	private int attackSpeed;
	
	public TearHero(Hero hero,Vector2 direction) {
	
		this.position = hero.getPosition();
		this.direction = direction;
		this.size = new Vector2(0.03,0.03);
		this.speed = 0.01;
		this.imagePath = ImagePaths.TEAR;
		this.degat = hero.getDegat();
		this.attackSpeed = 20;
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

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	
}