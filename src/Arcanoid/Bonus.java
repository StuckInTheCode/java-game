package Arcanoid;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Bonus extends Pane{
    enum BONUS_TYPE{FROZEN,LIFE,SPEED,LONG_PADDLE}

    static Image bonus=new Image("bonus.png");
    static ImageView bonusIV= new ImageView(bonus);
    BONUS_TYPE type;
    int width = 30;
    int height = 30;
    boolean isCatched=false;
    TranslateTransition translateTransition;
    ParallelTransition parallelTransition;
    public FramesAnimation animation;
    public Point2D playerVelocity = new Point2D(0,0);
    Bonus(BONUS_TYPE mType,double y)
    {
        type=mType;
        translateTransition =
                new TranslateTransition(Duration.millis(10000), this);
        translateTransition.setFromY(y);
        translateTransition.setToY(600);
        translateTransition.setCycleCount(1);
        bonusIV.translateYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(testPaddleCollision()) {
                    parallelTransition.stop();
                    isCatched=true;
                }
            }
        });
        bonusIV.setFitWidth(30);
        bonusIV.setFitHeight(30);
        switch(type)
        {
            case FROZEN: {
                bonusIV.setViewport(new Rectangle2D(0,0,width,height));
                animation = new FramesAnimation(bonusIV, Duration.millis(200),2,1,0,0,width,height);
                getChildren().addAll(bonusIV);
                //bonusIV.setX(0);
                //bonusIV.setY(0);
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
        /*parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                translateTransition,
                animation
        );
        parallelTransition.setCycleCount(Timeline.INDEFINITE);*/

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
    /*void changeKeyFrame()
    {
        if(this.bonusIV.getLayoutX()==0)
            this.bonusIV.setLayoutX(30);
        else
            this.bonusIV.setLayoutX(0);
    }*/

}
