package vwr.project.organism.creature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import vwr.util.Funcs;

public class Neuron implements Cloneable {
	
	private int id;
	public int getID() { return id; }
	
	private ArrayList<Double> neuralmap;
	private Stack<Integer> in = new Stack<Integer>();
	//clamped to be never above 1 or below -1
	
	public Neuron()
	{
	}
	
	public void addToNmap(ArrayList<Double> nmap)
	{
		neuralmap = nmap;
		if(neuralmap.contains(-2))
		{
			id = neuralmap.indexOf(-2);
			neuralmap.set(id, new Double(0));
		}
		else
		{
			id = neuralmap.size();
			neuralmap.add(new Double(0));
		}
		in.clear();
	}
	
	public void connectToNmap(ArrayList<Double> nmap)
	{
		neuralmap = nmap;
	}

	//randomizes internal equation values, but makes no connections
	public void randomize()
	{
		yoffset = (Math.random()*2 - 1)*(Math.random()*2 - 1);
		xoffset = (Math.random()*2 - 1)*(Math.random()*2 - 1);
		factor = (Math.random()*4 - 2)*(Math.random()*4 - 2);
		pcursx = Math.random()*2 - 1;
		combiner = (int)(Math.random()*7);
		ptype = (int)(Math.random()*5);
		fcn = (int)(Math.random()*12);
	}
	
	public void sense(double val)
	{
		neuralmap.set(id, val);
	}
	
	public void fire()
	{
		//don't need to do periodic() if fcn = randomize
		if(fcn < 11)
		{
			//don't need to do combine() if periodic type = randomize
			if(ptype < 4)
			{
				for(Iterator<Integer> i = in.iterator(); i.hasNext();)
				{
					int n = i.next();
					if(n <= -1)
					{
						i.remove();
						continue;
					}
					double input = neuralmap.get(n);
					if(input == -2)
					{
						//-2 is a sentental value. meaning
						//no neurons output to this position anymore.
						//so no need to read from it anymore
						i.remove();
						continue;
					}
					combine(input);
				}
			}
			periodic();
		}
		neuralmap.set(id, func(pcursx));
	}
	
	//combiner
	//0 = average
	//1 = product
	//2 = sum
	//3 = choose only lowest
	//4 = choose only highest
	//5 = choose only lowest absolute value
	//6 = choose only highest absolute value
	private int combiner = 0;
	private double cursx = 0;
	private void combine(double x)
	{
		switch(combiner)
		{
		case 0:
			cursx += x/in.size();
			break;
		case 1:
			cursx *= x;
			break;
		case 2:
			cursx += x;
			break;
		case 3:
			if(x > cursx)
			{
				cursx = x;
			}
			break;
		case 4:
			if(x < cursx)
			{
				cursx = x;
			}
			break;
		case 5:
			if(Math.abs(x) > cursx)
			{
				cursx = x;
			}
			break;
		case 6:
			if(Math.abs(x) < cursx)
			{
				cursx = x;
			}
			break;
		}
	}
	
	//periodicity type
	// 0 = none. out = fcn(inputs)
	// 1 = linear. out = fcn(x) where x goes from -1 to 1, wrapping around at the ends, at a speed of (inputs) per frame
	// 2 = linear with stops. same as above but upon reaching 1, will go no higher, and -1 vice versa
	// 3 = cyclic. sin(x*pi) where x changes at a rate of input, and wraps around between -1 and 1
	// 4 = choose randomly between -1 and 1
	
	private int ptype = 0;
	private double pcursx = 0;
	private void periodic()
	{
		switch(ptype)
		{
		case 0:
			pcursx = cursx;
			break;
		case 1:
			pcursx += cursx;
			if(pcursx > 1) pcursx -= 2;
			if(pcursx < -1) pcursx += 2;
			break;
		case 2:
			pcursx += cursx;
			if(pcursx > 1) pcursx = 1;
			if(pcursx < -1) pcursx = -1;
			break;
		case 3:
			pcursx += cursx*Math.PI;
			if(pcursx > Math.PI) pcursx -= Math.PI*2;
			if(pcursx < -Math.PI) pcursx += Math.PI*2;
			break;
		default:
			pcursx = Math.random()*2 - 1;
			break;
		}
		cursx = 0;
	}

	//fcn
	//0 = constant
	//1 = linear. what you put in is what you get out.
	//2 = absolute linear
	//3 = sin(input*pi). -1 is 0, -0.5 is -1, 0 is 0, 0.5 is 1, 1 is 0
	//4 = cos(input*pi)
	//5 = quadratic
	//6 = cubic
	//7 = absolute cubic
	//8 = 1/x
	//9 = absolute 1/x
	//10= flipped absolute, 1-(absolute linear)
	//11= completely random
	private int fcn = 1;
	
	//result from fcn will be multiplied by this factor.
	private double factor = 1;

	//val = (fcn(x+xoffset)*factor) + yoffset
	private double yoffset = 0;
	private double xoffset = 0;
	
	private double func(double x)
	{
		double result = 0;
		x += xoffset;
		switch(fcn)
		{
		case 0:
			result = 0;
			break;
		case 1:
			result = (x*factor);
			break;
		case 2:
			result = Math.abs(x);
			break;
		case 3:
			result = Math.abs(x);
			break;
		case 4:
			result = Math.sin(x);
			break;
		case 5:
			result = x*x;
			break;
		case 6:
			result = x*x*x;
			break;
		case 7:
			result = Math.abs(x*x);
			break;
		case 8:
			result = Math.abs(x*x*x);
			break;
		case 9:
			if(x == 0)
				result = (Math.random()*200)-100;
			else
				result = 0.5/x; //factor can go up to 2.
			break;
		case 10:
			result = 1 - Math.abs(x);
			break;
		default:
			result = Math.random()*2 - 1;
			break;
		}
		result *= factor;
		result += yoffset;
		result = Funcs.clamp(result, -1, 1);
		return result;
	}
	
	public void recieveFrom(Neuron n)
	{
		if(n == null) return;
		in.add(n.id);
	}
	
	public double output()
	{
		return neuralmap.get(id);
	}
	
	public void trimRandomConnection()
	{
		if(in.size() > 0)
		in.remove((int)(Math.random()*in.size()));
	}
	
	public void nuke()
	{
		in.clear();
		yoffset = 0;
		xoffset = 0;
		factor = 1;
		pcursx = 0;
		cursx = 0;
		combiner = 0;
		ptype = 0;
		fcn = 1;
		neuralmap.set(id, 0.0);
	}
	
	public Neuron clone()
	{
		Neuron n = new Neuron();
		n.combiner = combiner;
		n.cursx = cursx;
		n.factor = factor;
		n.fcn = fcn;
		n.id = id;
		n.in = new Stack<Integer>();
		for(Integer i : in)
		{
			n.in.add(i.intValue());
		}
		n.pcursx = pcursx;
		n.ptype = ptype;
		n.xoffset = xoffset;
		n.yoffset = yoffset;
		return n;
	}
}
