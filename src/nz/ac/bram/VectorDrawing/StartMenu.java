package nz.ac.bram.VectorDrawing;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JColorChooser;

import ecs100.UI;

public class StartMenu {

	public ArrayList<Shape> shapes = new ArrayList<Shape>();
	public Shape tempShape;
	public int tempIndex = -1, shapeIndex = 0;
	private double mouseX, mouseY;
	public Node selectedNode;
	public int tool = 0;
	public double lineWidth = 5;
	public boolean isFilled = false, isMove = false, isGrab = false;
	public Color c = Color.black;

	private void toolLine() {
		tool = 0;
	}

	private void toolBox() {
		tool = 1;
	}

	private void toolCircle() {
		tool = 2;
	}

	private void toolSpline() {
		tool = 3;
	}

	private void getFilled(double v) {
		this.isFilled = !this.isFilled;
		UI.println("Filled " + isFilled);
	}

	private void getLineWidth(double v) {
		this.lineWidth = v;
	}

	private void getColour() {
		this.c = JColorChooser.showDialog(null, "Choose colour", Color.black);
	}

	private void doMouse(String action, double x, double y)	{

		mouseX = x; mouseY = y;
		if(isMove && !isGrab) {
			tempShape = shapes.get(shapeIndex);

			UI.clearGraphics();
			tempShape.updateNode(tempIndex, (int)mouseX, (int)mouseY);
			tempShape.setLineWidth(this.lineWidth);
			tempShape.drawShape();

			for (Shape s : shapes) {
				s.drawShape();
			}

			if ("released".equals(action)) {
				tempShape.updateNode(tempIndex, (int)mouseX, (int)mouseY);
				shapes.add(shapeIndex, tempShape);
				tempShape = null;
				isMove = false;
			}
		} else if(isMove && isGrab) {
			tempShape = shapes.get(shapeIndex);

		} else if (!isMove && !isGrab) {
			if ("released".equals(action) && tempShape == null) {

				switch (tool) {
					case 0:
						tempShape = new Line((int) x, (int) y, c);
						tempShape.setLineWidth(this.lineWidth);
						selectedNode = tempShape.getNextNode((int) x, (int) y);

						selectedNode = tempShape.getNextNode((int) x, (int) y);
						break;
					case 1:
						tempShape = new Box((int) x, (int) y, c, isFilled);
						tempShape.setLineWidth(this.lineWidth);
						tempShape.getNextNode((int) x, (int) y);
						break;
					case 2:
						tempShape = new Circle((int) x, (int) y, c, isFilled);
						tempShape.setLineWidth(this.lineWidth);
						tempShape.getNextNode((int) x, (int) y);
						break;
					case 3:
						tempShape = new Spline((int) x, (int) y, c);
						tempShape.setLineWidth(this.lineWidth);
						tempShape.getNextNode((int) x, (int) y);
						break;
				}
			} else if ("released".equals(action) && tempShape != null) {
				this.tempShape.getNextNode((int) x, (int) y);
				tempShape.addNode();
				selectedNode = tempShape.getNextNode((int) x, (int) y);
				if (selectedNode == null) {

					shapes.add(tempShape);
					tempShape = null;

				}
			}

			if (selectedNode != null) {
				selectedNode = new Node((int) x, (int) y);
			}

			if (this.tempShape != null) {
				UI.clearGraphics();
				this.tempShape.getNextNode((int) x, (int) y);
				this.tempShape.drawShape();

				for (Shape s : shapes) {
					s.drawShape();
				}
			}
		}
	}

	private void doKey(String k) {
		if(k.equalsIgnoreCase("m")) {
			int i = 0;
			for(Shape s : shapes) {
				if (s.hitNode((int)mouseX, (int)mouseY) != -1) {
					tempIndex = s.hitNode((int)mouseX, (int)mouseY);
					shapeIndex = i;
					UI.println("Move NODE");
					isMove = true;
					break;
				} else if (s.withinShape((int)mouseX, (int)mouseY)) {
					shapeIndex = i;
					UI.println("Move SHAPE");
					isMove = true;
					isGrab = true;
					break;
				}

				i++;
			}
		}
	}

	public StartMenu() {
		UI.initialise();
		UI.addButton("Line", 								this::toolLine);
		UI.addButton("Box", 								this::toolBox);
		UI.addButton("Circle", 							this::toolCircle);
		UI.addButton("Spline", 							this::toolSpline);
		UI.addSlider("Hollow - Filled",0, 1, 0,  this::getFilled);
		UI.addSlider("Thin - Thick",1, 10, 5,  	this::getLineWidth);
		UI.addButton("Colour", 							this::getColour);
		UI.setMouseMotionListener(							this::doMouse);
		UI.setKeyListener(									this::doKey);
	}

	public static void main(String[] args) {
		new StartMenu();
	}

}
