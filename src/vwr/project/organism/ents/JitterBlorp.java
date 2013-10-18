package vwr.project.organism.ents;
import java.awt.Graphics;

import vwr.geom.Point;


public class JitterBlorp extends Entity
{
	public void draw(Graphics g)
	{
		int width = (int)(Math.random()*4) + 8;
		int height = (int)(Math.random()*4) + 8;
		g.fillOval((int)(pos.x)-(width/2),
				   (int)(pos.y)-(height/2),
				   width,
				   height);
	}
	public void update()
	{
		vel.add(Point.frand(1.0,1.0));
		vel.multiply(0.9);
		super.update();
	}
}
