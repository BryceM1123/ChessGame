package pieces;

//these are the only valid directions for movement/attacking
//this makes movements objective (north) instead of subjective (forward) or ambiguous (left)
//NORTH is towards the top of the screen
public enum MovementDirection {
	NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST
}
