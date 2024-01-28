package gameobjects;


import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;

public class Boss extends Mob {

	private String imagePath;
	private Hero hero;
	
	public Boss(Hero hero) {
		this.hero = hero;
		this.imagePath = ImagePaths.BOSS;
		setHp(15);
		setDegat(1);
		setSpeed(0.009);
		Vector2 taille = new Vector2 (0.15 , 0.15);
		setSize(taille);
	}


	@Override
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


	@Override
	public void drawMob() {
		StdDraw.picture(getPosition().getX(), getPosition().getY() , getImagePath(), getSize().getX() , getSize().getY());	
		
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	
	
}