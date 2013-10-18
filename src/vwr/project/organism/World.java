package vwr.project.organism;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;

//import vwr.geom.Point;
import vwr.project.organism.creature.Creature;
import vwr.project.organism.ents.*;
import vwr.sys.KeyHandler;

public class World extends TimerTask {
	
	public Component window;
	private Camera cam;
	private Timer timer;
	private double framerate = 60;
	private Border border;
	private LinkedList<Entity> ents;
	private ArrayList<Tree> trees;
	private EntityManager entmanager;
	private long time = 0;
	
	public World()
	{
		timer = new Timer();
		timer.schedule(this, (long)(500), (long)(1000/framerate));
		
		border = new Border(1200, 1000);
		//border.rule = Border.Rule.WrapAround;
		
		cam = new Camera();

		trees = new ArrayList<Tree>();
		generateForest(25);
		
		ents = new LinkedList<Entity>();
		
		entmanager = new EntityManager(ents);
		entmanager.setBorder(border);
		entmanager.setTrees(trees);
		entmanager.add(cam);
		
		for(int i = 0; i < 400; ++i)
		{
			Creature hoogly = new Creature();
			entmanager.add(hoogly);
		}
	}
	public void draw(Graphics2D g, int bufwidth, int bufheight)
	{
		g.clearRect(0,0,bufwidth,bufheight);

		//put (0,0) at center of buffer
		g.translate(bufwidth/2, bufheight/2);
		//transform world coordinates using camera's coordinates
		g.scale(1/cam.scale, 1/cam.scale);
		g.translate(-cam.pos.x, -cam.pos.y);
		
		//draw all (java doesn't like me using iterators for this one)
		for(int i = ents.size()-1; i > 0; --i)
		{
			ents.get(i).draw(g);
		}

		//draw trees
		for(Tree t : trees)
		{
			t.draw(g);
		}

		border.draw(g);
	}
	public void run()
	{
		entmanager.update();
		for(Tree t : trees)
		{
			entmanager.add(t.bearFruit());
		}
		if(KeyHandler.key(84)) generateForest(20);
		if(KeyHandler.key(82)) entmanager.add(new Creature());
		if(KeyHandler.key(89)) entmanager.add(new Fruit());
		
		if(window != null) window.repaint();
		
		++time;
	}

	public void generateForest(int amount)
	{
		trees.clear();
		for(int i = 0; i < amount; ++i)
		{
			double width = 0;
			double height = 0;
			if(Math.random() > 0.5)
			{
				width = 100+Math.random()*200;
				height = 25+Math.random()*50;
			}
			else
			{
				height = 100+Math.random()*200;
				width = 25+Math.random()*50;
			}
			trees.add(new Tree( (Math.random()*border.width()) - border.width()/2 - width/2,
								(Math.random()*border.height()) - border.height()/2 - height/2,
								width, height));
		}
	}
	public long getTime()
	{
		return time;
	}
	public void printInfo(Graphics g)
	{
		String output = "";
		output += "T:" + time;
		g.setColor(Color.white);
		g.drawString(output, 20, 20);
	}
}
