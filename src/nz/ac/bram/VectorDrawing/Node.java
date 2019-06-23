package nz.ac.bram.VectorDrawing;

public class Node {
	private int x;
	private int y;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}

	public void updateXY(int x, int y) { this.x = x; this.y = y; }
	
}
