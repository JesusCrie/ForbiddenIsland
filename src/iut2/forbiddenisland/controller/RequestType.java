package iut2.forbiddenisland.controller;

public enum RequestType {
	// Player related requests
	PLAYERS_ALL,
	PLAYER_MOVE_AMOUNT,
	PLAYER_MOVE,
	PLAYER_SEND,
	PLAYERS_SENDABLE,

	// Cells related requests,
	CELLS_ALL,
	CELLS_REACHABLE,
	CELLS_DRYABLE,
	CELL_DRY,
	CELL_CLAIM_TREASURE,

	// Game related requests
	TREASURES_CLAIMABLE,
	FLOODING
}
