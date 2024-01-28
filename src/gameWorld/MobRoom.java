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
import libraries.Physics;
import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;
import resources.RoomInfos;

public class MobRoom extends Room { // initiaisation de toutes les variables
	private Hero hero;
	private Vector2 position;
	private Vector2 size;
	private String imagePath;
	
	private ArrayList<Spike> spikes; // piques.
	private Spike s1;
	private Spike s2;
	private Spike s3;
	
	private ArrayList<Porte> portes; // portes.
	private Porte p0;
	private Porte p1;
	private Porte p2;
	private Porte p3;
	
	private ArrayList<Tear> listTear; // différentes listes.
	private ArrayList<Mob> listMob;
	private ArrayList<Passif> listItemP;
	private HashMap<Integer, TearHero> TearRangeIndex;
	
	private int horlogeAll; // toutes les horloges.
	private int horlogeTear;
	private int horlogeMob;
	private int horlogeHero;
	private int horlogeItemP;
	private int horlogeItemC;
	private int horlogeToucan;
	private int cycleRangeTear;
	
	private int itemChoose;
	private boolean isClearC;
	
	private PMartyr martyr ;
	private PCoeur coeur ;
	private PToucan toucan ;
	private Consommable coin;
	private Consommable nickel;
	private Consommable dime;
	private Consommable halfCoeur;
	private Consommable fullCoeur;
	
	public MobRoom(Hero hero) {
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
		
		// initialisation de la liste des piques.
		this.spikes = new ArrayList<Spike>();
		// création des positions des piques, avec des nombres aléatoire.
		this.spikes = super.setSpikes(spikes, s1, s2, s3);
		
		// initialisation des horloges.
		this.horlogeAll = 0;
		this.horlogeTear = 0;
		this.horlogeMob = 0;
		this.horlogeHero = 0;
		this.horlogeToucan = 0;
		this.cycleRangeTear = 0;
		this.horlogeItemP = 0;
		this.horlogeItemC = 0;
		
		// initialisation des listes.
		this.listTear = new ArrayList<Tear>();
		this.listMob = new ArrayList<Mob>();
		this.listItemP = new ArrayList<Passif>();
		this.TearRangeIndex = new HashMap<Integer, TearHero>();
		this.isClearC = false;
		
		// Selection un chiffre aléatoire pour savoir quel item est droppé.
		Random r = new Random(); 
		itemChoose = r.nextInt(5 - 1 + 1) + 1;
	}
	
	// FONCTIONS - Gestion de toutes les fonctions. ------------------------------------------------------

	/**
	 * Update toutes les fonctinos nécesaires au déroulement d'une salle.
	 */
	public void updateRoom() {
		horlogeAll++;
		heroAction();
		passifAction();
		tearAction();
		consommableAction();
		mobAction();
		passifToucanTir();
	}
	
	public void heroAction() {
		horlogeHero++;
		super.makeHeroPlay();
		heroRedeviensVulnerable();
		super.heroTakeDmg(listMob, spikes);
	}

	public void mobAction() {
		horlogeMob++;
		supprMob();
		makeMobUpdate();
		shootTearMob();
	}

	public void tearAction() {
		horlogeToucan++;
		horlogeTear++;
		cycleRangeTear++;
		TearMakeDmg();
		super.supprTearRange(listTear, TearRangeIndex, cycleRangeTear);
		super.makeTearUpdate(listTear);
	}

	public void passifAction() {
		horlogeItemP++;
		super.updateItems();
		passifDropping();
		collisionPassif();
	}

	public void consommableAction() {
		horlogeItemC++;
		consommableDropping();
		collisionConsommable();
	}
	
	// MOB - partie mobs -------------------------------------------------------------------------------
	
	/**
	 * Met a jour les mob
	 */
	public void makeMobUpdate() {
		int x = 2;
		if (!listMob.isEmpty()) {
			if (horlogeMob - x > 0) {
				for (Mob mob : listMob) {
					mob.mobSeDeplace();
					horlogeMob = 0;
				}
			}
		}
	}
	
	/**
	 * Supprime un mob si son attribut point de vie est a 0
	 */
	public void supprMob() {
		ArrayList<Mob> nvlMob = new ArrayList<Mob>();
		for (Mob mob : listMob) {
			if (mob.getHp() > 0) {
				nvlMob.add(mob);
			}
		}
		listMob = nvlMob;
	}
	
	// HERO -------------------------------------------------------------------------------------------
	
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
	
	// PASSIF -----------------------------------------------------------------------------------------
	
