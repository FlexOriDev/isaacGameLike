package gameWorld;

import java.util.ArrayList;
import java.util.Random;

import gameobjects.CCoeur;
import gameobjects.Consommable;
import gameobjects.Hero;
import gameobjects.PCoeur;
import gameobjects.PMartyr;
import gameobjects.PToucan;
import gameobjects.Passif;
import libraries.Physics;
import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;
import resources.RoomInfos;

public class ShopRoom extends Room {
	
	private Hero hero;
	
	private Vector2 position;
	private Vector2 size;
	private String imagePath;
	
	private  ArrayList<Porte> portes;
	private Porte p0;
	private Porte p1;
	private Porte p2;
	private Porte p3;
	
	private  ArrayList<Passif> itemsP;
	private  ArrayList<Consommable> itemsC;
	
	private PMartyr martyr ;
	private PCoeur coeur ;
	private PToucan toucan ;
	
	private CCoeur fullCoeur;
	private boolean hitFullCoeur;
	private CCoeur halfCoeur;
	private boolean hitHalfCoeur;
	
	private int itemChoose;
	
	private int horlogeAll;
	private int horlogeItemP;
	private int horlogeFullCoeurs;
	private int horlogeHalfCoeurs;
	private int horlogeDropItems;
	
	public ShopRoom(Hero hero) {
		super(hero);
		this.hero = getHero();								// on récupère les informations de notre héros.
		this.position = RoomInfos.POSITION_CENTER_OF_ROOM;	// position de la texture de la room.
		this.size = RoomInfos.SIZE_TEXTURE_ROOM;			// taille de la texture de la room.
		this.imagePath = ImagePaths.FONT;					// fond de bossroom.
		
		// initialisation de toutes les portes de la room.
		this.p0 = new Porte(RoomInfos.POSITION_DOOR_0, RoomInfos.SIZE_DOOR_0, 0.);
		this.p1 = new Porte(RoomInfos.POSITION_DOOR_1, RoomInfos.SIZE_DOOR_1, -90.);
		this.p2 = new Porte(RoomInfos.POSITION_DOOR_2, RoomInfos.SIZE_DOOR_2, 180.);
		this.p3 = new Porte(RoomInfos.POSITION_DOOR_3, RoomInfos.SIZE_DOOR_3, 90.);
		// initialisation de la liste contenant les portes.
		this.portes = new ArrayList<Porte>();
		//ajout des portes dans cette liste.
		this.portes.add(p0);
		this.portes.add(p1);
		this.portes.add(p2);
		this.portes.add(p3);
		
		this.itemsP = new ArrayList<Passif>();
		this.itemsC = new ArrayList<Consommable>();
		
		this.horlogeAll = 0;
		this.horlogeItemP = 0;
		this.horlogeFullCoeurs = 0;
		this.horlogeHalfCoeurs = 0;
		this.horlogeDropItems = 0;
		
		Random r2 = new Random(); 
		itemChoose = r2.nextInt(3 - 1 + 1) + 1;
		
		if(itemChoose == 1) {
			martyr = new PMartyr(hero);
			martyr.setPosition(new Vector2(0.35, 0.5));
			
			fullCoeur = new CCoeur(2);
			fullCoeur.setPosition(new Vector2(0.5, 0.5));
			hitFullCoeur = false;
			
			coeur = new PCoeur(hero);
			coeur.setPosition(new Vector2(0.65, 0.5));
		}if(itemChoose == 2) {
			toucan = new PToucan(hero);
			toucan.setPosition(new Vector2(0.35, 0.5));
			
			halfCoeur = new CCoeur(1);
			halfCoeur.setPosition(new Vector2(0.5, 0.5));
			hitHalfCoeur = false;
			
			martyr = new PMartyr(hero);
			martyr.setPosition(new Vector2(0.65, 0.5));
		}if(itemChoose == 3) {
			halfCoeur = new CCoeur(1);
			halfCoeur.setPosition(new Vector2(0.35, 0.5));
			hitHalfCoeur = false;
			
			fullCoeur = new CCoeur(2);
			fullCoeur.setPosition(new Vector2(0.5, 0.5));
			hitFullCoeur = false;
			
			toucan = new PToucan(hero);
			toucan.setPosition(new Vector2(0.65, 0.5));
		}
	}
	
	// FONCTIONS - Gestion de toutes les fonctions. ---------------------------------------------------

	/**
	* Update toutes les fonctinos nécesaires au déroulement d'une salle.
	*/
	public void updateRoom() {
		horlogeAll++;
		if(!hitFullCoeur) {
			horlogeFullCoeurs++;
		}if(!hitHalfCoeur) {
			horlogeHalfCoeurs++;
		}
		super.updateRoom();
		passifAction();
	}
	
	public void passifAction() {
		horlogeItemP++;
		super.updateItems();
		collisionItems();
	}
	
	// ITEMS --------------------------------------------------------------------------------------------

