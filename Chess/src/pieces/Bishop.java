package pieces;

import java.io.IOException;

import javax.imageio.ImageIO;

import tiles.Tile;
import tiles.TileManager;

public final class Bishop extends Piece{
	
	public Bishop(PieceColor color, int x, int y) throws IOException {
		super(color, x, y, "/pieces/White_Bishop.png", "/pieces/Black_Bishop.png", "/pieces/test.png");
	}
	
	@Override
	public void showPathing() {
		for (int i = 0; i < tiles.length; ++i)	{
			for (int j = 0; j < tiles[i].length; ++j) {
				if ((y - tiles[i][j].getTopY() <= 700 && y - tiles[i][j].getTopY() > 0) && x - tiles[i][j].getLeftX() == y - tiles[i][j].getTopY()) {
					if (checkCollision(MovementDirection.NORTHEAST, x, y, tiles[i][j].getLeftX(), tiles[i][j].getTopY())) {
						if (tiles[i][j].isOccupied()) {
							if (tiles[i][j].getPiece().getColor() != color) {
								if (tiles[i][j].isResidentKing()) {
									tiles[i][j].setInCheck();
								} 
								else {	
									tiles[i][j].setTargetable();
									targetingStorage.push(new int[]{i, j});
								}
							}
						} else {
							if (tiles[i][j].isCheckable()) {
								tiles[i][j].setChecked();
							}
							tiles[i][j].setPathable();
							pathingStorage.push(new int[]{i, j});
							
						}
					}
				}
				else if ((y - tiles[i][j].getTopY() <= 700 && y - tiles[i][j].getTopY() > 0) && -(x - tiles[i][j].getLeftX()) == y - tiles[i][j].getTopY()) {
					if (checkCollision(MovementDirection.NORTHWEST, x, y, tiles[i][j].getLeftX(), tiles[i][j].getTopY())) {
						if (tiles[i][j].isOccupied()) {
							if (tiles[i][j].getPiece().getColor() != color) {
								if (tiles[i][j].isResidentKing()) {
									tiles[i][j].setInCheck();
								} 
								else {
									tiles[i][j].setTargetable();
									targetingStorage.push(new int[]{i, j});
								}
							}
						} else {
							if (tiles[i][j].isCheckable()) {
								tiles[i][j].setChecked();
							}
							tiles[i][j].setPathable();
							pathingStorage.push(new int[]{i, j});
						}
					}
				}
				else if ((y - tiles[i][j].getTopY() < 0 && y - tiles[i][j].getTopY() >= -700) && -(x - tiles[i][j].getLeftX()) == y - tiles[i][j].getTopY()) {
					if (checkCollision(MovementDirection.SOUTHEAST, x, y, tiles[i][j].getLeftX(), tiles[i][j].getTopY())) {
						if (tiles[i][j].isOccupied()) {
							if (tiles[i][j].getPiece().getColor() != color) {
								if (tiles[i][j].isResidentKing()) {
									tiles[i][j].setInCheck();
								} 
								else {
								
									tiles[i][j].setTargetable();
									targetingStorage.push(new int[]{i, j});
								}
							}
						} else {
							if (tiles[i][j].isCheckable()) {
								tiles[i][j].setChecked();
							}
							tiles[i][j].setPathable();
							pathingStorage.push(new int[]{i, j});
						}
					}
				}
				else if ((y - tiles[i][j].getTopY() < 0 && y - tiles[i][j].getTopY() >= -700) && x - tiles[i][j].getLeftX() == y - tiles[i][j].getTopY()) {
					if (checkCollision(MovementDirection.SOUTHWEST, x, y, tiles[i][j].getLeftX(), tiles[i][j].getTopY())) {
						if (tiles[i][j].isOccupied()) {
							if (tiles[i][j].getPiece().getColor() != color) {
								if (tiles[i][j].isResidentKing()) {
									tiles[i][j].setInCheck();
								} 
								else {
									
									tiles[i][j].setTargetable();
									targetingStorage.push(new int[]{i, j});
								}
							}
						} else {
							if (tiles[i][j].isCheckable()) {
								tiles[i][j].setChecked();
							}
							tiles[i][j].setPathable();
							pathingStorage.push(new int[]{i, j});
						}
					}
				}
			}
		}
	}
}
