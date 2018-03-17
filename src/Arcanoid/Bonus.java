package Arcanoid;

import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Bonus extends Pane{
    enum BONUS_TYPE{FROZEN,LIFE,SPEED,LONG_PADDLE}

    private static Image bonus = new Image("bonus.png");
    private static ImageView bonusIV = new ImageView(bonus);
    private BONUS_TYPE type;
    private int width = 30;
    private int height = 30;
    private boolean isCatched = false;
    private TranslateTransition translateTransition;
    ParallelTransition parallelTransition;
    private FramesAnimation animation;
    public Point2D playerVelocity = new Point2D(0,0);
    Bonus(BONUS_TYPE mType,double y)
    {
        type=mType;
        translateTransition =
                new TranslateTransition(Duration.millis(10000), this);
        translateTransition.setFromY(y);
        translateTransition.setToY(600);
        translateTransition.setCycleCount(1);

        bonusIV.setFitWidth(30);
        bonusIV.setFitHeight(30);
        switch(type)
        {
            case FROZEN: {
                bonusIV.setViewport(new Rectangle2D(0,0,width,height));
                animation = new FramesAnimation(bonusIV, Duration.millis(200),2,1,0,0,width,height);
                getChildren().addAll(bonusIV);
                break;
            }
            case LIFE:
            {

            }
            case SPEED:
            {

            }
            case LONG_PADDLE:
            {

            }
        }
        parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                translateTransition,
                animation
        );
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        bonusIV.translateYProperty().addListener((observable, oldValue, newValue) -> {
            if (testPaddleCollision()) {
                parallelTransition.stop();
                isCatched = true;
            }
        });
        //parallelTransition.play();
    }

    private boolean testPaddleCollision() {
        return this.getBoundsInParent().intersects(Game.player.getBoundsInParent());
    }

    void useBonus(Paddle paddle, Ball ball)
    {
       /* switch(this.type)
        {
            case FROZEN:{
                ball.
                break;
            }
            case LIFE:
            {

            }

        }*/
    }

}
