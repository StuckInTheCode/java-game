package Arcanoid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//import java.awt.Color;
//import java.awt.Graphics;

/**
 * Class Paddle
 * @version 0.0 - Inherited from Rectangle
 */
public class Paddle extends Rectangle {

	private static final double SCREEN_WIDTH = 800;
	private double velocity = 0.0;

	public Paddle(double x, double y) {
		this.setX(x);
		this.setY(y);
		this.setHeight(20);
		this.setWidth(80);
		this.setFill(Color.YELLOW);

	}
	void update() {
		this.setTranslateX(this.getTranslateX()+ velocity * 7.0);
	}

	void stopMove() {
		velocity = 0.0;
	}

	boolean isMoveLeft()
	{
		return this.velocity < 0;
	}

	boolean isMoveRight()
	{
		return this.velocity > 0;
	}

	void moveLeft() {
		if (this.getTranslateX() > (-SCREEN_WIDTH+this.getWidth())/2) {
			velocity = -1.5;
		} else {
			velocity = 0.0;
		}
	}

	void moveRight() {
		if (this.getTranslateX() < (SCREEN_WIDTH-this.getWidth())/2) {
			velocity = 1.5;
		} else {
			velocity = 0.0;
		}
	}


}
