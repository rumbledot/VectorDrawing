package nz.ac.bram.VectorDrawing;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import ecs100.UI;

public class Spline implements Shape {

	ArrayList<Node> nodes = new ArrayList<Node>();
	int nodeCount = 3;
	int nodeIndex = 0;
	int sx, sy;
	double lineWidth;
	Color c;

	public Spline(int sx, int sy, Color c) {
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
		Node op = n1;
		Node np = op;

		if (nodeIndex < 2) {

			this.circle(n1.x(), n1.y(), 4);
			this.circle(n2.x(), n2.y(), 4);
			UI.setColor(Color.orange);
			UI.drawLine((double)n1.x(), (double)n1.y(), (double)n2.x(), (double)n2.y());
		}
		
		if (nodes.size() >= 2) {

			this.circle(n1.x(), n1.y(), 4);
			this.circle(n2.x(), n2.y(), 4);
			UI.setColor(Color.orange);
			UI.drawLine((double)n1.x(), (double)n1.y(), (double)n2.x(), (double)n2.y());

			Node n3 = nodes.get(2);
			this.circle(n3.x(), n3.y(), 4);
			UI.setColor(Color.orange);
			UI.drawLine((double)n2.x(), (double)n2.y(), (double)n3.x(), (double)n3.y());

			for (float t = 0; t < 1.0f; t += 0.01f)
			{
				UI.setLineWidth(this.lineWidth);
				UI.setColor(c);

				int npX = (int)((1 - t) * (1 - t) * n1.x() +
						2 * (1 - t) * t * n2.x() + t * t * n3.x());
				int npY = (int)((1 - t) * (1 - t) * n1.y() + 
						2 * (1 - t) * t * n2.y() + t * t * n3.y());

				UI.drawLine(np.x(), np.y(), npX, npY);
				np = new Node(npX, npY);
				op = np;
			}
			UI.setLineWidth(1);
			UI.setColor(Color.black);
		}
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