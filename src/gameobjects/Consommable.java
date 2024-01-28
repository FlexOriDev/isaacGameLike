package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;

/**
 * Représente un consommable, peut être une pièce ou un coeur (half ou full).
 */
public abstract class Consommable {
	
	private Vector2 position;
	private Vector2 size;
	private int valeur;
	private int prix;
	
	
	public Consommable(int valeur) {
		this.valeur = valeur;
		this.position = new Vector2(0.7,0.3);
		this.size = new Vector2(0.035, 0.03);
		this.prix = 3;
	}

	public abstract void drawConsommable();

	/*
	 * Getters and setters.
	 */
	
	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
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

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}
	
}