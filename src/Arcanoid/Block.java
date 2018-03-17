package Arcanoid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class Block
 * @version 0.0 - Inherited from Rectangle
 */
public class Block extends Rectangle{
	boolean destroyed = false;
    public final Bonus bonus;
    int breakable=0;
    public enum BlockType {
        BUBBLE, CRYSTALL, BONUS, BRICK, STONE, DIAMAND, UNBREAKABLE_BLOCK
    }
    BlockType typeOfBlock;
	Block(double x, double y) {
		this.setX(x);
		this.setY(y);
		this.setHeight(30);
		this.setWidth(30);
		this.setFill(Color.BLUE);
        this.bonus = new Bonus(Bonus.BONUS_TYPE.FROZEN, y);
        Game.gameRoot.getChildren().add(this);
	}

    static Block createBlock(double x, double y, BlockType type)
    {
        Block block = new Block(x, y);

        switch(type)
        {
            case BUBBLE:
            {
                block.setFill(Color.TURQUOISE);
                block.setArcHeight(15);
                block.setArcWidth(10);
                block.setOpacity(0.5);
                break;
            }
            case CRYSTALL:
            {}
            case BONUS:
            {
                block.setFill(Color.YELLOWGREEN);
                block.setSmooth(true);
                block.setAccessibleText("B");
                break;
            }
            case BRICK:
            {
                block.setFill(Color.SADDLEBROWN);
                block.breakable=1;
                break;
            }
            case STONE:
            {
                block.setFill(Color.GREY);
                block.breakable=2;
                break;
            }
            case DIAMAND:
            {
                block.setOpacity(70);
                block.setFill(Color.GHOSTWHITE);
                block.breakable=3;
                break;
            }
            case UNBREAKABLE_BLOCK:
            {
                block.setFill(Color.PEACHPUFF);
                block.breakable=4;
                break;
            }
        }
        return block;
    }
	boolean isDestroyed()
	{
		return this.destroyed;
	}
	/* Delete()
	{
		Game.gameRoot.getChildren().remove(this);
		this.setVisible(false);
	}*/


}
