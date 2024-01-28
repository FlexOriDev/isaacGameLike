package gameWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import gameobjects.Boss;
import gameobjects.CCoeur;
import gameobjects.Consommable;
import gameobjects.Fly;
import gameobjects.Hero;
import gameobjects.Mob;
import gameobjects.PCoeur;
import gameobjects.PMartyr;
import gameobjects.PToucan;
import gameobjects.Passif;
import gameobjects.Piece;
import gameobjects.Spider;
import gameobjects.Tear;
import gameobjects.TearHero;
import gameobjects.TearMob;
import gameobjects.TearToucan;
import libraries.StdDraw;
import libraries.Vector2;
import libraries.Physics;
import resources.ImagePaths;
import resources.RoomInfos;

/**
 * Représente une salle ( qui peut etre une spawnroom, bossroom, mobroom ou shoproom ) dans la partie.
 */
public abstract class Room {
	
	private Hero hero;
	
	private ArrayList<Tear> listTear;
	private ArrayList<Mob> listMob;
	private HashMap<Integer, TearHero> TearRangeIndex;

	private int horlogeAll;		// horloge pour toutes les boucles du jeu.
	private int horlogeTear;	// horloge pour la tear.
	private int horlogeHero;	// horloge pour le hero.
	private int horlogeItemP;	// horloge pour update les itesm passifs.
	private int cycleRangeTear; // horloge pour la portée de la tear.
	
	private  ArrayList<Spike> spikes; // Liste pour les piques.
	
	public Room(Hero hero) { // on initialise tous nos attributs.
		
		this.hero = hero;
		this.listTear = new ArrayList<Tear>();
		this.listMob = new ArrayList<Mob>();
		this.spikes = new ArrayList<Spike>();
		this.TearRangeIndex = new HashMap<Integer, TearHero>();
		
		this.horlogeAll = 0;
		this.horlogeTear = 0;
		this.horlogeHero = 0;
		this.horlogeItemP = 0;
	}
	
	// FONCTIONS - Gestion de toutes les fonctions. ----------------------------------------------------------
	
	/**
	 * Update toutes les fonctinos nécesaires au déroulement d'une salle.
	 */
	public void updateRoom() { 
		heroAction();
		horlogeAll++;
		passifAction();
		tearAction();
	}

	public void heroAction() { 	// Permet d'update toutes les actions du hero.
		horlogeHero++;
		makeHeroPlay();
		heroRedeviensVulnerable();
		heroTakeDmg(listMob, spikes);
	}

	public void tearAction() { 	// Permet d'update toutes les actions du projectile : "tear".
		horlogeTear++;	
		cycleRangeTear++;
		
		supprTearRange(listTear, TearRangeIndex, cycleRangeTear);
		
		makeTearUpdate(listTear);
		

	}

	public void passifAction() {// Permet d'update toutes les actions liées aux items passifs.
		horlogeItemP++;
		makeDrawPassif();
		updateItems();
	}

	
	// HERO ----------------------------------------------------------------------------------------------------
	
	/**
	 * Rend le hero invicible s'il a recu recemment des degats
	 */
	public void heroRedeviensVulnerable() {
		int x = 80;
		if(horlogeHero == x) {
			hero.setEstInvincible(false);
			this.horlogeHero = 0;
		}
		
	}
	
	/**
	 * Permet d'update le heros en permanence.
	 */
	public void makeHeroPlay() {
		hero.updateGameObject();
	}
	
	/**
	 * Soustrait les points de vie du hero par les degats recu si il vient d'en
	 * recevoir
	 */
	public void heroTakeDmg( ArrayList<Mob> listMob, ArrayList<Spike> spikes) {
		if (!hero.isInvTriche() && hero.getHp() != 0) {
			for (Mob mob : listMob) {
				if (Physics.rectangleCollision(mob.getPosition(), mob.getSize(), hero.getPosition(), hero.getSize())) {
					hero.setHp(hero.getHp() - mob.getDegat());
					hero.setEstInvincible(true);
				}
			}
			
			for (Spike s : spikes) {
				if (Physics.rectangleCollision(s.getPosition(), s.getSize(), hero.getPosition(), hero.getSize())) {
					hero.setHp(hero.getHp() - s.getDegat());
					hero.setEstInvincible(true);
				}
			}
		}
	}
	
