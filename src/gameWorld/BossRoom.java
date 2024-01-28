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

public class BossRoom extends Room { // initiaisation de toutes les variables
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
	private HashMap<Integer, TearHero> TearRangeIndex;
	
	private int horlogeAll;
	private int horlogeTear;
	private int horlogeMob;
	private int horlogeHero;
	private int horlogeItemP;
	private int horlogeToucan;
	private int cycleRangeTear;

	public BossRoom(Hero hero) {
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
		
		// initialisation des listes.
		this.listTear = new ArrayList<Tear>();
		this.listMob = new ArrayList<Mob>();
		this.TearRangeIndex = new HashMap<Integer, TearHero>();
	}
	
	// FONCTIONS - Gestion de toutes les fonctions. ---------------------------------------------------

	/**
	 * Update toutes les fonctinos nécesaires au déroulement d'une salle.
	 */
	public void updateRoom() {
		horlogeAll++;
		heroAction();
		passifAction();
		tearAction();
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
	
	// TEAR -------------------------------------------------------------------------------------------
	
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
     * Crée les projectiles du boss
     * Le boss devient plus puissant lorsque qu'il est bas en PV
     */
    private void shootTearMob() {
    
            for (Mob mob : listMob) {
                int x = 50;
                if(mob instanceof Boss && mob.getHp() <= 5) {
                     x = 20;
                }
                if (horlogeAll - x > 0) {
                    horlogeAll = 0;
                if (mob instanceof Boss) {
                    Vector2 haut = new Vector2(0, 1);
                    Vector2 bas = new Vector2(0, -1);
                    Vector2 gauche = new Vector2(-1, 0);
                    Vector2 droite = new Vector2(1, 0);
                    Vector2 diagHautGauche = new Vector2(-1, 1);
                    Vector2 diagHautDroite = new Vector2(1, 1);
                    Vector2 diagBasGauche = new Vector2(-1, -1);
                    Vector2 diagBasDroite = new Vector2(1, -1);
                    listTear.add(new TearMob(mob, haut));
                    listTear.add(new TearMob(mob, bas));
                    listTear.add(new TearMob(mob, gauche));
                    listTear.add(new TearMob(mob, droite));
                    listTear.add(new TearMob(mob, diagHautGauche));
                    listTear.add(new TearMob(mob, diagHautDroite));
                    listTear.add(new TearMob(mob, diagBasGauche));
                    listTear.add(new TearMob(mob, diagBasDroite));

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
                            //    supprTearCollision();
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
				if(p instanceof PToucan) {
					if(p.isDropped()) {
						
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
	
	/*
	 * Drawing --------------------------------------------------------------------------------------------
	 */
	
	/**
	 * Dessine toutes les parties de la salle.
	 */
	public void drawRoom() {
		drawGameObject();
		super.drawingDoors(portes);
		super.drawningSpikes(spikes);
		makeMobDraw();
		hero.drawGameObject();
		super.makeDrawPassif();	
		makeTearDraw();
		drawBossHp();
		super.drawOverlayRoom();
		drawEcranGagne();
	}
	
	/**
	 * Dessine le fond de la salle.
	 */
	public void drawGameObject() {
		StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(),
				0);
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
	 * Dessine le fond quand la partie est gagnée.
	 */
	private void drawEcranGagne() {
		if (listMob.isEmpty()) {
			StdDraw.picture(0.5, 0.5, ImagePaths.WIN_SCREEN, 1, 1);
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
	
	/**
	 * Dessine les points de vie du Boss
	 */
	private void drawBossHp() {
        for(Mob m : listMob)
            if(m instanceof Boss && m.getHp() !=0) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(0.75, 0.025, "POINTS DE VIE DU BOSS");
            StdDraw.filledRectangle(0.75, 0.1, 0.15, 0.05);
            StdDraw.setPenColor(StdDraw.BOOK_RED);
            StdDraw.filledRectangle(0.7525, 0.1025, m.getHp()*0.009, 0.0325);
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
