package core;

public enum RotationDirection {
    CW,
    CCW
    ;

	public static RotationDirection getOpposite(RotationDirection rd) {
		if (RotationDirection.CCW.equals(rd)) {
			return RotationDirection.CW;
		}
		return RotationDirection.CCW;
	}
}
