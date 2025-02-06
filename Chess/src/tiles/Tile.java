package tiles;

import pieces.Piece;

public class Tile {
	final private int leftX;
	final private int rightX;
	final private int topY;
	final private int botY;
	final private String name;
	private Piece occupyingPiece;
	
	public Tile(int leftX, int rightX, int topY, int botY, String name) {
		this.leftX = leftX;
		this.rightX = rightX;
		this.topY = topY;
		this.botY = botY;
		this.name = name;
	}
	
	//getters
	public int getLeftX() {
		return leftX;
	}
	
	public int getRightX() {
		return rightX;
	}
	
	public int getTopY() {
		return topY;
	}
	
	public int getBotY() {
		return botY;
	}
	
	public Piece getPiece() {
		return occupyingPiece;
	}
	
	public String getName() {
		return name;
	}
	
	//attempts to fill the tile if it is empty.
	//will need to be updated to allow pieces to attack.
	public boolean occupyTile(Piece incomingPiece) {
		if (occupyingPiece == null) {
			occupyingPiece = incomingPiece;
			return true;
		} 
		return false;
	}
	public void occupyTileByForce(Piece incomingPiece) {
		occupyingPiece = incomingPiece;
	}
	
	public void unoccupyTile() {
		occupyingPiece = null;
	}
}
