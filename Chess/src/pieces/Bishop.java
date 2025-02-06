package pieces;

import java.io.IOException;

import javax.imageio.ImageIO;

import tiles.Tile;
import tiles.TileManager;

public class Bishop extends Piece{

	public Bishop(PieceColor color, int x, int y) throws IOException {
		super(color, x, y, "/pieces/test.png", "/pieces/test.png");
	}

	@Override
	public boolean move(int targetX, int targetY) {
		System.out.println("moving");
		Tile oldTile = currentTile;
		Tile targetTile = TileManager.findTile(targetX, targetY);
			//makes sure that the pawn can only move forward and only by 1-2 spaces
		System.out.println("x: " + x + " y: " + y);
		System.out.println("tilex: " + targetTile.getLeftX() + " tiley: " + targetTile.getTopY());
		if ((y - targetTile.getTopY() <= 700 || y - targetTile.getTopY() >= -700) && x - targetTile.getLeftX() == y - targetTile.getTopY()) {
			System.out.println("yippee");
			if (targetTile.getPiece() != null) {
				System.out.println("piece is there");
				if (targetTile.getPiece().getColor() != color) {
					
				} else {
					return false;
				}
				
				
			} else {
				//take an unoccupied tile
				targetTile.occupyTile(this);
				currentTile = targetTile;
				this.x = targetTile.getLeftX();
				this.y = targetTile.getTopY();
				oldTile.unoccupyTile();	
				chessPanel.callDraw();
				return true;
			}
			

		}
		System.out.println("aaaa");
			 
		return false;
	}
}
