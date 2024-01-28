package gameWorld;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import gameobjects.Boss;
import gameobjects.Fly;
import gameobjects.Hero;
import gameobjects.Mob;
import gameobjects.Spider;
import libraries.Physics;
import libraries.StdDraw;
import libraries.Vector2;
import resources.Controls;
import resources.HeroInfos;
import resources.ImagePaths;

/**
 * Représente la partie avec l'ensemble de ses salles, ses mobs, son heros etc..
 */
public class GameWorld {
	
	private Room currentRoom;
	private Hero hero;
	private SpawnRoom spawnroom;
	private ShopRoom shoproom;
	private MobRoom mobroom;
	private MobRoom mobroom2;
	private MobRoom mobroom3;
	private MobRoom mobroom4;
	private BossRoom bossroom;
	
	private int  horlogePiece;

	// A world needs a hero
	public GameWorld(Hero hero) {
		this.hero = hero;
		this.spawnroom = new SpawnRoom(hero);
		this.shoproom = new ShopRoom(hero);
		this.mobroom = new MobRoom(hero);
		this.mobroom2 = new MobRoom(hero);
		this.mobroom3 = new MobRoom(hero);
		this.mobroom4 = new MobRoom(hero);
		this.bossroom = new BossRoom(hero);
		
		Mob f1 = new Fly(hero);
		Mob f2 = new Fly(hero);
		Mob s1 = new Spider(hero);
		mobroom.getListmob().add(f1);
		mobroom.getListmob().add(f2);
		mobroom.getListmob().add(s1);
		
		Mob f3 = new Fly(hero);
		Mob s2 = new Spider(hero);
		Mob s3 = new Spider(hero);
		mobroom2.getListmob().add(f3);
		mobroom2.getListmob().add(s2);
		mobroom2.getListmob().add(s3);
		
		Mob f4 = new Fly(hero);
		Mob f5 = new Fly(hero);
		Mob f6 = new Fly(hero);
		mobroom3.getListmob().add(f4);
		mobroom3.getListmob().add(f5);
		mobroom3.getListmob().add(f6);
		
		Mob s4 = new Spider(hero);
		Mob s5 = new Spider(hero);
		Mob s6= new Spider(hero);
		Mob f7 = new Fly(hero);
		mobroom4.getListmob().add(s4);
		mobroom4.getListmob().add(s5);
		mobroom4.getListmob().add(s6);
		mobroom4.getListmob().add(f7);
		
		Mob boss = new Boss(hero);
		bossroom.getListmob().add(boss);
		
		horlogePiece = 0;
		
		valeursPortes();
		
		currentRoom = spawnroom;
	}
	
	/**
	 * Donne les valeurs de chaque porte selon le plan de salle choisi.
	 */
	public void valeursPortes() {
		
		Boolean existe = true;
		Boolean inexistante = false;
		Boolean porteOuverte = true;
		Boolean porteFermee = false;
		
		spawnroom.getPortes().get(0).setPorteExiste(existe);
		spawnroom.getPortes().get(1).setPorteExiste(existe);
		spawnroom.getPortes().get(2).setPorteExiste(existe);
		spawnroom.getPortes().get(3).setPorteExiste(existe);
		spawnroom.getPortes().get(0).setCle(porteOuverte);
		spawnroom.getPortes().get(1).setCle(porteOuverte);
		spawnroom.getPortes().get(2).setCle(porteOuverte);
		spawnroom.getPortes().get(3).setCle(porteOuverte);
		
		shoproom.getPortes().get(2).setPorteExiste(existe);
		shoproom.getPortes().get(2).setCle(porteOuverte);
		shoproom.getPortes().get(3).setPorteExiste(existe);
		shoproom.getPortes().get(3).setCle(porteOuverte);
		
		bossroom.getPortes().get(0).setPorteExiste(existe);
		bossroom.getPortes().get(0).setCle(porteFermee);
		if (bossroom.getListmob().isEmpty()) {
			bossroom.getPortes().get(0).setCle(porteOuverte);
		}
		
		mobroom.getPortes().get(3).setPorteExiste(existe);
		mobroom.getPortes().get(3).setCle(porteFermee);
		if (mobroom.getListmob().isEmpty()) {
			mobroom.getPortes().get(3).setCle(porteOuverte);
		}
		mobroom.getPortes().get(1).setPorteExiste(existe);
		mobroom.getPortes().get(1).setCle(porteFermee);
		if (mobroom.getListmob().isEmpty()) {
			mobroom.getPortes().get(1).setCle(porteOuverte);
		}
		
		mobroom2.getPortes().get(1).setPorteExiste(existe);
		mobroom2.getPortes().get(1).setCle(porteFermee);
		if (mobroom2.getListmob().isEmpty()) {
			mobroom2.getPortes().get(1).setCle(porteOuverte);
		}
		mobroom2.getPortes().get(0).setPorteExiste(existe);
		mobroom2.getPortes().get(0).setCle(porteFermee);
		if (mobroom2.getListmob().isEmpty()) {
			mobroom2.getPortes().get(0).setCle(porteOuverte);
		}
		
		mobroom3.getPortes().get(1).setPorteExiste(existe);
		mobroom3.getPortes().get(1).setCle(porteFermee);
		if (mobroom3.getListmob().isEmpty()) {
			mobroom3.getPortes().get(1).setCle(porteOuverte);
		}
		mobroom3.getPortes().get(2).setPorteExiste(existe);
		mobroom3.getPortes().get(2).setCle(porteFermee);
		if (mobroom3.getListmob().isEmpty()) {
			mobroom3.getPortes().get(2).setCle(porteOuverte);
		}
		
		mobroom4.getPortes().get(3).setPorteExiste(existe);
		mobroom4.getPortes().get(3).setCle(porteFermee);
		if (mobroom4.getListmob().isEmpty()) {
			mobroom4.getPortes().get(3).setCle(porteOuverte);
		}
		
		 
	}
	
