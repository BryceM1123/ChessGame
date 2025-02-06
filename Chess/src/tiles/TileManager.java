package tiles;

public class TileManager {
	//create all the tiles manually
	private static final Tile A8 = new Tile(0, 99, 0, 99, "A8");
	private static final Tile B8 = new Tile(100, 199, 0, 99, "B8");
	private static final Tile C8 = new Tile(200, 299, 0, 9, "C8");
	private static final Tile D8 = new Tile(300, 399, 0, 99, "D8");
	private static final Tile E8 = new Tile(400, 499, 0, 99, "E8");
	private static final Tile F8 = new Tile(500, 599, 0, 99, "F8");
	private static final Tile G8 = new Tile(600, 699, 0, 99, "G8");
	private static final Tile H8 = new Tile(700, 800, 0, 99, "H8");

	private static final Tile A7 = new Tile(0, 99, 100, 199, "A7");
	private static final Tile B7 = new Tile(100, 199, 100, 199, "B7");
	private static final Tile C7 = new Tile(200, 299, 100, 199, "C7");
	private static final Tile D7 = new Tile(300, 399, 100, 199, "D7");
	private static final Tile E7 = new Tile(400, 499, 100, 199, "E7");
	private static final Tile F7 = new Tile(500, 599, 100, 199, "F7");
	private static final Tile G7 = new Tile(600, 699, 100, 199, "G7");
	private static final Tile H7 = new Tile(700, 800, 100, 199, "H7");

	private static final Tile A6 = new Tile(0, 99, 200, 299, "A6");
	private static final Tile B6 = new Tile(100, 199, 200, 299, "B6");
	private static final Tile C6 = new Tile(200, 299, 200, 299, "C6");
	private static final Tile D6 = new Tile(300, 399, 200, 299, "D6");
	private static final Tile E6 = new Tile(400, 499, 200, 299, "E6");
	private static final Tile F6 = new Tile(500, 599, 200, 299, "F6");
	private static final Tile G6 = new Tile(600, 699, 200, 299, "G6");
	private static final Tile H6 = new Tile(700, 800, 200, 299, "H6");

	private static final Tile A5 = new Tile(0, 99, 300, 399, "A5");
	private static final Tile B5 = new Tile(100, 199, 300, 399, "B5");
	private static final Tile C5 = new Tile(200, 299, 300, 399, "C5");
	private static final Tile D5 = new Tile(300, 399, 300, 399, "D5");
	private static final Tile E5 = new Tile(400, 499, 300, 399, "E5");
	private static final Tile F5 = new Tile(500, 599, 300, 399, "F5");
	private static final Tile G5 = new Tile(600, 699, 300, 399, "G5");
	private static final Tile H5 = new Tile(700, 800, 300, 399, "H5");

	private static final Tile A4 = new Tile(0, 99, 400, 499, "A4");
	private static final Tile B4 = new Tile(100, 199, 400, 499, "B4");
	private static final Tile C4 = new Tile(200, 299, 400, 499, "C4");
	private static final Tile D4 = new Tile(300, 399, 400, 499, "D4");
	private static final Tile E4 = new Tile(400, 499, 400, 499, "E4");
	private static final Tile F4 = new Tile(500, 599, 400, 499, "F4");
	private static final Tile G4 = new Tile(600, 699, 400, 499, "G4");
	private static final Tile H4 = new Tile(700, 800, 400, 499, "H4");

	private static final Tile A3 = new Tile(0, 99, 500, 599, "A3");
	private static final Tile B3 = new Tile(100, 199, 500, 599, "B3");
	private static final Tile C3 = new Tile(200, 299, 500, 599, "C3");
	private static final Tile D3 = new Tile(300, 399, 500, 599, "D3");
	private static final Tile E3 = new Tile(400, 499, 500, 599, "E3");
	private static final Tile F3 = new Tile(500, 599, 500, 599, "F3");
	private static final Tile G3 = new Tile(600, 699, 500, 599, "G3");
	private static final Tile H3 = new Tile(700, 800, 500, 599, "H3");

