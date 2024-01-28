package resources;

import libraries.Vector2;

public class HeroInfos
{
	public static Vector2 ISAAC_SIZE = RoomInfos.TILE_SIZE.scalarMultiplication(0.7);
	public static final double ISAAC_SPEED = 0.01;
	
	// Toutes les positions de spawn pour les changements de salle.
	public static final Vector2 POSITION_SPAWN_DOOR_0 = new Vector2(0.5, 0.8);
	public static final Vector2 POSITION_SPAWN_DOOR_1 = new Vector2(0.8, 0.5);
	public static final Vector2 POSITION_SPAWN_DOOR_2 = new Vector2(0.5, 0.2);
	public static final Vector2 POSITION_SPAWN_DOOR_3 = new Vector2(0.2, 0.5);
}
