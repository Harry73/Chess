/**
 * Simple structure class to store two points that constitute a move.
 * 
 * Author: Ian Patel
 */

import java.awt.Point;

public class Move
{
	private Point to;
	private Point from;
	
	//Create a Move from two points
	public Move(Point from, Point to)
	{
		this.to = to;
		this.from = from;
	}
	
	//Set the "move to" point
	public void to(Point to)
	{
		this.to = to;
	}
	
	//Get the "move to" point
	public Point to()
	{
		return to;
	}
	
	//Set the "move from" point
 	public void from(Point from)
	{
		this.from = from;
	}
	
	//Get the "move from" point
	public Point from()
	{
		return from;
	}
}