package com.bioxx.jmapgen;

import com.bioxx.jmapgen.graph.Center;

public class GenericNode 
{
	private GenericNode upCanyon;
	private Center center;
	private GenericNode downCanyon;
	public int nodeNum = 0;

	public GenericNode(Center c)
	{
		center = c;	
	}

	public void setUp(GenericNode u)
	{
		upCanyon = u;
	}
	public void setDown(GenericNode d)
	{
		downCanyon = d;
	}

	public Center getCenter()
	{
		return center;
	}

	public GenericNode getUp()
	{
		return this.upCanyon;
	}

	public GenericNode getDown()
	{
		return this.downCanyon;
	}
}
