package resources;

import libraries.Vector2;

public class RoomInfos
{
	public static final int NB_TILES = 9;
	public static final double TILE_WIDTH = 1.0 / NB_TILES;
	public static final double TILE_HEIGHT = 1.0 / NB_TILES;
	public static final Vector2 TILE_SIZE = new Vector2(TILE_WIDTH, TILE_HEIGHT);
	public static final Vector2 HALF_TILE_SIZE = new Vector2(TILE_WIDTH, TILE_HEIGHT).scalarMultiplication(0.5);
	
	public static final Vector2 POSITION_CENTER_OF_ROOM = new Vector2(0.5, 0.5);
	
	
	public static final Vector2 POSITION_WALL_1 = new Vector2(0.5, 1.0);
	public static final Vector2 SIZE_WALL_1 = new Vector2(1.0, 0.2);
	
	public static final Vector2 POSITION_WALL_2 = new Vector2(0.5, 0.0);
	public static final Vector2 SIZE_WALL_2 = new Vector2(1.0, 0.2);
	
	public static final Vector2 POSITION_WALL_3 = new Vector2(0.0, 0.5);
	public static final Vector2 SIZE_WALL_3 = new Vector2(0.175, 1);
	
	public static final Vector2 POSITION_WALL_4 = new Vector2(1.0, 0.5);
	public static final Vector2 SIZE_WALL_4 = new Vector2(0.175, 1);
	
	
	public static final Vector2 SIZE_TEXTURE_ROOM = new Vector2(1.0, 1.0);
	
	public static final Vector2 POSITION_DOOR_0 = new Vector2(0.5, 0.96);
	public static final Vector2 SIZE_DOOR_0 = new Vector2(0.15, 0.12);
	
	public static final Vector2 POSITION_DOOR_1 = new Vector2(0.96, 0.5);
	public static final Vector2 SIZE_DOOR_1 = new Vector2(0.15, 0.12);
	
	public static final Vector2 POSITION_DOOR_2 = new Vector2(0.5, 0.04);
	public static final Vector2 SIZE_DOOR_2 = new Vector2(0.15, 0.12);
	
	public static final Vector2 POSITION_DOOR_3 = new Vector2(0.04, 0.5);
	public static final Vector2 SIZE_DOOR_3 = new Vector2(0.15, 0.12);
	
	public static final Vector2 SIZE_SPIKE = new Vector2(0.1, 0.1);
	
	public static final Vector2 POS_PIECE = new Vector2(0.5, 0.7);
	
	public static final Vector2 POS_COEUR = new Vector2(0.5, 0.3);
}
