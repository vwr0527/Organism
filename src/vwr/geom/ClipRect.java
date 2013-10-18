package vwr.geom;

import java.awt.Graphics;
import java.awt.Rectangle;

public class ClipRect extends Clip2D {
	
	private Point center;
	public Rectangle rect;

	public ClipRect(double width, double height) {
		rect = new Rectangle();
		rect.width = (int)width;
		rect.height = (int)height;
		center = new Point(width/2, height/2);
		colliding = false;
	}
	
	public void resize(double width, double height)
	{
		rect.x += center.x;
		rect.y += center.y;
		rect.width = (int)width;
		rect.height = (int)height;
		center.x = width/2;
		center.y = height/2;
		rect.x -= (int)center.x;
		rect.y -= (int)center.y;
	}
	
	public void update(Point pos)
	{
		rect.x = (int)(pos.x-center.x);
		rect.y = (int)(pos.y-center.y);
	}
	
	@Override
	public boolean intersects(Clip2D c)
	{
		if(c == null) {
			return false;
		}
		if(c instanceof ClipRect)
		{
			ClipRect d = (ClipRect)c;
			
			if(d.rect.intersects(rect))
				return true;
			else
				return false;
		}
		return false;
	}

	@Override
	public void draw(Graphics g)
	{
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
	}
}
