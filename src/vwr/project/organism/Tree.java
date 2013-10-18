package vwr.project.organism;

import java.awt.Color;
import java.awt.Graphics;

import vwr.geom.Clip2D;
import vwr.geom.ClipRect;
import vwr.geom.Point;
import vwr.project.organism.creature.Creature;
import vwr.project.organism.ents.Collider;
import vwr.project.organism.ents.Entity;
import vwr.project.organism.ents.Fruit;

public class Tree {

	private double _x;
	private double _y;
	private double _width;
	private double _height;
	private Color col;
	private int ripeningPeriod = 400;
	private double ripePeriod = 400;
	private double bearingRate = 0.02;
	
	public Tree(double xpos, double ypos, double w, double h)
	{
		_x = xpos;
		_y = ypos;
		_width = w;
		_height = h;
		col = new Color(25,100,50);
	}
	public Fruit bearFruit()
	{
		if(Math.random() > bearingRate) return null;
		Fruit bananapple = new Fruit();
		if(Math.random() > (_width/_height))
		{
			bananapple.pos.y = Math.random()*_height;
			bananapple.pos.y += _y;
			if(Math.random() > 0.5)
				bananapple.pos.x = _x;
			else
				bananapple.pos.x = _x + _width;
		}
		else
		{
			bananapple.pos.x = Math.random()*_width;
			bananapple.pos.x += _x;
			if(Math.random() > 0.5)
				bananapple.pos.y = _y;
			else
				bananapple.pos.y = _y + _height;
		}
		bananapple.pos.add(Point.frand(2, 2));
		return bananapple;
	}
	public void draw(Graphics g)
	{
		g.setColor(col);
		g.fillRoundRect((int)_x, (int)_y, (int)_width, (int)_height, 10, 10);
	}
	public void block(Entity e)
	{
		if(e instanceof Collider)
		{
			Collider coll = (Collider)e;
			Clip2D clip = coll.getClip();
			if(clip instanceof ClipRect)
			{
				ClipRect crec = (ClipRect)clip;
				double xmin = crec.rect.x;
				double ymin = crec.rect.y;
				double xmax = crec.rect.x+crec.rect.width;
				double ymax = crec.rect.y+crec.rect.height;
				double wallxmax = _x+_width;
				double wallymax = _y+_height;
				boolean on_left = (xmin < _x && xmax > _x);
				boolean on_right = (xmin < wallxmax && xmax > wallxmax);
				boolean on_top = (ymin < _y && ymax > _y);
				boolean on_bottom = (ymin < wallymax && ymax > wallymax);
				boolean within_topbottom = (ymax > _y && ymin < wallymax);
				boolean within_leftright = (xmax > _x && xmin < wallxmax);

				if(within_topbottom && within_leftright)
				{
					if(!on_top && !on_bottom && !on_left && !on_right)
					{
						//inside
						Point dist = new Point(_x+(_width/2), _y+(_height/2));
						dist.x = e.pos.x-dist.x;
						dist.y = e.pos.y-dist.y;
						dist.normalize();
						e.vel.add(dist);
						return;
					}
				}

				double accelX = 0;
				double accelY = 0;
				
				if(on_left && within_topbottom)
					accelX = _x - xmax;
				if(on_top && within_leftright)
					accelY = _y - ymax;
				if(on_right && within_topbottom)
					accelX = wallxmax - xmin;
				if(on_bottom && within_leftright)
					accelY = wallymax - ymin;
				
				if(accelX != 0 && accelY != 0)
				{
					if(Math.abs(accelX) < Math.abs(accelY))
						e.vel.x = accelX;
					else
						e.vel.y = accelY;
				}
				else if(accelY != 0)
				{
					e.vel.y = accelY;
				}
				else if(accelX != 0)
				{
					e.vel.x = accelX;
				}
				if(accelX != 0 || accelY != 0)
				{
					if(e instanceof Creature)
					{
						((Creature)e).touched(0.5+(Math.abs(accelX)+Math.abs(accelY)));
					}
				}
			}
		}
		if(e instanceof Fruit)
		{
			Fruit f = (Fruit)e;
			if(f.getAge() < (ripeningPeriod+(Math.random()*ripePeriod)))
			{
				f.vel.multiply(0.001);
			}
		}
	}
}
