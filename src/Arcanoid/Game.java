package Arcanoid;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class, that contains main game mechanic
 */
public  class Game {
    private static final double BALL_VELOCITY = 1;
    /**Container for storing elements (blocks) for the current level*/
    private ArrayList<Block> blocks = new ArrayList<>();
    private HashMap<KeyCode,Boolean> keys = new HashMap<>();

    private static boolean  running=true;
    private static Image backgroundImg = new Image("java.png");
    ImageView backgroundIV;
    private static final int BLOCK_SIZE = 31;
    /** Containers of elements of the main node and game content */
	private static Pane appRoot = new Pane();
	public static Pane gameRoot = new Pane();
	/**Game scene*/
	private static Text scorefield;
    private static Text score = new Text("0");
	private static Scene scene;
    private static AnimationTimer timer;
	private Ball ball;
    private Paddle player;
    private int levelNumber = 0;

    /**Initialization content on the scene
     *
     * @return Parent
     */
    private Parent initContent(){
        backgroundIV = new ImageView(backgroundImg);

        backgroundIV.autosize();

        backgroundIV.setLayoutY(-(600*(3-levelNumber)));

        Create_blocks(Level_data.levels[levelNumber]);

        scorefield = new Text("Your score:");
        scorefield.setX(700);
        scorefield.setY(50);
        score.setX(760);
        score.setY(50);

        player = new Paddle(360,500);
        ball= new Ball(400,490);

        gameRoot.getChildren().add(player);
        appRoot.setPrefSize(800,600);
        appRoot.setMaxSize(800,600);
        appRoot.getChildren().addAll(backgroundIV,scorefield,score,gameRoot);
        return appRoot;
    }

    private void scrollBackgroundIV() {
        int step = 1;                                         //doesnt work finally
        double current = backgroundIV.getLayoutY();
        //ScrollPane pane= new ScrollPane();
        while (current < (-600 * (3 - levelNumber))) {
            // try {
            //     Thread.sleep(30);
            current = backgroundIV.getLayoutY();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println(current);
            backgroundIV.setLayoutY(current + step);
        }
    }

    private void goToNewLevel() {
        levelNumber++;
        scrollBackgroundIV();
        Create_blocks(Level_data.levels[levelNumber]);

    }
   private boolean isIntersectingPaddleBall(Paddle pl, Ball bk) {
       if(bk.getCenterY()+bk.getTranslateY()>=pl.getY())
        if(bk.getCenterX()+bk.getTranslateX()<=pl.getX()+pl.getTranslateX()+pl.getWidth() &&
                bk.getCenterX()+bk.getTranslateX()>= pl.getX()+pl.getTranslateX())
           return true;
       return false;
    }
    private boolean isIntersectingBlockBall(Block bl, Ball bk) {
        double overlapXLeft=Math.abs(bk.getCenterX()+bk.getTranslateX()- bl.getX());
        double overlapXRight=Math.abs(bk.getCenterX()+bk.getTranslateX()- bl.getX()-bl.getWidth());
        double overlapYTop=Math.abs(bk.getCenterY()+bk.getTranslateY()-bl.getY());
        double overlapYBottom=Math.abs(bk.getCenterY()+bk.getTranslateY()-bl.getY()-bl.getHeight());
        double minimalIntersection=bk.getRadius();
        if((overlapXLeft<=minimalIntersection||overlapXRight<=minimalIntersection) &&
                (overlapYTop<=minimalIntersection || overlapYBottom<=minimalIntersection)){ //if(overlapYTop<=minimalIntersection || overlapYBottom<=minimalIntersection)
            return true;
        }
        return false;
    }

    private void testCollision(Block mBlock, Ball mBall) {
        if (!isIntersectingBlockBall(mBlock, mBall))
            return;

        mBlock.destroyed = true;

        mBall.velocityX=-mBall.velocityX;
        mBall.velocityY=-mBall.velocityY;
    }

    /**The method for creating on the panel of blocks the current level
     *
     * @param strings
     * strings contain level_data
     */
	private void Create_blocks(String[] strings) {
		
        for(int i = 0; i < Level_data.levels[levelNumber].length; i++){
            String line = Level_data.levels[levelNumber][i];
            for(int j = 0; j < line.length();j++){
                switch (line.charAt(j)){
                    case '0':
                        break;
                    case '1':
                    	blocks.add(new Block (j*BLOCK_SIZE, i*BLOCK_SIZE));
                        break;
            }

        }
        }
	
	}
    private void update(){
	    int i=0;
	    if(blocks.size()==0)
        {
            goToNewLevel();
        }
        while(i<blocks.size())
        {
            Block buffer=blocks.get(i);
            testCollision(buffer, ball);
            if(buffer.isDestroyed())
            {
                buffer.setVisible(false);
                blocks.remove(buffer);
                int your_score=Integer.parseInt(score.getText());
                score.setText(Integer.toString(your_score+1));
            }
            else
                i++;
        }
        if(isIntersectingPaddleBall(player,ball))
        {
            ball.velocityY=-ball.velocityY;
            if((player.isMoveLeft() && ball.isMoveRight() )|| (player.isMoveRight() && ball.isMoveLeft()))
                ball.velocityX -= 0.2;
            if((player.isMoveLeft() && ball.isMoveLeft() )|| (player.isMoveRight() && ball.isMoveRight()))
                ball.velocityX += 0.2;
        }
	    ball.update(player);

        if (isPressed(KeyCode.ESCAPE)) {
            //timer.stop();
            keys.clear();
            Main.SetWindow_Scene(Main.Menu_screen);

        }
        if (isPressed(KeyCode.PAUSE))
        {
            //running=false;
            //player.stopMove();
            System.out.println(running);
        }
        if(isPressed(KeyCode.LEFT)){
            System.out.println("Pressed left!");
            System.out.println("I've been here");
            System.out.println(player.getTranslateX());
            player.moveLeft();
            player.update();
            System.out.println("And now");
            System.out.println(player.getTranslateX());
        }
        else if(isPressed(KeyCode.RIGHT)){
            System.out.println("Pressed Right!");
            System.out.println("I've been here");
            System.out.println(player.getTranslateX());
           player.moveRight();
            player.update();
            System.out.println("And now");
            System.out.println(player.getTranslateX());
        }
        else
            player.stopMove();

    }




    private boolean isPressed(KeyCode key){
        return keys.getOrDefault(key,false);
    }
    /**Set content on the scene
     *
     * @return Scene
     * @throws Exception
     */
    public  Scene  set_scene() throws Exception {
        initContent();

        scene = new Scene(appRoot,800,600);
        scene.setOnKeyPressed(event-> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> {
            keys.put(event.getCode(), false);
            //player.stopMove();
        });
        return scene;
    }

    /*public  Scene  getScene() {
        return scene;
    }*/
    /**Game processing
     *
     */
    public void Game_Processing()
    {

        System.out.println(running);
        timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            try {
                Thread.sleep(10);
                update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        };
        timer.start();
    }
}

