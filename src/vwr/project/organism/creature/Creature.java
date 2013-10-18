package vwr.project.organism.creature;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import vwr.geom.ClipRect;
import vwr.geom.Point;
import vwr.project.organism.Edible;
import vwr.project.organism.Spawner;
import vwr.project.organism.ents.Collider;
import vwr.project.organism.ents.Entity;
import vwr.project.organism.ents.Fruit;
import vwr.sys.KeyHandler;
import vwr.util.Funcs;

public class Creature extends Collider implements Edible, Spawner
{
	public Brain brain;
	private double energy;
	private double metabolism;
	private boolean dead;
	private int age;
	public double size;
	private double movespeed;
	private double spinspeed;
	private double maturity;
	private double matureAge;
	private int old;
	private double damage;
	private double spawnrate;
	private Color col;
	private LinkedList<Entity> spawner;
	public boolean attacking;
	private double mutation_rate;
	private double birthSize;
	private double grownSize;
	private int decay;
	private double strength;
	private double growthfactor;
	public Creature()
	{
		super();
		energy = 2000;
		metabolism = 0.1;
		age = 0;
		rot = Math.random()*Math.PI*2;
		pos = Point.frand(800, 600);
		clip = new ClipRect(20, 20);
		dead = false;
		brain = new Brain();
		movespeed = 0.4;
		spinspeed = Math.PI/6;
		grownSize = 20;
		birthSize = 12;
		size = birthSize;
		brain.Random();
		maturity = 0;
		matureAge = 200+((int)(Math.random()*200));
		old = 2000+((int)(Math.random()*2000));
		damage = 0;
		spawnrate = 0.002;
		col = Color.green;
		mutation_rate = 0.5;
		attacking = false;
		decay = 0;
		strength = size/grownSize;
		growthfactor = 0.02;
	}
	
	@Override
	public void draw(Graphics g)
	{
		if (dead)
		{
			if(KeyHandler.key(81))
				showEnergy(g);
			float s = (float) (energyContent()/100);
			float b = (float) (0.1+(0.8*(s)));
			g.setColor(Color.getHSBColor((float)Funcs.convertScale(Funcs.clamp(energyContent(), 0, 4000), 0, 4000, 0.5, 5.0/6.0), (float)Funcs.clamp(s, 0, 1), (float)Funcs.clamp(b, 0, 1)));
		}
		else
		{
			if(KeyHandler.key(81))
			{
				//showEnergy(g);
				//showHP(g);
				brain.draw(g, pos);
			}
			int brighten = 0;
			if(attacking) brighten = (int)(Math.random()*128);
			
			float lusture = (float) Funcs.convertScale(Funcs.clamp(age, old, old*2), old, old*2, 1.0, 0.4);
			float b = (float) (0.2+(0.8*(lusture)));
			Color basecolor = Color.getHSBColor(
					(float)Funcs.convertScale(Funcs.clamp(energy, 0, 2000), 0, 2000, 0.5, 0.33), lusture, b);

			g.setColor(col);
			col = new Color(
					Funcs.clamp(basecolor.getRed()+brighten+((int)(damage*2.56)), 0, 255),
					Funcs.clamp(basecolor.getGreen()+brighten-((int)(damage*2.56)), 0, 255),
					Funcs.clamp(basecolor.getBlue()+brighten, 0, 255));
		}
		if(isDeleted())
		{
			g.setColor(Color.darkGray);
		}
		g.fillOval((int)(pos.x-(size/2)),
				   (int)(pos.y-(size/2)),
				   (int)size,
				   (int)size);
		
		drawEye(g);
		
		//electric shock cloud
		if(attacking)
		{
			drawSparks(g);
		}
	}
	
