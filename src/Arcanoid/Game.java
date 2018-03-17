package Arcanoid;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

import static Arcanoid.Block.createBlock;

/**
 * Class, that contains main game mechanic
 */
public class Game {
    private static final double BALL_VELOCITY = 1;
    /**
     * Container for storing elements (blocks) for the current level
     */
    public static ArrayList<Block> blocks = new ArrayList<>();
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private static boolean running = false;
    private static Image backgroundImg = new Image("java.png");
    public static Ball ball;
    private static final int BLOCK_SIZE = 31;
    /**
     * Containers of elements of the main node and game content
     */
    private Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();
    private ImageView backgroundIV;

    private Text scorefield;
    private Text lifefield;
    private Text lives;
    /**
     * Main group of the scene
     */
    private Group root = new Group();
    private Text score;
    private int Life;
    private AnimationTimer timer;
    public static Paddle player;
    public static int levelNumber = 0;
    private final int colvoOfLevels = 2;
    /**
     * Game scene
     */
    private Scene scene = null;

    /**
     * Initialization content on the scene
     *
     * @return Parent
     */
    private Parent initContent() {
        backgroundIV = new ImageView(backgroundImg);
        backgroundIV.autosize();
        backgroundIV.setLayoutY(-(600 * (3 - levelNumber)));

        System.out.println(levelNumber);
        Create_blocks(Level_data.levels[levelNumber]);

        scorefield = new Text("Your score:");
        scorefield.setX(700);
        scorefield.setY(50);
        score = new Text("0");
        score.setX(760);
        score.setY(50);
        lifefield = new Text("Your life:");
        lifefield.setX(700);
        lifefield.setY(65);
        lives = new Text("3");
        lives.setX(760);
        lives.setY(65);
        Life = 5;
        player = new Paddle(360, 500);
        ball = new Ball(400, 490);
        gameRoot.getChildren().add(ball);
        gameRoot.getChildren().add(player);
        appRoot.setPrefSize(800, 600);
        appRoot.setMaxSize(800, 600);
        appRoot.getChildren().addAll(backgroundIV, scorefield, score, lifefield, lives, gameRoot);
        return appRoot;
    }

    private void scrollBackgroundIV() {
        int step = 1;                                         //doesnt work finally
        /*double current = backgroundIV.getLayoutY();
        //ScrollPane pane= new ScrollPane();
        while (current != (-600 * (3 - levelNumber))) {
            // try {
            //     Thread.sleep(30);
            current = backgroundIV.getLayoutY();
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            backgroundIV.setLayoutY(current + step);
        }*/
        backgroundIV.setLayoutY(-600 * (3 - levelNumber));
    }

    private void goToNewLevel() {
        if (levelNumber < colvoOfLevels) {
            levelNumber++;
            scrollBackgroundIV();
            Create_blocks(Level_data.levels[levelNumber]);
        } else {
            levelNumber = 0;
            Main.SetWindow_Scene(Main.Menu_screen);
        }

    }

    public static boolean isRunning() {
        return running;
    }

    /**
     * The method for creating on the panel of blocks the current level
     *
     * @param strings strings contain level_data
     */
    private void Create_blocks(String[] strings) {

        for (int i = 0; i < Level_data.levels[levelNumber].length; i++) {
            String line = Level_data.levels[levelNumber][i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        blocks.add(createBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, Block.BlockType.BUBBLE));
                        break;
                    case '2':
                        blocks.add(new Block(j * BLOCK_SIZE, i * BLOCK_SIZE));
                        break;
                    case '3':
                        blocks.add(createBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, Block.BlockType.BONUS));
                        break;
                    case '4':
                        blocks.add(createBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, Block.BlockType.BRICK));
                        break;
                    case '5':
                        blocks.add(createBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, Block.BlockType.STONE));
                        break;
                    case '6':
                        blocks.add(createBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, Block.BlockType.DIAMAND));
                        break;
                    case '7':
                        blocks.add(createBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, Block.BlockType.UNBREAKABLE_BLOCK));
                        break;
                }

            }
        }

    }

    /**
     * Main process, that checks updates and call the redrawing methods
     */
    private void gamePlaying() {
        int i = 0;
        if (Life == 0) {
            timer.stop();
            restart();
            keys.clear();
            running = false;
            Main.SetWindow_Scene(Main.Menu_screen);
        }
        if (blocks.size() == 0) {           //checking wining
            goToNewLevel();
        }
        while (i < blocks.size()) {
            Block buffer = blocks.get(i);
            if (buffer.isDestroyed()) {     //checking blocks sre destroyed
                buffer.bonus.parallelTransition.play();
                blocks.remove(buffer);
                int your_score = Integer.parseInt(score.getText());
                score.setText(Integer.toString(your_score + 1));
            } else
                i++;
        }
        if (ball.update(player))             //main redraw the ball
            decLife();
        if (ball.testBallNPaddleCollision()) {//special redraw the ball
            ball.intersectWithPaddle();
        }

    }

    /**
     * Method interaction with gamer
     */
    private void update() {
        gamePlaying();
        if (isPressed(KeyCode.ESCAPE)) {
            keys.clear();
            timer.stop(); //working!
            Main.SetWindow_Scene(Main.Menu_screen);

        }
        if (isPressed(KeyCode.PAUSE)) {
            System.out.println(running);
        }
        if (isPressed(KeyCode.LEFT)) {
            player.moveLeft();
            player.update();
        } else if (isPressed(KeyCode.RIGHT)) {
            player.moveRight();
            player.update();
        } else
            player.stopMove();

    }

    /**
     * Restarting the game
     */
    public void restart() {
        ball.goToStartPosition();
        player.goToStartPosition();
        score.setText("0");
        lives.setText("5");
        Life = 5;
        int i = 0;
        while (i < blocks.size()) {
            Block buffer = blocks.get(i);
            buffer.setVisible(false);
            //blocks.remove(buffer);
            gameRoot.getChildren().remove(buffer);
            i++;
        }
        blocks.clear();
        Create_blocks(Level_data.levels[levelNumber]);
        scrollBackgroundIV();
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    /**
     * Set content on the scene
     *
     * @return Scene
     */
    public Scene set_scene() {
        initContent();
        root.getChildren().add(appRoot);
        scene = new Scene(root, 800, 600);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> {
            keys.put(event.getCode(), false);
            //player.stopMove();
        });
        return scene;
    }

    /**
     * Decrease the life
     */
    private void decLife() {
        Life--;
        ball.goToStartPosition();
        player.goToStartPosition();
        lives.setText(Integer.toString(Life));
    }

    /**
     * Game processing
     */
    public void Game_Processing() {
        running = true;
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

    public void AutoPlay() {
        player.setVelocity(Ball.BALL_VELOCITY);
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    Thread.sleep(10);
                    computerPlay();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }

    /**
     * Bot plays
     */
    private void computerPlay() {
        gamePlaying();
        if (isPressed(KeyCode.ESCAPE)) {
            keys.clear();
            timer.stop(); //working!
            Main.SetWindow_Scene(Main.Menu_screen);

        }
        if (ball.isMoving())
            player.setVelocity(Math.abs(ball.velocityX) - 0.1);
        if (ball.isMoveLeft()) {
            player.moveLeft();
            player.update();
        } else if (ball.isMoveRight()) {
            player.moveRight();
            player.update();
        } else {
            player.moveRight();
            player.update();
        }
    }
}

