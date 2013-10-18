package vwr.project.organism;
import java.awt.*;
//import java.awt.event.ComponentEvent;
//import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import vwr.sys.KeyHandler;
import vwr.sys.QuitListener;

public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int RESOLUTION_X = 800;
	private static final int RESOLUTION_Y = 600;
	public World world;
	private int x_margin = 7;
	private int top_margin = 34;
	private int bottom_margin = 7;
	private boolean keep_aspect = true;
	private boolean scale_image = true;
	private BufferedImage buffer;
	
	public Window()
	{
		setVisible(true);
		setSize(820,645);
		addWindowListener(new QuitListener());
		this.addKeyListener(new KeyHandler());
		this.setBackground(Color.darkGray);
		
		buffer = new BufferedImage(RESOLUTION_X,RESOLUTION_Y, BufferedImage.TYPE_INT_RGB);
	}
	int prevsize = 0;
	public void paint(Graphics g)
	{
		if(prevsize != getWidth()+getHeight())
		{
			prevsize = getWidth() + getHeight();
			g.clearRect(0, 0, getWidth(), getHeight());
		}
		if(world == null)
		{
			g.drawString("No World",(getWidth()/2)-20, getHeight()/2);
			return;
		}
		
		world.draw(buffer.createGraphics(), buffer.getWidth(), buffer.getHeight());
		
		Image img = buffer;

		if(img == null)
		{
			g.drawString("No Image", (getWidth()/2)-20, getHeight()/2);
			return;
		}

		world.printInfo(buffer.getGraphics());

		if(scale_image)
		{
			if(keep_aspect)
			{
				int temp = getHeight() - (top_margin + bottom_margin);
				temp *= ((double)img.getWidth(null) / (double)img.getHeight(null));
				temp = (getWidth() - temp)/2;
				g.drawImage(img, temp, top_margin, getWidth() - (temp*2), getHeight() - (bottom_margin+top_margin), null);
			}
			else
			{			
				g.drawImage(img, x_margin, top_margin, getWidth() - (x_margin*2), getHeight() - (bottom_margin+top_margin), null);
			}
		}
		else g.drawImage(img, x_margin, top_margin, null);
	}
}
