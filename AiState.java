/*
 Brendan Aucoin
 2018/05/01
 this holds the data for which ai state the ai is.
 */
package state;

public enum AiState {
	DRAW_CARD(),
	DRAW_MANA(),
	PLAY_CARD(),
	PICK_MANA(),
	PLAY_ON_BOARD(),
	PLAYERS_TURN(),
	
	;
}
