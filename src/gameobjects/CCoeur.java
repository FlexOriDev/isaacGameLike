package gameobjects;

import libraries.StdDraw;
import resources.ImagePaths;

public class CCoeur extends Consommable {

	public CCoeur(int valeur) {
		super(valeur);
	}

	@Override
	public void drawConsommable() {
		if(getValeur()== 1) {
			StdDraw.picture(getPosition().getX(), getPosition().getY() , ImagePaths.HALF_HEART_PICKABLE , getSize().getX() , getSize().getY());
		}
		if (getValeur()== 2) {
			StdDraw.picture(getPosition().getX(), getPosition().getY() , ImagePaths.HEART_PICKABLE , getSize().getX() , getSize().getY());
		}
		
	}

	
}