	// PASSIF ---------------------------------------------------------------------------------------------------
	
	/**
	 * Met a jour la position du passif Martyr et du Passif Toucan.
	 */
	public void updateItems() {
		if (!hero.getListItemP().isEmpty()) {
			for (Passif i : hero.getListItemP()) {
				if (i.isDropped()) {
					if (i instanceof PMartyr) {
						double posX = hero.getPosition().getX();
						double posY = hero.getPosition().getY() + 0.065;
						Vector2 newPos = new Vector2(posX, posY);
						((PMartyr) i).setPosition(newPos);
					}
					if (i instanceof PToucan) {
						double posX = hero.getPosition().getX()+0.05;
						double posY = hero.getPosition().getY() + 0.03;
						Vector2 newPos = new Vector2(posX, posY);
						((PToucan) i).setPosition(newPos);
					}
				}
			}
		}
	}
	
	// TEAR -----------------------------------------------------------------------------------------------------
	
	/**
	 * Met a jour les projectiles
	 */
	public void makeTearUpdate(ArrayList<Tear> listTear) {
		if (!listTear.isEmpty()) {
			for (Tear tear : listTear) {
				tear.tirUpdate();
			}
		}
	}
	
	/**
	 * Crée le projectile du Hero
	 */
	public void shootTearHero(Vector2 direction) {
		int x = 20;
		if (horlogeTear - x > 0) {
			TearHero a = new TearHero(hero, direction);
			listTear.add(a);
			TearRangeIndex.put(cycleRangeTear, a);
			horlogeTear = 0;
		}
	}
	
	/**
	 * supprime le projectile du hero lorsqu'il a parcouru toute sa portée de tir
	 */
	public void supprTearRange(ArrayList<Tear> listTear, HashMap<Integer, TearHero> TearRangeIndex, int cycleRangeTear) {
		if (!listTear.isEmpty()) {
			try {
			TearRangeIndex.forEach((key, value) -> {
				for (Tear tear : listTear) {
					if (tear == value && cycleRangeTear == key + hero.getRange() && tear instanceof TearHero) {
						try {
							listTear.remove(tear);
						} catch (Exception E) {
							//eviter crash d'iteration
							}
						}
					}
				});
			} catch (Exception E) {
				//eviter crash d'iteration
			}
		}
	}
	
	
	
	// SPIKES ---------------------------------------------------------------------------------------------------
	
	/**
	 * Permet de positionner les piques dans une salle. Soit 1 pique, 2 ou 3 choisis aléatoirement.
	 * Elle positionne les spikes sans en mettre 2 au même endroit.
	 * @param une liste de spikes, 3 objets Spike.
	 * @return la liste spikes avec soit 1, 2 ou 3 piques ajoutés dans celle-ci avec de nouvelles positions.
	 */
	public ArrayList<Spike> setSpikes(ArrayList<Spike> spikes, Spike s1, Spike s2, Spike s3) {
		Random rand = new Random(); 
		int nombrePiques = rand.nextInt(3 - 1 + 1) + 1;
		// Les piques ne se superposeront pas, il se génerent sur des positions aléatoire, 
		// mais jamais 2 piques au même endroit.
		if(nombrePiques == 1) {
			s1 = setOneSpike(s1);
			spikes.add(s1);
		}if(nombrePiques == 2) {
			s1 = setOneSpike(s1);
			spikes.add(s1);
			s2 = setOneSpike(s2);
			while(collisionSpikes(s1, s2)) {
				s2 = setOneSpike(s2);
			}
			spikes.add(s2);
			
		}if(nombrePiques == 3) {
			s1 = setOneSpike(s1);
			s2 = setOneSpike(s2);
			s3 = setOneSpike(s3);
			while(collisionSpikes(s1, s2, s3)) {
				s1 = setOneSpike(s1);
				s2 = setOneSpike(s2);
				s3 = setOneSpike(s3);
			}
			spikes.add(s1);
			spikes.add(s2);
			spikes.add(s3);
		}
		return spikes;
	}
	
