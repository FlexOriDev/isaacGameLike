package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;

public class PCoeur extends Passif {
	
	private Vector2 position;
	private String imagePath;
	private Vector2 size;	
	private int gainHp;
	
	public PCoeur(Hero hero) {
		
		this.position = new Vector2 (0.7,0.5);
		this.size = new Vector2(0.1,0.1);
		this.imagePath = ImagePaths.HP_UP;
		this.gainHp = 2;
	}
	
	
	public void effetItem(Hero hero) {
		hero.setHp(8);
		hero.setHpDeBase(8);
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

	public Vector2 getSize() {
		return size;
	}


	public void setSize(Vector2 size) {
		this.size = size;
	}


	public int getGainHp() {
		return gainHp;
	}


	public void setGainHp(int gainHp) {
		this.gainHp = gainHp;
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	
	

	
	
}