	private void drawEye(Graphics g)
	{
		//eye, white part
		Point eyepos = new Point(pos.x, pos.y);
		double eyesize = 10;
		double eyeoffset = 0.2*size;
		eyepos.x += Math.sin(rot)*eyeoffset;
		eyepos.y += Math.cos(rot)*eyeoffset;

		g.setColor(Color.white);
		g.fillOval((int)(eyepos.x-(eyesize/2)),
			(int)(eyepos.y-(eyesize/2)),
			(int)eyesize, (int)eyesize);
		
		if(!attacking)
		{
			g.setColor(Color.white);
			g.fillOval((int)(eyepos.x-(eyesize/2)),
				(int)(eyepos.y-(eyesize/2)),
				(int)eyesize, (int)eyesize);
			
			//pupil
			g.setColor(Color.black);
			if(!dead)
			{
				eyepos.x += Math.sin(rot);
				eyepos.y += Math.cos(rot);
				eyesize = 5;
				g.fillOval((int)(eyepos.x-(eyesize/2)),
						(int)(eyepos.y-(eyesize/2)),
						(int)eyesize, (int)eyesize);
			} else
			{
				double xsize = eyesize/6;
				g.drawLine((int)(eyepos.x-xsize), (int)(eyepos.y-xsize), (int)(eyepos.x+xsize), (int)(eyepos.y+xsize));
				g.drawLine((int)(eyepos.x+xsize), (int)(eyepos.y-xsize), (int)(eyepos.x-xsize), (int)(eyepos.y+xsize));
			}
		}
		else
		{
			if(Math.random() > 0.5)
				g.setColor(Color.cyan);
			else
				g.setColor(Color.white);
			
			g.fillOval((int)(eyepos.x-(eyesize/2)),
				(int)(eyepos.y-(eyesize/2)),
				(int)eyesize, (int)eyesize);
			
			//pupil
			if(Math.random() > 0.5)
				g.setColor(Color.orange);
			else
				g.setColor(Color.blue);
			
			eyepos.x += Math.sin(rot)/2;
			eyepos.y += Math.cos(rot)/2;
			eyesize = 4+Math.random()*4;
			g.fillOval((int)(eyepos.x-(eyesize/2)),
					(int)(eyepos.y-(eyesize/2)),
					(int)eyesize, (int)eyesize);
		}
	}

	private void drawSparks(Graphics g)
	{
		for(int i = 0; i < size/8; ++i)
		{
			if(Math.random() > 0.5)
				g.setColor(Color.white);
			else
				if(Math.random() > 0.5)
					g.setColor(Color.pink);
				else
					g.setColor(Color.cyan);

			double dir = rot + (Math.random()*0.8) - 0.4;
			double sparkX = Math.sin(dir)*size/2.5;
			double sparkY = Math.cos(dir)*size/2.5;
			
			g.drawLine(
					(int)(pos.x + sparkX*0.5)+(int)(Math.random()*size/2 - size/4),
					(int)(pos.y + sparkY*0.5)+(int)(Math.random()*size/2 - size/4),
					(int)(pos.x + sparkX)+(int)(Math.random()*size/2 - size/4),
					(int)(pos.y + sparkY)+(int)(Math.random()*size/2 - size/4));
			
			g.drawLine(
					(int)(pos.x + sparkX)+(int)(Math.random()*size/2 - size/4),
					(int)(pos.y + sparkY)+(int)(Math.random()*size/2 - size/4),
					(int)(pos.x + sparkX)+(int)(Math.random()*size/2 - size/4),
					(int)(pos.y + sparkY)+(int)(Math.random()*size/2 - size/4));
		}		
	}
	/*
	private void drawAreaAttack(Graphics g)
	{
		for(int i = 0; i < 5; ++i)
		{
			if(Math.random() > 0.5)
				g.setColor(Color.pink);
			else
				if(Math.random() > 0.5)
					g.setColor(Color.white);
				else
					g.setColor(Color.cyan);

			double dir = Math.random() * 2 * Math.PI;
			double sparkX = Math.sin(dir)*attackRange();
			double sparkY = Math.cos(dir)*attackRange();
			
			g.drawLine(
					(int)(pos.x + sparkX*0.5)+(int)(Math.random()*4 - 2),
					(int)(pos.y + sparkY*0.5)+(int)(Math.random()*4 - 2),
					(int)(pos.x + sparkX)+(int)(Math.random()*16 - 8),
					(int)(pos.y + sparkY)+(int)(Math.random()*16 - 8));
			
			g.drawLine(
					(int)(pos.x + sparkX)+(int)(Math.random()*16 - 8),
					(int)(pos.y + sparkY)+(int)(Math.random()*16 - 8),
					(int)(pos.x + sparkX)+(int)(Math.random()*16 - 8),
					(int)(pos.y + sparkY)+(int)(Math.random()*16 - 8));
		}		
	}
	*/
	
