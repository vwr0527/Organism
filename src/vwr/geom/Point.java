package vwr.geom;
public class Point {
	public double x;
	public double y;
	public double z;
	public Point()
	{
		x = 0;
		y = 0;
		z = 0;
	}
	public Point(double a, double b)
	{
		x = a;
		y = b;
		z = 0;
	}
	public Point(double a, double b, double c)
	{
		x = a;
		y = b;
		z = c;
	}
	public static Point frand(double x, double y)
	{
		Point vec = new Point();
		vec.x = (Math.random()*x)-(x/2);
		vec.y = (Math.random()*y)-(y/2);
		vec.z = 0;
		return vec;
	}
	public void add(Point b)
	{
		x += b.x;
		y += b.y;
		z += b.z;
	}
	public void multiply(double f)
	{
		x *= f;
		y *= f;
		z *= f;
	}
	public double magnitude()
	{
		return Math.sqrt(x*x + y*y + z*z);
	}
	public double dist(Point b)
	{
		double xpos = x - b.x;
		double ypos = y - b.y;
		double zpos = z - b.z;
		return Math.sqrt(xpos*xpos + ypos*ypos + zpos*zpos);
	}
	public void normalize()
	{
		if(this.magnitude() <= 0.1) return;
		this.multiply(1/this.magnitude());
	}
	public static Point random(int x, int y)
	{
		Point vec = new Point();
		vec.x = Math.random()*x;
		vec.y = Math.random()*y;
		vec.z = 0;
		return vec;
	}
}