	/**
	 * permet de dessiner les différents piques.
	 */
	public void drawningSpikes(ArrayList<Spike> spikes) {
		for(Spike spike : spikes) {
			spike.drawGameObject();
		}
	}
	
	/**
	 * Gère la collision entre 2 piques.
	 * @param 2 objets Spike qui sont des piques.
	 * @return un boolean qui rend vrai si les 2 piques sont en collision.
	 */
	public boolean collisionSpikes(Spike p1, Spike p2) {
		if(Physics.rectangleCollision(p1.getPosition(), p1.getSize(), p2.getPosition(), p2.getSize())){
			return true;
		}else {
			return false;
		}
	}
		
	/**
	 * Gère la collision entre 3 piques.
	 * @param 3 objets Spike qui sont des piques.
	 * @return un boolean qui rend vrai si les 3 piques sont en collision.
	 */
	public boolean collisionSpikes(Spike p1, Spike p2, Spike p3) { 
		if(Physics.rectangleCollision(p1.getPosition(), p1.getSize(), p2.getPosition(), p2.getSize()) |
				Physics.rectangleCollision(p1.getPosition(), p1.getSize(), p3.getPosition(), p3.getSize()) |
				Physics.rectangleCollision(p2.getPosition(), p2.getSize(), p3.getPosition(), p3.getSize())){
			return true;
		}else {
			return false;
		}	
	}
	
	/**
	 * Permet de placer un pique, mais jamais sur la croix définie entre les 4 portes d'une salle,
	 * ce qui permet de ne pas être bloqué sur un pique lors d'un changement de salle.
	 * @param un objet Spike p qui est un pique.
	 * @return un nouvel objet Spike avec une position aléatoire.
	 */
	public Spike setOneSpike(Spike p) {
		Random rand = new Random(); 	
		int a = rand.nextInt(4 - 1 + 1) + 1;
		Double x = (0.15 + (Math.random() * (0.8 - 0.15)));
		Double y = 0.15 + (Math.random() * (0.8 - 0.15));
		
			if(a == 1) {
				x = 0.15 + (Math.random() * (0.4 - 0.15));
				y = 0.15 + (Math.random() * (0.4 - 0.15));
			}if(a == 2) {
				x = 0.15 + (Math.random() * (0.4 - 0.15));
				y = 0.6 + (Math.random() * (0.85 - 0.6));
			}if(a == 3) {
				x = 0.6 + (Math.random() * (0.85 - 0.6));
				y = 0.6 + (Math.random() * (0.85 - 0.6));
			}if(a == 4) {
				x = 0.6 + (Math.random() * (0.85 - 0.6));
				y = 0.15 + (Math.random() * (0.4 - 0.15));
			}
		
		Vector2 pos = new Vector2(x, y);
		p = new Spike(pos);
		return p;
	}
	
	// DRAWING --------------------------------------------------------------------------------------------------
	
	/**
	 * Dessine toutes les parties de la salle.
	 */
	public void drawRoom() {
		makeTearDraw();
		hero.drawGameObject();
		drawOverlayRoom();
		makeDrawPassif();
	}
	
	/**
	 * Dessine la barre de vie, le solde du heros et si la partie est perdue.
	 */
	public void drawOverlayRoom() {
		drawHeroHp();
		drawHeroSolde();
		drawEcranPerdu();
	}

	/**
	 * Affiche le lose screen si l'attribut point de vie du hero est a 0.
	 */
	private void drawEcranPerdu() {
		if (hero.getHp() == 0) {
			StdDraw.picture(0.5, 0.5, ImagePaths.LOSE_SCREEN, 1, 1);
		}
	}
	
