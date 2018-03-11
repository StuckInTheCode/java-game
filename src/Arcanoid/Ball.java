package Arcanoid;

import javafx.scene.paint.Color;

import javafx.scene.shape.Circle;

/**
 * Class Ball
 * @version 0.1 - Inherited from Circle
 */
public class Ball extends Circle{
    private static final double BALL_VELOCITY = 1.0;
    double velocityX=1;
    double velocityY=-1;
    private static final double SCREEN_WIDTH=800;
    private static final double SCREEN_HEIGHT=600;

    Ball(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(8);
        this.setFill(Color.CORAL);
        Game.gameRoot.getChildren().add(this);
    }
    void update(Paddle paddle) {
        this.setTranslateX(this.getTranslateX() + velocityX * 5);
        this.setTranslateY(this.getTranslateY() + velocityY * 5);

        if (this.getCenterX()+this.getTranslateX()<= 0)
            velocityX = BALL_VELOCITY;
        else if (this.getCenterX()+this.getTranslateX() >= SCREEN_WIDTH)
            velocityX = -BALL_VELOCITY;
        if (this.getCenterY()+this.getTranslateY() <= 0) {
            velocityY = BALL_VELOCITY;
        } else if (this.getCenterY()+this.getTranslateY() >= SCREEN_HEIGHT) {
            velocityY = -BALL_VELOCITY;
            this.setTranslateX(0);
            this.setTranslateY(0);
        }

    }

}