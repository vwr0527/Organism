package vwr.project.organism.creature;

//import java.util.ArrayList;

//import vwr.util.Funcs;

public class CopyOfBrain implements Cloneable
{
	/*
	private String DNA = "";
	private ArrayList<CopyOfNeuron> neurons = new ArrayList<CopyOfNeuron>();
	private ArrayList<CopyOfNeuron> actuators = new ArrayList<CopyOfNeuron>();
	private ArrayList<CopyOfNeuron> sensors = new ArrayList<CopyOfNeuron>();
	
	private CopyOfNeuron side_controller = new CopyOfNeuron(CopyOfNeuron.neuron_pattern.avg);
	private CopyOfNeuron forward_controller = new CopyOfNeuron(CopyOfNeuron.neuron_pattern.avg);
	private CopyOfNeuron rotation_controller = new CopyOfNeuron(CopyOfNeuron.neuron_pattern.avg);
	private CopyOfNeuron bite_controller = new CopyOfNeuron(CopyOfNeuron.neuron_pattern.avg);
	private CopyOfNeuron grab_controller = new CopyOfNeuron(CopyOfNeuron.neuron_pattern.avg);
	
	private CopyOfNeuron touch_sense = new CopyOfNeuron();
	private CopyOfNeuron pain_sense = new CopyOfNeuron();
	private CopyOfNeuron taste_sense = new CopyOfNeuron();
	
	private CopyOfNeuron food_dist = new CopyOfNeuron();
	private CopyOfNeuron food_leftright = new CopyOfNeuron();

	private CopyOfNeuron creature_dist = new CopyOfNeuron();
	private CopyOfNeuron creature_leftright = new CopyOfNeuron();
	
	private CopyOfNeuron counter = new CopyOfNeuron(CopyOfNeuron.neuron_pattern.accumulator);
	public CopyOfBrain()
	{
		actuators.add(side_controller);
	}
	public void print()
	{
		for(CopyOfNeuron n : neurons)
		{
			System.out.println("---------------");
			System.out.println(n + " type = " + n.behavior);
			for(CopyOfNeuron m: n.in)
			{
				System.out.println("connected to " + actuatorName(m));
			}
		}
	}
	private String actuatorName(CopyOfNeuron m) {
		if(m == side_controller) return "side_controller";
		if(m == forward_controller) return "forward_controller";
		if(m == rotation_controller) return "rotation_controller";
		return "unknown : "+m.toString();
	}
	public void Random()
	{
		wash();

		neurons.add(side_controller);
		neurons.add(forward_controller);
		neurons.add(rotation_controller);
		
		int numneurons = 20;
		for(int i = 0; i < numneurons; ++i)
		{
			CopyOfNeuron n = new CopyOfNeuron();
			neurons.add(n);
		}
		for(CopyOfNeuron n : neurons)
		{
			n.receivesFrom(neurons.get( (int) (Math.random()*neurons.size()) ));
			n.receivesFrom(neurons.get( (int) (Math.random()*neurons.size()) ));
			n.receivesFrom(neurons.get( (int) (Math.random()*neurons.size()) ));
			if(Math.random() > 0.5)
				n.receivesFrom(neurons.get( (int) (Math.random()*neurons.size()) ));
			if(Math.random() > 0.5)
				n.receivesFrom(neurons.get( (int) (Math.random()*neurons.size()) ));
			
			if(Math.random() > 0.5)
			{
				n.incoming_multiplier = 10*Math.random()*Math.random();
			}
			if(Math.random() > 0.5)
			{
				n.outgoing_multiplier = 10*Math.random()*Math.random();
			}
		}
		for(int i = 8 + (int)(Math.random()*8); i > 0; --i)
		{
			randomActuator().receivesFrom(neurons.get( (int) (Math.random()*neurons.size()) ));
		}
	}
	public CopyOfNeuron randomActuator()
	{
		int rand = (int)(Math.random()*3);
		if(rand == 0)return forward_controller;
		if(rand == 1)return side_controller;
		return rotation_controller;
	}
	//test brain;
	public void Jiggler()
	{
		wash();

		neurons.add(side_controller);
		neurons.add(forward_controller);
		neurons.add(rotation_controller);
		
		CopyOfNeuron oscillator = new CopyOfNeuron(CopyOfNeuron.neuron_pattern.oscillator, 0.7, 1.0);
		oscillator.receivesFrom(counter);
		forward_controller.receivesFrom(oscillator);
		CopyOfNeuron one = new CopyOfNeuron(CopyOfNeuron.neuron_pattern.emit1, 0.01);
		rotation_controller.receivesFrom(one);
		neurons.add(one);
		neurons.add(counter);
		neurons.add(oscillator);
	}
	public void look(Vision v)
	{
		
	}
	public void think()
	{
		counter.recieve(1);
		for(CopyOfNeuron n : neurons)
		{
			n.fire();
		}
	}
	public double getMoveSideways()
	{
		return Funcs.clamp(side_controller.val(), -1, 1);
	}
	public double getMoveForward()
	{
		return Funcs.clamp(forward_controller.val(), -1, 2);
	}
	public double getRotation()
	{
		return rotation_controller.val();
	}
	public int numCopyOfNeurons() {
		return neurons.size();
	}
	public void wash()
	{
		for(CopyOfNeuron n : neurons)
		{
			n.nuke();
		}
		neurons.clear();
	}
	public CopyOfBrain clone()
	{
		CopyOfBrain c = new CopyOfBrain();
		c.wash();
		c.neurons = neurons;
		return c;
	}
	*/
}