	/**
	 * Permet le changement de salle.
	 */
	public void switchRoom() {
		
		if(currentRoom == spawnroom) {
		
			if(touchDoor(spawnroom, spawnroom.getPortes().get(0))) {
				currentRoom = shoproom;
				hero.setPosition(HeroInfos.POSITION_SPAWN_DOOR_2);
				currentRoom.updateRoom();
			}if(touchDoor(spawnroom, spawnroom.getPortes().get(1))) {
				currentRoom = mobroom;
				hero.setPosition(HeroInfos.POSITION_SPAWN_DOOR_3);
				currentRoom.updateRoom();
			}if(touchDoor(spawnroom, spawnroom.getPortes().get(2))) {
				currentRoom = bossroom;
				hero.setPosition(HeroInfos.POSITION_SPAWN_DOOR_0);
				currentRoom.updateRoom();
			}if(touchDoor(spawnroom, spawnroom.getPortes().get(3))) {
				currentRoom = mobroom2;
				hero.setPosition(HeroInfos.POSITION_SPAWN_DOOR_1);
				currentRoom.updateRoom();	
			}
		}if(currentRoom == shoproom) {
			if(touchDoor(shoproom, shoproom.getPortes().get(2))) {
				currentRoom = spawnroom;
				hero.setPosition(HeroInfos.POSITION_SPAWN_DOOR_0);
				currentRoom.updateRoom();	
			}if(touchDoor(shoproom, shoproom.getPortes().get(3))) {
				currentRoom = mobroom3;
				hero.setPosition(HeroInfos.POSITION_SPAWN_DOOR_1);
				currentRoom.updateRoom();
			}
		}if(currentRoom == mobroom) {
			if(touchDoor(mobroom, mobroom.getPortes().get(1)) && mobroom.getPortes().get(1).getCle() == true) {
				currentRoom = mobroom4;
				hero.setPosition(HeroInfos.POSITION_SPAWN_DOOR_3);
				currentRoom.updateRoom();
			}if(touchDoor(mobroom, mobroom.getPortes().get(3)) && mobroom.getPortes().get(3).getCle() == true) {
				currentRoom = spawnroom;
				hero.setPosition(HeroInfos.POSITION_SPAWN_DOOR_1);
				currentRoom.updateRoom();
			}
		}if(currentRoom == bossroom) {
			if(touchDoor(bossroom, bossroom.getPortes().get(0)) && bossroom.getPortes().get(0).getCle() == true) {
				currentRoom = spawnroom;
				hero.setPosition(HeroInfos.POSITION_SPAWN_DOOR_2);
				currentRoom.updateRoom();
			}
		}if(currentRoom == mobroom2) {
			if(touchDoor(mobroom2, mobroom2.getPortes().get(0)) && mobroom2.getPortes().get(0).getCle() == true) {
				currentRoom = mobroom3;
				hero.setPosition(HeroInfos.POSITION_SPAWN_DOOR_2);
				currentRoom.updateRoom();
			}if(touchDoor(mobroom2, mobroom2.getPortes().get(1)) && mobroom2.getPortes().get(1).getCle() == true) {
				currentRoom = spawnroom;
				hero.setPosition(HeroInfos.POSITION_SPAWN_DOOR_3);
				currentRoom.updateRoom();
			}
		}if(currentRoom == mobroom3) {
			if(touchDoor(mobroom3, mobroom3.getPortes().get(1)) && mobroom3.getPortes().get(1).getCle() == true) {
				currentRoom = shoproom;
				hero.setPosition(HeroInfos.POSITION_SPAWN_DOOR_3);
				currentRoom.updateRoom();
			}if(touchDoor(mobroom3, mobroom3.getPortes().get(2)) && mobroom3.getPortes().get(2).getCle() == true) {
				currentRoom = mobroom2;
				hero.setPosition(HeroInfos.POSITION_SPAWN_DOOR_0);
				currentRoom.updateRoom();
			}
		}if(currentRoom == mobroom4) {
			if(touchDoor(mobroom4, mobroom4.getPortes().get(3)) && mobroom4.getPortes().get(3).getCle() == true) {
				currentRoom = mobroom;
				hero.setPosition(HeroInfos.POSITION_SPAWN_DOOR_1);
				currentRoom.updateRoom();
			}
		}
	}

