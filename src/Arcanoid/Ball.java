package Arcanoid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.Serializable;

/**
 * Class Ball
 * @version 0.1 - Inherited from Circle
 */
public class Ball extends Circle implements Serializable {

    public static final double BALL_VELOCITY = 1.0;
    private static final double MAX_SPEED_OF_BALL = 1.4;
    private static final double MIN_SPEED_OF_BALL = 0.6;
    /*Point2D top;
    Point2D bottom;
    Point2D left;
    Point2D right;
    Point2D center;*/
    double velocityX = 0;
    double velocityY = -0;
    private static final double SCREEN_WIDTH=800;
    private static final double SCREEN_HEIGHT=600;

    Ball(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(8);
        this.translateXProperty().addListener((observable, oldValue, newValue) -> {
            if (testBlockNBallCollision())
                changeDirectionX();
        });
        this.translateYProperty().addListener((observable, oldValue, newValue) -> {
            if (testBlockNBallCollision())
                changeDirectionY();
        });
        this.setFill(Color.CORAL);
    }

    boolean testBlockNBallCollision() {
        boolean collision = false;
        for (Block block : Game.blocks) {

            if (block.getBoundsInParent().intersects(this.getBoundsInParent())) {
                collision = true;
                block.destroyed = true;
                break;
            }
        }
        return collision;
    }

    boolean testBallNPaddleCollision() {
        return this.getBoundsInParent().intersects(Game.player.getBoundsInParent());// && this.getCenterY() + this.getTranslateY() == Game.player.getY();
    }

    void intersectWithPaddle() {
        this.velocityY = -this.velocityY;
        if (Game.player.isMoveLeft() && this.isMoveRight())
            Game.ball.velocityX = Game.ball.velocityX <= MIN_SPEED_OF_BALL ? MIN_SPEED_OF_BALL : this.velocityX - 0.2;
        if (Game.player.isMoveRight() && this.isMoveLeft())
            Game.ball.velocityX = Game.ball.velocityX <= -MIN_SPEED_OF_BALL ? MIN_SPEED_OF_BALL : this.velocityX - 0.2;
        if (Game.player.isMoveLeft() && this.isMoveLeft())
            Game.ball.velocityX = Game.ball.velocityX >= MAX_SPEED_OF_BALL ? MAX_SPEED_OF_BALL : this.velocityX + 0.2;
        if (Game.player.isMoveRight() && this.isMoveRight())
            Game.ball.velocityX = Game.ball.velocityX >= -MAX_SPEED_OF_BALL ? MAX_SPEED_OF_BALL : this.velocityX + 0.2;
    }

    private void changeDirectionX() {
        this.velocityX = -this.velocityX;
    }

    private void changeDirectionY() {
        this.velocityY = -this.velocityY;
    }
    boolean update(Paddle paddle) {
        boolean ball_fall=false;
        this.moveX();
        this.moveY();
        if(paddle.isMoving() && !this.isMoving())
            starting();
        if (this.getCenterX()+this.getTranslateX()<= 0)
            velocityX = -velocityX;
        else if (this.getCenterX()+this.getTranslateX() >= SCREEN_WIDTH)
            velocityX = -velocityX;
        if (this.getCenterY()+this.getTranslateY() <= 0) {
            velocityY = -velocityY;
        } else if (this.getCenterY()+this.getTranslateY() >= SCREEN_HEIGHT) {
            velocityY = -velocityY;
            ball_fall=true;
            goToStartPosition();
        }
        return ball_fall;

    }

    private void moveY() {
        int distance = (int) this.velocityY * 5;
        boolean diraction;
        double oldY = this.getTranslateY();
        int step = 0;
        for (int i = 0; i < Math.abs(distance); i++) {

            this.setTranslateY(oldY + step);
            diraction = velocityY > 0;
            if (diraction)
                step++;
            else
                step--;
        }
    }

    private void moveX() {
        int distance = (int) this.velocityX * 5;
        boolean diraction;
        double oldX = this.getTranslateX();
        int step = 0;
        for (int i = 0; i < Math.abs(distance); i++) {

            this.setTranslateX(oldX + step);
            //if (testBlockNBallCollision()) {
            //    changeDirectionX();
            //return;
            // }
            diraction = velocityX > 0;
            if (diraction)
                step++;
            else
                step--;
        }
    }
    private void changeDirection() {
    }

    void increaseSpeed() {
        this.velocityX = this.velocityX + 0.2;
        this.velocityY = this.velocityY + 0.2;
    }

    boolean isMoveLeft() {
        return this.velocityX < 0;
    }
    boolean isMoveRight()
    {
        return this.velocityX > 0;
    }
    boolean isMoving()
    {
        return velocityX!=0 && velocityY!=0;
    }
    void goToStartPosition()
    {
        this.setTranslateX(Game.player.getTranslateX());//+(Game.player.getWidth()/2));
        this.setTranslateY(8 - this.getRadius());
        this.velocityX=0;
        this.velocityY=0;
    }

    /**
     * Start moving the ball
     */
    private void starting() {
        velocityX = Game.player.isMoveRight() ? BALL_VELOCITY : -BALL_VELOCITY;
        velocityY=-BALL_VELOCITY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }
}