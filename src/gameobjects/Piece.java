package gameobjects;

import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;

public class Piece extends Consommable {

	public Piece(int valeur) {
		super(valeur);
		
	}
	
	public  void drawConsommable() {
		if(getValeur()== 1) {
			StdDraw.picture(getPosition().getX(), getPosition().getY() , ImagePaths.COIN , getSize().getX() , getSize().getY());
			//COIN
		}
		if(getValeur() == 5) {
			StdDraw.picture(getPosition().getX(), getPosition().getY() , ImagePaths.NICKEL ,getSize().getX() , getSize().getY());
			//NICKEL
		}
		if(getValeur() == 10) {
			//DIME
			StdDraw.picture(getPosition().getX(), getPosition().getY() , ImagePaths.DIME ,getSize().getX() , getSize().getY());	
		}
	}
	
	
}