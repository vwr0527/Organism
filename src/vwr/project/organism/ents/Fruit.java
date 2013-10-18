package vwr.project.organism.ents;

import java.awt.Color;
import java.awt.Graphics;

import vwr.geom.ClipRect;
import vwr.geom.Point;
import vwr.project.organism.Edible;

public class Fruit extends Collider implements Edible
{
	private double _width;
	private double _height;
	private long age = 0;
	private long maxage;
	private Color _col;
	
	public Fruit()
	{
		maxage = 1000+(int)(Math.random()*1000);
		pos = Point.frand(1000,1000);
		clip = new ClipRect(10, 10);
		_width = 8 + Math.random()*2;
		_height = 8 + Math.random()*2;
		_col = new Color(50 + (int)(Math.random()*206), 100 + (int)(Math.random()* 50), 100 + (int)(Math.random()*20));
	}
	public void update()
	{
		super.update();
		noclip = false;
		vel.add(Point.frand(0.1, 0.1));//brownian motion
		vel.multiply(0.8);
		if(age > maxage)
		{
			setDeleted(true);
		}
		++age;
	}
	public void draw(Graphics g)
	{
		g.setColor(_col);
		g.fillOval((int)(pos.x-(_width/2)), (int)(pos.y-(_height/2)), (int)_width, (int)_height);
	}
	
	public long getAge()
	{
		return age;
	}

	public double getNutrients()
	{
		return 250;
	}
	public boolean eaten = false;
	@Override
	public double eatenAction(Point mouthpos, double strength)
	{
		noclip = true;
		if(mouthpos.dist(pos) < 6)
			eaten = true;
		return 0;
	}
	@Override
	public boolean hasBeenEaten()
	{
		return eaten;
	}
}
