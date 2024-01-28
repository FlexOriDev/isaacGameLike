package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;

/**
 * Représente un mob, peut être une araignée, une mouche ou un boss.
 */
public abstract class Mob {

	private int hp;
	private int degat;
	private double speed;
	private Vector2 position;
	private Vector2 direction;
	private Vector2 size;
	
	public Mob() {
		
		double randomPosX = Math.random();
		double randomPosY = Math.random();
		while(randomPosX < 0.1 || randomPosX > 0.9) {
			randomPosX = Math.random();
		}
		while(randomPosY < 0.1 || randomPosY > 0.9) {
			randomPosY = Math.random();
		}
		
		this.position = new Vector2(randomPosX, randomPosY);
		
	}
	
	
	public abstract void mobSeDeplace();

	public abstract void drawMob();
	

	public int getHp() {
		return hp;
	}


	public void setHp(int hp) {
		this.hp = hp;
	}


	public int getDegat() {
		return degat;
	}


	public void setDegat(int degat) {
		this.degat = degat;
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


	public Vector2 getSize() {
		return size;
	}


	public void setSize(Vector2 size) {
		this.size = size;
	}


	public double getSpeed() {
		return speed;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}
		
	
	
	
}