	private static final Tile A2 = new Tile(0, 99, 600, 699, "A2");
	private static final Tile B2 = new Tile(100, 199, 600, 699, "B2");
	private static final Tile C2 = new Tile(200, 299, 600, 699, "C2");
	private static final Tile D2 = new Tile(300, 399, 600, 699, "D2");
	private static final Tile E2 = new Tile(400, 499, 600, 699, "E2");
	private static final Tile F2 = new Tile(500, 599, 600, 699, "F2");
	private static final Tile G2 = new Tile(600, 699, 600, 699, "G2");
	private static final Tile H2 = new Tile(700, 800, 600, 699, "H2");

	private static final Tile A1 = new Tile(0, 99, 700, 799, "A1");
	private static final Tile B1 = new Tile(100, 199, 700, 799, "B1");
	private static final Tile C1 = new Tile(200, 299, 700, 799, "C1");
	private static final Tile D1 = new Tile(300, 399, 700, 799, "D1");
	private static final Tile E1 = new Tile(400, 499, 700, 799, "E1");
	private static final Tile F1 = new Tile(500, 599, 700, 799, "F1");
	private static final Tile G1 = new Tile(600, 699, 700, 799, "G1");
	private static final Tile H1 = new Tile(700, 800, 700, 799, "H1");
	
	//put all tiles in a 2D array
	private static Tile[][] tiles = {
		    {A8, B8, C8, D8, E8, F8, G8, H8}, 
		    {A7, B7, C7, D7, E7, F7, G7, H7}, 
		    {A6, B6, C6, D6, E6, F6, G6, H6},
		    {A5, B5, C5, D5, E5, F5, G5, H5},
		    {A4, B4, C4, D4, E4, F4, G4, H4}, 
		    {A3, B3, C3, D3, E3, F3, G3, H3}, 
		    {A2, B2, C2, D2, E2, F2, G2, H2}, 
		    {A1, B1, C1, D1, E1, F1, G1, H1}};
	
	
	public static Tile[][] getTiles() {
		return tiles;
	}
	//returns a tile from two coordinates
	public static Tile findTile(int x, int y) {
		return binarySearch(tiles, x, y);
	}
	
	//binary search
	private static Tile binarySearch(Tile[][] dataSet, int targetX, int targetY) {
		int upper = dataSet.length - 1;
		int lower = 0;
		int selectedColumn;
		int topY;
		int botY;
		
		// break early before an invalid tile can go through the search
		if (targetX > 800 || targetX < 0 || targetY > 800 || targetY < 0) {
			System.out.println("BINARY SEARCH ERROR");
			return null;
		}
		
		//check For Y values 
		while (lower <= upper) {	
			int mid = (lower + upper) / 2;
			topY = dataSet[mid][1].getTopY();
			botY = dataSet[mid][1].getBotY();
			if ((targetY >= topY) && (targetY <= botY)) {
				selectedColumn = mid;
				
				//check for X values
				upper = dataSet[0].length - 1;
				lower = 0;
				int leftX;
				int rightX;
	
				while (lower <= upper) {
					mid = (lower + upper) / 2;
					leftX = dataSet[selectedColumn][mid].getLeftX();
					rightX = dataSet[selectedColumn][mid].getRightX();
					if ((targetX >= leftX) && (targetX <= rightX)) {
						return dataSet[selectedColumn][mid];
					}
					else if (leftX < targetX) {
						lower = mid + 1;
					}
					else if (rightX > targetX) {
						upper = mid - 1;
					}
					//unable to match an X value to the Y value
					//this should never occur 
					else {
						System.out.println("target not found (X)");
						return null;
					}
				}		
			}
			else if (botY < targetY) {
				lower = mid + 1;
			}
			else if (topY > targetY) {
				upper = mid - 1;
			}
		}
		//Unable to find an initial Y value.
		//this should never occur
		System.out.println("target not found");
		return null;
	}
}

