package edu.school21.chase.astar;

import edu.school21.chase.utils.Vector2;

public class AStarNode implements Vector2<Integer> {
	private Integer x;
	private Integer y;
	private Integer g;
	private Integer h;
	private AStarNode parent;

	public AStarNode(Integer x, Integer y, Integer g, Integer h) {
		this(x, y, g, h, null);
	}	
	
	public AStarNode(Integer x, Integer y, Integer g, Integer h, AStarNode parent) {
		this.x = x;
		this.y = y;
		this.g = g;
		this.h = h;
		this.parent = parent;
	}

	@Override
	public Integer getX() {
		return this.x;
	}

	@Override
	public Integer getY() {
		return this.y;
	}

	public Integer getG() {
		return g;
	}

	public Integer getH() {
		return h;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public void setG(Integer g) {
		this.g = g;
	}

	public void setH(Integer h) {
		this.h = h;
	}

	public AStarNode getParent() {
		return parent;
	}

	public boolean equals(AStarNode other) {
		return this.getX() == other.getX() && this.getY() == other.getY();
	}

	public String ToString()
	{
		return "AStarNode{" +
				"x=" + x +
				", y=" + y +
				", g=" + g +
				", h=" + h +
				'}';
		// return "(" + x +
		// 		", " + y + ")";
	}
}

// class AStarNode implements VectorInt {
// 	public int          x;
// 	public int          y;
// 	public AStarNode    parent;

// 	public AStarNode(int x, int y, int g, int h)
// 	{
// 		this(x, y, g, h, null);
// 	}
	
// 	public AStarNode(int x, int y, int g, int h, AStarNode parent)
// 	{
// 		this.x = x;
// 		this.y = y;
// 		this.g = g;
// 		this.h = h;
// 		this.parent = parent;
// 	}

// 	@Override
// 	public int getX() {
// 		return x;
// 	}

// 	@Override
// 	public int getY() {
// 		return y;
// 	}

// 	public Integer getG() {
// 		return g;
// 	}

// 	public Integer getH() {
// 		return h;
// 	}

// 	public AStarNode getParent() {
// 		return parent;
// 	}

// 	public String ToString()
// 	{
// //            return "AStarNode{" +
// //                    "x=" + x +
// //                    ", y=" + y +
// //                    ", g=" + g +
// //                    ", h=" + h +
// //                    '}';
// 		return "(" + x +
// 				", " + y + ")";
// 	}
// }