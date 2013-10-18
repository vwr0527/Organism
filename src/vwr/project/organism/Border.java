package vwr.project.organism;
import java.awt.*;

import vwr.project.organism.ents.Entity;
import vwr.sys.KeyHandler;

public class Border {
	public enum Rule
	{
		Solid,
		WrapAround
	};
	
	public Rule rule = Rule.Solid;
	private double width;
	private double height;
	private double x;
	private double y;
	
	public Border()
	{
		x = -100;
		y = -100;
		width = 200;
		height = 200;
		init();
	}
	
	public Border(double xpos, double ypos, double w, double h)
	{
		x = xpos;
		y = ypos;
		width = w;
		height = h;
		init();
	}
	
	public Border(double w, double h)
	{
		width = w;
		height = h;
		x = -w/2;
		y = -h/2;
		init();
	}
	
	public void init()
	{
	}

	public void draw(Graphics g)
	{
		if(rule == Rule.Solid)
			g.setColor(Color.white);
		else
			g.setColor(Color.yellow);
		
		g.drawRect((int)x, (int)y, (int)width, (int)height);
	}
	
	public void update(Entity e)
	{
		if(rule == Rule.Solid)
			confine(e);
		else if(rule == Rule.WrapAround)
			wrapAround(e);
		if(KeyHandler.key(87)) rule = Rule.WrapAround;
		if(KeyHandler.key(69)) rule = Rule.Solid;
	}

	public void confine(Entity e)
	{
		double xmax = x + width;
		double ymax = y + height;
		if(e.pos.x > xmax)
		{
			e.pos.x = xmax;
			e.vel.x = -e.vel.x;
		} else if (e.pos.x < x)
		{
			e.pos.x = x;
			e.vel.x = -e.vel.x;
		}
		if(e.pos.y > ymax)
		{
			e.pos.y = ymax;
			e.vel.y = -e.vel.y;
		} else if (e.pos.y < y)
		{
			e.pos.y = y;
			e.vel.y = -e.vel.y;
		}
	}
	
	public void wrapAround(Entity e)
	{
		double xmax = x + width;
		double ymax = y + height;
		if(e.pos.x > xmax)
		{
			e.pos.x = x;
		} else if (e.pos.x < x)
		{
			e.pos.x = xmax;
		}
		if(e.pos.y > ymax)
		{
			e.pos.y = y;
		} else if (e.pos.y < y)
		{
			e.pos.y = ymax;
		}
	}
	
	public double width()
	{
		return width;
	}
	public double height()
	{
		return height;
	}
}
