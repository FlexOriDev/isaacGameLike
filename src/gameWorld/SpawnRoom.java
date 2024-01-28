package gameWorld;

import java.util.ArrayList;
import java.util.Random;

import gameobjects.Consommable;
import gameobjects.Hero;
import gameobjects.PMartyr;
import gameobjects.Passif;
import libraries.StdDraw;
import libraries.Vector2;
import resources.ImagePaths;
import resources.RoomInfos;

public class SpawnRoom extends Room {
	
	private Vector2 position;
	private Vector2 size;
	private String imagePath;
	
	private ArrayList<Porte> portes;
	private Porte p0;
	private Porte p1;
	private Porte p2;
	private Porte p3;
	
	public SpawnRoom(Hero hero) {
		super(hero);
		this.position = RoomInfos.POSITION_CENTER_OF_ROOM;
		this.size = RoomInfos.SIZE_TEXTURE_ROOM;
		this.imagePath = ImagePaths.FONT;
		
		this.p0 = new Porte(RoomInfos.POSITION_DOOR_0, RoomInfos.SIZE_DOOR_0, 0.);
		this.p1 = new Porte(RoomInfos.POSITION_DOOR_1, RoomInfos.SIZE_DOOR_1, -90.);
		this.p2 = new Porte(RoomInfos.POSITION_DOOR_2, RoomInfos.SIZE_DOOR_2, 180.);
		this.p2.setBoss(true);
		this.p3 = new Porte(RoomInfos.POSITION_DOOR_3, RoomInfos.SIZE_DOOR_3, 90.);
		this.portes = new ArrayList<Porte>();
		
		this.portes.add(p0);
		this.portes.add(p1);
		this.portes.add(p2);
		this.portes.add(p3);
	}
	
	public void updateRoom() {
		super.updateRoom();
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
		super.drawRoom();
	}

	/**
	 * Dessine le fond de la salle.
	 */
	public void drawGameObject() {
		StdDraw.picture(getPosition().getX(), getPosition().getY(), getImagePath(), getSize().getX(), getSize().getY(),
				0);	
	}
	
	/*
	 * Getters and Setters --------------------------------------------------------------------------------
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