	/**
	 * Gère les collisions entre les items et le heros.
	 * @return vrai si il y a collision.
	 */
	public boolean collisionItems() {
		if(martyr != null && !hero.getPossedeMartyr() && hero.getSolde()>= 15 ) {
			if (Physics.rectangleCollision(martyr.getPosition(), martyr.getSize(), hero.getPosition(),
					hero.getSize())) {
				martyr.setDropped(true);
				martyr.effetItem(hero);
				hero.getListItemP().add(martyr);
				hero.setSolde(hero.getSolde()-15);
				hero.setPossedeMartyr(true);
				martyr = null;	
				return true;
			}
		}if(coeur != null && !hero.getPossedeCoeur() && hero.getSolde()>= 15) {
			if (Physics.rectangleCollision(coeur.getPosition(), coeur.getSize(), hero.getPosition(),
					hero.getSize())) {
				coeur.setDropped(true);
				coeur.effetItem(hero);
				hero.setSolde(hero.getSolde()-15);
				hero.setPossedeCoeur(true);
				coeur = null;
				return true;
			}
		}if(toucan != null && !hero.getPossedeToucan() && hero.getSolde()>= 15) {
			if (Physics.rectangleCollision(toucan.getPosition(), toucan.getSize(), hero.getPosition(),
					hero.getSize())) {
				toucan.setDropped(true);
				toucan.effetItem(hero);
				hero.getListItemP().add(toucan);
				hero.setSolde(hero.getSolde()-15);
				hero.setPossedeToucan(true);
				toucan = null;
				return true;
			}
		}if(fullCoeur != null && hero.getSolde()>= 3) {
			if (Physics.rectangleCollision(fullCoeur.getPosition(), fullCoeur.getSize(), hero.getPosition(),
					hero.getSize())) {
				int x = 20;
				if (hero.getHp() != hero.getHpDeBase() && horlogeFullCoeurs-x > 0) {
				    if (hero.getHp() + fullCoeur.getValeur() >= hero.getHpDeBase()) {
				        hero.setHp(hero.getHpDeBase());   
				    } else {
				        hero.setHp(hero.getHp() + fullCoeur.getValeur());
				    }
				    hero.setSolde(hero.getSolde()-3);
				    fullCoeur = null;
				    hitFullCoeur = true;
				    horlogeFullCoeurs = 0;
				    return true;
				}
			}
		}if(halfCoeur != null && hero.getSolde()>= 3) {
			if (Physics.rectangleCollision(halfCoeur.getPosition(), halfCoeur.getSize(), hero.getPosition(),
					hero.getSize())) {
				int x = 20;
				if (hero.getHp() != hero.getHpDeBase() && horlogeHalfCoeurs-x > 0) {
				    if (hero.getHp() + halfCoeur.getValeur() >= hero.getHpDeBase()) {
				        hero.setHp(hero.getHpDeBase());
				    } else {
				        hero.setHp(hero.getHp() + halfCoeur.getValeur());    
				    }
				    hero.setSolde(hero.getSolde()-3);
				    halfCoeur = null;
				    hitHalfCoeur = true;
				    horlogeHalfCoeurs = 0;
				    return true;
				}
			}
		}				
		return false;
	}
	
	/*
	 * Drawing --------------------------------------------------------------------------------------------
	 */
	
	/**
	 * Dessine toutes les parties de la salle.
	 */
	public void drawRoom() {
		drawGameObject();
		super.drawingDoors(portes);
		drawItems();
		super.drawRoom();
	}
	
	/**
	 * Dessine les items si ils existent.
	 */
	public void drawItems() {
		StdDraw.setPenColor(StdDraw.WHITE);
		if(martyr!=null) {
			martyr.drawPassif();
			StdDraw.text(martyr.getPosition().getX(), martyr.getPosition().getY()-0.05, "" + 15);
		}if(toucan != null) {
			toucan.drawPassif();
			StdDraw.text(toucan.getPosition().getX(), toucan.getPosition().getY()-0.05, "" + 15);
		}if(coeur != null) {
			coeur.drawPassif();
			StdDraw.text(coeur.getPosition().getX(), coeur.getPosition().getY()-0.05, "" + 15);
		}if(halfCoeur != null) {
			halfCoeur.drawConsommable();
			StdDraw.text(halfCoeur.getPosition().getX(), halfCoeur.getPosition().getY()-0.05, "" + 3);
		}
		if(fullCoeur != null) {
			fullCoeur.drawConsommable();
			StdDraw.text(fullCoeur.getPosition().getX(), fullCoeur.getPosition().getY()-0.05, "" + 3);
		}
	}
	
	/**
	 * Dessine le fond de la salle.
	 */
	public void drawGameObject() {
		StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(),
				0);
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
	
	public ArrayList<Porte> getPortes() {
		return portes;
	}

	public void setPortes(ArrayList<Porte> portes) {
		this.portes = portes;
	}
}
