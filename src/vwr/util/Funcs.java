package vwr.util;

public class Funcs {
	public static double clamp(double num, double min, double max)
	{
		if(num < min)
		{
			return min;
		}
		if(num > max)
		{
			return max;
		}
		return num;
	}
	public static int clamp(int num, int min, int max)
	{
		if(num < min)
		{
			return min;
		}
		if(num > max)
		{
			return max;
		}
		return num;
	}
	//from Jonathan ANTOINE's blog
	// "Calculate the real difference between two angles, keeping the correct sign"
	public static double calculateDifferenceBetweenAngles(double firstAngle, double secondAngle)
	{
		double difference = secondAngle - firstAngle;
		while (difference < -Math.PI) difference += Math.PI*2;
		while (difference > Math.PI) difference -= Math.PI*2;
		return difference;
	}
	
	public static double convertScale(double val, double valmin, double valmax, double outmin, double outmax)
	{
		double norm = (val-valmin)/(valmax-valmin); //norm is from -1~1
		return (norm*(outmax-outmin))+outmin;
	}
}
