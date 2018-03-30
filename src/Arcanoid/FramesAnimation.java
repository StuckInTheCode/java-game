package Arcanoid;

//import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.Serializable;

public class FramesAnimation extends Transition implements Serializable {
    private final ImageView imageView;
    private final int count;
    private final int colomns;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;

    public FramesAnimation
            (
            ImageView imageView,
            Duration duration,
            int count, int colomns,
            int offsetX, int offsetY,
            int width, int height
    )
    {
        this.imageView=imageView;
        this.count=count;
        this.colomns=colomns;
        this.offsetX=offsetX;
        this.offsetY=offsetY;
        this.width=width;
        this.height=height;
        this.setCycleDuration(duration);
        this.setAutoReverse(true);
        this.setCycleCount(INDEFINITE);
    }

    @Override
    protected void interpolate(double frac) {
        final int index = Math.min((int)Math.floor(frac*count),count-1);
        final int y = (index % colomns) * width + offsetX;
        final int x = (index / colomns) * height + offsetY;
        imageView.setViewport(new Rectangle2D(x,y,width,height));
    }
}
