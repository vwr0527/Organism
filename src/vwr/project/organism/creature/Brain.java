package vwr.project.organism.creature;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

import vwr.geom.Point;
import vwr.util.Funcs;

public class Brain implements Cloneable
{
	private LinkedList<Neuron> mutable; //mutates
	private LinkedList<Neuron> actuators;//doesn't mutate
	private LinkedList<Neuron> sensors;//doesn't mutate
	private ArrayList<Double> neuralmap;

	private Neuron side_controller;
	private Neuron forward_controller;
	private Neuron rotation_controller;
	private Neuron attack_controller;

	private Neuron pain;
	private Neuron touch;
	//private Neuron pleasure;
	
	//private Neuron see_tree_dir;
	//private Neuron see_tree_dist;
	//private Neuron see_cret_dir;
	//private Neuron see_cret_dist;
	//private Neuron see_cret_num;
	//private Neuron see_fruit_dir;
	//private Neuron see_fruit_dist;
	//private Neuron see_fruit_num;
	
	private void init()
	{
		side_controller = new Neuron();
		addNeuron(side_controller, actuators);
		
		forward_controller = new Neuron();
		addNeuron(forward_controller, actuators);

		rotation_controller = new Neuron();
		addNeuron(rotation_controller, actuators);
		
		attack_controller = new Neuron();
		addNeuron(attack_controller, actuators);
		

		pain = new Neuron();
		addNeuron(pain, sensors);
		
		touch = new Neuron();
		addNeuron(touch, sensors);
	}
	
	public Brain()
	{
		mutable = new LinkedList<Neuron>(); //mutates
		actuators = new LinkedList<Neuron>();//doesn't mutate
		sensors = new LinkedList<Neuron>();//doesn't mutate
		neuralmap = new ArrayList<Double>();
		init();
	}
	
	public Brain(ArrayList<Double> nmap)
	{
		neuralmap = nmap;
	}
	public void look(Vision v)
	{
		
	}
	public void think()
	{
		for(Neuron n : sensors)
		{
			n.fire();
		}
		for(Neuron n : mutable)
		{
			n.fire();
		}
		for(Neuron n : actuators)
		{
			n.fire();
		}
		/*
		if(Math.random() < 0.0001)
		{
			System.out.println("Brain of "+this+" has #"+numNeurons()+" neurons, neuralmap size = "+neuralmap.size()
					+ " , sensors: "+sensors.size()+ " , mutable: "+mutable.size()+ " , actuators: "+actuators.size());
		}
		*/
		sensors.get(0).sense(0);
		sensors.get(1).sense(0);
	}
	public void pain(double val)
	{
		sensors.get(0).sense(Funcs.clamp(val, -1, 1));
	}
	public void touch(double val)
	{
		sensors.get(1).sense(Funcs.clamp(val, -1, 1));
	}
	public double getMoveSideways()
	{
		return actuators.get(0).output();
	}
	public double getMoveForward()
	{
		return actuators.get(1).output();
	}
	public double getRotation()
	{
		return actuators.get(2).output();
	}
	public double getAttack()
	{
		return actuators.get(3).output();
	}
	public int numNeurons()
	{
		return mutable.size() + actuators.size() + sensors.size();
	}
	public void wash()
	{
		mutable.clear();
		sensors.clear();
		actuators.clear();
		neuralmap.clear();
		init();
	}
	//test brain;
	public void Jiggler()
	{
		wash();
	}
	
	public void Random()
	{
		wash();
		
		for(int i = 0; i < 3; ++i)
			addRandomNeuron();
		for(int i = 0; i < 8; ++i)
			makeRandomConnection();
	}
	
	private void addNeuron(Neuron n, LinkedList<Neuron> list)
	{
		n.addToNmap(neuralmap);
		list.add(n);
	}

	private void addRandomNeuron()
	{
		Neuron newNeuron = new Neuron();
		newNeuron.randomize();
		makeOneConnection(newNeuron);
		addNeuron(newNeuron, mutable);
	}
	
	public void makeOneConnection(Neuron newNeuron)
	{
		if(Math.random() > 0.5) newNeuron.recieveFrom(getRandomMutableOrSensor());
		else getRandomMutableOrActuator().recieveFrom(newNeuron);
	}
	
	private void makeRandomConnection()
	{
		//sensors cannot recieve from neurons / nothing can output to sensors
		//actuators cannot connect to neurons / nothing can recieve from actuators
		Neuron source = getRandomMutableOrSensor();
		Neuron target = getRandomMutableOrActuator();
		if(target == null) return;
		target.recieveFrom(source);
	}
	
