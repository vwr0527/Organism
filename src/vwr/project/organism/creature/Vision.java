package vwr.project.organism.creature;

import java.awt.Graphics;
import java.util.BitSet;

import vwr.geom.Point;

//A 2 dimensional creature has a 1 dimensional vision
public class Vision {
	private BitSet image;
	public void draw(Graphics G, Point pos, double rot, double fov)
	{
		
	}
	public void clear()
	{
		image.clear();
	}
}
