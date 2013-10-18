package vwr.project.organism.ents;

import java.awt.Color;
import java.awt.Graphics;

import vwr.geom.ClipRect;
import vwr.geom.Point;

public class Blob extends Collider{
	
	public Blob()
	{
		pos = Point.frand(400, 400);
		clip = new ClipRect(12, 12);
	}
	
	@Override
	public void draw(Graphics g)
	{
		if(clip.colliding)
		{
			g.setColor(Color.pink);
			//clip.draw(g);
		} else {
			g.setColor(Color.orange);
		}
		int width = (int)(Math.random()*4) + 8;
		int height = (int)(Math.random()*4) + 8;
		g.fillOval((int)(pos.x)-(width/2),
				   (int)(pos.y)-(height/2),
				   width,
				   height);
	}
	@Override
	public void update()
	{
		vel.add(Point.frand(0.01,0.01));
		vel.multiply(0.9);
		super.update();
	}
	@Override
	public void forAllOthers(Collider c)
	{
		Point dist = new Point(c.pos.x-pos.x, c.pos.y-pos.y);
		double distance = dist.magnitude();
		if(distance > 20)
		{
			dist.normalize();
			dist.multiply(10/((distance*distance)+10));
			vel.add(dist);
		}
	}

	@Override
	public void forIntersecting(Collider c)
	{
		Point dist = new Point(c.pos.x-pos.x, c.pos.y-pos.y);
		double distance = dist.magnitude();
		dist.normalize();
		dist.multiply(-100/((distance*distance*distance)+50));
		vel.add(dist);
	}
}
