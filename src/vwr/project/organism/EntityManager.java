package vwr.project.organism;

import java.util.ArrayList;
import java.util.LinkedList;
import vwr.project.organism.ents.Collider;
import vwr.project.organism.ents.Entity;

public class EntityManager
{
	private LinkedList<Entity> delist;
	private LinkedList<Entity> enlist;
	private LinkedList<Entity> ents;
	private LinkedList<Collider> collidable;
	private ArrayList<Tree> trees;
	private Border border;

	public EntityManager(LinkedList<Entity> list)
	{
		ents = list;
		
		delist = new LinkedList<Entity>();
		enlist = new LinkedList<Entity>();
		collidable = new LinkedList<Collider>();
	}
	public void setTrees(ArrayList<Tree> tlist)
	{
		trees = tlist;
	}
	public void setBorder(Border b)
	{
		border = b;
	}
	public void add(Entity e)
	{
		enlist.add(e);
	}
	public void remove(Entity e)
	{
		delist.add(e);
	}
	
	public void update()
	{
		//add new ents
		for(Entity e : enlist)
		{
			if(e != null)
			ents.add(e);
			if(e instanceof Spawner)
			{
				((Spawner)e).setSpawn(enlist);
			}
		}
		enlist.clear();
		
		//remove dead ents
		for(Entity e : delist)
		{
			if(e != null)
			ents.remove(e);
		}
		delist.clear();
		
		//primary update
		for(Entity e : ents)
		{
			e.update();
			////////////////////////////
			if(e instanceof Collider)
			{
				collidable.add((Collider)e);
			}
			
			////confine within border rectangle//////
			if(border != null)
			{
				border.update(e);
			}
			
			////trees block it////
			for(Tree t : trees)
			{
				t.block(e);
			}
			
			///////////////////////////////
			if(e.deleted())
			{
				delist.add(e);
			}
		}
		//second pass, collisions update
		for(Collider cldr : collidable)
		{
			cldr.collide(collidable);
		}
		collidable.clear();
	}
}
