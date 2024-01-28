package gameWorld;

import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;

/**
 * Représente une porte dans une salle.
 */
public class Porte { // une porte possède plusieurs attributs pour la reconnaitre.
	private Vector2 position; // position de la porte
	private Vector2 size; // taille de la porte
	private Double angle;
	private boolean porteExiste; // pour savoir de quelle porte il s'agit ( 1 = haut, 2 = droite, 3 = bas, 4 = gauche ou -1 = il n'y a pas de portes.).
	private boolean cle; // clé pour savoir si la porte est ouverte ou fermee.
	private boolean isBoss;
	
	
	public Porte(Vector2 position, Vector2 size, Double angle) {
		this.position = position;
		this.size = size;
		this.angle = angle;
		this.isBoss = false;
	}
	
	public Porte(Vector2 position, Vector2 size, Double angle, boolean porteExiste, boolean cle) {
		this.position = position;
		this.size = size;
		this.angle = angle;
		this.porteExiste = porteExiste;
		this.cle = cle;
	}
	
	public void drawGameObject() {
		String s = "";
		if(this.porteExiste == false) {
			s = "";
		}else if(this.porteExiste == true){
			if(this.cle == true) {
				if(isBoss) {
					s = ImagePaths.OPEN_BOSS_DOOR;
				}else {
					s = ImagePaths.OPENED_DOOR;
				}
			}else if(this.cle == false){
				if(isBoss) {
					s = ImagePaths.CLOSED_BOSS_DOOR;
				}else {
					s = ImagePaths.CLOSED_DOOR;
				}
			}
		}
		
		StdDraw.picture(getPosition().getX(), getPosition().getY(), s, getSize().getX(), getSize().getY(),
				this.angle);
		
	}
	
	// getters et setters de chaque attribut.
	
	public Vector2 getPosition()
	{
		return position;
	}

	public void setPosition(Vector2 position)
	{
		this.position = position;
	}

	public Vector2 getSize()
	{
		return size;
	}

	public void setSize(Vector2 size)
	{
		this.size = size;
	}
	
	public boolean getPorteExiste()
	{
		return porteExiste;
	}

	public void setPorteExiste(boolean tag)
	{
		this.porteExiste = tag;
	}
	
	public boolean getCle()
	{
		return cle;
	}

	public void setCle(Boolean cle)
	{
		this.cle = cle;
	}
	
	public boolean getPorteOuverte()
	{
		return cle;
	}

	public void setPorteFermee(boolean cle)
	{
		this.cle = cle;
	}

	public boolean isBoss() {
		return isBoss;
	}

	public void setBoss(boolean isBoss) {
		this.isBoss = isBoss;
	}

	
	

}
