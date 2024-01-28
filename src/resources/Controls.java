package resources;

import libraries.Keybinding;
import libraries.Keybinding.SpecialKeys;

public class Controls
{
	public static int goUp = Keybinding.keycodeOf('z');
	public static int goDown = Keybinding.keycodeOf('s');
	public static int goRight = Keybinding.keycodeOf('d');
	public static int goLeft = Keybinding.keycodeOf('q');
	public static int invincibleTriche = Keybinding.keycodeOf('i');
    public static int speedTriche = Keybinding.keycodeOf('l');
    public static int tueMobTriche = Keybinding.keycodeOf('k');
    public static int degatTriche = Keybinding.keycodeOf('p');
    public static int pieceTriche = Keybinding.keycodeOf('o');
	
	public static int shootUp = Keybinding.keycodeOf(SpecialKeys.UP);
	public static int shootDown = Keybinding.keycodeOf(SpecialKeys.DOWN);
	public static int shootRight = Keybinding.keycodeOf(SpecialKeys.RIGHT);
	public static int shootLeft = Keybinding.keycodeOf(SpecialKeys.LEFT);
	
	public static int escape = Keybinding.keycodeOf(SpecialKeys.ESCAPE);
	public static int space = Keybinding.keycodeOf(SpecialKeys.SPACE);
	
}
