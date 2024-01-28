package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;

public class Spider extends Mob {
	
	private String imagePath;
	private Hero hero;
	
	public Spider( Hero hero ) {
		
		this.imagePath = ImagePaths.SPIDER;
		this.hero = hero;
		setHp(5);
		setDegat(1);
		setSpeed(0.012);
		Vector2 taille = new Vector2 (0.05 , 0.025);
		setSize(taille);
		
	}

	public void mobSeDeplace() {
		double randomYorX = Math.random();
		Vector2 oldPos = getPosition();
			if(randomYorX > 0.5) {
				
				Vector2 newDirection = new Vector2 (hero.getPosition().getX(),Math.random());
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
				
		} else {
			
			Vector2 newDirection = new Vector2 (Math.random(),hero.getPosition().getY());
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
	}
	
	
	public void drawMob() {
		StdDraw.picture(getPosition().getX(), getPosition().getY() , getImagePath(), 0.07 , 0.07);	
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

		
	
	
}