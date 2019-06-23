package nz.ac.bram.VectorDrawing;

public interface Shape {

	public Node getNextNode(int x, int y);
	public int hitNode(int x, int y);
	public void drawNodes();
	public void addNode();
	public void updateNode(int i, int x, int y);
	public void setLineWidth(double v);
	//public boolean withinShape(int x, int y);
	void drawShape();
}
