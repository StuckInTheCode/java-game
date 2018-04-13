package Arcanoid;

import Savings.GameRecord;
import Savings.GameRecordElement;
import Savings.GameSavings;
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
import java.util.Date;
import java.util.HashMap;

import static Arcanoid.Block.createBlock;

/**
 * Class, that contains main game mechanic
 */
public class Game {
    private static final double BALL_VELOCITY = 1;
    private static final int BLOCK_SIZE = 31;
    public static Paddle player;
    public static GameRecord records = new GameRecord();
    private static final BonusManager bonusManager = new BonusManager();

    public static long first_time;
    public static String level[];
    //final Date currentDate = new Date();
    public static ArrayList<Block> blocks = new ArrayList<>();
    public static ArrayList<Bonus> bonuses = new ArrayList<>();
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();

    private static boolean running = false;
    //public static boolean reloading = false;

    private static Image backgroundImg = new Image("java.png");

    public static Ball ball;
    /**
     * Container for storing elements (blocks) for the current level
     */

    public GameSavings savings = new GameSavings();

    /**
     * Containers of elements of the main node and game content
     */
    private Pane appRoot = new Pane();
    public static Pane npsRoot = new Pane();
    private Pane gameRoot = new Pane();
    public int Score;
    /**
     * Main group of the scene
     */
    private Group root = new Group();
    private Text score;
    private Text scorefield;

    public Date time;

    public int Life;
    private Text lifefield;
    private Text lives;

    /**
     * Game scene
     */
    public Scene scene = null;
    private ImageView backgroundIV;
    private AnimationTimer timer;


    public static int levelNumber = 0;
    private int colvoOfLevels = 2;


    /**
     * Initialization content on the scene
     *
     * @return Parent
     */
    private Parent initContent() {
        backgroundIV = new ImageView(backgroundImg);
        backgroundIV.setFitHeight(77 * BLOCK_SIZE);
        backgroundIV.setFitWidth(26 * BLOCK_SIZE);
        System.out.println(levelNumber);
        Create_blocks(Level_data.levels[levelNumber]);
        level = Level_data.levels[levelNumber];
        //savings.LEVEL=Level_data.levels[levelNumber];
        backgroundIV.setLayoutY(-(600 * (3)));
        scorefield = new Text("Your score:");
        scorefield.setX(700);
        scorefield.setY(50);
        score = new Text("0");
        score.setX(760);
        score.setY(50);
        lifefield = new Text("Your life:");
        //lifefield.setFont();
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
        appRoot.getChildren().addAll(backgroundIV, scorefield, score, lifefield, lives, gameRoot, npsRoot);
        return appRoot;
    }

    private void scrollBackgroundIV() {
        //double step=0.05;                                    //okay speed?
        double step = 0.5;
        double currentLayoutBack = backgroundIV.getLayoutY();
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Consumer<? super Block> consumer = block->block.moveDown(step);
        Game.blocks.forEach(block -> block.setTranslateY(block.getTranslateY() + step));
        backgroundIV.setLayoutY(currentLayoutBack + step);
        //for (Block block : Game.blocks) {
        //    block.setTranslateY(block.getTranslateY()+step);
        // }
    }

    private void goToNewLevel() {
        if (levelNumber < colvoOfLevels) {
            levelNumber++;
            //scrollBackgroundIV();
            backgroundIV.setLayoutY(-600 * (3 - levelNumber));
            Create_blocks(Level_data.levels[levelNumber]);
        } else {
            levelNumber = 0;
            Main.SetWindow_Scene(Main.Menu_screen);
        }

    }