	/**
	 * Permet de dessiner les différentes portes.
	 */
	public void drawingDoors(ArrayList<Porte> portes) {
		for (Porte p : portes) {
            p.drawGameObject();
        }
	}
	
	/**
	 * Dessine les projectiles "Tear".
	 */
	public void makeTearDraw() {
		if (!listTear.isEmpty()) {
			ArrayList<Tear> nvlTear = new ArrayList<Tear>();
			for (Tear tear : listTear) {
				if (tear.getPosition().getY() < 1 - RoomInfos.TILE_HEIGHT) {
					if (tear.getPosition().getY() > RoomInfos.TILE_HEIGHT) {
						if (tear.getPosition().getX() > RoomInfos.TILE_HEIGHT) {
							if (tear.getPosition().getX() < 1 - RoomInfos.TILE_HEIGHT) {
								nvlTear.add(tear);
								tear.drawTear();
							}
						}
					}
				} else {
					listTear = nvlTear;
				}
			}
		}
	}
	
	/**
	 * Dessine les items Passif.
	 */
	public void makeDrawPassif() {
		if (!hero.getListItemP().isEmpty()) {
			for (Passif i : hero.getListItemP()) {
				i.drawPassif();
			}
		}
	}
	
	/**
	 * Affiche le nombre de piece que possede le hero
	 */
	public void drawHeroSolde() {
		double posX = 0.1;
		double posY = 0.1;
		StdDraw.picture(posX, posY, ImagePaths.COIN, 0.065, 0.05);
		StdDraw.setPenColor(StdDraw.BLACK);
		int solde = hero.getSolde();
		StdDraw.text(0.15, 0.1, "" + solde);
	}
	
	/**
	 * Permet d'afficher la barre de vie du hero
	 */
	public void drawHeroHp() {
		double positionCoeurX = 0.05;
		double positionCoeurXVides = 0.05;
		double positionCoeurXPourDemi = 0.05;
		double positionCoeurY = 0.95;
		for (int i = 0; i < hero.getHpDeBase() / 2; i++) {
			StdDraw.picture(positionCoeurXVides, positionCoeurY, ImagePaths.EMPTY_HEART_HUD, 0.08, 0.08);
			positionCoeurXVides += 0.1;
		}
		if (hero.getHp() % 2 == 0) {
			for (int i = 0; i < hero.getHp() / 2; i++) {
				StdDraw.picture(positionCoeurX, positionCoeurY, ImagePaths.HEART_HUD, 0.08, 0.08);
				positionCoeurX += 0.1;
			}
		} else {
			for (int i = 0; i < hero.getHp() / 2; i++) {
				StdDraw.picture(positionCoeurXPourDemi, positionCoeurY, ImagePaths.HEART_HUD, 0.08, 0.08);
				positionCoeurXPourDemi += 0.1;
			}
			StdDraw.picture(positionCoeurXPourDemi, positionCoeurY, ImagePaths.HALF_HEART_HUD, 0.08, 0.08);
		}
	}
	
	/**
	 * Convert a tile index to a 0-1 position.
	 * 
	 * @param indexX
	 * @param indexY
	 * @return
	 */
	private static Vector2 positionFromTileIndex(int indexX, int indexY)
	{
		return new Vector2(indexX * RoomInfos.TILE_WIDTH + RoomInfos.HALF_TILE_SIZE.getX(),
				indexY * RoomInfos.TILE_HEIGHT + RoomInfos.HALF_TILE_SIZE.getY());
	}
	
	public Hero getHero()
	{
		return hero;
	}
	
	public void setHero(Hero hero)
	{
		this.hero = hero;
	}
	
	public ArrayList<Mob> getListmob() {
		return listMob;
	}

	public void setListmob(ArrayList<Mob> listMob) {
		this.listMob = listMob;
	}
}
