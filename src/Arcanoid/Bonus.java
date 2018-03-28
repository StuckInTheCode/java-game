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
    private static final Image bonus = new Image("bonus.png");
    public TranslateTransition translateTransition;
    public Point2D playerVelocity = new Point2D(0, 0);
    private BONUS_TYPE type;
    private int width = 30;
    private int height = 30;
    private boolean isCatched = false;
    private ImageView bonusIV = new ImageView(bonus);
    private ParallelTransition parallelTransition;
    private FramesAnimation animation;

    Bonus(BONUS_TYPE mType, double x, double y)
    {
        type=mType;
        translateTransition =
                new TranslateTransition(Duration.millis(20000), this);
        translateTransition.setFromY(y);
        translateTransition.setToY(600);
        translateTransition.setCycleCount(1);
        bonusIV.setViewport(new Rectangle2D(0, 0, width, height));
        bonusIV.setX(x);
        bonusIV.setY(y);
        //bonusIV.setFitWidth(30);
        //bonusIV.setFitHeight(30);
        switch(type)
        {
            case FROZEN: {


                //bonusIV.setVisible(false);
                animation = new FramesAnimation(bonusIV, Duration.millis(200),2,1,0,0,width,height);
                break;
            }
            case LIFE:
            {
                animation = new FramesAnimation(bonusIV, Duration.millis(200), 2, 1, 30, 0, width, height);
                break;
            }
            case SPEED:
            {
                animation = new FramesAnimation(bonusIV, Duration.millis(200), 2, 1, 60, 0, width, height);
                break;
            }
            case LONG_PADDLE: {
                animation = new FramesAnimation(bonusIV, Duration.millis(200), 2, 1, 90, 0, width, height);
                break;
            }
            case NO_BONUS: {
                bonusIV.setViewport(new Rectangle2D(0, 0, 0, 0));
                //bonusIV=null;

                animation = new FramesAnimation(bonusIV, Duration.millis(0), 2, 1, 0, 0, width, height);
                //translateTransition.setToY(y);
                break;
            }
        }
        this.getChildren().addAll(bonusIV);
        parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                translateTransition,
                animation
        );
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        this.translateYProperty().addListener((observable, oldValue, newValue) -> {
            if (testPaddleCollision()) {
                parallelTransition.stop();
                this.setVisible(false);
                isCatched = true;
            }
        });
    }

    boolean isExist() {
        return this.type != BONUS_TYPE.NO_BONUS;
    }

    private boolean testPaddleCollision() {
        return this.getBoundsInParent().intersects(Game.player.getBoundsInParent());
    }

    public void play() {
        if (this.isExist()) {
            this.setVisible(true);
            this.parallelTransition.play();
        }
    }

    enum BONUS_TYPE {FROZEN, LIFE, SPEED, LONG_PADDLE, NO_BONUS}

}