    private void goToLevel(int levelNum) {
        backgroundIV.setLayoutY(-600 * (3 - levelNum));
        int i = 0;
        while (i < blocks.size()) {
            Block buffer = blocks.get(i);
            buffer.setVisible(false);
            gameRoot.getChildren().remove(buffer);
            i++;
        }
        blocks.clear();
        Create_blocks(Level_data.levels[levelNum]);
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

        for (int i = 0; i < strings.length; i++) {
            String line = strings[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        createBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, Block.BlockType.BUBBLE);
                        break;
                    case '2':
                        createBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, Block.BlockType.CRYSTALL);
                        break;
                    case '3':
                        createBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, Block.BlockType.BONUS);
                        break;
                    case '4':
                        createBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, Block.BlockType.BRICK);
                        break;
                    case '5':
                        createBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, Block.BlockType.STONE);
                        break;
                    case '6':
                        createBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, Block.BlockType.DIAMAND);
                        break;
                    case '7':
                        createBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, Block.BlockType.UNBREAKABLE_BLOCK);
                        break;
                }

            }
        }
    }

    /**
     * Main process, that checks updates and call the redrawing methods
     */
    private void gamePlaying() {
        //scrollBackgroundIV();
        int i = 0;
        if (Life == 0) {
            timer.stop();
            Scores s = new Scores("player", Integer.parseInt(score.getText()));
            Main.playerScores.add(s);
            ScoreTableItem e = new ScoreTableItem(s, Main.playerScores.size());
            Main.scoresTable.getChildren().add(e);
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
            if (buffer.isDestroyed()) {     //checking blocks are destroyed
                buffer.setVisible(false);
                int rand = (int) (Math.random() * 10);
                System.out.println(rand);
                if (rand > 6) {
                    bonuses.add(buffer.bonus);
                    buffer.bonus.play();
                }
                int currentLine = (int) (buffer.getY() / BLOCK_SIZE);
                String line = level[currentLine];
                char[] charline = line.toCharArray();
                charline[(int) (buffer.getX() / BLOCK_SIZE)] = '0';
                level[currentLine] = String.valueOf(charline);
                //savings.LEVEL = level;
                blocks.remove(buffer);
                Score++;
                // your_score = Integer.parseInt(score.getText());
                score.setText(Integer.toString(Score));
            } else
                i++;
        }
        for (Bonus e : bonuses) {
            if (e.isCatched)
                bonusManager.addBuff(new BonusEffect(i, e.getType(), 50000));
            if (e.isFallen)
                bonuses.remove(e);
        }
        bonusManager.updateBuffs();
        if (ball.update(player))             //main redraw the ball
            decLife();
        if (ball.testBallNPaddleCollision()) {//special redraw the ball
            ball.intersectWithPaddle();
        }

    }

    /**
     * Method interaction with gamer
     */
    private void update(long now) {
        gamePlaying();
        //time = new Date();
        if (isPressed(KeyCode.ESCAPE)) {
            keys.clear();
            //records.last_time = now;
            timer.stop();//working!
            Main.SetWindow_Scene(Main.Menu_screen);

        }
        if (isPressed(KeyCode.PAUSE)) {
            System.out.println(running);
        }
        if (isPressed(KeyCode.LEFT)) {
            //records.add(KeyCode.LEFT,time.getTime()-currentDate.getTime());
            //records.add();
            //records.last_time=now-records.first_time;
            player.moveLeft();
            player.update();
        } else if (isPressed(KeyCode.RIGHT)) {
            //records.add(KeyCode.RIGHT,time.getTime()-currentDate.getTime());
            //records.add();
            //records.last_time=now-records.first_time;
            player.moveRight();
            player.update();
        } else {

            player.stopMove();
        }
        records.add();

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
            gameRoot.getChildren().remove(buffer);
            i++;
        }
        blocks.clear();
        Create_blocks(Level_data.levels[levelNumber]);
        scrollBackgroundIV();
    }

    public void loadSavings(GameSavings savings) {
        ball.setTranslateX(savings.ballX);
        ball.setTranslateY(savings.ballY);
        ball.velocityX = savings.ball_velocityX;
        ball.velocityY = savings.ball_velocityY;
        player.setTranslateX(savings.playerX);
        player.setTranslateY(savings.playerY);

        Life = savings.current_life;
        Score = savings.current_score;
        score.setText(Integer.toString(Score));
        lives.setText(Integer.toString(Life));
        int i = 0;
        while (i < blocks.size()) {
            Block buffer = blocks.get(i);
            buffer.setVisible(false);
            gameRoot.getChildren().remove(buffer);
            i++;
        }
        blocks.clear();
        level = savings.LEVEL;

        //goToLevel();
        //Create_blocks(Level_data.levels[levelNumber]);
        Create_blocks(savings.LEVEL);
        /*int i = 0;
        while (i < blocks.size()) {
            Block buffer = blocks.get(i);
            buffer.setVisible(false);
            gameRoot.getChildren().remove(buffer);
            i++;
        }*/
        //blocks.clear();
        //blocks=savings.blocks;
        //Create_blocks(Level_data.levels[levelNumber]);
        //scrollBackgroundIV();
    }

    private void loadRecord(GameRecord record, int i) {
        GameRecordElement e = records.record.get(i);
        ball.setTranslateX(e.ballX);
        ball.setTranslateY(e.ballY);
        ball.velocityX = e.ball_velocityX;
        ball.velocityY = e.ball_velocityY;
        player.setTranslateX(e.playerX);
        player.setTranslateY(e.playerY);

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
        System.out.println(running + "main");
        time = new Date();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    //if (records.first_time == 0)
                    //    records.first_time = now;
                    Thread.sleep(10);
                    update(now);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }

    public void AutoPlay() {
        running = true;
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

    private void updateReloading(long now) {
        gamePlaying();
       /* if (records.record.containsKey(now - first_time)) {
            //if(records.record.get(time.getTime()-currentDate.getTime())==KeyCode.LEFT)
            if (records.record.get(now - first_time) == KeyCode.LEFT) {
                player.moveLeft();
                player.update();
            } else {
                player.moveRight();
                player.update();
            }
        }*/

    }

    public void reloadTheGame() {
        running = true;
        System.out.println(running + "reload");
        int i = 0;
        while (i < blocks.size()) {
            Block buffer = blocks.get(i);
            buffer.setVisible(false);
            gameRoot.getChildren().remove(buffer);
            i++;
        }
        blocks.clear();
        levelNumber = records.current_level;
        //goToLevel();
        Create_blocks(Level_data.levels[levelNumber]);
        //Create_blocks(savings.LEVEL);
        //goToLevel(records.current_level);
        //Date current_time = new Date();

        //time = new Date();
        //Timer timer1= new Timer();
        //timer1.
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //try {
                //if (first_time == 0)
                first_time++;
                loadRecord(records, (int) first_time);
                //Thread.sleep(10);
                //updateReloading(now);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}

            }
        };
        timer.start();
    }
}

