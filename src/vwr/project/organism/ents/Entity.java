package vwr.project.organism.ents;
import java.awt.*;

import vwr.geom.Point;
public class Entity {
	public Point pos;
	public Point vel;
	public double rot;
	private boolean deleted;
	
	public Entity()
	{
		pos = new Point();
		vel = new Point();
		rot = 0;
		setDeleted(false);
	}

	public void draw(Graphics g)
	{
	}
	
	public void update()
	{
		pos.add(vel);
	}
	
	public boolean deleted()
	{
		return isDeleted();
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isDeleted() {
		return deleted;
	}
}
