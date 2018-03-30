package Arcanoid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class Block
 * @version 0.0 - Inherited from Rectangle
 */

public class Block extends Rectangle {
	boolean destroyed = false;
    public Bonus bonus;
    int breakable=0;
    public enum BlockType {
        BUBBLE, CRYSTALL, BONUS, BRICK, STONE, DIAMAND, UNBREAKABLE_BLOCK
    }
    BlockType typeOfBlock;

    private Block(double x, double y) {
		this.setX(x);
		this.setY(y);
		this.setHeight(30);
		this.setWidth(30);
		this.setFill(Color.BLUE);
        //this.bonus = new Bonus(Bonus.BONUS_TYPE.FROZEN,x, y);
        //this.bonus.setVisible(false);
        /*bonus.translateYProperty().addListener((observable, oldValue, newValue) -> {
            if (testPaddleCollision()) {
                bonus.parallelTransition.stop();
                //bonusIV.setVisible(false);
                bonus.isCatched = true;
            }
        });*/
        /*this.translateYProperty().addListener((observable, oldValue, newValue) -> {
            for (Block block : Game.blocks) {
                if(block == this)
                block.setTranslateY(this.getTranslateY());
            }
        });*/

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
                block.bonus = new Bonus(Bonus.BONUS_TYPE.NO_BONUS, x, y);
                //block.bonus = new Pane();
                block.bonus.setVisible(false);
                break;
            }
            case CRYSTALL: {
                block.bonus = new Bonus(Bonus.BONUS_TYPE.FROZEN, x, y);
                block.bonus.setVisible(false);
            }
            case BONUS:
            {
                block.setFill(Color.YELLOWGREEN);
                block.setSmooth(true);
                block.setAccessibleText("B");
                block.bonus = new Bonus(Bonus.BONUS_TYPE.LIFE, x, y);
                block.bonus.setVisible(false);
                break;
            }
            case BRICK:
            {
                block.setFill(Color.SADDLEBROWN);
                block.breakable=1;
                block.bonus = new Bonus(Bonus.BONUS_TYPE.SPEED, x, y);
                block.bonus.setVisible(false);
                break;
            }
            case STONE:
            {
                block.setFill(Color.GREY);
                block.breakable=2;
                block.bonus = new Bonus(Bonus.BONUS_TYPE.FROZEN, x, y);
                block.bonus.setVisible(false);
                break;
            }
            case DIAMAND:
            {
                block.setOpacity(70);
                block.setFill(Color.GHOSTWHITE);
                block.breakable=3;
                block.bonus = new Bonus(Bonus.BONUS_TYPE.LONG_PADDLE, x, y);
                block.bonus.setVisible(false);
                break;
            }
            case UNBREAKABLE_BLOCK:
            {
                block.setFill(Color.PEACHPUFF);
                block.breakable=4;
                block.bonus = new Bonus(Bonus.BONUS_TYPE.FROZEN, x, y);
                block.bonus.setVisible(false);
                break;
            }
        }
        Game.blocks.add(block);
        Game.npsRoot.getChildren().add(block.bonus);
        Game.npsRoot.getChildren().add(block);
        return block;
    }
	boolean isDestroyed()
	{
		return this.destroyed;
	}

    void moveDown(double step) {
        this.setTranslateY(this.getTranslateY() + step);
    }

    void playBonus() {

    }

    /* Delete()
	{
		Game.gameRoot.getChildren().remove(this);
		this.setVisible(false);
	}*/


}
