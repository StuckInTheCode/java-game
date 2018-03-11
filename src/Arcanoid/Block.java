package Arcanoid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class Block
 * @version 0.0 - Inherited from Rectangle
 */
public class Block extends Rectangle{
	boolean destroyed = false;
	Block(double x, double y) {
		this.setX(x);
		this.setY(y);
		this.setHeight(30);
		this.setWidth(30);
		this.setFill(Color.BLUE);
	     Game.gameRoot.getChildren().add(this);
	}
	boolean isDestroyed()
	{
		return this.destroyed;
	}
	void Delete()
	{
		//Game.gameRoot.getChildren().remove(this);
		this.setVisible(false);
	}


}