	/**
	 * Indique si une porte est touchée par un joueur.
	 * @param currentroom un salle de jeu, p une porte de cette salle.
	 * @return vrai si la porte et touchée, faux sinon.
	 */
	public boolean touchDoor(Room currentroom, Porte p) {
		if(Physics.rectangleCollision(hero.getPosition(), hero.getSize(), p.getPosition(), p.getSize())){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Assigne les touches.
	 */
	public void processUserInput() {
		processKeysForMovement();
	}

	/**
	 * Update la salle.
	 */
	public void updateGameObjects() {
		horlogePiece++;
		currentRoom.updateRoom();
		valeursPortes();
		switchRoom();
	}
	
	/**
	 * indique si la partie est perdue.
	 * @return vrai si la partie est perdue.
	 */
	public boolean gameOver() {
		if(hero.getHp() == 0 ) {
			return true;
		} else {
		return false;
		}
	}

	/**
	 * Dessine la salle.
	 */
	public void drawGameObjects() {
		currentRoom.drawRoom();
	}
	

	/*
	 * Keys processing
	 */

	private void processKeysForMovement()
	{
		if (StdDraw.isKeyPressed(Controls.goUp))
		{
			hero.goUpNext();
		}
		if (StdDraw.isKeyPressed(Controls.goDown))
		{
			hero.goDownNext();
		}
		if (StdDraw.isKeyPressed(Controls.goRight))
		{
			hero.goRightNext();
		}
		if (StdDraw.isKeyPressed(Controls.goLeft))
		{
			hero.goLeftNext();
		}
		
		if (StdDraw.isKeyPressed(Controls.shootUp))
		{
			Vector2 direction = new Vector2(0,1);
			currentRoom.shootTearHero(direction);
					
		}
		if (StdDraw.isKeyPressed(Controls.shootDown))
		{
			Vector2 direction = new Vector2(0,-1);
			currentRoom.shootTearHero(direction);
		}
		if (StdDraw.isKeyPressed(Controls.shootRight))
		{
			Vector2 direction = new Vector2(1,0);
			currentRoom.shootTearHero(direction);
		}
		if (StdDraw.isKeyPressed(Controls.shootLeft))
		{
			Vector2 direction = new Vector2(-1,0);
			currentRoom.shootTearHero(direction);
		}if (StdDraw.isKeyPressed(Controls.pieceTriche)) 
		{
            int x = 30;
            if(horlogePiece - x > 0 ) {
                hero.setSolde(hero.getSolde()+10);
                horlogePiece = 0;
            }
		}if (StdDraw.isKeyPressed(Controls.invincibleTriche))
        {
            //touche i
            hero.setInvTriche(true);
        }
        
        if (StdDraw.isKeyPressed(Controls.speedTriche))
        {
            //touche l
            hero.setSpeed(0.02);
        }
        if (StdDraw.isKeyPressed(Controls.tueMobTriche))
        {
            //touche k
            ArrayList<Mob> listMob = new ArrayList<Mob>();
            currentRoom.setListmob(listMob);
        }
        if (StdDraw.isKeyPressed(Controls.degatTriche))
        {
            //touche p
            hero.setDegat(100);
        }
	}
}