	private Neuron getRandomMutableOrSensor()
	{
		int mutsize = mutable.size();
		int sensize = sensors.size();
		if(Math.random() < ((double)mutsize/(double)(sensize+mutsize)))
		{
			if(mutsize > 0)
				return mutable.get((int)(Math.random()*mutsize));
		}
		else
		{
			if(sensize > 0)
				return sensors.get((int)(Math.random()*sensize));
		}
		return null;
	}
	
	private Neuron getRandomMutableOrActuator()
	{
		int mutsize = mutable.size();
		int actsize = actuators.size();
		if(Math.random() < ((double)mutsize/(double)(actsize+mutsize)))
		{
			if(mutsize > 0)
				return mutable.get((int)(Math.random()*mutsize));
		}
		else
		{
			if(actsize > 0)
				return actuators.get((int)(Math.random()*actsize));
		}
		return null;
	}
	
	private void deleteRandomNeuron()
	{
		if(mutable.size() == 0) return;
		Neuron toDel = mutable.get((int)(Math.random()*mutable.size()));
		neuralmap.set(toDel.getID(), -2.0);
		mutable.remove(toDel);
	}

	private void cutRandomConnection()
	{
		Neuron target = getRandomMutableOrActuator();
		if(target == null) return;
		target.trimRandomConnection();
	}
	
	public void mutate()
	{
		if(Math.random() > 0.5)
		{
			if(Math.random() > 0.5)
				addRandomNeuron();
			else
				deleteRandomNeuron();
		}
		else
		{
			if(Math.random() > 0.5)
				makeRandomConnection();
			else
				cutRandomConnection();
		}
	}
	
	public Brain clone()
	{
		Brain c = new Brain(new ArrayList<Double>());
		for(Double d : neuralmap)
		{
			c.neuralmap.add(d.doubleValue());
		}
		c.sensors = new LinkedList<Neuron>();
		for(Neuron n : sensors)
		{
			Neuron neuronclone = n.clone();
			c.sensors.add(neuronclone);
			neuronclone.connectToNmap(c.neuralmap);
		}
		c.actuators = new LinkedList<Neuron>();
		for(Neuron n : actuators)
		{
			Neuron neuronclone = n.clone();
			c.actuators.add(neuronclone);
			neuronclone.connectToNmap(c.neuralmap);
		}
		c.mutable = new LinkedList<Neuron>();
		for(Neuron n : mutable)
		{
			Neuron neuronclone = n.clone();
			c.mutable.add(neuronclone);
			neuronclone.connectToNmap(c.neuralmap);
		}
		return c;
	}
	public void draw(Graphics g, Point pos)
	{
		int boxwidth = 12;
		int boxheight = 5;
		int starty = 10;
		int horizspace = 4;
		int vertspace = 4;
		Point pen = new Point(pos.x - (sensors.size()*(boxwidth+horizspace)/2), pos.y+starty);
		for(Neuron n : sensors)
		{
			g.setColor(Color.red);
			g.drawRect((int)pen.x-1, (int)pen.y-1, boxwidth+2, boxheight+2);
			g.setColor(Color.yellow);
			int linepos_x = (int)((n.output()*(boxwidth/2))+pen.x)+(boxwidth/2);
			g.drawLine(linepos_x, (int)pen.y, linepos_x, (int)pen.y+boxheight);
			pen.x += boxwidth + horizspace;
		}
		pen.x = pos.x - (mutable.size()*(boxwidth+horizspace)/2);
		pen.y += boxheight + vertspace;
		for(Neuron n : mutable)
		{
			g.setColor(Color.yellow);
			g.drawRect((int)pen.x-1, (int)pen.y-1, boxwidth+2, boxheight+2);
			g.setColor(Color.white);
			int linepos_x = (int)((n.output()*(boxwidth/2))+pen.x)+(boxwidth/2);
			g.drawLine(linepos_x, (int)pen.y, linepos_x, (int)pen.y+boxheight);
			pen.x += boxwidth + horizspace;
		}
		
		pen.x = pos.x - (actuators.size()*(boxwidth+horizspace)/2);
		pen.y += boxheight + vertspace;
		for(Neuron n : actuators)
		{
			g.setColor(Color.blue);
			g.drawRect((int)pen.x-1, (int)pen.y-1, boxwidth+2, boxheight+2);
			g.setColor(Color.green);
			int linepos_x = (int)((n.output()*(boxwidth/2))+pen.x)+(boxwidth/2);
			g.drawLine(linepos_x, (int)pen.y, linepos_x, (int)pen.y+boxheight);
			pen.x += boxwidth + horizspace;
		}
		
	}
}