	/**
	 * Active les effets des items passif lors de la collision
	 * @return un boolean si le heros entre en collision avec un des items.
	 */
	public boolean collisionPassif() {
			if(martyr != null) {
				if (Physics.rectangleCollision(martyr.getPosition(), martyr.getSize(), hero.getPosition(),
						hero.getSize())) {
					martyr.setDropped(true);
					martyr.effetItem(hero);
					hero.getListItemP().add(martyr);
					hero.setPossedeMartyr(true);
					martyr = null;
					return true;
				}
			}if(coeur != null) {
				if (Physics.rectangleCollision(coeur.getPosition(), coeur.getSize(), hero.getPosition(),
						hero.getSize())) {
					coeur.setDropped(true);
					coeur.effetItem(hero);
					hero.setPossedeCoeur(true);
					coeur = null;
					return true;
				}
			}if(toucan != null) {
				if (Physics.rectangleCollision(toucan.getPosition(), toucan.getSize(), hero.getPosition(),
						hero.getSize())) {
					toucan.setDropped(true);
					toucan.effetItem(hero);
					hero.getListItemP().add(toucan);
					hero.setPossedeToucan(true);
					toucan = null;
					return true;
				}
			}
		return false;
	}
	
	/**
	 * Donne une chance au joueur d'obtenir un passif lorsqu'il a tue tous les mobs
	 * d'une salle
	 */
	public void passifDropping() {
		if (listMob.isEmpty()) {
			if(hero.getListItemP().size() < 2) {
				double roulette = Math.random();
				if(roulette < 0.8) {
					if(itemChoose == 1 | itemChoose == 2 && !hero.getPossedeMartyr()) {
						martyr = new PMartyr(hero);
					} if(itemChoose == 3 | itemChoose == 4 && !hero.getPossedeCoeur()){
						coeur = new PCoeur(hero);			
					} if(itemChoose == 5 && !hero.getPossedeToucan()){
						toucan = new PToucan(hero);			
					} 
				}
			}
		}
	}
	
	// CONSOMMABLE ------------------------------------------------------------------------------------------
	
	/**
	 * Donne une chance au joueur d'obtenir un consommable lorsqu'il a tue tous les
	 * mobs d'une salle
	 */
	public void consommableDropping() {
		if (!isClearC) {
			if (listMob.isEmpty()) {
				double roulette = Math.random();
				if (roulette < 0.9) {
					Random rand = new Random(); 
					int coinOrHeart = rand.nextInt(2 - 1 + 1) + 1;
					if (coinOrHeart == 1) {
						Random r = new Random(); 
						int nb = r.nextInt(3 - 1 + 1) + 1;
						if (nb == 1) {
							coin = new Piece(1);
							coin.setPosition(RoomInfos.POS_PIECE);
						}
						if (nb == 2) {
							nickel = new Piece(5);
							nickel.setPosition(RoomInfos.POS_PIECE);
						}
						if (nb == 3) {
							dime = new Piece(10);
							dime.setPosition(RoomInfos.POS_PIECE);
						}
					} else {
						Random r = new Random(); 
						int nb = r.nextInt(2 - 1 + 1) + 1;
						if(nb == 1) {
							halfCoeur = new CCoeur(1);
							halfCoeur.setPosition(RoomInfos.POS_COEUR);
						}else {
							fullCoeur = new CCoeur(2);
							fullCoeur.setPosition(RoomInfos.POS_COEUR);
						}
					}
				}
				isClearC = true;
			}
		}
	}
	
	/**
	 * Active les effets des items consommable lors de la collision
	 * @return un boolean si le heros entre en collision avec un des consommables.
	 */
	public boolean collisionConsommable() {
		
		if(coin != null) {
			if (Physics.rectangleCollision(coin.getPosition(), coin.getSize(), hero.getPosition(),
					hero.getSize())) {
				coin = null;
				hero.setSolde(hero.getSolde()+1);
				return true;
			}
		}if(nickel != null) {
			if (Physics.rectangleCollision(nickel.getPosition(), nickel.getSize(), hero.getPosition(),
					hero.getSize())) {
				nickel = null;
				hero.setSolde(hero.getSolde()+5);
				return true;
			}
		}if(dime != null) {
			if (Physics.rectangleCollision(dime.getPosition(), dime.getSize(), hero.getPosition(),
					hero.getSize())) {
				dime = null;
				hero.setSolde(hero.getSolde()+10);
				return true;
			}
		}if(halfCoeur != null) {
			if (Physics.rectangleCollision(halfCoeur.getPosition(), halfCoeur.getSize(), hero.getPosition(),
					hero.getSize())) {
				if(hero.getHp()+1 <= hero.getHpDeBase()) {
					halfCoeur = null;
					hero.setHp(hero.getHp()+1);
					return true;
				}else {
					return false;
				}
			}
		}if(fullCoeur != null) {
			if (Physics.rectangleCollision(fullCoeur.getPosition(), fullCoeur.getSize(), hero.getPosition(),
					hero.getSize())) {
				if (hero.getHp() != hero.getHpDeBase()) {
				    if (hero.getHp() + fullCoeur.getValeur() >= hero.getHpDeBase()) {
				        hero.setHp(hero.getHpDeBase());  
				    } else {
				        hero.setHp(hero.getHp() + fullCoeur.getValeur()); 
				    }
				    fullCoeur = null;
				    return true;
				}
			}
		}
		return false;
	}
	
