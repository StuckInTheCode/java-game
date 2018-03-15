package Arcanoid;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Class Ball
 * @version 0.1 - Inherited from Circle
 */
public class Ball extends Circle{

    private static final double BALL_VELOCITY = 1.0;
    Point2D top;
    Point2D bottom;
    Point2D left;
    Point2D right;
    Point2D center;
    double velocityX=1;
    double velocityY=-1;
    private static final double SCREEN_WIDTH=800;
    private static final double SCREEN_HEIGHT=600;

    Ball(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(8);
        //this.localToParent(this.getBoundsInLocal());
        this.top=new Point2D(x,y+8);
        this.bottom=new Point2D(x,y-8);
        this.left=new Point2D(x-8,y);
        this.right=new Point2D(x+8,y);
        this.center= new Point2D(x,y);
        this.translateXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (testBlockNBallCollision())
                    changeDirectionX();
            }


        });
        this.translateYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (testBlockNBallCollision())
                    changeDirectionY();
            }

        });
        this.setFill(Color.CORAL);
        Game.gameRoot.getChildren().add(this);
    }

    boolean testBlockNBallCollision() {
        boolean collision = false;
        for (Block block : Game.blocks) {

            if (!block.getBoundsInParent().intersects(this.getBoundsInParent()))
                continue;
            collision = true;
            //block.destroyed = true;
            block.Delete();
            block.setVisible(false);
            Game.blocks.remove(block);
            break;
        }
        return collision;
    }

    void changeDirectionX() {
        this.velocityX = -this.velocityX;
    }

    void changeDirectionY() {
        this.velocityY = -this.velocityY;
    }
    void update(Paddle paddle) {
        double newX = this.getTranslateX() + velocityX * 5;
        double newY = this.getTranslateY() + velocityY * 5;
        //this.setTranslateX(this.getTranslateX() + velocityX * 5);
        //this.setTranslateY(this.getTranslateY() + velocityY * 5);
        this.setTranslateX(newX);
        this.setTranslateY(newY);

        this.top= new Point2D (this.getCenterX()+newX,this.getCenterY()+newY+8);
        this.bottom= new Point2D (this.getCenterX()+newX,this.getCenterY()+newY-8);
        this.left= new Point2D (this.getCenterX()+newX-8,this.getCenterY()+newY);
        this.right= new Point2D (this.getCenterX()+newX+8,this.getCenterY()+newY);
        //boolean movingRight = value > 0;
        //this.center.add(newX,newY);
        this.center= new Point2D (this.getCenterX()+newX,this.getCenterY()+newY);
        /*if (this.getCenterX()+this.getTranslateX()<= 0)
            velocityX = BALL_VELOCITY;
        else if (this.getCenterX()+this.getTranslateX() >= SCREEN_WIDTH)
            velocityX = -BALL_VELOCITY;
        if (this.getCenterY()+this.getTranslateY() <= 0) {
            velocityY = BALL_VELOCITY;
        } else if (this.getCenterY()+this.getTranslateY() >= SCREEN_HEIGHT) {
            velocityY = -BALL_VELOCITY;
            this.setTranslateX(0);
            this.setTranslateY(0);
        }*/
        if (this.getCenterX()+this.getTranslateX()<= 0)
            velocityX = -velocityX;
        else if (this.getCenterX()+this.getTranslateX() >= SCREEN_WIDTH)
            velocityX = -velocityX;
        if (this.getCenterY()+this.getTranslateY() <= 0) {
            velocityY = -velocityY;
        } else if (this.getCenterY()+this.getTranslateY() >= SCREEN_HEIGHT) {
            velocityY = -velocityY;
            this.setTranslateX(0);
            this.setTranslateY(0);
        }

    }

    boolean isMoveLeft() {
        return this.velocityX < 0;
    }
    boolean isMoveRight()
    {
        return this.velocityX > 0;
    }

    boolean isMoveDown() {
        return this.velocityY < 0;
    }

    boolean isMoveUp() {
        return this.velocityY > 0;
    }
}