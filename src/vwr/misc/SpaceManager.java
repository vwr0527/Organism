package vwr.misc;
import java.awt.Graphics;
import java.awt.Rectangle;

// aborted

public class SpaceManager {
	private int _cols;
	private int _rows;
	private double _xmin;
	private double _xmax;
	private double _ymin;
	private double _ymax;
	public Rectangle bounds;
	private TileContainer[][] _tile;
	public SpaceManager(int numcolumns, int numrows, double xmin, double xmax, double ymin, double ymax)
	{
		_cols = numcolumns;
		_rows = numrows;
		_xmin = xmin;
		_xmax = xmax;
		_ymin = ymin;
		_ymax = ymax;
		
		bounds = new Rectangle((int)xmin, (int)ymin, (int)(xmax-xmin), (int)(ymax-ymin));
		_tile = new TileContainer[_cols][_rows];
		for(int curs_x = 0; curs_x < _cols; ++curs_x)
		{
			for(int curs_y = 0; curs_y < _rows; ++curs_y)
			{
				_tile[curs_x][curs_y] = new TileContainer();
			}
		}
	}
	public int cols()
	{
		return _cols;
	}
	public int rows()
	{
		return _rows;
	}
	public TileContainer getTile(int x, int y)
	{
		return _tile[x][y];
	}
	public void showTile(Graphics g, int x, int y)
	{
		double start = _xmin;
		double end = _xmax;
		double distance = end - start;
		int numdivisions = _cols;
		double tilewidth = distance / numdivisions;
		start = _ymin;
		end = _ymax;
		distance = end - start;
		numdivisions = _rows;
		double tileheight = distance / numdivisions;
		double box_x = _xmin + (x*tilewidth);
		double box_y = _ymin + (y*tileheight);
		g.drawRect((int)box_x, (int)box_y, (int)tilewidth, (int)tileheight);
	}
}
