package pieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import tiles.Tile;
import tiles.TileManager;

public class Knight extends Piece {
	
	public Knight(PieceColor color, int x, int y) throws IOException {
		
		super(color, x, y, "/pieces/White_Knight.png", "/pieces/Black_Knight.png", "/pieces/test.png");
	}
	
	@Override
	public void showPathing() {
		for (int i = 0; i < tiles.length; ++i)	{
			for (int j = 0; j < tiles[i].length; ++j) {
				if ((y - tiles[i][j].getTopY() == -200 && x - tiles[i][j].getLeftX() == 100) //down 2 left 1
						|| (y - tiles[i][j].getTopY() == -200 && x - tiles[i][j].getLeftX() == -100) //down 2 right 1
						|| (y - tiles[i][j].getTopY() == 200 && x - tiles[i][j].getLeftX() == 100) //up 2 left 1
						|| (y - tiles[i][j].getTopY() == 200 && x - tiles[i][j].getLeftX() == -100) //up 2 right 1 
						|| (y - tiles[i][j].getTopY() == -100 && x - tiles[i][j].getLeftX() == 200) //left 2 down 1
						|| (y - tiles[i][j].getTopY() == 100 && x - tiles[i][j].getLeftX() == 200) //left 2 up 1
						|| (y - tiles[i][j].getTopY() == -100 && x - tiles[i][j].getLeftX() == -200) //right 2 down 1
						|| (y - tiles[i][j].getTopY() == 100 && x - tiles[i][j].getLeftX() == -200)) {
						
					if (tiles[i][j].isOccupied()) {
						if (tiles[i][j].getPiece().getColor() != color) {
							if (tiles[i][j].isResidentKing()) {
								tiles[i][j].setInCheck();
							} else {
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
