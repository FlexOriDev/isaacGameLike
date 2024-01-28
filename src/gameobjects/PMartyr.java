package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;

public class PMartyr extends Passif {

	private Vector2 position;
	private String imagePath;
	private Vector2 size;
	private Hero hero;
	
	public PMartyr(Hero hero) {
		
		this.hero = hero;
		this.imagePath = ImagePaths.BLOOD_OF_THE_MARTYR;
		this.size = new Vector2(0.1,0.1);
		this.position = new Vector2 (0.3,0.5);
		
	}

	public void effetItem(Hero hero) {
		hero.setDegat(2);
	}
	
	
	public void drawPassif() {
		StdDraw.picture(getPosition().getX(), getPosition().getY() , getImagePath(),size.getX() , size.getY());	
		}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
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


	
	
}