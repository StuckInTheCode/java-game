package Arcanoid;

import javafx.scene.paint.Color;
//import java.awt.Color;
//import java.awt.Graphics;
import javafx.scene.shape.Rectangle;

/**
 * Class Paddle
 * @version 0.0 - Inherited from Rectangle
 */
public class Paddle extends Rectangle {

	double velocity = 0.0;

	public Paddle(double x, double y) {
		this.setX(x);
		this.setY(y);
		this.setHeight(20);
		this.setWidth(80);
		this.setArcHeight(0.5);
		this.setArcWidth(0.5);
		this.setFill(Color.YELLOW);

	}


}
