package nz.ac.bram.VectorDrawing;

import java.awt.Color;
import java.util.ArrayList;

import ecs100.UI;

public class Box implements Shape {
	
	ArrayList<Node> nodes = new ArrayList<Node>();
	int nodeCount = 2;
	int nodeIndex = 0;
	int sx, sy, ex, ey;
	int sizeX, sizeY;
	double lineWidth;
	Color c;
	boolean isFilled = false;

	public Box(int sx, int sy, Color c, boolean f) {
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
		int sx = 0, sy = 0;

		this.circle(n1.x(), n1.y(), 4);
		this.circle(n2.x(), n2.y(), 4);
		
		if(n1.x() > n2.x()) { sizeX = n1.x() - n2.x(); sx = n2.x(); }
		else { sizeX = n2.x() - n1.x(); sx = n1.x(); }
		if(n1.y() > n2.y()) { sizeY = n1.y() - n2.y(); sy = n2.y(); }
		else { sizeY = n2.y() - n1.y(); sy = n1.y(); }

		UI.setColor(c);
		UI.setLineWidth(this.lineWidth);
		if (isFilled) {
			UI.fillRect(sx, sy, (double)sizeX, (double)sizeY);
		} else {
			UI.drawRect(sx, sy, (double)sizeX, (double)sizeY);
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
