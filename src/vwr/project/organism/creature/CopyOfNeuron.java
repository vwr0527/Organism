package vwr.project.organism.creature;

//import java.util.ArrayList;

public class CopyOfNeuron {
	/*
	public enum neuron_pattern
	{
		sensory,
		random,
		emitneg1,
		emit0,
		emit1,
		toggle,
		accumulator,
		oscillator,
		adder,
		multiplier,
		highest,
		lowest,
		avg
	};
	public neuron_pattern behavior;

	public ArrayList<CopyOfNeuron> in;
	private double val = 0;
	public double incoming_multiplier = 1;
	public double outgoing_multiplier = 1;
	private double threshhold = 10000;

	private void init()
	{
		in = new ArrayList<CopyOfNeuron>();
	}
	
	public CopyOfNeuron()
	{
		behavior = neuron_pattern.sensory;
	}
	
	public CopyOfNeuron(neuron_pattern p)
	{
		behavior = p;
		init();
	}
	
	public CopyOfNeuron(neuron_pattern p, double in_factor, double out_factor)
	{
		behavior = p;
		incoming_multiplier = in_factor;
		outgoing_multiplier = out_factor;
		init();
	}
	
	public CopyOfNeuron(neuron_pattern p, double out_factor)
	{
		behavior = p;
		outgoing_multiplier = out_factor;
		init();
	}
	
	public void recieve(double v)
	{
		if((behavior != neuron_pattern.accumulator)
		&& (behavior != neuron_pattern.toggle))
		{
			val = 0;
		}
		
		if(behavior == neuron_pattern.oscillator)
			val += v;
		if(behavior == neuron_pattern.adder ||
			behavior == neuron_pattern.accumulator)
			val += v;
		if(behavior == neuron_pattern.multiplier)
			val *= v;
		if(behavior == neuron_pattern.highest)
			if(val < v) val = v;
		if(behavior == neuron_pattern.lowest)
			if(val > v) val = v;
		if(behavior == neuron_pattern.avg)
			if(in.size() > 0) val += v/(in.size());
	}
	
	public void receivesFrom(CopyOfNeuron n)
	{
		if(n == this) return;
		if(behavior == neuron_pattern.emitneg1) return;
		if(behavior == neuron_pattern.emit0) return;
		if(behavior == neuron_pattern.emit1) return;
		if(behavior == neuron_pattern.random) return;
		in.add(n);
	}
	
	public void fire()
	{
		for(CopyOfNeuron n : in)
		{
			if(n.behavior == neuron_pattern.oscillator)
				recieve(Math.sin(n.val)*incoming_multiplier);
			else
			recieve(n.val()*incoming_multiplier);
		}
	}
	
	public double val()
	{
		if(Math.abs(val) > threshhold || Double.isInfinite(val) || Double.isNaN(val))
			val = 0;
		
		if(behavior == neuron_pattern.emitneg1) return -outgoing_multiplier;
		if(behavior == neuron_pattern.emit0) return 0;
		if(behavior == neuron_pattern.emit1) return outgoing_multiplier;
		if(behavior == neuron_pattern.toggle)
		{
			if(val == 0) val = (int)(Math.random()*2);
			else
			if(val == 1) val = -1;
			else
			if(val == -1) val = 1;
		}
		if(behavior == neuron_pattern.random)
			val = ((Math.random()*2)-1.0);
		
		return val*outgoing_multiplier;
	}
	
	public void nuke()
	{
		in.clear();
		val = 0;
		incoming_multiplier = 1;
		outgoing_multiplier = 1;
	}
	
	public neuron_pattern RandomNeuronPattern()
	{
		int pat = ((int)(Math.random()*12));
		if(pat == 0) return neuron_pattern.accumulator;
		if(pat == 1) return neuron_pattern.adder;
		if(pat == 2) return neuron_pattern.avg;
		if(pat == 3) return neuron_pattern.emit0;
		if(pat == 4) return neuron_pattern.emit1;
		if(pat == 5) return neuron_pattern.emitneg1;
		if(pat == 6) return neuron_pattern.highest;
		if(pat == 7) return neuron_pattern.lowest;
		if(pat == 8) return neuron_pattern.multiplier;
		if(pat == 9) return neuron_pattern.oscillator;
		if(pat == 10) return neuron_pattern.toggle;
		return neuron_pattern.random;
	}
	*/
}
