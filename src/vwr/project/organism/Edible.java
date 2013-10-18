package vwr.project.organism;

import vwr.geom.Point;

public interface Edible {
	public double getNutrients();
	public double eatenAction(Point mouthpos, double bite);
	public boolean hasBeenEaten();
}
