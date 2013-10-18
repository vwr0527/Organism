package vwr.project.organism;

import javax.swing.JApplet;

public class Applet extends JApplet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Called when this applet is loaded into the browser.
    public void init()
    {
		AppletWindow window = new AppletWindow();
		World world = new World();
		window.world = world;
		world.window = window;
		this.add(window);
    }
}