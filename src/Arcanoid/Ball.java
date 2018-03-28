package Arcanoid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Class Ball
 * @version 0.1 - Inherited from Circle
 */
public class Ball extends Circle{

    public static final double BALL_VELOCITY = 1.0;
    private static final double MAX_SPEED_OF_BALL = 1.4;
    private static final double MIN_SPEED_OF_BALL = 0.6;
    /*Point2D top;
    Point2D bottom;
    Point2D left;
    Point2D right;
    Point2D center;*/
    double velocityX=0;
    private double velocityY = -0;
    private static final double SCREEN_WIDTH=800;
    private static final double SCREEN_HEIGHT=600;

    Ball(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(8);
        /*this.top=new Point2D(x,y+8);
        this.bottom=new Point2D(x,y-8);
        this.left=new Point2D(x-8,y);
        this.right=new Point2D(x+8,y);
        this.center= new Point2D(x,y);*/
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

    private boolean testBlockNBallCollision() {
        boolean collision = false;
        for (Block block : Game.blocks) {

            if (block.getBoundsInParent().intersects(this.getBoundsInParent())) {
                collision = true;
                block.destroyed = true;
                //block.Delete();
                //block.setVisible(false);
                //Game.blocks.remove(block);
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
       /* int newX = (int)(this.getTranslateX() + velocityX * 5);
        int oldX = (int)this.getTranslateX();
        int oldY = (int)(this.getTranslateY());
        int newY = (int)(this.getTranslateY() + velocityY * 5);
        Point2D velocity=new Point2D(velocityX * 5,velocityY * 5);
        int dist = (int)velocity.distance(0,0);

        for( int i=0;i<dist;i++) {
            this.setTranslateX(oldX++);
            this.setTranslateY(oldY++);
        }*/

        this.moveX();
        this.moveY();

        /*this.top= new Point2D (this.getCenterX()+newX,this.getCenterY()+newY+8);
        this.bottom= new Point2D (this.getCenterX()+newX,this.getCenterY()+newY-8);
        this.left= new Point2D (this.getCenterX()+newX-8,this.getCenterY()+newY);
        this.right= new Point2D (this.getCenterX()+newX+8,this.getCenterY()+newY);
        this.center= new Point2D (this.getCenterX()+newX,this.getCenterY()+newY);*/

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
            // if (testBlockNBallCollision()) {
            //     changeDirectionY();
            //return;
            // }
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

   /* private void move(int x, int y)
    {
        int distanceX = (int)this.velocityX*5;
        boolean diractionX= distanceX>0;
        int distanceY = (int)this.velocityY*5;
        boolean diractionY= distanceY>0;
        double oldX=this.getTranslateX();
        double oldY=this.getTranslateY();
        double step = distanceX>distanceY?(double)distanceY/distanceX:(double)distanceX/distanceY;
        int X=0;
        int Y=0;
        //for(int i=0;i<Math.abs(distance);i++){
            this.setTranslateX(oldX+X);
            this.setTranslateY(oldY+Y);
            if (testBlockNBallCollision()) {
                changeDirection();
                //return;
            }
            X=diractionX?X+1:X-1;
            Y=diractionY?Y+1:Y-1;
            //else
            //    step--;
       // }
    }*/

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
        this.setTranslateX(0);
        this.setTranslateY(0);
        this.velocityX=0;
        this.velocityY=0;
    }

    /**
     * Start moving the ball
     */
    private void starting() {
        velocityX=BALL_VELOCITY;
        velocityY=-BALL_VELOCITY;
    }
}