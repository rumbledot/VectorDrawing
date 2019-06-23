package nz.ac.bram.VectorDrawing;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import ecs100.UI;

public class Circle implements Shape {
	ArrayList<Node> nodes = new ArrayList<Node>();
	int nodeCount = 2;
	int nodeIndex = 0;
	int sx, sy, ex, ey;
	int sizeX, sizeY;
	double lineWidth;
	Color c;
	boolean isFilled = false;

	public Circle(int sx, int sy, Color c, boolean f) {
		this.sx = sx;
		this.sy = sy;
		this.c = c;
		this.isFilled = f;
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
		int r = 0;

		this.circle(n1.x(), n1.y(), 4);
		this.circle(n2.x(), n2.y(), 4);
		 
		r = (int)Point2D.distance(n1.x(), n1.y(), n2.x(), n2.y());

		UI.setColor(c);
		UI.setLineWidth(this.lineWidth);
		if (isFilled) {
			UI.fillOval(n1.x() - r, n1.y() - r, (double)r * 2, (double)r * 2);
			UI.setColor(Color.orange);
			UI.drawLine(n1.x(), n1.y(), n2.x(), n2.y());
		} else {
			UI.drawOval(n1.x() - r, n1.y() - r, (double)r * 2, (double)r * 2);
			UI.setColor(Color.orange);
			UI.drawLine(n1.x(), n1.y(), n2.x(), n2.y());
		}
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
}
