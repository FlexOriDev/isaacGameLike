package gameobjects;

import libraries.Vector2;

/**
 * Représente un objet passif, peut être un Toucan (PToucan), un Martyr (PMartyr) ou un gros Coeur (PCoeur).
 */
public abstract class Passif {

	private double ChanceDrop;
	private boolean isDropped;
	private int prix;
	
	public Passif() {
		
		this.ChanceDrop = 0.4;
		this.prix = 15;
		this.isDropped = false;
		
	}
	
	public abstract void effetItem(Hero hero);
	
	public abstract void drawPassif();

	public double getChanceDrop() {
		return ChanceDrop;
	}

	public void setChanceDrop(double chanceDrop) {
		ChanceDrop = chanceDrop;
	}

	public boolean isDropped() {
		return isDropped;
	}

	public void setDropped(boolean isDropped) {
		this.isDropped = isDropped;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public abstract Vector2 getPosition();

	public abstract Vector2 getSize();
	
	
	
}