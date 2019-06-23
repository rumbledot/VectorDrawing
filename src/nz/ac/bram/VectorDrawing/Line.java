package nz.ac.bram.VectorDrawing;

import java.awt.Color;
import java.util.ArrayList;

import ecs100.UI;

public class Line implements Shape{

	ArrayList<Node> nodes = new ArrayList<Node>();
	int nodeCount = 2;
	int nodeIndex = 0;
	int sx, sy, ex, ey;
	int sizeX, sizeY;
	double lineWidth;
	Color c;
	
	public Line(int sx, int sy, Color c) {
		this.sx = sx;
		this.sy = sy;
		this.c = c;
		nodes.add(new Node(sx, sy));
		nodeIndex++;
	}
	
	public Node getNextNode(int x, int y) {
		if (nodeIndex == nodeCount) {
			return null;
		} else {
			Node n = new Node(x, y);
			nodes.add(nodeIndex, n);
			return nodes.get(nodes.size() - 1);
		}
	}

	public void addNode() {
		nodeIndex++;
	}

	public void updateNode(int i, int x, int y) {
		nodes.get(i).updateXY(x, y);
	}

	public int hitNode(int x, int y) {
		int i = 0;
		for (Node n : nodes) {
			if(n.x() >= x - 2 && n.x() <= x + 2 && 
					n.y() >= y - 2 && n.y() <= y + 2) {
				circleFill(n.x(), n.y(), 4);
				return i;
			}
			i++;
		}
		return -1;
	}

	public void drawNodes() {
		for(Node n : nodes) {
			int sx, sy;
			sx = n.x(); sy = n.y();
			this.circle(sx, sy, 4);
		}
	}
	
	public void drawShape() {
		Node n1 = nodes.get(0);
		Node n2 = nodes.get(1);
		
		this.circle(n1.x(), n1.y(), 4);
		this.circle(n2.x(), n2.y(), 4);
		UI.setLineWidth(this.lineWidth);
		UI.setColor(c);
		UI.drawLine((double)n1.x(), (double)n1.y(), (double)n2.x(), (double)n2.y());
		UI.setLineWidth(1);
		UI.setColor(Color.black);
	}
	
	private void circle(int x, int y, int d) {
		UI.setLineWidth(1);
		UI.setColor(Color.orange);
		UI.drawOval(x - d/2, y - d/2, d, d);
		UI.setColor(Color.black);
	}
	
	private void circleFill(int x, int y, int d) {
		UI.setLineWidth(1);
		UI.setColor(Color.orange);
		UI.fillOval(x - d/2, y - d/2, d, d);
		UI.setColor(Color.black);
	}

	public void setLineWidth(double v) {
		this.lineWidth = v;
	}
/*
	public boolean withinShape(int x, int y) {
		Node n1 = nodes.get(0);
		Node n2 = nodes.get(1);

		if(n1.x() > n2.x()) {
			if(x > n2.x() && x < n1.x()) { return true; }
		} else {
			if(x > n1.x() && x < n2.x()) { return true; }
		}

		if(n1.y() > n2.y()) {
			if(y > n2.y() && y < n1.y()) { return true; }
		} else {
			if(y > n1.y() && y < n2.y()) { return true; }
		}

		return false;
	}*/
}