	public void showHP(Graphics g)
	{
		g.setColor(col);
		g.drawString((int)(hp()-damage) + "/" + (int)hp(), (int)pos.x-16, (int)pos.y-8);
	}
	
	public void showEnergy(Graphics g)
	{
		g.setColor(col);
		g.drawString("["+(int)energyContent()+"]", (int)pos.x-18, (int)pos.y-18);
	}

	@Override
	public void update()
	{
		++age;
		if(dead)
		{
			if(Math.random() < 0.1)
			{
				decay += 1;
				energy *= 0.995;
			}
			if(energyContent() < 0) setDeleted(true);
			attacking = false;
		}
		else
		{
			brain.think();
			energy -= (brain.numNeurons() * 0.01);
			
			strength = size/grownSize;
			
			//---------------------movement-----------------------
			double exert_x = movespeed*(Math.sin(rot)*brain.getMoveForward() + Math.sin(rot+(Math.PI/2))*brain.getMoveSideways());
			double exert_y = movespeed*(Math.cos(rot)*brain.getMoveForward() + Math.cos(rot+(Math.PI/2))*brain.getMoveSideways());

			energy -= (exert_x+exert_y) * 0.5 * strength;
			
			vel.x += exert_x*strength;
			vel.y += exert_y*strength;
			//---------------rotation------------------
			double rotate = brain.getRotation()*spinspeed;
			
			energy -= Math.abs(rotate) * 0.1;
			
			rot += rotate;
			while(rot > Math.PI)
			{
				rot -= 2*Math.PI;
			}
			while(rot < -Math.PI)
			{
				rot += 2*Math.PI;
			}
			
			//--------------attacking--------------------
			attacking = (brain.getAttack() > 0.0);
			if(attacking) energy -= 2.0;
			
			//---------------wounded----------------------
			if(damage > 0)
			{
				brain.pain(damage*3/hp());
				if(Math.random() < 0.1)
				{
					energy -= 1; //healing penalty
					damage -= 1; //heal
					double dmg = (damage/10);
					if(Math.random() < ((dmg*dmg*dmg)/1000)) dead = true;
				}
				//chance of death =
				//damage | chance
				//0 | 0%
				//10 | 0.1%
				//20 | 0.8%
				//30 | 2.7%
				//50 | 12.5%
				//70 | 34.3%
				//90 | 72.9%
				//99 | 97.0%
				//((damage/10)^3)/1000
			}
		
			//------------------reproduce-----------------
			if(maturity > matureAge && Math.random() < spawnrate)
			{
				reproduce();
			}
			
			//------------------basal---------------------
			if(maturity <= matureAge)
			{
				//energy to growth ratio
				//400   | 0.4% = 0.04
				//900   | 0.6% = 0.06
				//10000 | 20.0% = 0.2
				//Math.sqrt(energy/100)*0.02;
				if(Math.random() < Math.sqrt(energy/100)*growthfactor)
				{
					double growth = Math.random();
					maturity += growth;
					size = (grownSize-birthSize)*(maturity/matureAge) + birthSize;
					((ClipRect)clip).resize(size, size);
					energy -= growth;
				}
			}
			energy -= metabolism;
			
			//=============death is likely================
			if(age > old)
			{
				if(Math.random() < ((age/old)/energy))
					dead = true;
			}
			
			if(damage > hp()) dead = true;
			if(energy < 0) dead = true;
		}
		vel.add(Point.frand(0.1, 0.1));
		vel.multiply(0.9);
		super.update();
		noclip = false;
	}

