package Arcanoid;

import javafx.scene.paint.Color;
//import java.awt.Color;
//import java.awt.Graphics;
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
		//getChildren().add(block);
	       // super.add(block);
	     //Game.blocks.add(this);
	     Game.gameRoot.getChildren().add(this);
	}

}
