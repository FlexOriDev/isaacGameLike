package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;

public class Fly extends Mob {

	private Hero hero;
	private String imagePath;
	
	public Fly(Hero hero) {
		
		this.imagePath = ImagePaths.FLY;
		this.hero = hero;
		setHp(3);
		setDegat(1);
		setSpeed(hero.getSpeed()/8);
		Vector2 taille = new Vector2 (0.05 , 0.025);
		setSize(taille);
		
	}

	public void mobSeDeplace() {
		Vector2 oldPos = getPosition();
			
			Vector2 newDirection = new Vector2 (hero.getPosition().getX(),hero.getPosition().getY());
			double newPosX , newPosY;
			
				if (newDirection.getX() > oldPos.getX()) {
					newPosX = oldPos.getX()+ getSpeed();
				} else {
					newPosX = oldPos.getX()- getSpeed();
				}
				if (newDirection.getY() > oldPos.getY() ) {
					newPosY = oldPos.getY()+ getSpeed();
				} else {
					newPosY = oldPos.getY()- getSpeed();
				}
				Vector2 newPos = new Vector2(newPosX,newPosY);
				setPosition(newPos);
		
	}

	
	
	public void drawMob() {
		StdDraw.picture(getPosition().getX(), getPosition().getY() , getImagePath(), 0.075 , 0.05);	
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}