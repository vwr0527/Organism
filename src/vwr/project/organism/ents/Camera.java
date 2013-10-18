package vwr.project.organism.ents;

import vwr.geom.Point;
import vwr.sys.KeyHandler;




public class Camera extends Entity {
	public double scale;
	public Camera()
	{
		super();
		pos = new Point(0,0);
		scale = 1.0;
	}
	@Override
	public void update()
	{
		if(KeyHandler.key(37)) vel.x -= 2.0;
		if(KeyHandler.key(38)) vel.y -= 2.0;
		if(KeyHandler.key(39)) vel.x += 2.0;
		if(KeyHandler.key(40)) vel.y += 2.0;
		if(KeyHandler.key(45)) scale += 0.1;
		if(KeyHandler.key(61) && scale > 0.2) scale -= 0.1;
		vel.multiply(0.9);
		super.update();
	}
}
