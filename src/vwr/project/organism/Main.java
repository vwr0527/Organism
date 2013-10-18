package vwr.project.organism;

public class Main
{
	public static void main(String[] args)
	{
		Window window = new Window();
		World world = new World();
		window.world = world;
		world.window = window;
	}
}
