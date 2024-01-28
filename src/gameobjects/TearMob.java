package gameobjects;

import java.util.TreeMap;

import gameWorld.Room;
import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;

public class TearMob extends Tear {
	
	private Vector2 position;
	private Vector2 direction;
	private Vector2 size;	
	private int degat;
	private double speed;
	private Mob mob;
	private int attackSpeed;
	
	public TearMob(Mob mob, Vector2 direction ) {
		
		this.mob= mob;
		this.position = mob.getPosition();
		if(mob instanceof Fly) {
		Vector2 newDir = direction.subVector(getPosition());
		this.direction = newDir;
		} else {
		this.direction = direction;
		}
		this.size = new Vector2(0.025,0.025);
		this.speed = 0.01;
		this.degat = 1;
		this.attackSpeed = 20;	
	}
	
	public void tirUpdate() {
		
		Vector2 normalizedVector = new Vector2(direction);
		normalizedVector.euclidianNormalize(speed);
		Vector2 positionAfterMoving = getPosition().addVector(normalizedVector);
		this.setPosition(positionAfterMoving);
		
	}
	
	public void drawTear() {
		StdDraw.picture(getPosition().getX(), getPosition().getY(), ImagePaths.TEARMOB, size.getX(), size.getY());	
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

	public Mob getMob() {
		return mob;
	}

	public void setMob(Mob mob) {
		this.mob = mob;
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	
}