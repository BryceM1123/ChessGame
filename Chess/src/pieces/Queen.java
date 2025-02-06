package pieces;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Queen extends Piece{
	public Queen(PieceColor color, int x, int y) throws IOException {
		super(color, x, y, "/pieces/White_Queen.png", "/pieces/Black_Queen.png");
	}
	
	@Override
	public boolean move(int targetX, int targetY) {
		return false;
	}
}
