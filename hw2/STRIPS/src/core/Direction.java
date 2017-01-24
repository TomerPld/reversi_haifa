package core;

public enum Direction {
	UP,
	RIGHT,
	DOWN,
	LEFT
	;

	public static Direction getOpposite(Direction direction) {
		if (Direction.UP.equals(direction)) {
			return Direction.DOWN;
		}
		else if (Direction.DOWN.equals(direction)) {
			return Direction.UP;
		}
		else if (Direction.LEFT.equals(direction)) {
			return Direction.RIGHT;
		}
		else {
			return Direction.LEFT;
		}
	}
}