	// TEAR --------------------------------------------------------------------------------------------------
	
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
	 * Crée les projectiles des mouches.
	 */
	private void shootTearMob() {
		Vector2 viseHero = hero.getPosition();
		int x = 50;
		if (horlogeAll - x > 0) {
			horlogeAll = 0;
			for (Mob mob : listMob) {
				if (mob instanceof Fly) {
					
					listTear.add(new TearMob(mob, viseHero));
				}
			}
		}
	}
	
	/**
	 * Crée les projectiles du Toucan.
	 */
	private void passifToucanTir() {
        int x = 140;
        if(horlogeToucan - x > 0) {
        if(!hero.getListItemP().isEmpty()) {
            if(!listMob.isEmpty()) {
            for(Passif p : hero.getListItemP()) {
                for(Mob m : listMob) {
                    int randomIndex = (int) (Math.random() * (listMob.size() - 1));
                if(p instanceof PToucan) {
                    if(p.isDropped()) {
                        if(listMob.get(randomIndex) == m) {
                        TearToucan a = new TearToucan(hero, m.getPosition());
                        listTear.add(a);
                        horlogeToucan = 0;
                            }
                            }
                        }
                     }
                  }
                }
            }
        }
    }
	
	/**
	 * Supprime un projectile - si il vient du hero et qu'il touche un mob - si il
	 * vient d'un mob et qu'il touche le hero
	 */
	public void supprTearCollision() {
		ArrayList<Tear> nvlTear = new ArrayList<Tear>();
		for (Tear tear : listTear) {
			if (tear instanceof TearHero || tear instanceof TearToucan) {
				for (Mob mob : listMob) {
					if (Physics.rectangleCollision(mob.getPosition(), mob.getSize(), tear.getPosition(),
							tear.getSize())) {
						tear.setAlive(false);
					}
				}
				if (tear.isAlive()) {
					nvlTear.add(tear);
				}
			}
			if (tear instanceof TearMob) {
				if (Physics.rectangleCollision(hero.getPosition(), hero.getSize(), tear.getPosition(),
						tear.getSize())) {
					tear.setAlive(false);
				}
			}
			if (tear.isAlive()) {
				nvlTear.add(tear);
			}
		}
		listTear = nvlTear;
	}
	
	/**
     * affecte des degats au hero si il recoit un projectile d'un Mob et affecte des
     * degats au Mob si il recoit un projectile du Hero
     */
    public void TearMakeDmg() {
        if (!listMob.isEmpty()) {
            if (!listTear.isEmpty()) {
                for (Mob mob : listMob) {
                    for (Tear tear : listTear) {
                        if (tear instanceof TearHero || tear instanceof TearToucan ) {
                            if (Physics.rectangleCollision(mob.getPosition(), mob.getSize(), tear.getPosition(),
                                    tear.getSize())) {
                                mob.setHp(mob.getHp() - tear.getDegat());
                                supprTearCollision();
                            }
                        }
                        if (tear instanceof TearMob) {
                            if (Physics.rectangleCollision(hero.getPosition(), hero.getSize(), tear.getPosition(),
                                    tear.getSize())) {
                                supprTearCollision();
                                if(!hero.isInvTriche()) {
                                hero.setHp(hero.getHp() - tear.getDegat());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
	
	// Drawing -------------------------------------------------------------------------------------------------------------------------
	 
    /**
	 * Dessine toutes les parties de la salle.
	 */
	public void drawRoom() {
		drawGameObject();
		super.drawingDoors(portes);
		super.drawningSpikes(spikes);
		drawItems();
		makeMobDraw();
		hero.drawGameObject();
		super.makeDrawPassif();	
		makeTearDraw();
		super.drawOverlayRoom();
	}
	
	/**
	 * Dessine le font de la salle.
	 */
	public void drawGameObject() {
		StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(),
				0);
	}
	
	/**
	 * Dessine les différents items si ils sont selectionnés.
	 */
	public void drawItems() {
		if(martyr != null && !collisionPassif() && !hero.getPossedeMartyr()) {
			martyr.drawPassif();
		}if(coeur != null && !collisionPassif() && !hero.getPossedeCoeur()) {
			coeur.drawPassif();
		}if(coin != null && !collisionConsommable()) {
			coin.drawConsommable();
		}if(nickel != null && !collisionConsommable()) {
			nickel.drawConsommable();
		}if(dime != null && !collisionConsommable()) {
			dime.drawConsommable();
		}if(halfCoeur != null && !collisionConsommable()) {
			halfCoeur.drawConsommable();
		}if(fullCoeur != null && !collisionConsommable()) {
			fullCoeur.drawConsommable();
		}if(toucan != null && !collisionPassif() && !hero.getPossedeToucan()) {
			toucan.drawPassif();
		}
	}
	
	/**
	 * Dessine les mobs.
	 */
	public void makeMobDraw() {
		if (!listMob.isEmpty()) {
			for (Mob mob : listMob) {
				mob.drawMob();
			}
		}
	}
	
	/**
	 * Dessine les tears.
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
	
	public ArrayList<Mob> getListmob() {
		return listMob;
	}

	public void setListmob(ArrayList<Mob> listMob) {
		this.listMob = listMob;
	}
	
}
