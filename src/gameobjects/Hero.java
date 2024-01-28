package gameobjects;

import java.util.ArrayList;

import gameWorld.Spike;
import libraries.Physics;
import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;
import resources.RoomInfos;

/**
 * Représente le heros et toutes ses caractéristiques dans le jeu.
 */
public class Hero
{
	private Vector2 position;
	private Vector2 size;
	private String imagePath;
	private double speed;
	private Vector2 direction;
	private boolean estInvincible;
	private boolean possedeMartyr;
	private boolean possedeCoeur;
	private boolean possedeToucan;
	private boolean invTriche;
	private int hp; //TODO
	private int hpDeBase;
	private int degat;
	private int solde;
	private int range;
	private ArrayList<Passif> listItemP;
	private ArrayList<Consommable> listItemC; 

	public Hero(Vector2 position, Vector2 size, double speed, String imagePath)
	{
		this.position = position;
		this.size = size;
		this.speed = speed;
		this.imagePath = imagePath;
		this.direction = new Vector2();
		this.hp = 6;
		this.hpDeBase=6;
		this.estInvincible = false;
		this.degat=1;
		this.listItemP = new ArrayList<Passif>();
		this.listItemC = new ArrayList<Consommable>();
		this.possedeMartyr = false;
		this.possedeCoeur = false;
		this.possedeToucan = false;
		this.solde = 0;
		this.range = 30;
		this.invTriche = false;
	}

	public void updateGameObject() {
		move();
	}

	private void move() {
		Vector2 normalizedDirection = getNormalizedDirection();
		Vector2 positionAfterMoving = getPosition().addVector(normalizedDirection);
		setPosition(positionAfterMoving);
		direction = new Vector2();
		
	}

	public void drawGameObject() {
		StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(),
				0);
	}

	/*
	 * Moving from key inputs. Direction vector is later normalised.
	 */
	public void goUpNext()
	{
		if(Physics.rectangleCollision(position, size, RoomInfos.POSITION_WALL_1, RoomInfos.SIZE_WALL_1)){
			//System.out.println("mur");
		getDirection().addY(0);
		}else {
			getDirection().addY(1);
			this.imagePath = ImagePaths.ISAACB;
		}
	}

	public void goDownNext()
	{
		if(Physics.rectangleCollision(position, size, RoomInfos.POSITION_WALL_2, RoomInfos.SIZE_WALL_2)){
			//	System.out.println("mur");
				getDirection().addY(0);
			}else {
				getDirection().addY(-1);
				this.imagePath = ImagePaths.ISAAC;
			}
	}

	public void goLeftNext()
	{
		if(Physics.rectangleCollision(position, size, RoomInfos.POSITION_WALL_3, RoomInfos.SIZE_WALL_3)){
			//	System.out.println("mur");
				getDirection().addX(0);
			}else {
				getDirection().addX(-1);
				this.imagePath = ImagePaths.ISAACG;
			}
	}

	public void goRightNext()
	{
		if(Physics.rectangleCollision(position, size, RoomInfos.POSITION_WALL_4, RoomInfos.SIZE_WALL_4)){
			//	System.out.println("mur");
				getDirection().addX(0);
			}else {
				getDirection().addX(1);
				this.imagePath = ImagePaths.ISAACD;
			}
	}

	public Vector2 getNormalizedDirection()
	{
		Vector2 normalizedVector = new Vector2(direction);
		normalizedVector.euclidianNormalize(speed);
		return normalizedVector;
	}


	/*
	 * Getters and Setters
	 */
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

	public String getImagePath()
	{
		return imagePath;
	}

	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}

	public double getSpeed()
	{
		return speed;
	}

	public void setSpeed(double speed)
	{
		this.speed = speed;
	}

	public Vector2 getDirection()
	{
		return direction;
	}

	public void setDirection(Vector2 direction)
	{
		this.direction = direction;
	}
	
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		if(!estInvincible) {
			this.hp = hp;
		}
	}

	public boolean getEstInvincible() {
		return estInvincible;
	}

	public void setEstInvincible(boolean estInvincible) {
		this.estInvincible = estInvincible;
		
	}
	
	public int getDegat() {
		return degat;
	}

	public void setDegat(int degat) {
		
		this.degat = degat;
	}

	public int getHpDeBase() {
		return hpDeBase;
	}

	public void setHpDeBase(int hpDeBase) {
		this.hpDeBase = hpDeBase;
	}



	public ArrayList<Passif> getListItemP() {
		return listItemP;
	}



	public void setListItemP(ArrayList<Passif> listItemP) {
		this.listItemP = listItemP;
	}
	
	public ArrayList<Consommable> getListItemC() {
		return listItemC;
	}



	public void setListItemC(ArrayList<Consommable> listItemC) {
		this.listItemC = listItemC;
	}

	public boolean getPossedeMartyr() {
		return possedeMartyr;
	}

	public void setPossedeMartyr(boolean possedeMartyr) {
		this.possedeMartyr = possedeMartyr;
	}

	public boolean getPossedeCoeur() {
		return possedeCoeur;
	}

	public void setPossedeCoeur(boolean possedeCoeur) {
		this.possedeCoeur = possedeCoeur;
	}

	public int getSolde() {
		return solde;
	}

	public void setSolde(int solde) {
		this.solde = solde;
	}
	
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public boolean getPossedeToucan() {
		return possedeToucan;
	}

	public void setPossedeToucan(boolean possedeToucan) {
		this.possedeToucan = possedeToucan;
	}

	public boolean isInvTriche() {
		return invTriche;
	}

	public void setInvTriche(boolean invTriche) {
		this.invTriche = invTriche;
	}
	
	
}
