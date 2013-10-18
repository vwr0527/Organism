package vwr.geom;

import java.awt.Graphics;

public abstract class Clip2D {
	public boolean colliding;
	
	public abstract void update(Point pos);

	//the only difference between the next two functions
	//is that this one sets colliding to true if true
	// and the intersects(Clip2D) function is meant to be
	// customized for each class that inherits this.
	public boolean intersecting(Clip2D c)
	{
		if(intersects(c))
		{
			colliding = true;
			return true;
		} else
		{
			return false;
		}
	}
	protected abstract boolean intersects(Clip2D c);
	public abstract void draw(Graphics g);
}
