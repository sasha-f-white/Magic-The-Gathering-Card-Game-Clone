/*
 Brendan Aucoin
 2018/04/20
 this is an enum to keep track of what phase of the game you are currently on.
 so that you cannot draw cards when you are on a different phase.
 */
package phase;

public enum Phase {
	CARD_DRAW("Draw Card"),//each enum id has a name attached to it.
	MANA_DRAW("Draw Mana"),
	PICK_MANA("Pick Mana"),
	PLAY_CARD("Play Card"),
	PLAY_ON_BOARD("Play on Board"),
	OPPONENTS_TURN("Opponents Turn"),
	SWITCH_TURN("Turn Switch"),
	LOOK_AT_DISCARD("Check Graveyard"),
	SELECT_CARD("Select Card"),
	;
	private String name;
	
	private Phase(String name) {
		this.name= name;
	}
	public String getName() {
		return name;
	}
}
