package vwr.project.organism.ents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import vwr.geom.Clip2D;
import vwr.geom.ClipRect;
import vwr.geom.Point;

public abstract class Collider extends Entity
{
	public boolean noclip;
	public Clip2D clip;
	public double mass;
	public Collider()
	{
		super();
		mass = 1;
	}
	public void draw(Graphics g)
	{
		if(clip == null) return;
		if(clip.colliding)
		{
			g.setColor(Color.pink);
		} else {
			g.setColor(Color.orange);
		}
		clip.draw(g);
	}
	
	public void update()
	{
		super.update();
		if(clip != null)
		clip.update(pos);
	}
	
	public void collide(LinkedList<Collider> collidable)
	{
		if(clip == null) return;
		clip.colliding = false;
		if(clip == null) return;
		for(Collider c : collidable)
		{
			if(c == this) continue;
			forAllOthers(c);
			if(this.clip.intersecting(c.clip))
			{
				forIntersecting(c);
			}
		}
	}
	public void forAllOthers(Collider c)
	{
		
	}
	public void forIntersecting(Collider c)
	{
		if(c.noclip || noclip) return;
		Point dist = new Point(c.pos.x-pos.x, c.pos.y-pos.y);
		dist.normalize();
		dist.multiply(-1);
		if(c.clip instanceof ClipRect)
		{
			Rectangle2D intersection = (((ClipRect)c.clip).rect.createIntersection(((ClipRect)clip).rect));
			double area = intersection.getWidth() * intersection.getHeight();
			dist.multiply(area/100);
		}
		vel.add(dist);
	}
	
	public Clip2D getClip()
	{
		return clip;
	}
}