	public double hp()
	{
		return (size/grownSize)*100;
	}

	/*
	private double attackRange()
	{
		return size*1.5;
	}
	*/
	
	private void getHurt(double dmg)
	{
		damage += dmg;
		if(Math.random() > 0.5) col = Color.red;
		brain.pain(1);
	}
	
	public void reproduce()
	{
		if(energy < 1000) return;
		Creature child = new Creature();
		child.brain = brain.clone();
		if(Math.random() > mutation_rate) child.brain.mutate();
		child.pos.x = pos.x;
		child.pos.y = pos.y;
		energy -= 500;
		child.energy = 500;
		spawner.add(child);
	}
	
	private void eat(Entity c)
	{
		double mouthx = pos.x + Math.sin(rot)*10;
		double mouthy = pos.y + Math.cos(rot)*10;
		
		//sucking action
		double xdist = mouthx - c.pos.x;
		double ydist = mouthy - c.pos.y;
		c.vel.x += xdist / 10;
		c.vel.y += ydist / 10;
		vel.x -= xdist / 10;
		vel.y -= ydist / 10;
		if(c instanceof Edible)
		{
			energy += ((Edible) c).eatenAction(new Point(mouthx, mouthy), strength);
			if(((Edible)c).hasBeenEaten())
			{
				c.setDeleted(true);
				energy += ((Edible)c).getNutrients();
			}
		}
	}

	public void touched(double val)
	{
		brain.touch(val);
	}
	
	@Override
	public void forAllOthers(Collider c)
	{
		/*if(attacking)
		{
			if(c.pos.dist(pos) < attackRange())
			{
				double getAngle = Math.PI/2 - Math.atan2(c.pos.y-pos.y, c.pos.x-pos.x);
					c.vel.add(new Point(Math.sin(getAngle)*2, Math.cos(getAngle)*2));
					c.vel.add(Point.frand(4, 4));
					if(c instanceof Creature)
					{
						((Creature)c).getHurt(attackvalue/2+(Math.random()*attackvalue/2));
						((Creature)c).col = Color.red;
					}
			}
		}*/
	}

	@Override
	public void forIntersecting(Collider c)
	{
		//System.out.println("Type of c: "+c.getClass());
		
		//generic collider
		super.forIntersecting(c);
		if(dead) return;
		
		//double dist = pos.dist(c.pos);
		brain.touch(0.2+c.vel.magnitude()/2+vel.magnitude()/2);

		double getAngle = Math.PI/2 - Math.atan2(c.pos.y-pos.y, c.pos.x-pos.x);
		boolean infront = Math.abs(Funcs.calculateDifferenceBetweenAngles(rot, getAngle)) < Math.PI/3; // less than 60 degrees
		
		if(c instanceof Fruit && infront)
		{
			this.eat(c);
		}
		
		if(c instanceof Creature && infront)
		{
			//they eat their own dead...
			if(((Creature)c).dead)
				this.eat(c);
			if(attacking)
				this.eat(c);
		}
	}

	public void setSpawn(LinkedList<Entity> m)
	{
		spawner = m;
	}

	@Override
	public double eatenAction(Point mouthpos, double bite)
	{
		if(dead)
		{
			noclip = true;
			size -= bite;
			return energyContent()*(bite/grownSize);
		}
		else
		{
			bite = Math.random()*2.0;
			getHurt(bite);
			return bite;
		}
	}
	
	public double getNutrients()
	{
		return (hp()-damage);
	}

	public double energyContent()
	{
		return energy + (100-damage) - decay;
	}

	@Override
	public boolean hasBeenEaten()
	{
		if(size <= 10)
			return true;
		else
			return false;
	}
}
